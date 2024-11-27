package Model.Screen;

import Model.Algorithm.Classic.Alphabet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassicScreen_Model implements ModelObservable {
    public static final String CAESAR_ALGORITHM = "Caesar";
    public static final String AFFINE_ALGORITHM = "Affine";
    public static final String VIGENERE_ALGORITHM = "Vigenere";
    public static final String SUBSTITUTION_ALGORITHM = "Substitution";
    public static final String HILL_ALGORITHM = "Hill";
    private List<ScreenObserver> observers = new ArrayList<>();
    private String algorithm;
    private String alphabet;
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

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
