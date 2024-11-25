package Model.Algorithm.Hash.SHA;

import org.bouncycastle.util.encoders.Base64;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDateTime;

public class SHA {
    private MessageDigest messageDigest;
    private byte[] hashBytes;
    private BufferedInputStream bufferedInputStream;


    public String hashAFile(String filePath, String typeSHA) throws IOException, NoSuchAlgorithmException {
        bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
        byte [] byteReaded = new byte[1024];
        messageDigest = MessageDigest.getInstance(typeSHA);
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
    public String hashText(String text,String typeSHA) throws IOException, NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance(typeSHA);
        hashBytes = messageDigest.digest(text.getBytes());
        return new String(Base64.encode(hashBytes));
    }

    public static void main(String[] args) {
        SHA sha = new SHA();
        try {
            LocalDateTime timeStart = LocalDateTime.now();
            String typeSha = "SHA3-256";
            System.out.println(sha.hashAFile("C:\\Users\\Yukihira Souma\\Desktop\\UpdateProduct bản nháp 4-4-2024.txt", typeSha));
            LocalDateTime timeEnd = LocalDateTime.now();
            System.out.println("During time: " + (Duration.between(timeStart, timeEnd).toMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
