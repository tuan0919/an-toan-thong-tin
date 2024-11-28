package Controller;

import Model.Algorithm.Asymmetric.AsymmetricAlgorithm;
import Model.Algorithm.Hash.HashAlgorithm;
import Model.Screen.SignatureScreen_Model;
import MyException.ErrorType;
import MyException.MyAppException;
import View.SignatureScreen_View;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class SignatureScreen_Controller extends AController<SignatureScreen_View> {
    private SignatureScreen_Model model;
    private HashAlgorithm hashAlgorithm;
    private AsymmetricAlgorithm asymmetricAlgorithm;
    public SignatureScreen_Controller(SignatureScreen_View view) {
        super(view);
    }

    @Override
    protected void initialCallbacks() {
        view.onChangeInputType(newInput -> handleChangeInputMode(newInput));
        view.onChooseAlgorithm_ComboBox(algorithm -> handleChooseAlgorithm(algorithm));
        view.onChooseKeySize_ComboBox(size -> handleChooseKeySize(size));
        view.onGenerateKeyButton_Click(x -> handleGenerateKeyButton_Click());
        view.onInputTextChange(inputText -> model.setInputText(inputText));
        view.onCreateSignatureButton_Click(x -> handleCreateSignatureButton());
        view.onChooseUsageKey(clazz -> model.setUsingKey(clazz));
        view.onChangeSignatureInputText(signatureInput -> model.setInputSignature(signatureInput));
        view.onCheckingSignatureButton_Click(x -> handleCheckingSignatureButton_Click());
        view.onLoadFileButton_Click(x -> model.notifyObservers("open_file_chooser_for_chosen_file", Map.of()));
        view.onInputFileChosen(file -> model.setFile(file));
    }

    private void handleCheckingSignatureButton_Click() {
        int currentMode = model.getCurrentMode();
        try {
            String hash;
            String decryptHash;
            boolean isMatched = false;
            if (currentMode == SignatureScreen_Model.INPUT_TEXT) {
                hash = hashAlgorithm.hashText(model.getInputText(), model.getAlgorithm());
            } else {
                hash = hashAlgorithm.hashFile(model.getFile().getAbsolutePath(), model.getAlgorithm());
            }
            decryptHash = asymmetricAlgorithm.decryptText(model.getInputSignature(), model.getUsingKey());
            isMatched = hash.equals(decryptHash);
            if (isMatched) {
                JOptionPane.showMessageDialog(view, "Nội dung khớp với chữ ký", "Xác thực thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Nội dung không khớp với chữ ký", "Xác thực thất bại", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        }
    }

    private void handleCreateSignatureButton() {
        int currentMode = model.getCurrentMode();
        String currentAlgorithm = model.getAlgorithm();
        String hash;
        String signature = "";
        var clazz = model.getUsingKey();
        if (currentMode == SignatureScreen_Model.INPUT_TEXT) {
            String inputText = model.getInputText();
            hash = hashAlgorithm.hashText(inputText, currentAlgorithm);
        } else {
            File file = model.getFile();
            hash = hashAlgorithm.hashFile(file.getAbsolutePath(), currentAlgorithm);
        }
        try {
            signature = asymmetricAlgorithm.encryptText(hash, clazz);
            model.notifyObservers("created_signature", Map.of(
                    "signature_created", signature
            ));
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        }
    }

    private void handleGenerateKeyButton_Click() {
        try {
            int keySize = model.getKeySize();
            var keyPair = asymmetricAlgorithm.generateKeyPair(keySize);
            // load to asymmetric algorithm model
            asymmetricAlgorithm.setPrivateKey(keyPair.getPrivate());
            asymmetricAlgorithm.setPublicKey(keyPair.getPublic());
            String privateKey = asymmetricAlgorithm.getPrivateKeyAsString();
            String publicKey = asymmetricAlgorithm.getPublicKeyAsString();
            model.setPrivateKey(privateKey);
            model.setPublicKey(publicKey);
            model.notifyObservers("generated_key_pair", Map.of(
                    "generated_private_key", privateKey,
                    "generated_public_key", publicKey
            ));
        } catch (NoSuchAlgorithmException e) {
            throw new MyAppException(ErrorType.UNSUPPORTED_ALGORITHM, view);
        }
    }

    private void handleChooseKeySize(Integer size) {
        model.setKeySize(size);
    }

    private void handleChooseAlgorithm(String algorithm) {
        model.setAlgorithm(algorithm);
    }

    private void handleChangeInputMode(int newInput) {
        int currentMode = model.getCurrentMode();
        if (currentMode == newInput) return;
        model.setCurrentMode(newInput);
        model.notifyObservers("change_input_mode", Map.of(
                "current_input_mode", newInput
        ));
    }

    @Override
    protected void initialModels() {
        hashAlgorithm = new HashAlgorithm();
        asymmetricAlgorithm = new AsymmetricAlgorithm();
        model = new SignatureScreen_Model();
        model.addObserver(view);
        model.notifyObservers("first_load", Map.of(
                "available_algorithm", model.getAvailableAlgorithm(),
                "available_key_size", model.getAvailableKeySize()
        ));
        model.setCurrentMode(SignatureScreen_Model.INPUT_FILE);
        model.notifyObservers("change_input_mode", Map.of(
                "current_input_mode", SignatureScreen_Model.INPUT_FILE
        ));
    }

}
