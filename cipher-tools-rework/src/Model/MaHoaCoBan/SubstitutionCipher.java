package Model.MaHoaCoBan;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SubstitutionCipher {

    private static final String ALPHABET = "aáàảãạăắằẳẵặâấầẩẫậbcdđeéèẻẽẹêếềểễệfghiíìỉĩịjklmnoóòỏõọôốồổỗộơớờởỡợpqrstuúùủũụưứừửữựvwxyýỳỷỹỵz";
    private Map<Character, Character> substitutionMap = new HashMap<>();
    private Map<Character, Character> reverseMap = new HashMap<>();

    // Khởi tạo bảng hoán vị ngẫu nhiên
    public SubstitutionCipher() {
        generateRandomSubstitution();
    }

    // Phương thức để tạo bảng hoán vị ngẫu nhiên
    private void generateRandomSubstitution() {
        StringBuilder shuffledAlphabet = new StringBuilder(ALPHABET);
        Random random = new Random();

        for (int i = 0; i < ALPHABET.length(); i++) {
            int randomIndex = random.nextInt(ALPHABET.length());
            char temp = shuffledAlphabet.charAt(i);
            shuffledAlphabet.setCharAt(i, shuffledAlphabet.charAt(randomIndex));
            shuffledAlphabet.setCharAt(randomIndex, temp);
        }

        for (int i = 0; i < ALPHABET.length(); i++) {
            substitutionMap.put(ALPHABET.charAt(i), shuffledAlphabet.charAt(i));
            reverseMap.put(shuffledAlphabet.charAt(i), ALPHABET.charAt(i));
        }
    }

    // Mã hóa văn bản bằng cách thay thế ký tự theo bảng hoán vị
    public String encrypt(String text) {
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (substitutionMap.containsKey(ch)) {
                result.append(substitutionMap.get(ch));
            } else {
                result.append(ch); // Ký tự không phải chữ giữ nguyên
            }
        }

        return result.toString();
    }

    // Giải mã văn bản bằng cách thay thế ký tự ngược lại
    public String decrypt(String text) {
        StringBuilder result = new StringBuilder();

        for (char ch : text.toCharArray()) {
            if (reverseMap.containsKey(ch)) {
                result.append(reverseMap.get(ch));
            } else {
                result.append(ch); // Ký tự không phải chữ giữ nguyên
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        SubstitutionCipher cipher = new SubstitutionCipher();

        String text = "Hello World!";

        String encryptedText = cipher.encrypt(text);
        System.out.println("Văn bản mã hóa: " + encryptedText);

        String decryptedText = cipher.decrypt(encryptedText);
        System.out.println("Văn bản giải mã: " + decryptedText);
    }
}
