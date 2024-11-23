package Model.Algorithm.MaHoaHienDai.MaHoaDoiXung;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

public class Camellia {
    private SecretKey secretKey;
    private IvParameterSpec iv;
    static final String saveKeyDir = ".";

    public String encrypt(String text, String mode, String padding) throws NoSuchAlgorithmException, NoSuchProviderException {

        try {
            Security.addProvider(new BouncyCastleProvider());
            if(mode.equals("ECB")){
                takeKeyFromTxtFile();
                Cipher cipher = Cipher.getInstance("Camellia/ECB/"+padding, "BC");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] plainText = text.getBytes();
                byte[] cipherText = cipher.doFinal(plainText);

                String result = Base64.getEncoder().encodeToString(cipherText);
                return result;
            }else {
                if(mode.equals("CBC")){
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("Camellia/CBC/"+padding, "BC");
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                    byte[] plainText = text.getBytes();
                    byte[] cipherText = cipher.doFinal(plainText);
                    String result = Base64.getEncoder().encodeToString(cipherText);
                    return result;
                } else {
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("Camellia/"+mode+"/"+padding, "BC");
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
        }
    }
    public boolean encryptAFile(String path, String mode, String padding) {

        try {
            Security.addProvider(new BouncyCastleProvider());
            if (mode.equals("ECB")) {
                takeKeyFromTxtFile();
                Cipher cipher = Cipher.getInstance("Camellia/ECB/" + padding);
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
                    Cipher cipher = Cipher.getInstance("Camellia/CBC/" + padding);
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
                    Cipher cipher = Cipher.getInstance("Camellia/" + mode + "/" + padding);
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


    public String getIv() {
        return Base64.getEncoder().encodeToString(iv.getIV());
    }

    public void loadKey(int sizeKey) throws NoSuchAlgorithmException, NoSuchProviderException {
        // Đăng ký provider của Bouncy Castle
        Security.addProvider(new BouncyCastleProvider());

        KeyGenerator keyGen = KeyGenerator.getInstance("Camellia", "BC");
        keyGen.init(sizeKey);
        secretKey = keyGen.generateKey();

        // lưu key  vào 1 file
        File file = new File(saveKeyDir, "CamelliaKey.txt");
        System.out.println("Saving key  to: " + file.getAbsolutePath());



        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void loadKeyAndIV(int sizeKey, String nameEncryption) throws NoSuchAlgorithmException, NoSuchProviderException {
        // Đăng ký provider của Bouncy Castle
        Security.addProvider(new BouncyCastleProvider());

        KeyGenerator keyGen = KeyGenerator.getInstance(nameEncryption, "BC");
        keyGen.init(sizeKey);
        secretKey = keyGen.generateKey();

        byte[] ivBytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        iv = new IvParameterSpec(ivBytes);

        String saveKeyDir = ".";
        // lưu key  vào 1 file
        File file = new File(saveKeyDir, "CamelliaKey.txt");
        System.out.println("Saving key and IV to: " + file.getAbsolutePath());
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            fileWriter.write("\n");
            System.out.println("IV: "+Base64.getEncoder().encodeToString(iv.getIV()));
            fileWriter.write(Base64.getEncoder().encodeToString(iv.getIV()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void loadKeyFromUser(String keyFromUser){

        byte[] key = Base64.getDecoder().decode(keyFromUser);
        secretKey = new SecretKeySpec(key, 0, key.length, "Camellia");

        String saveKeyDir = ".";
        // lưu key  vào 1 file
        File file = new File(saveKeyDir, "CamelliaKey.txt");
        System.out.println("Saving key and IV to: " + file.getAbsolutePath());
        try(FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
    public String encrpytWithKeyFromUser(String text,String keyFromUser,String mode,String padding) {
        try {
            Security.addProvider(new BouncyCastleProvider());

            if (mode.equals("ECB")) {
                loadKeyFromUser(keyFromUser);
                Cipher cipher = Cipher.getInstance("Camellia/ECB/PKCS5Padding", "BC");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] plainText = text.getBytes();
                byte[] cipherText = cipher.doFinal(plainText);

                String result = Base64.getEncoder().encodeToString(cipherText);
                return result;
            } else {
                if (mode.equals("CBC")) {
                    loadKeyAndIVFromUser(keyFromUser);
                    Cipher cipher = Cipher.getInstance("Camellia/CBC/" + padding, "BC");
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
                    byte[] plainText = text.getBytes();
                    byte[] cipherText = cipher.doFinal(plainText);
                    String result = Base64.getEncoder().encodeToString(cipherText);
                    return result;
                }else {
                    loadKeyAndIVFromUser(keyFromUser);
                    Cipher cipher = Cipher.getInstance("Camellia/"+mode+"/"+padding, "BC");
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
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadKeyAndIVFromUser(String keyFromUser){

        byte[] key = Base64.getDecoder().decode(keyFromUser);
        secretKey = new SecretKeySpec(key, 0, key.length, "IDEA");
        byte [] ivBytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        iv = new IvParameterSpec(ivBytes);
        String saveKeyDir = ".";
        // lưu key  vào 1 file
        File file = new File(saveKeyDir, "CamelliaKey.txt");
        System.out.println("Saving key and IV to: " + file.getAbsolutePath());
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            fileWriter.write("\n");
            System.out.println("IV: "+Base64.getEncoder().encodeToString(iv.getIV()));
            fileWriter.write(Base64.getEncoder().encodeToString(iv.getIV()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }
    public String decryptText(String encryptedText, String mode, String padding) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            if (mode.equals("ECB")) {
                takeKeyFromTxtFile();
                Cipher cipher = Cipher.getInstance("Camellia/ECB/" + padding, "BC");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] cipherText = Base64.getDecoder().decode(encryptedText);
                byte[] plainText = cipher.doFinal(cipherText);
                String result = new String(plainText);
                return result;

            } else {

                if (mode.equals("CBC")) {
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("Camellia/CBC/" + padding,"BC");
                    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
                    byte[] cipherText = Base64.getDecoder().decode(encryptedText);
                    byte[] plainText = cipher.doFinal(cipherText);
                    String result = new String(plainText);
                    return result;
                } else {
                    takeKeyFromTxtFile();
                    Cipher cipher = Cipher.getInstance("Camellia/" + mode + "/" + padding,"BC");
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
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }

    }
    public boolean decryptAFile(String path, String mode, String padding) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            if (mode.equals("ECB")) {
                takeKeyFromTxtFile();
                Cipher cipher = Cipher.getInstance("Camellia/ECB/" + padding);
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
                    Cipher cipher = Cipher.getInstance("Camellia/CBC/" + padding);
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
                    Cipher cipher = Cipher.getInstance("Camellia/" + mode + "/" + padding);
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
            Path pathFile = Paths.get("CamelliaKey.txt");
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
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
        Camellia camellia = new Camellia();
        String text = "Hello World";
//        String encryptedText = null;
//        //            encryptedText = camellia.encryptWith256BitKeySize(text,"CTR","NoPadding");
//        String encryptedText2 = camellia.encrpytWithKeyFromUser(text,"+FKixI0qWMVHe70YA8wXj/fjwooa16aFefmdyj3jBXE=","CTR","PKCS5Padding");
////            System.out.println("Văn bản máy hóa: " + encryptedText2);
//        String decryptedText = camellia.decryptText(encryptedText2
//                , "CTR", "PKCS5Padding");
//        System.out.println("Văn bản giải mã hóa: " + decryptedText);
        camellia.loadKey(128);
        camellia.encryptAFile("C:\\Users\\Yukihira Souma\\Desktop\\account chat GPT.txt","ECB","PKCS5Padding");
        camellia.decryptAFile("C:\\Users\\Yukihira Souma\\Desktop\\account chat GPTDaMaHoa.txt","ECB","PKCS5Padding");

    }
}