package me.guanming.vehicle.infolib;

import android.util.Base64;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;

/**
 * 简化的 ADB 二进制协议客户端。
 *
 * <p>实现 A_CNXN/A_AUTH/A_OPEN/A_WRTE/A_OKAY/A_CLSE 消息，
 * 通过 TCP 127.0.0.1:5555 连接车机本机 ADB。
 *
 * <p>可通过 {@link Vehicle#getAdb()} 获取已连接的实例，
 * 复用 init 时建立的 ADB 连接执行任意 shell 命令。
 *
 * <p>使用示例：
 * <pre>
 *   VehicleAdbClient adb = Vehicle.getAdb();
 *   if (adb != null) {
 *       String output = adb.shellQuiet("getprop ro.product.model", 10000);
 *   }
 * </pre>
 */
public final class VehicleAdbClient {

    private static final String TAG = "VehicleAdbClient";

    private static final int CMD_CNXN = 0x4e584e43;   // "CNXN"
    private static final int CMD_AUTH = 0x48545541;   // "AUTH"
    private static final int CMD_OPEN = 0x4e45504f;   // "OPEN"
    private static final int CMD_OKAY = 0x59414b4f;   // "OKAY"
    private static final int CMD_WRTE = 0x45545257;   // "WRTE"
    private static final int CMD_CLSE = 0x45534c43;   // "CLSE"

    private static final int ADB_VERSION = 0x01000001;
    private static final int MAX_PAYLOAD = 262144;

    private final KeyPair keyPair;
    private final VehicleLogger logger;
    private Socket socket;
    private DataInputStream input;
    private OutputStream output;
    private int localIdSeq = 1;

    VehicleAdbClient(KeyPair keyPair, VehicleLogger logger) {
        this.keyPair = keyPair;
        this.logger = logger;
    }

    /** 是否已连接 */
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    /** 连接本机 ADB（127.0.0.1:5555） */
    public synchronized boolean connect() {
        return connect("127.0.0.1", 5555);
    }

    /** 连接指定 host:port 的 ADB */
    public synchronized boolean connect(String host, int port) {
        if (isConnected()) return true;
        try {
            close();
            log("I", "connect: opening socket to " + host + ":" + port);
            Socket s = new Socket(host, port);
            s.setSoTimeout(30000);
            s.setTcpNoDelay(true);
            socket = s;
            input = new DataInputStream(s.getInputStream());
            output = s.getOutputStream();

            byte[] payload = ("host::\u0000").getBytes(StandardCharsets.UTF_8);
            writeMessage(CMD_CNXN, ADB_VERSION, MAX_PAYLOAD, payload);

            AdbMessage resp = readMessage();
            log("I", "connect: recv cmd=0x" + Integer.toHexString(resp.cmd)
                    + " arg0=" + resp.arg0 + " arg1=" + resp.arg1);

            switch (resp.cmd) {
                case CMD_CNXN:
                    log("I", "Connected (no auth required)");
                    return true;
                case CMD_AUTH:
                    if (resp.arg0 != 1) {
                        log("E", "Unexpected AUTH arg0=" + resp.arg0);
                        close();
                        return false;
                    }
                    return handleAuth(resp.payload);
                default:
                    log("E", "Unexpected handshake response: 0x" + Integer.toHexString(resp.cmd));
                    close();
                    return false;
            }
        } catch (Exception e) {
            log("W", "connect failed: " + e.getMessage());
            close();
            return false;
        }
    }

    private boolean handleAuth(byte[] token) {
        try {
            byte[] prefix = new byte[]{
                    0x30, 0x21, 0x30, 0x09, 0x06, 0x05, 0x2b, 0x0e, 0x03, 0x02, 0x1a, 0x05, 0x00, 0x04, 0x14
            };
            byte[] data = new byte[prefix.length + token.length];
            System.arraycopy(prefix, 0, data, 0, prefix.length);
            System.arraycopy(token, 0, data, prefix.length, token.length);

            Signature sig = Signature.getInstance("NONEwithRSA");
            sig.initSign(keyPair.getPrivate());
            sig.update(data);
            byte[] sigBytes = sig.sign();
            writeMessage(CMD_AUTH, 2, 0, sigBytes);
            AdbMessage r = readMessage();
            if (r.cmd == CMD_CNXN) {
                log("I", "Connected via signature auth");
                return true;
            }
            if (r.cmd != CMD_AUTH || r.arg0 != 1) {
                log("E", "Auth failed after signature: 0x" + Integer.toHexString(r.cmd));
                close();
                return false;
            }
        } catch (Exception e) {
            log("W", "Signature auth failed: " + e.getMessage());
        }
        return sendPublicKey();
    }

    private boolean sendPublicKey() {
        try {
            RSAPublicKey pub = (RSAPublicKey) keyPair.getPublic();
            BigInteger modulus = pub.getModulus();
            BigInteger one = BigInteger.ONE;
            BigInteger mask32 = one.shiftLeft(32).subtract(one);
            BigInteger r32 = one.shiftLeft(32);

            BigInteger rMod = modulus.and(mask32).modInverse(r32).negate().mod(r32);
            BigInteger r4096 = one.shiftLeft(4096).mod(modulus);

            ByteBuffer buf = ByteBuffer.allocate(524).order(ByteOrder.LITTLE_ENDIAN);
            buf.putInt(64);
            buf.putInt(rMod.intValue());
            BigInteger m = modulus;
            for (int i = 0; i < 64; i++) {
                buf.putInt(m.and(mask32).intValue());
                m = m.shiftRight(32);
            }
            BigInteger rWord = r4096;
            for (int i = 0; i < 64; i++) {
                buf.putInt(rWord.and(mask32).intValue());
                rWord = rWord.shiftRight(32);
            }
            buf.putInt(pub.getPublicExponent().intValue());

            String b64 = Base64.encodeToString(buf.array(), Base64.NO_WRAP);
            byte[] payload = (b64 + " vehicle-infolib@android\u0000").getBytes(StandardCharsets.UTF_8);
            log("I", "Sending RSA public key for device approval (" + payload.length + "B)");
            writeMessage(CMD_AUTH, 3, 0, payload);

            socket.setSoTimeout(60000);
            AdbMessage authResp = readMessage();
            socket.setSoTimeout(30000);
            if (authResp.cmd == CMD_CNXN) {
                log("I", "Connected via public key auth (user accepted)");
                return true;
            }
            log("E", "Public key auth failed: 0x" + Integer.toHexString(authResp.cmd));
            close();
            return false;
        } catch (Exception e) {
            log("E", "sendPublicKey failed: " + e.getMessage());
            close();
            return false;
        }
    }

    /**
     * 执行 shell 命令并返回输出。
     *
     * @param cmd       要执行的命令
     * @param timeoutMs 超时（毫秒）
     * @return 命令的标准输出（stdout+stderr 合并）
     * @throws IOException 连接断开或超时
     */
    public synchronized String shell(String cmd, int timeoutMs) throws IOException {
        int oldTimeout = socket != null ? socket.getSoTimeout() : 30000;
        if (socket != null) socket.setSoTimeout(timeoutMs);
        try {
            return shellInternal(cmd);
        } finally {
            if (socket != null) {
                try {
                    socket.setSoTimeout(oldTimeout);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private String shellInternal(String cmd) throws IOException {
        int localId = localIdSeq++;
        byte[] openPayload = ("shell:" + cmd + "\u0000").getBytes(StandardCharsets.UTF_8);
        writeMessage(CMD_OPEN, localId, 0, openPayload);

        int remoteId = 0;
        boolean gotOkay = false;
        for (int i = 0; i < 20; i++) {
            AdbMessage msg = readMessage();
            if (msg.cmd == CMD_OKAY && msg.arg1 == localId) {
                remoteId = msg.arg0;
                gotOkay = true;
                break;
            }
            handleStrayMessage(msg);
        }
        if (!gotOkay) {
            throw new IOException("shell: no OKAY for localId=" + localId);
        }

        StringBuilder sb = new StringBuilder();
        int count = 0;
        while (count < 500) {
            AdbMessage msg = readMessage();
            count++;
            if (msg.cmd == CMD_WRTE && msg.arg0 == remoteId && msg.arg1 == localId) {
                sb.append(new String(msg.payload, StandardCharsets.UTF_8));
                writeMessage(CMD_OKAY, localId, remoteId, new byte[0]);
            } else if (msg.cmd == CMD_CLSE && msg.arg0 == remoteId) {
                writeMessage(CMD_CLSE, localId, remoteId, new byte[0]);
                break;
            } else {
                handleStrayMessage(msg);
            }
        }
        return sb.toString();
    }

    private void handleStrayMessage(AdbMessage msg) {
        try {
            if (msg.cmd == CMD_WRTE) {
                writeMessage(CMD_OKAY, msg.arg1, msg.arg0, new byte[0]);
            } else if (msg.cmd == CMD_CLSE) {
                writeMessage(CMD_CLSE, msg.arg1, msg.arg0, new byte[0]);
            }
        } catch (IOException ignored) {
        }
    }

    /** 关闭连接 */
    public synchronized void close() {
        try {
            if (socket != null) socket.close();
        } catch (Exception ignored) {
        }
        socket = null;
        input = null;
        output = null;
    }

    private void writeMessage(int cmd, int arg0, int arg1, byte[] payload) throws IOException {
        if (output == null) throw new IllegalStateException("not connected");
        ByteBuffer header = ByteBuffer.allocate(24).order(ByteOrder.LITTLE_ENDIAN);
        header.putInt(cmd);
        header.putInt(arg0);
        header.putInt(arg1);
        header.putInt(payload.length);
        int checksum = 0;
        for (byte b : payload) checksum += b & 0xff;
        header.putInt(checksum);
        header.putInt(cmd ^ 0xFFFFFFFF);
        output.write(header.array());
        if (payload.length > 0) output.write(payload);
        output.flush();
    }

    private AdbMessage readMessage() throws IOException {
        if (input == null) throw new IllegalStateException("not connected");
        byte[] header = new byte[24];
        input.readFully(header);
        ByteBuffer buf = ByteBuffer.wrap(header).order(ByteOrder.LITTLE_ENDIAN);
        int cmd = buf.getInt();
        int arg0 = buf.getInt();
        int arg1 = buf.getInt();
        int length = buf.getInt();
        int checksum = buf.getInt();
        int magic = buf.getInt();
        if (magic != (cmd ^ 0xFFFFFFFF)) {
            throw new IOException("Bad magic: 0x" + Integer.toHexString(magic));
        }
        byte[] payload = new byte[0];
        if (length > 0) {
            payload = new byte[length];
            input.readFully(payload);
        }
        return new AdbMessage(cmd, arg0, arg1, payload);
    }

    private static final class AdbMessage {
        final int cmd;
        final int arg0;
        final int arg1;
        final byte[] payload;

        AdbMessage(int cmd, int arg0, int arg1, byte[] payload) {
            this.cmd = cmd;
            this.arg0 = arg0;
            this.arg1 = arg1;
            this.payload = payload;
        }
    }

    private void log(String level, String msg) {
        if (logger != null) {
            logger.log(level, TAG, msg, null);
        }
    }

    /**
     * 静默执行 shell：捕获所有异常，超时/出错返回空串。
     *
     * @param cmd       要执行的命令
     * @param timeoutMs 超时（毫秒）
     * @return 命令输出，出错时返回空串
     */
    public String shellQuiet(String cmd, int timeoutMs) {
        try {
            return shell(cmd, timeoutMs);
        } catch (SocketTimeoutException e) {
            log("W", "shell timeout: " + e.getMessage() + " | cmd=" + cmd);
            return "";
        } catch (Exception e) {
            log("W", "shell error: " + e.getMessage() + " | cmd=" + cmd);
            return "";
        }
    }
}
