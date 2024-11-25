package Model.Screen;

import java.security.AsymmetricKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

public class AsymmetricScreen_Model implements ModelObservable {
    private String publicKey;
    private String privateKey;
    private Class<? extends AsymmetricKey> usingKey;
    private String inputText;
    private String outputText;
    private List<ScreenObserver> observers = new ArrayList<>();
    private int keySize;

    static List<Integer> availableKeySize = new ArrayList<>(){{
        add(1024);
        add(2028);
        add(3072);
        add(4096);
    }};

    // Đăng ký Observer
    public void addObserver(ScreenObserver observer) {
        observers.add(observer);
    }

    // Hủy đăng ký Observer
    public void removeObserver(ScreenObserver observer) {
        observers.remove(observer);
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int keySize) {
        var index = availableKeySize.indexOf(keySize);
        this.keySize = keySize;
    }

    public Class<? extends AsymmetricKey> getUsingKey() {
        return usingKey;
    }

    public void setUsingKey(Class<? extends AsymmetricKey> usingKey) {
        this.usingKey = usingKey;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public String getOutputText() {
        return outputText;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public void notifyObservers(String event, Map<String, Object> data) {
        for (ScreenObserver observer : observers) {
            observer.update(event, data);
        }
    }

    public void initialize() {
        notifyObservers("first_load", Map.of(
                "available_key_size", availableKeySize
        ));
        setUsingKey(PrivateKey.class);
    }
}
