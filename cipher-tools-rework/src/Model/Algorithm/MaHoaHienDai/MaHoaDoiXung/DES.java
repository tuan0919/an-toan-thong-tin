package Model.Algorithm.MaHoaHienDai.MaHoaDoiXung;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class DES {
    private SecretKey secretKey;
    private IvParameterSpec iv;
    private byte[] ivBytes = new byte[8];
    static final String saveKeyDir = ".";

    public String getSecretKey() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
    public String getIv() {
        return Base64.getEncoder().encodeToString(iv.getIV());
    }

    public String encrypt(String plainText, String mode, String padding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if(mode.equals("ECB")) {
            takeKeyFromTxtFile();
            Cipher cipher = Cipher.getInstance("DES/ECB/" + padding);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherText = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        } else {
            if(mode.equals("CBC")) {
                takeKeyFromTxtFile();
                Cipher cipher = Cipher.getInstance("DES/CBC/" + padding);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                byte[] cipherText = cipher.doFinal(plainText.getBytes());
                return Base64.getEncoder().encodeToString(cipherText);
            }else {
                takeKeyFromTxtFile();
                Cipher cipher = Cipher.getInstance("DES/" + mode + "/" + padding);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                byte[] cipherText = cipher.doFinal(plainText.getBytes());
                return Base64.getEncoder().encodeToString(cipherText);
            }
        }
    }
    public String encryptFromKeyUser(int sizeKey,String keyUser,String plainText, String mode, String padding) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if(mode.equals("ECB")) {
            loadKeyFromUser(keyUser);
            Cipher cipher = Cipher.getInstance("DES/ECB/" + padding);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherText = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        }else{
            if(mode.equals("CBC")) {
                loadKeyAndIVFromUser(keyUser);
                Cipher cipher = Cipher.getInstance("DES/CBC/" + padding);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                byte[] cipherText = cipher.doFinal(plainText.getBytes());
                return Base64.getEncoder().encodeToString(cipherText);
            }else {
                loadKeyAndIVFromUser(keyUser);
                Cipher cipher = Cipher.getInstance("DES/" + mode + "/" + padding);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                byte[] cipherText = cipher.doFinal(plainText.getBytes());
                return Base64.getEncoder().encodeToString(cipherText);
            }
        }

    }
    public boolean encryptAFile(String path, String mode, String padding) {

        try {
            if (mode.equals("ECB")) {
                takeKeyFromTxtFile();
                Cipher cipher = Cipher.getInstance("DES/ECB/" + padding);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                String destFile = path.substring(0, path.lastIndexOf('.')) + "DaMaHoa" + path.substring(path.lastIndexOf('.'));
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(path)));
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destFile));
                CipherOutputStream cipherOutputStream = new CipherOutputStream(bufferedOutputStream, cipher);
                byte [] byteArray = new byte[1024];
                int bytesRead;
                while ((bytesRead = bufferedInputStream.read(byteArray)) != -1) {
                    cipherOutputStream.write(byteArray, 0, bytesRead);
                }

                cipherOutputStream.close();
                bufferedOutputStream.close();
                bufferedInputStream.close();
                return true;

            } else {
                if (mode.equals("CBC")) {
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("DES/CBC/" + padding);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                    String destFile = path.substring(0, path.lastIndexOf('.')) + "DaMaHoa" + path.substring(path.lastIndexOf('.'));
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(path)));
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destFile));
                    CipherOutputStream cipherOutputStream = new CipherOutputStream(bufferedOutputStream, cipher);
                    byte [] byteArray = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = bufferedInputStream.read(byteArray)) != -1) {
                        cipherOutputStream.write(byteArray, 0, bytesRead);
                    }
                    cipherOutputStream.close();
                    bufferedOutputStream.close();
                    bufferedInputStream.close();

                    return true;
                }else{
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("DES/" + mode + "/" + padding);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                    String destFile = path.substring(0, path.lastIndexOf('.')) + "DaMaHoa" + path.substring(path.lastIndexOf('.'));
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(path)));
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destFile));
                    CipherOutputStream cipherOutputStream = new CipherOutputStream(bufferedOutputStream, cipher);
                    byte [] byteArray = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = bufferedInputStream.read(byteArray)) != -1) {
                        cipherOutputStream.write(byteArray, 0, bytesRead);
                    }
                    cipherOutputStream.close();
                    bufferedOutputStream.close();
                    bufferedInputStream.close();

                    return true;
                }
            }
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    public String decryptText(String encryptedText, String mode, String padding) {
        try {

            if (mode.equals("ECB")) {
                takeKeyFromTxtFile();
                Cipher cipher = Cipher.getInstance("DES/ECB/" + padding);
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] cipherText = Base64.getDecoder().decode(encryptedText);
                byte[] plainText = cipher.doFinal(cipherText);
                String result = new String(plainText);
                return result;

            } else {

                if (mode.equals("CBC")) {
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("DES/CBC/" + padding);
                    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
                    byte[] cipherText = Base64.getDecoder().decode(encryptedText);
                    byte[] plainText = cipher.doFinal(cipherText);
                    String result = new String(plainText);
                    return result;
                } else {
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("DES/" + mode + "/" + padding);
                    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
                    byte[] cipherText = Base64.getDecoder().decode(encryptedText);
                    byte[] plainText = cipher.doFinal(cipherText);
                    String result = new String(plainText);
                    return result;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }

    }
    public boolean decryptAFile(String path, String mode, String padding) {
        try {
            if (mode.equals("ECB")) {
                takeKeyFromTxtFile();
                Cipher cipher = Cipher.getInstance("DES/ECB/" + padding);
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                String destFile = path.replace("DaMaHoa", "DaGiaiMaHoa");
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(path)));
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destFile));
                CipherInputStream cipherInputStream = new CipherInputStream(bufferedInputStream, cipher);
                byte [] byteArray = new byte[1024];
                int bytesRead;
                while ((bytesRead = cipherInputStream.read(byteArray)) != -1) {
                    bufferedOutputStream.write(byteArray, 0, bytesRead);
                }
                bufferedOutputStream.close();
                cipherInputStream.close();
                bufferedInputStream.close();

                return true;

            } else {
                if (mode.equals("CBC")) {
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("DES/CBC/" + padding);
                    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
                    String destFile = path.replace("DaMaHoa", "DaGiaiMaHoa");
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(path)));
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destFile));
                    CipherInputStream cipherInputStream = new CipherInputStream(bufferedInputStream, cipher);
                    byte [] byteArray = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = cipherInputStream.read(byteArray)) != -1) {
                        bufferedOutputStream.write(byteArray, 0, bytesRead);
                    }
                    bufferedOutputStream.close();
                    cipherInputStream.close();
                    bufferedInputStream.close();
                    return true;
                }else{
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("DES/" + mode + "/" + padding);
                    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
                    String destFile = path.replace("DaMaHoa", "DaGiaiMaHoa");
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(path)));
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destFile));
                    CipherInputStream cipherInputStream = new CipherInputStream(bufferedInputStream, cipher);
                    byte [] byteArray = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = cipherInputStream.read(byteArray)) != -1) {
                        bufferedOutputStream.write(byteArray, 0, bytesRead);
                    }
                    bufferedOutputStream.close();
                    cipherInputStream.close();
                    bufferedInputStream.close();
                    return true;
                }
            }
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }


    public void loadKey(int sizeKey) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(sizeKey);
        secretKey = keyGen.generateKey();

        try (FileWriter fileWriter = new FileWriter(saveKeyDir + "/DESKey.txt")) {
            fileWriter.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadKeyAndIV(int sizeKey) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(sizeKey);
        secretKey = keyGen.generateKey();
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        iv = new IvParameterSpec(ivBytes);
        try (FileWriter fileWriter = new FileWriter(saveKeyDir + "/DESKey.txt")) {
            fileWriter.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            fileWriter.write("\n");
            fileWriter.write(Base64.getEncoder().encodeToString(iv.getIV()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadKeyFromUser(String keyFromUser) throws NoSuchAlgorithmException {
        byte[] key = Base64.getDecoder().decode(keyFromUser);
        secretKey = new SecretKeySpec(key, 0, key.length, "DES");

        // lưu key  vào 1 file
        File file = new File(saveKeyDir, "DESKey.txt");
        System.out.println("Saving key and IV to: " + file.getAbsolutePath());
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadKeyAndIVFromUser(String keyFromUser) throws NoSuchAlgorithmException {
        byte[] key = Base64.getDecoder().decode(keyFromUser);
        secretKey = new SecretKeySpec(key, 0, key.length, "DES");
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        iv = new IvParameterSpec(ivBytes);
        // lưu key  vào 1 file
        File file = new File(saveKeyDir, "DESKey.txt");
        System.out.println("Saving key and IV to: " + file.getAbsolutePath());
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            fileWriter.write("\n");
            fileWriter.write(Base64.getEncoder().encodeToString(iv.getIV()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void takeKeyFromTxtFile() {
        try {
            Path pathFile = Paths.get("DESKey.txt");
            BufferedReader bufferedReader = Files.newBufferedReader(pathFile);
            String encodedKey = bufferedReader.readLine();
            String encodedIV = bufferedReader.readLine();
            if (encodedIV == null) {
                byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
                secretKey = new SecretKeySpec(decodedKey, "DES");
            } else {
                byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
                byte[] decodedIV = Base64.getDecoder().decode(encodedIV);
                secretKey = new SecretKeySpec(decodedKey, "DES");
                iv = new IvParameterSpec(decodedIV);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        DES des = new DES();
        String text = "Hello World";

        String encryptedText = null;
        String decryptedText = null;

        try {
            encryptedText = des.encrypt(text,"CTR","NoPadding");
            System.out.println("Encrypted text: " + encryptedText);
            decryptedText = des.decryptText(encryptedText, "CTR", "NoPadding");
            System.out.println("Decrypted text: " + decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
