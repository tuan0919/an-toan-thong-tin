package Model.Algorithm.Hash;

import org.bouncycastle.util.encoders.Base64;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;

public class MD5 {
    private MessageDigest messageDigest;
    private byte[] hashBytes;
    private BufferedInputStream bufferedInputStream;


    public String hashAFile(String filePath) throws IOException, NoSuchAlgorithmException {
        bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
        byte [] byteReaded = new byte[1024];
        messageDigest = MessageDigest.getInstance("MD5");
        int readed;
        while (true) {
            try {
                if (!((readed = bufferedInputStream.read(byteReaded)) != -1)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            messageDigest.update(byteReaded, 0, readed);
        }
        bufferedInputStream.close();
        hashBytes = messageDigest.digest();
        return new String(Base64.encode(hashBytes));
    }
    public String hashText(String text) throws IOException, NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(text.getBytes());
        hashBytes = messageDigest.digest();
        return new String(Base64.encode(hashBytes));
    }

    public static void main(String[] args) {
        MD5 md5 = new MD5();
        try {
            LocalDateTime timeStart = LocalDateTime.now();

            System.out.println(md5.hashAFile("C:\\Users\\Yukihira Souma\\Desktop\\UpdateProduct bản nháp 4-4-2024.txt"));
            LocalDateTime timeEnd = LocalDateTime.now();
            System.out.println("During time: " + (Duration.between(timeStart, timeEnd).toMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
