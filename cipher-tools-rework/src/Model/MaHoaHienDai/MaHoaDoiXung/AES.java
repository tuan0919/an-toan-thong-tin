package Model.MaHoaHienDai.MaHoaDoiXung;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

public class AES {

    private SecretKey secretKey;
    private IvParameterSpec iv;

    public String encrypt( String text, String mode, String padding) {

        try {
            if (mode.equals("ECB")) {
               takeKeyFromTxtFile();
                Cipher cipher = Cipher.getInstance("AES/ECB/" + padding);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] plainText = text.getBytes();
                byte[] cipherText = cipher.doFinal(plainText);

                String result = Base64.getEncoder().encodeToString(cipherText);
                return result;
            } else {
                if (mode.equals("CBC")) {
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("AES/CBC/" + padding);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                    byte[] plainText = text.getBytes();
                    byte[] cipherText = cipher.doFinal(plainText);
                    String result = Base64.getEncoder().encodeToString(cipherText);
                    return result;
                } else {
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                    byte[] plainText = text.getBytes();
                    byte[] cipherText = cipher.doFinal(plainText);
                    String result = Base64.getEncoder().encodeToString(cipherText);
                    return result;
                }
            }


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
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
    public boolean encryptAFile(String path, String mode, String padding) {

        try {
            if (mode.equals("ECB")) {
                takeKeyFromTxtFile();
                Cipher cipher = Cipher.getInstance("AES/ECB/" + padding);
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
                    Cipher cipher = Cipher.getInstance("AES/CBC/" + padding);
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
                    Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);
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


    public String encryptWith192BitKeySize(String text, String mode, String padding) {

        try {
            if (mode.equals("ECB")) {
                loadKey(192);
                Cipher cipher = Cipher.getInstance("AES/ECB/" + padding);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] plainText = text.getBytes();
                byte[] cipherText = cipher.doFinal(plainText);

                String result = Base64.getEncoder().encodeToString(cipherText);
                return result;
            } else {
                if (mode.equals("CBC")) {
                    loadKeyAndIV(192);
                    Cipher cipher = Cipher.getInstance("AES/CBC/" + padding);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                    byte[] plainText = text.getBytes();
                    byte[] cipherText = cipher.doFinal(plainText);
                    String result = Base64.getEncoder().encodeToString(cipherText);
                    return result;
                } else {
                    loadKeyAndIV(192);
                    Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                    byte[] plainText = text.getBytes();
                    byte[] cipherText = cipher.doFinal(plainText);
                    String result = Base64.getEncoder().encodeToString(cipherText);
                    return result;
                }
            }


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
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }

    }



    public void loadKeyAndIV(int sizeKey) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(sizeKey);
        secretKey = keyGen.generateKey();

        byte[] ivBytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        iv = new IvParameterSpec(ivBytes);

        String saveKeyAndIVDir = ".";
        File file = new File(saveKeyAndIVDir, "keyAES.txt");
        System.out.println("Saving key and IV to: " + file.getAbsolutePath());
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            fileWriter.write("\n");
            fileWriter.write(Base64.getEncoder().encodeToString(iv.getIV()));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void loadKey(int sizeKey) throws NoSuchAlgorithmException, NoSuchProviderException {


        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(sizeKey);
        secretKey = keyGen.generateKey();
        String saveKeyDir = ".";
        // lưu key  vào 1 file
        File file = new File(saveKeyDir, "keyAES.txt");
        System.out.println("Saving key and IV to: " + file.getAbsolutePath());


        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void loadKeyFromUser(String keyFromUser, String nameEncryption) {
        byte[] key = Base64.getDecoder().decode(keyFromUser);
        secretKey = new SecretKeySpec(key, 0, key.length, nameEncryption);
        String saveKeyDir = ".";
        // lưu key  vào 1 file
        File file = new File(saveKeyDir, "keyAES.txt");
        System.out.println("Saving key and IV to: " + file.getAbsolutePath());
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadKeyAndIVFromUser(String keyFromUser) {

        byte[] key = Base64.getDecoder().decode(keyFromUser);
        secretKey = new SecretKeySpec(key, 0, key.length, "AES");
        byte[] ivBytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        iv = new IvParameterSpec(ivBytes);
        String saveKeyDir = ".";
        // lưu key  vào 1 file
        File file = new File(saveKeyDir, "keyAES.txt");
        System.out.println("Saving key and IV to: " + file.getAbsolutePath());
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            fileWriter.write("\n");
            fileWriter.write(Base64.getEncoder().encodeToString(iv.getIV()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String encryptTextFromSecretKeyUser(String text, String keyFromUser, String mode, String padding) {

        try {
            if (mode.equals("ECB")) {
                loadKeyFromUser(keyFromUser, "AES");
                Cipher cipher = Cipher.getInstance("AES/ECB/" + padding);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] plainText = text.getBytes();
                byte[] cipherText = cipher.doFinal(plainText);
                String result = Base64.getEncoder().encodeToString(cipherText);
                return result;
            } else {
                if (mode.equals("CBC")) {
                    loadKeyAndIVFromUser(keyFromUser);
                    Cipher cipher = Cipher.getInstance("AES/CBC/" + padding);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                    byte[] plainText = text.getBytes();
                    byte[] cipherText = cipher.doFinal(plainText);
                    String result = Base64.getEncoder().encodeToString(cipherText);
                    return result;
                } else {
                    loadKeyAndIVFromUser(keyFromUser);
                    Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                    byte[] plainText = text.getBytes();
                    byte[] cipherText = cipher.doFinal(plainText);
                    String result = Base64.getEncoder().encodeToString(cipherText);
                    return result;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }


    }

    public String decryptText(String encryptedText, String mode, String padding) {
        try {

            if (mode.equals("ECB")) {
                takeKeyFromTxtFile();
                Cipher cipher = Cipher.getInstance("AES/ECB/" + padding);
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] cipherText = Base64.getDecoder().decode(encryptedText);
                byte[] plainText = cipher.doFinal(cipherText);
                String result = new String(plainText);
                return result;

            } else {

                if (mode.equals("CBC")) {
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("AES/CBC/" + padding);
                    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
                    byte[] cipherText = Base64.getDecoder().decode(encryptedText);
                    byte[] plainText = cipher.doFinal(cipherText);
                    String result = new String(plainText);
                    return result;
                } else {
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);
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
                Cipher cipher = Cipher.getInstance("AES/ECB/" + padding);
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
                    Cipher cipher = Cipher.getInstance("AES/CBC/" + padding);
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
                    Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + padding);
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

    public void takeKeyFromTxtFile() {
        try {
            Path pathFile = Paths.get("keyAES.txt");
            BufferedReader bufferedReader = Files.newBufferedReader(pathFile);
            String encodedKey = bufferedReader.readLine();
            String encodedIV = bufferedReader.readLine();
            if (encodedIV == null) {
                byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
                secretKey = new SecretKeySpec(decodedKey, "AES");
            } else {
                byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
                byte[] decodedIV = Base64.getDecoder().decode(encodedIV);
                secretKey = new SecretKeySpec(decodedKey, "AES");
                iv = new IvParameterSpec(decodedIV);
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getSecretKey() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}
