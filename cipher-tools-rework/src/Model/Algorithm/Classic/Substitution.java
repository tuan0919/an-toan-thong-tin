package Model.Algorithm.Classic;

import Model.Algorithm.Classic.Alphabet;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Substitution {

    private String alphabet;
    private Map<Character, Character> substitutionMap;
    private Map<Character, Character> reverseMap;

    public Substitution() {
        this.substitutionMap = new HashMap<>();
        this.reverseMap = new HashMap<>();
    }

    public String generateKey(String language) {
        StringBuilder shuffledAlphabet = new StringBuilder(language);
        Random random = new Random();

        // Xáo trộn bảng chữ cái
        for (int i = 0; i < language.length(); i++) {
            int swapIndex = random.nextInt(language.length());
            char temp = shuffledAlphabet.charAt(i);
            shuffledAlphabet.setCharAt(i, shuffledAlphabet.charAt(swapIndex));
            shuffledAlphabet.setCharAt(swapIndex, temp);
        }
        return shuffledAlphabet.toString();
    }

    public void loadKey(String key) {
        if (key.length() != alphabet.length()) {
            throw new IllegalArgumentException("Key length must match alphabet length");
        }

        for (int i = 0; i < alphabet.length(); i++) {
            char originalChar = alphabet.charAt(i);
            char mappedChar = key.charAt(i);
            substitutionMap.put(originalChar, mappedChar);
            reverseMap.put(mappedChar, originalChar);
        }
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet; // Tạo cơ sở mapping động cho bảng mã hóa.
    }

    public String encrypt(String plainText) {
        StringBuilder cipherText = new StringBuilder();

        for (char c : plainText.toCharArray()) {
            if (substitutionMap.containsKey(c)) {
                cipherText.append(substitutionMap.get(c));
            } else {
                cipherText.append(c);
            }
        }

        return cipherText.toString();
    }

    public String decrypt(String cipherText) {
        StringBuilder plainText = new StringBuilder();

        for (char c : cipherText.toCharArray()) {
            if (reverseMap.containsKey(c)) {
                plainText.append(reverseMap.get(c));
            } else {
                plainText.append(c);
            }
        }

        return plainText.toString();
    }

    public static void main(String[] args) {
        Substitution substitutionEn = new Substitution();

        // English example
        String plainTextEn = "HELLO WORLD!";
        substitutionEn.setAlphabet(Alphabet.EN.getAlphabet());  // Set base ALPHABET
        String key = substitutionEn.generateKey(Alphabet.EN.getAlphabet());
        substitutionEn.loadKey(key);

        System.out.println("Key: " + key);
        String cipherText = substitutionEn.encrypt(plainTextEn);
        System.out.println("Cipher: " + cipherText);
        System.out.println("Decrypted: " + substitutionEn.decrypt(cipherText));

        // Vietnamese example
        Substitution substitutionVi = new Substitution();
        substitutionVi.setAlphabet(Alphabet.VN.getAlphabet());
        String plainTextVi = "á à ả ò ó o!";
        String viKey = substitutionVi.generateKey(Alphabet.VN.getAlphabet());
        substitutionVi.loadKey(viKey);
        System.out.println("Vietnamese key: " + viKey);

        String vietCipher = substitutionVi.encrypt(plainTextVi);
        System.out.println("Vietnamese Cipher: " + vietCipher);
        System.out.println("Decrypted VN: " + substitutionVi.decrypt(vietCipher));
    }
}
