package Model.Algorithm.Classic;

import java.util.Random;

public class Caesar {

    private String alphabet;
    private int key;

    public int generateKey(Alphabet language) {
        this.alphabet = language.alphabet();
        return new Random().nextInt(alphabet.length());
    }

    public void loadKey(int key, Alphabet language) {
        this.alphabet = language.alphabet();
        this.key = key % alphabet.length();
    }

    public String encrypt(String plainText) {
        StringBuilder cipherText = new StringBuilder();

        for (char c : plainText.toCharArray()) {
            int index = alphabet.indexOf(Character.toUpperCase(c));
            if (index != -1) {
                char encryptedChar = alphabet.charAt((index + key) % alphabet.length());
                cipherText.append(Character.isLowerCase(c) ? Character.toLowerCase(encryptedChar) : encryptedChar);
            } else {
                cipherText.append(c);
            }
        }

        return cipherText.toString();
    }

    public String decrypt(String cipherText) {
        StringBuilder plainText = new StringBuilder();

        for (char c : cipherText.toCharArray()) {
            int index = alphabet.indexOf(Character.toUpperCase(c));
            if (index != -1) {
                char decryptedChar = alphabet.charAt((index - key + alphabet.length()) % alphabet.length());
                plainText.append(Character.isLowerCase(c) ? Character.toLowerCase(decryptedChar) : decryptedChar);
            } else {
                plainText.append(c);
            }
        }

        return plainText.toString();
    }

    public static void main(String[] args) {
        String plainTextEn = "HELLO WORLD!";
        String plainTextVi = "Chào thế giới";

        Caesar caesar = new Caesar();

        // English encryption
        caesar.loadKey(3, Alphabet.EN);
        String cipherTextEn = caesar.encrypt(plainTextEn);
        System.out.println("English Cipher Text: " + cipherTextEn);
        System.out.println("Decrypted English: " + caesar.decrypt(cipherTextEn));

        // Vietnamese encryption
        caesar.loadKey(5, Alphabet.VN);
        String cipherTextVi = caesar.encrypt(plainTextVi);
        System.out.println("Vietnamese Cipher Text: " + cipherTextVi);
        System.out.println("Decrypted Vietnamese: " + caesar.decrypt(cipherTextVi));
    }
}
