package Model.Screen;

import java.io.File;
import java.security.AsymmetricKey;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HashScreen_Model implements ModelObservable {
    private String algorithm;
    private String inputText;
    private File file;
    private List<ScreenObserver> observers = new ArrayList<>();
    static List<String> availableAlgorithm = new ArrayList<>(){{
        add("MD5");
        add("SHA-1");
        add("SHA-256");
        add("SHA-384");
        add("SHA-512");
        add("SHA3-256");
        add("SHA3-512");
    }};

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    // Đăng ký Observer
    public void addObserver(ScreenObserver observer) {
        observers.add(observer);
    }

    // Hủy đăng ký Observer
    public void removeObserver(ScreenObserver observer) {
        observers.remove(observer);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public List<String> getAvailableAlgorithm() {
        return availableAlgorithm;
    }

    public void notifyObservers(String event, Map<String, Object> data) {
        for (ScreenObserver observer : observers) {
            observer.update(event, data);
        }
    }
}
