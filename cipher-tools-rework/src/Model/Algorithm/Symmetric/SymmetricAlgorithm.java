package Model.Algorithm.Symmetric;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class SymmetricAlgorithm {
    protected String mode;
    protected String algorithm;
    protected String padding;
    protected int keySize;
    protected SecretKey secretKey;
    protected IvParameterSpec ivParameterSpec;
    protected static Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    protected static Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = this.createSecretKeyFromString(secretKey);
    }

    public void setIvParameterSpec(String iv) {
        if (iv != null) {
            this.ivParameterSpec = this.createIVFromString(iv);
        }
    }

    public String encrypt(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
        var cipher = createCipherInstance(Cipher.ENCRYPT_MODE);
        var plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
        var encryptedTextBytes = cipher.doFinal(plainTextBytes);
        var encryptedText_EncodeBase64 = BASE64_ENCODER.encodeToString(encryptedTextBytes);
        return encryptedText_EncodeBase64;
    };
    public String decrypt(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
        var cipher = createCipherInstance(Cipher.DECRYPT_MODE);
        var cipherTextBytes_Base64Encode = cipherText.getBytes(StandardCharsets.UTF_8);
        var cipherTextBytes = BASE64_DECODER.decode(cipherTextBytes_Base64Encode);
        var plainTextBytes = cipher.doFinal(cipherTextBytes);
        return new String(plainTextBytes, StandardCharsets.UTF_8);
    };
    public boolean encryptFile(String sourcePath, String destPath) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        File sourceFile = new File(sourcePath);
        File destFile = new File(destPath);
        Cipher cipher = createCipherInstance(Cipher.ENCRYPT_MODE);
        try (FileInputStream fileInputStream = new FileInputStream(sourceFile);
             FileOutputStream fileOutputStream = new FileOutputStream(destFile)) {

            byte[] buffer = new byte[1024]; // Kích thước buffer để đọc file theo khối
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byte[] outputBytes = cipher.update(buffer, 0, bytesRead);
                if (outputBytes != null) {
                    fileOutputStream.write(outputBytes);
                }
            }

            // Ghi thêm byte cuối cùng sau khi hoàn thành mã hóa
            byte[] finalBytes = cipher.doFinal();
            if (finalBytes != null) {
                fileOutputStream.write(finalBytes);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean decryptFile(String sourcePath, String destPath) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        File sourceFile = new File(sourcePath);
        File destFile = new File(destPath);
        Cipher cipher = createCipherInstance(Cipher.DECRYPT_MODE);
        try (FileInputStream fileInputStream = new FileInputStream(sourceFile);
             FileOutputStream fileOutputStream = new FileOutputStream(destFile)) {

            byte[] buffer = new byte[1024]; // Kích thước buffer để đọc file theo khối
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                byte[] outputBytes = cipher.update(buffer, 0, bytesRead);
                if (outputBytes != null) {
                    fileOutputStream.write(outputBytes);
                }
            }

            // Ghi thêm byte cuối cùng sau khi hoàn thành giải mã
            byte[] finalBytes = cipher.doFinal();
            if (finalBytes != null) {
                fileOutputStream.write(finalBytes);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void loadKeyFromFile(String sourcePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourcePath))) {
            // Đọc dòng đầu tiên: Secret Key
            String secretKeyBase64 = reader.readLine();
            if (secretKeyBase64 == null || secretKeyBase64.isEmpty()) {
                throw new IllegalArgumentException("File nguồn không đúng định dạng: thiếu Secret Key.");
            }

            // Giải mã Secret Key từ Base64
            byte[] secretKeyBytes = Base64.getDecoder().decode(secretKeyBase64);
            this.secretKey = new SecretKeySpec(secretKeyBytes, algorithm);

            // Đọc dòng thứ hai: IV (nếu cần thiết)
            String ivBase64 = reader.readLine();
            if (ivBase64 != null && !ivBase64.isEmpty()) {
                byte[] ivBytes = Base64.getDecoder().decode(ivBase64);
                var algoEnum = getConstant(algorithm);
                if (ivBytes.length != algoEnum.getIvSize() / 8) {
                    throw new IllegalArgumentException("Độ dài IV không đúng, IV nên có độ dài là " + algoEnum.getIvSize() + " bytes.");
                }
                this.ivParameterSpec = new IvParameterSpec(ivBytes);
            } else if (!"ECB".equalsIgnoreCase(mode)) {
                throw new IllegalArgumentException("File nguồn không đúng định dạng: yêu cầu phải có IV cho mode: " + mode);
            }
        }
    }
    public SecretKey generateRandomKey(int keySize) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyGenerator generator = null;
        var algoEnum = SymmetricConstant.valueOf(algorithm);
        boolean isValidKeySize = algoEnum.isValidKeySize(keySize);
        if (!isValidKeySize) {
            throw new IllegalArgumentException(String.format("Thuật toán %s không hỗ trợ key size: %s", algorithm, keySize));
        }
        switch (algorithm) {
            case "Camellia", "IDEA" -> {
                Security.addProvider(new BouncyCastleProvider());
                generator = KeyGenerator.getInstance(algorithm, "BC");
            }
            case "AES", "DES", "DESede" -> {
                generator = KeyGenerator.getInstance(algorithm);
            }
        }
        generator.init(keySize);
        return generator.generateKey();
    }
    private static SymmetricConstant getConstant(String algorithm) {
        return SymmetricConstant.valueOf(algorithm);
    }
    public SecretKey createSecretKeyFromString(String keyString) {
        // Chuyển chuỗi key thành byte array
        byte[] keyBytes_Base64 = keyString.getBytes(StandardCharsets.UTF_8);
        byte[] keyBytes = BASE64_DECODER.decode(keyBytes_Base64);
        // Tạo một SecretKey từ byte array với thuật toán tương ứng
        return new SecretKeySpec(keyBytes, algorithm);
    }
    public IvParameterSpec createIVFromString(String ivString) {
        // Chuyển chuỗi key thành byte array
        byte[] ivBytes_Base64 = ivString.getBytes(StandardCharsets.UTF_8);
        byte[] ivBytes = BASE64_DECODER.decode(ivBytes_Base64);
        // Tạo một SecretKey từ byte array với thuật toán tương ứng
        return new IvParameterSpec(ivBytes);
    }
    public IvParameterSpec generateRandomIV() {
        var algoEnum = getConstant(algorithm); // Lấy kích thước khối theo thuật toán
        byte[] ivBytes = new byte[algoEnum.getIvSize() / 8];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes); // Sinh ngẫu nhiên IV
        return new IvParameterSpec(ivBytes);
    }
    public String getSecretKey() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
    public String parseToString(SecretKey secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
    public String parseToString(IvParameterSpec ivParameterSpec) {
        return Base64.getEncoder().encodeToString(ivParameterSpec.getIV());
    }
    public Cipher createCipherInstance(int cipherMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchProviderException {
        Cipher cipher = null;
        switch (algorithm) {
            case "Camellia", "IDEA" -> {
                Security.addProvider(new BouncyCastleProvider());
                cipher = Cipher.getInstance(String.format("%s/%s/%s", algorithm, mode, padding), "BC");
            }
            case "AES", "DES", "DESede" -> {
                cipher = Cipher.getInstance(String.format("%s/%s/%s", algorithm, mode, padding));
            }
            default -> {
                // This one shouldn't happen, wtf?
                throw new RuntimeException("Unsupported algorithm: " + algorithm);
            }
        }
        // Nếu chế độ yêu cầu IV (không phải ECB), khởi tạo với IV
        if (!"ECB".equalsIgnoreCase(mode) && ivParameterSpec != null) {
            cipher.init(cipherMode, this.secretKey, this.ivParameterSpec);
        } else {
            // Nếu là ECB hoặc không có IV, chỉ khởi tạo với SecretKey
            cipher.init(cipherMode, this.secretKey);
        }
        return cipher;
    }
    public boolean saveToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Lưu Secret Key
            String secretKeyBase64 = this.parseToString(this.secretKey);
            writer.write(secretKeyBase64);
            writer.newLine();

            // Kiểm tra xem có IV hay không và lưu nó
            if (ivParameterSpec != null) {
                String ivBase64 = this.parseToString(this.ivParameterSpec);
                writer.write(ivBase64);
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchProviderException, IOException {
        var instance = new SymmetricAlgorithm();
        String sourceFile = "C:\\Users\\Nguyen Tuan\\Downloads\\test.txt";
        String encryptFile = "C:\\Users\\Nguyen Tuan\\Downloads\\test_encrypted.txt";
        String decryptedFile = "C:\\Users\\Nguyen Tuan\\Downloads\\test_decrypted.txt";
        instance.algorithm = "Camellia";
        instance.mode = "CBC";
        instance.padding = "PKCS5Padding";
        String randomSecretKey = instance.parseToString(instance.generateRandomKey(256));
        String randomIV = instance.parseToString(instance.generateRandomIV());
        System.out.println("Random secret key: "+ randomSecretKey);
        System.out.println("Random IV: "+ randomIV);
        instance.secretKey = instance.createSecretKeyFromString(randomSecretKey);
        instance.ivParameterSpec = instance.createIVFromString(randomIV);
        System.out.println(instance.decrypt(instance.encrypt("THIS PLAIN TEXT SHOULD BE ENCRYPTED AND THEN DECRYPTED")));
        instance.saveToFile(sourceFile);
        instance.encryptFile(sourceFile, encryptFile);
        instance.decryptFile(encryptFile, decryptedFile);
        instance.loadKeyFromFile(decryptedFile);
        System.out.println("Loaded secret key: "+instance.parseToString(instance.secretKey));
        System.out.println("Loaded IV: "+instance.parseToString(instance.ivParameterSpec));
    }
}
