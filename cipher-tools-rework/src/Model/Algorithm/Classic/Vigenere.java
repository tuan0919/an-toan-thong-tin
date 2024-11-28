package Model.Algorithm.Classic;

import java.util.Random;

public class Vigenere {

    private String alphabet;
    private int m;
    private String key;

    public Vigenere() {
    }

    public void loadKey(String key, Alphabet language) {
        loadKey(key, language.getAlphabet());
    }

    public void loadKey(String key, String alphabet) {
        this.alphabet = alphabet;
        this.m = alphabet.length();
        this.key = key.toUpperCase();
    }

    public String generateKey(int length, Alphabet language) {
        this.alphabet = language.getAlphabet();

        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < length; i++) {
            key.append(alphabet.charAt(random.nextInt(m)));
        }
        return key.toString();
    }

    public String encrypt(String plainText) {
        StringBuilder cipherText = new StringBuilder();
        String originalText = plainText;

        plainText = plainText.toUpperCase();

        int keyIndex = 0;

        for (char c : plainText.toCharArray()) {
            if (alphabet.indexOf(c) != -1) {
                int textIndex = alphabet.indexOf(c);
                int keyIndexMod = alphabet.indexOf(key.charAt(keyIndex % key.length()));
                int encryptedIndex = (textIndex + keyIndexMod) % m;
                cipherText.append(alphabet.charAt(encryptedIndex));
                keyIndex++;
            } else {
                cipherText.append(c);
            }
        }

        return preserveCase(originalText, cipherText.toString());
    }

    public String decrypt(String cipherText) {
        StringBuilder plainText = new StringBuilder();
        String originalText = cipherText;

        cipherText = cipherText.toUpperCase();

        int keyIndex = 0;

        for (char c : cipherText.toCharArray()) {
            if (alphabet.indexOf(c) != -1) {
                int textIndex = alphabet.indexOf(c);
                int keyIndexMod = alphabet.indexOf(key.charAt(keyIndex % key.length()));
                int decryptedIndex = (textIndex - keyIndexMod + m) % m;
                plainText.append(alphabet.charAt(decryptedIndex));
                keyIndex++;
            } else {
                plainText.append(c);
            }
        }

        return preserveCase(originalText, plainText.toString());
    }

    private String preserveCase(String originalText, String transformedText) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < originalText.length(); i++) {
            char originalChar = originalText.charAt(i);
            char transformedChar = transformedText.charAt(i);

            if (Character.isUpperCase(originalChar)) {
                result.append(Character.toUpperCase(transformedChar));
            } else if (Character.isLowerCase(originalChar)) {
                result.append(Character.toLowerCase(transformedChar));
            } else {
                result.append(transformedChar);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String plainTextEn = "thisCryptosystemIsNotSecure";
        Vigenere vigenereCipher = new Vigenere();

        vigenereCipher.loadKey("CIPHER", Alphabet.EN);
        String cpt = vigenereCipher.encrypt(plainTextEn);
        System.out.println("Cipher Text: " + cpt);
        System.out.println("Decrypted Text: " + vigenereCipher.decrypt(cpt));
    }
}
