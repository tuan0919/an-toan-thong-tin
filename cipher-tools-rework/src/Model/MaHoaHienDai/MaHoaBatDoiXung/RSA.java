package Model.MaHoaHienDai.MaHoaBatDoiXung;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSA {
    PublicKey publicKey;
    PrivateKey privateKey;
    static final String saveKeyDir = ".";

    public String encrypt(int size,String text) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        loadKey(size);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] plainText = text.getBytes();
        byte[] cipherText = cipher.doFinal(plainText);
        String result = Base64.getEncoder().encodeToString(cipherText);
        return result;
    }
    public String encryptWithKeyFromUser(String text, String publicKeyFromUser, String privateKeyFromUser) throws NoSuchAlgorithmException {
        loadKeyFromUser(publicKeyFromUser,privateKeyFromUser);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] plainText = text.getBytes();
            byte[] cipherText = cipher.doFinal(plainText);
            String result = Base64.getEncoder().encodeToString(cipherText);
            return result;
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }
    public String decrypt(int keySize,String text) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        takeKeyFromTxtFile();
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = decryptCipher.doFinal(Base64.getDecoder().decode(text));
        String result = new String(decryptedBytes);


        return result;
    }
    public void loadKey(int sizeKey) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(sizeKey);
            KeyPair keyPair = keyPairGen.generateKeyPair();
             publicKey = keyPair.getPublic();
             privateKey = keyPair.getPrivate();


//             Lưu key vào file txt
            File file = new File(saveKeyDir, "RSAKey.txt");
            FileWriter writer = new FileWriter(file);

            writer.write("Public key:"+Base64.getEncoder().encodeToString(publicKey.getEncoded()) + "\n");
            writer.write( "Private key:"+Base64.getEncoder().encodeToString(privateKey.getEncoded()) + "\n");
            writer.close();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean loadKeyFromUser( String publicKeyFromUser, String privateKeyFromUser) throws NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        try {
            // Tạo lại PublicKey
            byte[] publicBytes = Base64.getDecoder().decode(publicKeyFromUser);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicBytes);
            publicKey = keyFactory.generatePublic(publicKeySpec);
            // Tạo lại PrivateKey
            byte[] privateBytes = Base64.getDecoder().decode(privateKeyFromUser);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateBytes);
            privateKey = keyFactory.generatePrivate(privateKeySpec);
            //             Lưu key vào file txt
            File file = new File(saveKeyDir, "RSAKey.txt");
            FileWriter writer = new FileWriter(file);

            writer.write("Public key:"+Base64.getEncoder().encodeToString(publicKey.getEncoded()) + "\n");
            writer.write( "Private key:"+Base64.getEncoder().encodeToString(privateKey.getEncoded()) + "\n");
            writer.close();
            return true;
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void takeKeyFromTxtFile() {
        try {
            String line;
            String publicKeyFromText = "";
            String privateKeyFromText = "";
            Path pathFile = Paths.get("RSAKey.txt");
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

    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public String getPrivateKey() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());

    }

    public static void main(String[] args) {
        RSA rsa = new RSA();
        String text = "Hello world";
        String encryptedText = null;
        try {
            System.out.println("Đoạn text ban đầu: " + text);
            String publicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCF2Zd+DNUeNAoCuYyPNI9nU9iU783jo8JAB99hqQU9mYbsb+6DvssV/f2e6kcKR8OBmlOT5fI/PH/0hLTB+6km0ztSFp9OfXq+d6HtjFwNKz+3s5GiR5I5SHQO7PEvVahtKcl6kOTtTIMurST3WhPwhD4sSHwy1wqpp0WASIZIgQIDAQAB";
            String privateKey ="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIXZl34M1R40CgK5jI80j2dT2JTvzeOjwkAH32GpBT2Zhuxv7oO+yxX9/Z7qRwpHw4GaU5Pl8j88f/SEtMH7qSbTO1IWn059er53oe2MXA0rP7ezkaJHkjlIdA7s8S9VqG0pyXqQ5O1Mgy6tJPdaE/CEPixIfDLXCqmnRYBIhkiBAgMBAAECgYAB0OJTQymuy0hTEe5Xk3V/h+cKGxW0iSsBnFhmMlFUMlmZlSRDy1ulDAKEGf6jiRaB+GcvrETA1tbPqU+BwH137ff4b4x2U1XRFvMW0YAiq0NJfWgYG31gS/TXTvYvP2fGnrtoIAFjctGNL7pFsRBV4b0GlCSz8w0VM1I0HGXpsQJBANAJHFB0WV3juXAMfSvHOXXpEmUAPPGf0t0lrsV8RQT5cBA1V2a+SwdVcPYp43SkPjx8S1wIubfcgl6LBa3axxECQQCktdJRPKut0NpB3YuC+IFelIdwk/JzZaiDp8shSVf2KbY9eHdt+bVHc7ck71UVqRp9rCIyMiATjWfMku8V0cpxAkB1yiLI/we2nIDbi4a36eaD94Kdw2O+X4LyCisJ0FH3/g/QbZNeLjKnJV5/UrphybkfYDT/GEWnb+g1BbwicvQxAkBrXPVl7LU/jJboPiA1VAUIAKs0T8vHOqmYf+RhoyxKEjBvazn2HsHyfFQSKEt+3gadinmT3KaZ+B+D8vcTcveRAkEAgRt/XRAB2l1lqjzLlx+2szfiw3WMlxQVrkqVvzZTNyTTOhScsCUEdE404Ja43g3n38eRYdi/s9jhRsy9WmQ/kg==";
            encryptedText = rsa.encryptWithKeyFromUser(text,publicKey,privateKey);
            String decryptedText = rsa.decrypt(1024,encryptedText);
            System.out.println("Text đã mã hóa: "+encryptedText);
            System.out.println("Text giải mã: "+decryptedText);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }

    }
}
