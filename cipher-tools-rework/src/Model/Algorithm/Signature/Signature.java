package Model.Algorithm.Signature;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Signature {
    private KeyPair keyPair;
    private java.security.Signature signature;
    private String rsaSignature;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    static final String saveKeyDir = ".";

    public void initKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        signature = java.security.Signature.getInstance("SHA256withRSA");
    }

    public void saveKey(PublicKey publicKey, PrivateKey privateKey) throws NoSuchAlgorithmException, IOException {
        //             Lưu key vào file txt
        File file = new File(saveKeyDir, "Cặp key của chữ ký số.txt");
        FileWriter writer = new FileWriter(file);
        writer.write("Public key:"+Base64.getEncoder().encodeToString(publicKey.getEncoded()) + "\n");
        writer.write( "Private key:"+Base64.getEncoder().encodeToString(privateKey.getEncoded()) + "\n");
        writer.close();
    }
    public void loadKey(){
        try {
            String line;
            String publicKeyFromText = "";
            String privateKeyFromText = "";
            Path pathFile = Paths.get("Cặp key của chữ ký số.txt");
            BufferedReader bufferedReader = Files.newBufferedReader(pathFile);
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("Public key:")) {
                    publicKeyFromText = line.substring("Public key:".length()).trim();
                } else if (line.startsWith("Private key:")) {
                    privateKeyFromText = line.substring("Private key:".length()).trim();
                }
            }
            bufferedReader.close();

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            // Tạo lại PublicKey
            byte[] publicBytes = Base64.getDecoder().decode(publicKeyFromText);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicBytes);
            publicKey = keyFactory.generatePublic(publicKeySpec);

            // Tạo lại PrivateKey
            byte[] privateBytes = Base64.getDecoder().decode(privateKeyFromText);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateBytes);
            privateKey = keyFactory.generatePrivate(privateKeySpec);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public String createSignatureWithText(String text) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        initKey();
        signature.initSign(privateKey);
        signature.update(text.getBytes());
        byte[] signatureBytes = signature.sign();
        saveKey(publicKey, privateKey);
        return Base64.getEncoder().encodeToString(signatureBytes);
    }
    public String createSignatureWithFile(String filePath) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        initKey();
        signature.initSign(privateKey);
        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                signature.update(buffer, 0, bytesRead);
            }
        }
        saveKey(publicKey, privateKey);
        return Base64.getEncoder().encodeToString(signature.sign());
    }
    public boolean verifySignatureWithText(String text, String signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        loadKey();
        java.security.Signature rsaSignature = java.security.Signature.getInstance("SHA256withRSA");
        rsaSignature.initVerify(publicKey);
        MessageDigest digest = MessageDigest.getInstance("SHA-256"); // Dùng để lấy hash dữ liệu
        rsaSignature.update(text.getBytes());
        digest.update(text.getBytes());
        // Lấy hash dưới dạng chuỗi Base64
        String fileHashBase64 = Base64.getEncoder().encodeToString(digest.digest());
        setRsaSignature(fileHashBase64); // Lưu giá trị hash để sử dụng
        return rsaSignature.verify(Base64.getDecoder().decode(signature));
    }
    public boolean verifySignatureWithFile(String filePath, String signature) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        loadKey();
        java.security.Signature rsaSignature = java.security.Signature.getInstance("SHA256withRSA");
        rsaSignature.initVerify(publicKey);
        MessageDigest digest = MessageDigest.getInstance("SHA-256"); // Dùng để lấy hash dữ liệu

        try (FileInputStream fis = new FileInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                rsaSignature.update(buffer, 0, bytesRead); // Cập nhật nội dung vào chữ ký
                digest.update(buffer, 0, bytesRead);      // Tính hash của dữ liệu
            }
        }

        // Lấy hash dưới dạng chuỗi Base64
        String fileHashBase64 = Base64.getEncoder().encodeToString(digest.digest());
        setRsaSignature(fileHashBase64); // Lưu giá trị hash để sử dụng

        // Xác minh chữ ký
        return rsaSignature.verify(Base64.getDecoder().decode(signature));
    }


    public KeyPair getKeyPair() {
        return keyPair;
    }

    public java.security.Signature getSignature() {
        return signature;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public String getRsaSignature() {
        return rsaSignature;
    }

    public void setRsaSignature(String rsaSignature) {
        this.rsaSignature = rsaSignature;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException {
        Signature signature = new Signature();
//        String chuKy = chuKySo.createSignatureWithText("Hello World!");
//        System.out.println(chuKy);
//        boolean result = chuKySo.verifySignatureWithText("Hello World!", chuKy);
//        System.out.println(result);

       String chuKy= signature.createSignatureWithFile("C:\\Users\\Yukihira Souma\\Desktop\\account chat GPT.txt");
        boolean result = signature.verifySignatureWithFile("C:\\Users\\Yukihira Souma\\Desktop\\Genshin Impact 2024.10.19 - 09.17.51.10.mp4", chuKy);
        System.out.println(result);
    }
}
