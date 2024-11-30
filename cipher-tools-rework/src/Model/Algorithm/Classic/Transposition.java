package Model.Algorithm.Classic;

import java.util.regex.Pattern;

public class Transposition {
    private String key;
    private int[] keyOrder;
    private char padding;

    // Load an existing key
    public String loadKey(String key, char padding) {
        this.key = key.toUpperCase(); // Giữ khoảng trắng trong key
        this.padding = padding;
        generateKeyOrder();
        return this.key;
    }

    public String generateKey(String word) {
        return word.toUpperCase();
    }

    // Encrypt plaintext using the current key
    public String encrypt(String plaintext) {
        if (key == null || keyOrder == null) {
            throw new IllegalStateException("Key chưa được load.");
        }

        int columns = key.replaceAll("\\s+", "").length(); // Dùng key không khoảng trắng cho grid
        int rows = (int) Math.ceil((double) plaintext.length() / columns);

        char[][] grid = new char[rows][columns];
        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = (index < plaintext.length()) ? plaintext.charAt(index++) : padding;
            }
        }

        StringBuilder ciphertext = new StringBuilder();
        for (int col : keyOrder) {
            for (int row = 0; row < rows; row++) {
                ciphertext.append(grid[row][col]);
            }
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext) {
        if (key == null || keyOrder == null) {
            throw new IllegalStateException("Key chưa sẵn có.");
        }

        int columns = key.replaceAll("\\s+", "").length(); // Dùng key không khoảng trắng cho grid
        int rows = (int) Math.ceil((double) ciphertext.length() / columns);

        char[][] grid = new char[rows][columns];
        int index = 0;

        for (int col : keyOrder) {
            for (int row = 0; row < rows; row++) {
                grid[row][col] = ciphertext.charAt(index++);
            }
        }

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                plaintext.append(grid[i][j]);
            }
        }
        return plaintext.toString().replaceAll(Pattern.quote(Character.toString(padding)) + "+$", "");
    }

    private void generateKeyOrder() {
        String sanitizedKey = key.replaceAll("\\s+", ""); // Loại bỏ khoảng trắng chỉ khi tạo thứ tự
        keyOrder = new int[sanitizedKey.length()];
        Character[] keyArray = new Character[sanitizedKey.length()];

        for (int i = 0; i < sanitizedKey.length(); i++) {
            keyArray[i] = sanitizedKey.charAt(i);
        }

        Character[] sortedKey = keyArray.clone();
        java.util.Arrays.sort(sortedKey);

        boolean[] taken = new boolean[sanitizedKey.length()];
        for (int i = 0; i < sanitizedKey.length(); i++) {
            for (int j = 0; j < sanitizedKey.length(); j++) {
                if (!taken[j] && keyArray[i].equals(sortedKey[j])) {
                    keyOrder[i] = j;
                    taken[j] = true;
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Transposition cipher = new Transposition();
        cipher.loadKey("Đại học nông lâm", '+');  // Key có chứa khoảng trắng

        String plaintext = "HELLO WORLD";
        String encrypted = cipher.encrypt(plaintext);
        System.out.println("Encrypted: " + encrypted);

        String decrypted = cipher.decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
