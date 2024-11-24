package Model.Algorithm.Asymmetric;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
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
        var cipher = Cipher.getInstance("RSA");
        cipher.init(mode, key);
        return cipher;
    }

    public Cipher createInstance(int mode, PrivateKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        var cipher = Cipher.getInstance("RSA");
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

    public String encryptText(String text, Class<? extends Key> clazz) {
        try {
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
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting text", e);
        }
    }

    public String decryptText(String text, Class<? extends Key> clazz) {
        try {
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
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting text", e);
        }
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
}
