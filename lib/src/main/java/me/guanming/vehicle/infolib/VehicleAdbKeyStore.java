package me.guanming.vehicle.infolib;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * ADB RSA 密钥仓库（vehicle 包内部使用）。
 *
 * 内存缓存 + 文件持久化（PKCS8/X509 DER）+ 懒加载。
 * 首次启动生成 RSA 2048 并落盘到 {@code filesDir/adb_keys/}，
 * 后续启动直接复用，避免每次弹 ADB 授权框。
 */
final class VehicleAdbKeyStore {

    private static final String DIR_NAME = "adb_keys";
    private static final String PRIV_FILE = "adb_key.priv";
    private static final String PUB_FILE = "adb_key.pub";
    private static final int RSA_KEY_SIZE = 2048;

    private final Context ctx;
    private final VehicleLogger logger;
    private volatile KeyPair cache;

    VehicleAdbKeyStore(Context ctx, VehicleLogger logger) {
        this.ctx = ctx.getApplicationContext();
        this.logger = logger;
    }

    KeyPair get() {
        KeyPair kp = cache;
        if (kp != null) return kp;
        synchronized (this) {
            if (cache != null) return cache;
            try {
                cache = loadOrGenerate();
            } catch (Exception e) {
                throw new RuntimeException("Failed to load/generate ADB key pair", e);
            }
            return cache;
        }
    }

    private KeyPair loadOrGenerate() throws Exception {
        File dir = new File(ctx.getFilesDir(), DIR_NAME);
        File privFile = new File(dir, PRIV_FILE);
        File pubFile = new File(dir, PUB_FILE);

        if (privFile.exists() && pubFile.exists()) {
            log("I", "Loading existing ADB key pair from " + dir.getAbsolutePath());
            byte[] privBytes = readFile(privFile);
            byte[] pubBytes = readFile(pubFile);
            PrivateKey priv = KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(privBytes));
            PublicKey pub = KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(pubBytes));
            return new KeyPair(pub, priv);
        }

        log("I", "No existing key pair, generating new RSA " + RSA_KEY_SIZE + " key");
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalStateException("Cannot create directory: " + dir.getAbsolutePath());
        }
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(RSA_KEY_SIZE);
        KeyPair kp = gen.generateKeyPair();
        writeFile(privFile, kp.getPrivate().getEncoded());
        writeFile(pubFile, kp.getPublic().getEncoded());
        log("I", "Saved new key pair to " + dir.getAbsolutePath());
        return kp;
    }

    private static byte[] readFile(File f) throws Exception {
        byte[] data = new byte[(int) f.length()];
        try (FileInputStream in = new FileInputStream(f)) {
            int off = 0, n;
            while (off < data.length && (n = in.read(data, off, data.length - off)) > 0) {
                off += n;
            }
        }
        return data;
    }

    private static void writeFile(File f, byte[] data) throws Exception {
        try (FileOutputStream out = new FileOutputStream(f)) {
            out.write(data);
            out.flush();
        }
        f.setReadable(false, false);
        f.setReadable(true, true);
    }

    private void log(String level, String msg) {
        if (logger != null) {
            logger.log(level, "VehicleAdbKeyStore", msg, null);
        }
    }
}
