package Model.Screen;

import java.io.File;
import java.util.*;

public class SymmetricScreen_Model implements ModelObservable {
    private String mode;
    private String padding;
    private String algorithm;
    private File chooseFile = null;
    private String key = null;
    private String iv;
    private int keySize;
    private String inputText;
    private String saveFilePath;
    private List<ScreenObserver> observers = new ArrayList<>();

    // Đăng ký Observer
    public void addObserver(ScreenObserver observer) {
        observers.add(observer);
    }

    // Hủy đăng ký Observer
    public void removeObserver(ScreenObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String event, Map<String, Object> data) {
        for (ScreenObserver observer : observers) {
            observer.update(event, data);
        }
    }

    static final List<String> availableModes = new ArrayList<String>() {{
        add("ECB");
        add("CBC");
        add("CFB");
        add("OFB");
        add("CTR");
    }};

    static final Map<String, List<String>> availableAlgorithm = new HashMap<>() {{
        put("ECB", Arrays.asList("AES", "DES", "3DES", "IDEA", "Camellia"));
        put("CBC", Arrays.asList("AES", "DES", "3DES", "IDEA", "Camellia"));
        put("CFB", Arrays.asList("AES", "DES", "3DES", "IDEA", "Camellia"));
        put("OFB", Arrays.asList("AES", "DES", "3DES", "IDEA", "Camellia"));
        put("CTR", Arrays.asList("AES", "DES", "3DES", "IDEA", "Camellia"));
    }};

    static final Map<String, List<String>> availablePadding = new HashMap<>() {{
        put("AES", Arrays.asList("PKCS5Padding", "NoPadding"));
        put("DES", Arrays.asList("PKCS5Padding", "NoPadding"));
        put("3DES", Arrays.asList("PKCS5Padding", "NoPadding"));
        put("IDEA", Arrays.asList("PKCS5Padding", "NoPadding"));
        put("Camellia", Arrays.asList("PKCS5Padding", "NoPadding"));
    }};

    // Khởi tạo Map availableKeySize
    static final Map<String, List<Integer>> availableKeySize = new HashMap<>() {{
        put("AES", Arrays.asList(128, 192, 256));  // AES có các kích thước khóa 128, 192, 256 bits
        put("DES", Arrays.asList(56));             // DES có kích thước khóa 56 bits
        put("3DES", Arrays.asList(168));           // 3DES có kích thước khóa 168 bits
        put("IDEA", Arrays.asList(128));           // IDEA có kích thước khóa 128 bits
        put("Camellia", Arrays.asList(128, 192, 256));  // Camellia có các kích thước khóa 128, 192, 256 bits
    }};

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
        var available_modes = availableAlgorithm.get(mode);
        notifyObservers("change_mode", Map.of(
                "current_mode", mode,
                "available_algorithm", available_modes
        ));
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public void setKeySize(int keySize) {
        this.keySize = keySize;
    }

    public int getKeySize() {
        return keySize;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        var available_padding = availablePadding.get(algorithm);
        var available_key_size = availableKeySize.get(algorithm);
        notifyObservers("change_algorithm", Map.of(
                "current_algorithm", algorithm,
                "available_padding", available_padding,
                "available_key_size", available_key_size
        ));
    }

    public File getChooseFile() {
        return chooseFile;
    }

    public void setChooseFile(File chooseFile) {
        this.chooseFile = chooseFile;
        notifyObservers("change_file", Map.of(
                "file", Optional.ofNullable(chooseFile)
        ));
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getSaveFilePath() {
        return saveFilePath;
    }

    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    public void initialize() {
        notifyObservers("init_state", Map.of(
                "current_mode", "ECB",
                "available_mode", availableModes
        ));
        this.setMode("ECB");
    }
}
