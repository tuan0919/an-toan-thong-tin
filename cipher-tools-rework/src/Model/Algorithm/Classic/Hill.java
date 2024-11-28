package Model.Algorithm.Classic;

import java.util.Arrays;
import java.util.Random;

public class Hill {
    private Integer[][] key;
    private int size;
    private String alphabet;
    private int m;
    private final static char PADDING = 'Z';
    private Random random = new Random();

    public void loadKey(Integer[][] key, Alphabet language) {
        this.loadKey(key, language.getAlphabet());
    }

    public void loadKey(Integer[][] key, String languageAsString) {
        this.key = key;
        this.size = key.length;
        this.alphabet = languageAsString;
        this.m = alphabet.length();
    }
    public Integer[][] generateRandomKey(int size, String alphabet) {
        int m = alphabet.length();
        Integer[][] newKey = new Integer[0][0];
        boolean validKey = false;

        while (!validKey) {
            newKey = new Integer[size][size];

            // Tạo ma trận ngẫu nhiên
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    newKey[i][j] = random.nextInt(m);  // Giá trị từ 0 đến m-1
                }
            }

            // Kiểm tra ma trận hợp lệ
            try {
                if (determinant(newKey, m) != 0) {
                    invertKeyMatrix(newKey, m); // Kiểm tra tính nghịch đảo, nếu lỗi sẽ ném exception
                    validKey = true;
                }
            } catch (IllegalArgumentException ex) {
                // Ignore and generate another key
            }
        }
        return newKey;
    }

    public String encrypt(String plainText) {
        return multiply(plainText, key);
    }

    public String decrypt(String cipherText) {
        Integer[][] inverseKey = invertKeyMatrix(key, m);
        String result = multiply(cipherText, inverseKey);
        int index = result.indexOf('=');

        return index != -1 ? result.substring(0, index) : result;
    }

    private String multiply(String text, Integer[][] key) {
        String originalText = text;

        text = text.chars()
                .mapToObj(c -> String.valueOf(Character.toUpperCase((char) c)))
                .filter(alphabet::contains)
                .reduce("", String::concat);

        int length = text.length();
        int row = length % size == 0 ? length / size : (length + size - length % size) / size;
        StringBuilder res = new StringBuilder();
        int[] subBlock = new int[size];

        for (int i = 0, cursor = 0, b = 0; i < row; i++) {
            for (int j = 0; j < size; j++) {
                int index = j + i * size;

                subBlock[j] = alphabet.indexOf(index < length ? text.charAt(j + i * size) : 'Z');
            }

            for (int j = 0; j < key.length; j++) {
                int num = 0;
                for (int k = 0; k < subBlock.length; k++) {
                    num += key[j][k] * subBlock[k];
                }
                num %= m;

                while (cursor < originalText.length() && alphabet.indexOf(Character.toUpperCase(originalText.charAt(cursor))) == -1) {
                    res.append(originalText.charAt(cursor));
                    cursor++;
                }

                char c = alphabet.charAt(num);
                if (cursor < originalText.length() && Character.isLowerCase(originalText.charAt(cursor))) {
                    c = Character.toLowerCase(c);
                }
                cursor++;

                if (b == 0 && j + i * size == length) {
                    res.append('=');
                    b++;
                }

                res.append(c);
            }
        }

        return res.toString();
    }

    private Integer[][] invertKeyMatrix(Integer[][] key, int mod) {
        int n = key.length;

        // Tính định thức modulo m
        int det = determinant(key, mod);
        if (det == 0) {
            throw new IllegalArgumentException("Matrix determinant is 0, cannot find inverse.");
        }

        int detInverse = modularInverse(det, mod);
        if (detInverse == -1) {
            throw new IllegalArgumentException("Determinant has no modular inverse under modulo " + mod);
        }

        Integer[][] adj = adjugate(key, mod);

        Integer[][] inverse = new Integer[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = (adj[i][j] * detInverse) % mod;
                if (inverse[i][j] < 0) {
                    inverse[i][j] += mod;
                }
            }
        }

        return inverse;
    }

    private int determinant(Integer[][] matrix, int mod) {
        int n = matrix.length;
        if (n == 1) return matrix[0][0] % mod;

        if (n == 2) {
            return ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0])) % mod;
        }

        int det = 0;
        for (int i = 0; i < n; i++) {
            Integer[][] minor = getMinor(matrix, 0, i);
            det += matrix[0][i] * determinant(minor, mod) * (i % 2 == 0 ? 1 : -1);
            det %= mod;
        }
        return (det + mod) % mod;
    }

    private Integer[][] adjugate(Integer[][] matrix, int mod) {
        int n = matrix.length;
        Integer[][] adj = new Integer[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Integer[][] minor = getMinor(matrix, i, j);
                int cofactor = determinant(minor, mod) * ((i + j) % 2 == 0 ? 1 : -1);
                adj[j][i] = (cofactor % mod + mod) % mod;
            }
        }

        return adj;
    }

    private Integer[][] getMinor(Integer[][] matrix, int row, int col) {
        int n = matrix.length;
        Integer[][] minor = new Integer[n - 1][n - 1];
        for (int i = 0, mi = 0; i < n; i++) {
            if (i == row) continue;
            for (int j = 0, mj = 0; j < n; j++) {
                if (j == col) continue;
                minor[mi][mj++] = matrix[i][j];
            }
            mi++;
        }
        return minor;
    }

    private int modularInverse(int a, int mod) {
        a = a % mod;
        for (int x = 1; x < mod; x++) {
            if ((a * x) % mod == 1) {
                return x;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Hill hill = new Hill();
        String plainText = "Đă Đâ Đư Đô";
        var key = hill.generateRandomKey(9, Alphabet.VN.getAlphabet());
        hill.loadKey(key, Alphabet.VN);
        System.out.println("key: "+ Arrays.deepToString(key));
        System.out.println("encrypted: "+hill.encrypt(plainText));
        System.out.println("decrypted: "+hill.decrypt(hill.encrypt(plainText)));
    }
}
