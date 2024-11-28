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
    private Integer[][] matrixKey;
    private int matrixSize;
    private String inputText;
    private Integer affineKey_A;
    private Integer affineKey_B;
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

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
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

    public Integer[][] getMatrixKey() {
        return matrixKey;
    }

    public void setMatrixKey(Integer[][] matrixKey) {
        this.matrixKey = matrixKey;
    }

    public int getMatrixSize() {
        return matrixSize;
    }

    public void setMatrixSize(int matrixSize) {
        this.matrixSize = matrixSize;
    }

    public Integer getAffineKey_A() {
        return affineKey_A;
    }

    public void setAffineKey_A(Integer affineKey_A) {
        this.affineKey_A = affineKey_A;
    }

    public Integer getAffineKey_B() {
        return affineKey_B;
    }

    public void setAffineKey_B(Integer affineKey_B) {
        this.affineKey_B = affineKey_B;
    }
}
