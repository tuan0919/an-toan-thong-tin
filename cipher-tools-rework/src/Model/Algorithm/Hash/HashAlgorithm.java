package Model.Algorithm.Hash;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class HashAlgorithm {

    public String hashText(String text, String algorithm) {
        try {
            // Tạo đối tượng MessageDigest với thuật toán được chỉ định
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            // Băm chuỗi text
            byte[] hashedBytes = digest.digest(text.getBytes());

            // Chuyển đổi mảng byte thành chuỗi hex
            return HexFormat.of().formatHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Unsupported algorithm: " + algorithm, e);
        }
    }

    public String hashFile(String filePath, String algorithm) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            // Tạo đối tượng MessageDigest với thuật toán được chỉ định
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            // Đọc file theo từng khối để băm
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            // Lấy kết quả băm cuối cùng
            byte[] hashedBytes = digest.digest();

            // Chuyển đổi mảng byte thành chuỗi hex
            return HexFormat.of().formatHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Unsupported algorithm: " + algorithm, e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + filePath, e);
        }
    }
    public static void main(String[] args) {
        HashAlgorithm hashAlgorithm = new HashAlgorithm();

        // Hash chuỗi văn bản
        String text = "Hello, World!";
        String hashTextResult = hashAlgorithm.hashText(text, "SHA-256");
        System.out.println("Hash of text: " + hashTextResult);

        // Hash file
        String filePath = "C:\\Users\\Nguyen Tuan\\Downloads\\test.txt"; // Thay bằng đường dẫn file thực tế
        String hashFileResult = hashAlgorithm.hashFile(filePath, "SHA3-512");
        System.out.println("Hash of file: " + hashFileResult);
    }
}
