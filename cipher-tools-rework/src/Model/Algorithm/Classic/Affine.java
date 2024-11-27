package Model.Algorithm.Classic;

import java.util.Random;

public class Affine {

    private int a;
    private int b;
    private String alphabet;
    private int m;

    public Affine() {
    }

    public void loadKey(int a, int b, Alphabet language) {
        if (gcd(a, language.getAlphabet().length()) != 1) {
            throw new IllegalArgumentException("Key 'a' must be coprime with the length of the alphabet.");
        }
        this.a = a;
        this.b = b;
        this.alphabet = language.getAlphabet();
        this.m = alphabet.length();
    }

    public void generateKey(Alphabet language) {
        this.alphabet = language.getAlphabet();
        this.m = alphabet.length();

        Random random = new Random();
        do {
            a = random.nextInt(m);
        } while (gcd(a, m) != 1);

        b = random.nextInt(m);
    }

    public String encrypt(String plainText) {
        StringBuilder cipherText = new StringBuilder();

        for (char c : plainText.toCharArray()) {
            int index = alphabet.indexOf(Character.toUpperCase(c));
            if (index != -1) {
                int encryptedIndex = (a * index + b) % m;
                char encryptedChar = alphabet.charAt(encryptedIndex);
                cipherText.append(Character.isLowerCase(c) ? Character.toLowerCase(encryptedChar) : encryptedChar);
            } else {
                cipherText.append(c);
            }
        }

        return cipherText.toString();
    }

    public String decrypt(String cipherText) {
        StringBuilder plainText = new StringBuilder();
        int aInverse = modularInverse(a, m);

        for (char c : cipherText.toCharArray()) {
            int index = alphabet.indexOf(Character.toUpperCase(c));
            if (index != -1) {
                int decryptedIndex = (aInverse * (index - b + m)) % m;
                char decryptedChar = alphabet.charAt(decryptedIndex);
                plainText.append(Character.isLowerCase(c) ? Character.toLowerCase(decryptedChar) : decryptedChar);
            } else {
                plainText.append(c);
            }
        }

        return plainText.toString();
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Tính nghịch đảo modular của a theo modulo m
    private int modularInverse(int a, int m) {
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        throw new IllegalArgumentException("No modular inverse for a under modulo " + m);
    }

    public static void main(String[] args) {
        String plainTextEn = "KHOA CONG NGHE THONG TIN";
        String plainTextVi = "Nguyễn quốc anh tuấn";

        // Affine cipher for English
        Affine affineEn = new Affine();
        affineEn.loadKey(7, 3, Alphabet.EN);
        System.out.println("Generated English Key - a: " + affineEn.a + ", b: " + affineEn.b);
        String cipherTextEn = affineEn.encrypt(plainTextEn);
        System.out.println("English Cipher Text: " + cipherTextEn);
        System.out.println("Decrypted English: " + affineEn.decrypt(cipherTextEn));

        // Affine cipher for Vietnamese
        Affine affineVi = new Affine();
        affineVi.generateKey(Alphabet.VN); // Tạo khóa ngẫu nhiên cho tiếng Việt
        System.out.println("Generated Vietnamese Key - a: " + affineVi.a + ", b: " + affineVi.b);
        String cipherTextVi = affineVi.encrypt(plainTextVi);
        System.out.println("Vietnamese Cipher Text: " + cipherTextVi);
        System.out.println("Decrypted Vietnamese: " + affineVi.decrypt(cipherTextVi));
    }
}
