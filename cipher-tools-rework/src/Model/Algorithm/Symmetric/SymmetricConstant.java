package Model.Algorithm.Symmetric;

public enum SymmetricConstant {
    AES("AES", 128, 128, 192, 256),        // AES hỗ trợ 128, 192, 256 bits key size
    Camellia("Camellia", 128, 128, 192, 256), // Camellia hỗ trợ 128, 192, 256 bits key size
    DES("DES", 64, 64),                   // DES hỗ trợ 64 bits key size
    TRIPLE_DES("3DES", 64, 128, 192),      // 3DES hỗ trợ 128, 192 bits key size
    IDEA("IDEA", 64, 128);                // IDEA hỗ trợ 128 bits key size

    private String name;
    private int ivSize;  // IV size in bits
    private int[] validKeySizes;  // Key sizes in bits

    // Constructor
    SymmetricConstant(String name, int ivSize, int... validKeySizes) {
        this.name = name;
        this.ivSize = ivSize;
        this.validKeySizes = validKeySizes;
    }

    public String getName() {
        return name;
    }

    public int getIvSize() {
        return ivSize;
    }

    public int[] getValidKeySizes() {
        return validKeySizes;
    }

    // Kiểm tra keySize có hợp lệ không cho thuật toán này (key size in bits)
    public boolean isValidKeySize(int keySize) {
        for (int validSize : validKeySizes) {
            if (keySize == validSize) {
                return true;
            }
        }
        return false;
    }
}
