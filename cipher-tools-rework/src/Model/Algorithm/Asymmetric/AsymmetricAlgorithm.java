package Model.Algorithm.Asymmetric;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class AsymmetricAlgorithm {
    PublicKey publicKey;
    PrivateKey privateKey;
    static Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    static Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    public Cipher createInstance(int mode, PublicKey key) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        var cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(mode, key);
        return cipher;
    }

    public Cipher createInstance(int mode, PrivateKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        var cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(mode, key);
        return cipher;
    }

    // Lấy Private Key dưới dạng Base64
    public String getPrivateKeyAsString() {
        if (privateKey == null) {
            throw new IllegalStateException("Private key is not initialized");
        }
        return BASE64_ENCODER.encodeToString(privateKey.getEncoded());
    }

    // Lấy Public Key dưới dạng Base64
    public String getPublicKeyAsString() {
        if (publicKey == null) {
            throw new IllegalStateException("Public key is not initialized");
        }
        return BASE64_ENCODER.encodeToString(publicKey.getEncoded());
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    // Đặt Public Key từ chuỗi Base64
    public void setPublicKey(String base64EncodeKeyText) throws GeneralSecurityException {
        byte[] keyBytes = BASE64_DECODER.decode(base64EncodeKeyText);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.publicKey = keyFactory.generatePublic(keySpec);
    }

    // Đặt Private Key từ chuỗi Base64
    public void setPrivateKey(String base64EncodeKeyText) throws GeneralSecurityException {
        byte[] keyBytes = BASE64_DECODER.decode(base64EncodeKeyText);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey = keyFactory.generatePrivate(keySpec);
    }

    // Tạo cặp khóa RSA mới
    public KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    public String encryptText(String text, Class<? extends Key> clazz) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher;
        if (clazz == PublicKey.class) {
            if (publicKey == null) throw new IllegalStateException("Public key is not initialized");
            cipher = createInstance(Cipher.ENCRYPT_MODE, publicKey);
        } else if (clazz == PrivateKey.class) {
            if (privateKey == null) throw new IllegalStateException("Private key is not initialized");
            cipher = createInstance(Cipher.ENCRYPT_MODE, privateKey);
        } else {
            throw new IllegalArgumentException("Unsupported key type: " + clazz.getName());
        }

        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
        return BASE64_ENCODER.encodeToString(encryptedBytes);
    }

    public String decryptText(String text, Class<? extends Key> clazz) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher;
        if (clazz == PublicKey.class) {
            if (publicKey == null) throw new IllegalStateException("Public key is not initialized");
            cipher = createInstance(Cipher.DECRYPT_MODE, publicKey);
        } else if (clazz == PrivateKey.class) {
            if (privateKey == null) throw new IllegalStateException("Private key is not initialized");
            cipher = createInstance(Cipher.DECRYPT_MODE, privateKey);
        } else {
            throw new IllegalArgumentException("Unsupported key type: " + clazz.getName());
        }

        byte[] decryptedBytes = cipher.doFinal(BASE64_DECODER.decode(text));
        return new String(decryptedBytes);
    }

    public void savePublicKey(String filePath) throws IOException {
        if (publicKey == null) {
            throw new IllegalStateException("Public key is not initialized");
        }

        // Mã hóa Public Key sang Base64
        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        // Ghi vào file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Public key:\n");
            writer.write(publicKeyBase64);
        }
    }

    public void savePrivateKey(String filePath) throws IOException {
        if (privateKey == null) {
            throw new IllegalStateException("Private key is not initialized");
        }

        // Mã hóa Private Key sang Base64
        String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        // Ghi vào file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Private key:\n");
            writer.write(privateKeyBase64);
        }
    }

    public void loadPublicKey(String filePath) throws IOException, GeneralSecurityException {
        // Đọc toàn bộ nội dung file
        String content = Files.readString(Path.of(filePath));

        // Xử lý để lấy chuỗi Base64 của Public Key
        if (!content.startsWith("Public key:")) {
            throw new IllegalArgumentException("Invalid public key file format");
        }

        String publicKeyBase64 = content.replace("Public key:", "").trim();

        // Giải mã Public Key từ Base64
        byte[] publicKeyBytes = BASE64_DECODER.decode(publicKeyBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

        // Gán Public Key
        this.publicKey = keyFactory.generatePublic(publicKeySpec);
    }

    public void loadPrivateKey(String filePath) throws IOException, GeneralSecurityException {
        // Đọc toàn bộ nội dung file
        String content = Files.readString(Path.of(filePath));

        // Xử lý để lấy chuỗi Base64 của Private Key
        if (!content.startsWith("Private key:")) {
            throw new IllegalArgumentException("Invalid private key file format");
        }

        String privateKeyBase64 = content.replace("Private key:", "").trim();

        // Giải mã Private Key từ Base64
        byte[] privateKeyBytes = BASE64_DECODER.decode(privateKeyBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

        // Gán Private Key
        this.privateKey = keyFactory.generatePrivate(privateKeySpec);
    }

    public void loadKeyPair(String filePath) throws IOException, GeneralSecurityException {
        // Đọc toàn bộ nội dung file
        String content = Files.readString(Path.of(filePath));

        // Tách phần Public Key và Private Key
        String[] parts = content.split("(?m)^Private key:$");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid key file format");
        }

        // Lấy Public Key (phần trước "Private key:")
        String publicKeyBase64 = parts[0].replace("Public key:", "").trim();

        // Lấy Private Key (phần sau "Private key:")
        String privateKeyBase64 = parts[1].trim();

        // Giải mã Public Key
        byte[] publicKeyBytes = BASE64_DECODER.decode(publicKeyBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        this.publicKey = keyFactory.generatePublic(publicKeySpec);

        // Giải mã Private Key
        byte[] privateKeyBytes = BASE64_DECODER.decode(privateKeyBase64);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        this.privateKey = keyFactory.generatePrivate(privateKeySpec);
    }

    public static void main(String[] args) throws Exception {
        String privateKeyFile = "C:\\Users\\Nguyen Tuan\\Desktop\\privateKey.txt";
        String publicKeyFile = "C:\\Users\\Nguyen Tuan\\Desktop\\publicKey.txt";
        var instance = new AsymmetricAlgorithm();
        var keyPair = instance.generateKeyPair(4096);
        instance.privateKey = keyPair.getPrivate();
        instance.publicKey = keyPair.getPublic();

        // save key
        instance.savePrivateKey(privateKeyFile);
        instance.savePublicKey(publicKeyFile);

        // remove key
        instance.privateKey = null;
        instance.publicKey = null;

        // reload key
        instance.loadPublicKey(publicKeyFile);
        instance.loadPrivateKey(privateKeyFile);

        System.out.println(instance.decryptText(instance.encryptText("THIS TEXT WILL BE ENCRYPTED BY PRIVATE KEY AND THEN DECRYPTED BY PUBLIC KEY", PrivateKey.class), PublicKey.class));
        System.out.println(instance.decryptText(instance.encryptText("THIS TEXT WILL BE ENCRYPTED BY PUBLIC KEY AND THEN DECRYPTED BY PRIVATE KEY", PublicKey.class), PrivateKey.class));
    }
}
