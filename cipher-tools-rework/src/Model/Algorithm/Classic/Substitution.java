package Model.Algorithm.Classic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

public class Substitution {

    private String alphabet;
    private Map<Character, Character> substitutionMap;
    private Map<Character, Character> reverseMap;

    public Substitution() {
        this.substitutionMap = new HashMap<>();
        this.reverseMap = new HashMap<>();
    }

    public void generateKey(Alphabet language) {
        this.alphabet = language.getAlphabet();

        StringBuilder shuffledAlphabet = new StringBuilder(alphabet);
        Random random = new Random();

        // Xáo trộn bảng chữ cái
        for (int i = 0; i < alphabet.length(); i++) {
            int swapIndex = random.nextInt(alphabet.length());
            char temp = shuffledAlphabet.charAt(i);
            shuffledAlphabet.setCharAt(i, shuffledAlphabet.charAt(swapIndex));
            shuffledAlphabet.setCharAt(swapIndex, temp);
        }

        // Tạo bảng ánh xạ mã hóa và giải mã
        for (int i = 0; i < alphabet.length(); i++) {
            char originalChar = alphabet.charAt(i);
            char mappedChar = shuffledAlphabet.charAt(i);
            substitutionMap.put(originalChar, mappedChar);
            reverseMap.put(mappedChar, originalChar);
        }
    }

    public void loadKey(String key) {
        this.alphabet = key;

        for (int i = 0; i < alphabet.length(); i++) {
            char originalChar = alphabet.charAt(i);
            char mappedChar = key.charAt(i);
            substitutionMap.put(originalChar, mappedChar);
            reverseMap.put(mappedChar, originalChar);
        }
    }

    public String encrypt(String plainText) {
        StringBuilder cipherText = new StringBuilder();

        for (char c : plainText.toCharArray()) {
            char upperC = Character.toUpperCase(c);
            if (substitutionMap.containsKey(upperC)) {
                char mappedChar = substitutionMap.get(upperC);
                cipherText.append(Character.isLowerCase(c) ? Character.toLowerCase(mappedChar) : mappedChar);
            } else {
                cipherText.append(c);
            }
        }

        return cipherText.toString();
    }

    public String decrypt(String cipherText) {
        StringBuilder plainText = new StringBuilder();

        for (char c : cipherText.toCharArray()) {
            char upperC = Character.toUpperCase(c);
            if (reverseMap.containsKey(upperC)) {
                char mappedChar = reverseMap.get(upperC);
                plainText.append(Character.isLowerCase(c) ? Character.toLowerCase(mappedChar) : mappedChar);
            } else {
                plainText.append(c);
            }
        }

        return plainText.toString();
    }

    public static void main(String[] args) {
        String plainTextEn = "HELLO WORLD!";
        String plainTextVi = "á à ả ò ó o!";

        // Substitution cipher for English
        Substitution substitutionEn = new Substitution();
        substitutionEn.generateKey(Alphabet.EN);
        String cipherTextEn = substitutionEn.encrypt(plainTextEn);
        System.out.println("English Cipher Text: " + cipherTextEn);
        System.out.println("Decrypted English: " + substitutionEn.decrypt(cipherTextEn));

        // Substitution cipher for Vietnamese
        Substitution substitutionVi = new Substitution();
        substitutionVi.generateKey(Alphabet.VN);
        String cipherTextVi = substitutionVi.encrypt(plainTextVi);
        System.out.println("Vietnamese Cipher Text: " + cipherTextVi);
        System.out.println("Decrypted Vietnamese: " + substitutionVi.decrypt(cipherTextVi));
        System.out.println("used alphabet: "+substitutionVi.alphabet);
        String test = "aăâbcdđeêghiklmnọôơpqrstuưvxyáàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵ";
        System.out.println();
        for (var c : test.toCharArray()) {
            System.out.print(Character.toUpperCase(c));
        }
        Character character = test.charAt(2);
        System.out.println();
        System.out.println(character == 'â');
    }
}
