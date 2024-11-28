package Controller;

import Model.Algorithm.Classic.*;
import Model.Screen.ClassicScreen_Model;
import MyException.ErrorType;
import MyException.MyAppException;
import Util.MyUtil;
import View.ClassicScreen_View;
import java.util.Map;

public class ClassicScreen_Controller extends AController<ClassicScreen_View> {
    private Hill hill;
    private Affine affine;
    private Vigenere vigenere;
    private Caesar caesar;
    private ClassicScreen_Model model;
    public ClassicScreen_Controller(ClassicScreen_View view) {
        super(view);
    }

    @Override
    protected void initialCallbacks() {
        view.onChangeAlgorithm(algorithmKey -> handleChangeAlgorithm(algorithmKey));
        view.onChangeAlphabet(alphabet -> model.setAlphabet(alphabet));
        view.onMatrixCellChange((x, data) -> handleMatrixKey_Change(data));
        view.onGenerateMatrixKey(size -> handleGenerateMatrixKey());
        view.onChangeMatrixSize(size -> model.setMatrixSize(size));
        view.onChangeAffineKey((key, name) -> handleChangeAffineKey(key, name));
        view.onInputTextChange(input -> model.setInputText(input));
        view.onEncryptButton_Click(a -> handleEncrypt());
        view.onDecryptButton_Click(a -> handleDecrypt());
        view.onChangeVigenereKey(vigenere -> model.setVigenereKey(vigenere));
        view.onChangeCaesarKey(caesarKey -> model.setCaesarKey(caesarKey));
    }

    private void handleChangeAffineKey(Integer key, String name) {
        switch (name) {
            case "a" -> model.setAffineKey_A(key);
            case "b" -> model.setAffineKey_B(key);
        }
    }

    private void handleDecrypt() {
        String input = model.getInputText();
        String algorithm = model.getAlgorithm();
        String plainText = "";
        switch (algorithm) {
            case "Hill" -> {
                plainText = hill.decrypt(input);
            }
            case "Affine" -> {
                Integer a = model.getAffineKey_A();
                Integer b = model.getAffineKey_B();
                if (a == null || b == null) {
                    throw new MyAppException(ErrorType.WRONG_AFFINE_KEY, view);
                }
                affine.loadKey(a, b, model.getAlphabet());
                plainText = affine.decrypt(input);
            }
            case "Vigenere" -> {
                String viegenereKey = model.getVigenereKey();
                vigenere.loadKey(viegenereKey, model.getAlphabet());
                plainText = vigenere.decrypt(input);
            }
            case "Caesar" -> {
                Integer caesarKey = model.getCaesarKey();
                caesar.loadKey(caesarKey, model.getAlphabet());
                plainText = caesar.decrypt(input);
            }
        }
        model.notifyObservers("decrypted", Map.of(
                "plain_text", plainText
        ));
    }

    private void handleEncrypt() {
        String input = model.getInputText();
        String algorithm = model.getAlgorithm();
        String cipherText = "";
        switch (algorithm) {
            case "Hill" -> {
                cipherText = hill.encrypt(input);
            }
            case "Affine" -> {
                Integer a = model.getAffineKey_A();
                Integer b = model.getAffineKey_B();
                if (a == null || b == null) {
                    throw new MyAppException(ErrorType.WRONG_AFFINE_KEY, view);
                }
                affine.loadKey(a, b, model.getAlphabet());
                cipherText = affine.encrypt(input);
            }
            case "Vigenere" -> {
                String viegenereKey = model.getVigenereKey();
                vigenere.loadKey(viegenereKey, model.getAlphabet());
                cipherText = vigenere.encrypt(input);
            }
            case "Caesar" -> {
                Integer caesarKey = model.getCaesarKey();
                caesar.loadKey(caesarKey, model.getAlphabet());
                cipherText = caesar.encrypt(input);
            }
        }
        model.notifyObservers("encrypted", Map.of(
                "cipher_text", cipherText
        ));
    }

    private void handleGenerateMatrixKey() {
        var size = model.getMatrixSize();
        if (size <= 0 || size >= 10) {
            throw new MyAppException(ErrorType.WRONG_MATRIX_SIZE, view);
        }
        var key = _createMatrix_(size, model.getAlphabet());
        handleMatrixKey_Change(key);
    }

    private void handleChangeAlgorithm(String algorithm) {
        model.setAlgorithm(algorithm);
        model.notifyObservers("change_algorithm", Map.of(
                "algorithm", algorithm
        ));
    }

    private void handleMatrixKey_Change(Integer[][] data) {
        model.setMatrixKey(data);
        model.notifyObservers("change_matrix_key", Map.of(
                "matrix_as_string", MyUtil.formatMatrix(data),
                "matrix", data
        ));
        hill.loadKey(data, model.getAlphabet());
    }

    private Integer[][] _createMatrix_(int size, String alphabet) {
        return hill.generateRandomKey(size, alphabet);
    }

    private void reloadAlgorithm() {
        String algorithm = model.getAlgorithm();
        switch (algorithm) {
            case "Hill" -> {

            }
        }
    }

    @Override
    protected void initialModels() {
        this.model = new ClassicScreen_Model();
        this.hill = new Hill();
        this.affine = new Affine();
        this.vigenere = new Vigenere();
        this.caesar = new Caesar();
        model.addObserver(view);
        model.notifyObservers("change_algorithm", Map.of(
                "algorithm", ClassicScreen_Model.AFFINE_ALGORITHM
        ));
        model.setAlphabet(Alphabet.EN.getAlphabet());
        model.notifyObservers("change_alphabet", Map.of(
                "alphabet_index", 0
        ));
        var key = _createMatrix_(5, model.getAlphabet());
        handleMatrixKey_Change(key);
    }

}
