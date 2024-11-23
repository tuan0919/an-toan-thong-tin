package Model.Algorithm.MaHoaCoBan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaesarCipher {

    private static final List<Character> ALPHABET = new ArrayList<>();
    private static int encryptionKey; // Lưu trữ khóa để sử dụng trong giải mã

    static {
        String characters = "aáàảãạăắằẳẵặâấầẩẫậbcdđeéèẻẽẹêếềểễệfghiíìỉĩịjklmnoóòỏõọôốồổỗộơớờởỡợpqrstuúùủũụưứừửữựvwxyýỳỷỹỵz";
        for (char c : characters.toCharArray()) {
            ALPHABET.add(c);
        }
    }

    public  String encrypt(String text) {
        // Random một key trong khoảng kích thước của bảng chữ cái
        Random random = new Random();
        encryptionKey = random.nextInt(ALPHABET.size()); // Random từ 0 đến ALPHABET.size() - 1

        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            int index = ALPHABET.indexOf(ch);
            if (index != -1) { // Ký tự nằm trong bảng tiếng Việt
                int newIndex = (index + encryptionKey) % ALPHABET.size();
                result.append(ALPHABET.get(newIndex));
            } else { // Ký tự không nằm trong bảng tiếng Việt, giữ nguyên
                result.append(ch);
            }
        }

        return result.toString();
    }

    public  String decrypt(String text) {
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            int index = ALPHABET.indexOf(ch);
            if (index != -1) { // Ký tự nằm trong bảng tiếng Việt
                int newIndex = (index - encryptionKey + ALPHABET.size()) % ALPHABET.size();
                result.append(ALPHABET.get(newIndex));
            } else { // Ký tự không nằm trong bảng chữ cái, giữ nguyên
                result.append(ch);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {

    }
}
