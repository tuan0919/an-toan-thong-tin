package Model.Screen;

import java.io.File;
import java.security.AsymmetricKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SignatureScreen_Model implements ModelObservable {
    public static final int INPUT_FILE = 1;
    public static final int INPUT_TEXT = 2;
    private File file;
    private int currentMode;
    private List<ScreenObserver> observers = new ArrayList<>();
    private String publicKey;
    private String privateKey;
    private Class<? extends AsymmetricKey> usingKey;
    private String inputSignature;
    static List<String> availableAlgorithm = new ArrayList<>(){{
        add("MD5");
        add("SHA-1");
        add("SHA-256");
        add("SHA-384");
        add("SHA-512");
        add("SHA3-256");
        add("SHA3-512");
    }};
    static List<Integer> availableKeySize = new ArrayList<>(){{
        add(1024);
        add(2028);
        add(3072);
        add(4096);
    }};

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public Class<? extends AsymmetricKey> getUsingKey() {
        return usingKey;
    }

    public String getInputSignature() {
        return inputSignature;
    }

    public static List<String> getAvailableAlgorithm() {
        return availableAlgorithm;
    }

    public static List<Integer> getAvailableKeySize() {
        return availableKeySize;
    }

    public int getCurrentMode() {
        return currentMode;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setCurrentMode(int currentMode) {
        this.currentMode = currentMode;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setUsingKey(Class<? extends AsymmetricKey> usingKey) {
        this.usingKey = usingKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public void setInputSignature(String inputSignature) {
        this.inputSignature = inputSignature;
    }

    public File getFile() {
        return file;
    }

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
}
