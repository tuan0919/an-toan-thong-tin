package Controller;

import Model.Algorithm.Asymmetric.AsymmetricAlgorithm;
import Model.Screen.AsymmetricScreen_Model;
import MyException.ErrorType;
import MyException.MyAppException;
import View.AsymmetricScreen_View;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class AsymmetricScreen_Controller extends AController<AsymmetricScreen_View> {
    private AsymmetricScreen_Model model;
    private AsymmetricAlgorithm algorithm;

    public AsymmetricScreen_Controller(AsymmetricScreen_View view) {
        super(view);
    }

    @Override
    protected void initialCallbacks() {
        view.onGenerateKeyButtonClick(event -> handleGenerateKeyButtonClick(event));
        view.onSaveKeyButton_Click(event -> handleSaveKeyButton_Click(event));
        view.onChooseUsageKey(clazz -> model.setUsingKey(clazz));
        view.onChooseKeySize((size, index) -> model.setKeySize(size));
        view.onSavePrivateKeyButton_Click(a -> view.openJFileChooser_ForSavePrivateKey());
        view.onLoadPrivateKeyButton_Click(a -> view.openJFileChooser_ForLoadPrivateKey());
        view.onSavePublicKeyButton_Click(a -> view.openJFileChooser_ForSavePublicKey());
        view.onLoadPublicKeyButton_Click(a -> view.openJFileChooser_ForLoadPublicKey());
        view.onChooseLocation_ForLoadPrivateKey(file -> handleOnChooseLocation_ForLoadPrivateKey(file));
        view.onChooseLocation_ForSavePrivateKey(file -> handleOnChooseLocation_ForSavePrivateKey(file));
        view.onChooseLocation_ForSavePublicKey(file -> handleOnChooseLocation_ForSavePublicKey(file));
        view.onChooseLocation_ForLoadPublicKey(file -> handleOnChooseLocation_ForLoadPublicKey(file));
        view.onInputTextArea_LostFocus(text -> model.setInputText(text));
        view.onPrivateKeyTextArea_LostFocus(text -> model.setPrivateKey(text));
        view.onPublicKeyTextArea_LostFocus(text -> model.setPublicKey(text));
        view.onEncryptButton_Click(a -> handleEncryptButtonClick());
        view.onDecryptButton_Click(a -> handleDecryptButtonClick());
    }

    private void handleOnChooseLocation_ForLoadPrivateKey(File file) {
        String filePath = file.getAbsolutePath();
        if (filePath.contains(".")) {
            String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
            if (!fileExtension.equals("txt") && !fileExtension.equals("txt")) {
                // Quăng lỗi nếu đuôi file không phải .txt
                throw new MyAppException(ErrorType.WRONG_FILE_FORMAT_LOAD_KEY, view);
            }
        } else {
            // Thêm đuôi .txt nếu người dùng không định nghĩa đuôi file
            filePath += ".txt";
        }
        try {
            algorithm.loadPrivateKey(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.IO_ERROR, view);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.UNKNOWN_ERROR, view);
        }
        String message = String.format("Load key thành công");
        JOptionPane.showMessageDialog(view, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        String privateKeyAsString = algorithm.getPrivateKeyAsString();
        model.setPrivateKey(privateKeyAsString);
        model.notifyObservers("load_private_key", Map.of(
                "current_private_key", privateKeyAsString
        ));
    }

    private void handleOnChooseLocation_ForSavePrivateKey(File file) {
        String filePath = file.getAbsolutePath();
        if (filePath.contains(".")) {
            String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
            if (!fileExtension.equals("txt") && !fileExtension.equals("txt")) {
                // Quăng lỗi nếu đuôi file không phải .txt
                throw new MyAppException(ErrorType.WRONG_FILE_FORMAT_SAVE_LOCATION, view);
            }
        } else {
            // Thêm đuôi .txt nếu người dùng không định nghĩa đuôi file
            filePath += ".txt";
        }
        String currentPrivateKey = model.getPrivateKey();
        if ("".equals(currentPrivateKey) || currentPrivateKey == null) {
            throw new MyAppException(ErrorType.EMPTY_KEY_WHEN_SAVE, view);
        }
        try {
            algorithm.savePrivateKey(filePath);
        } catch (IOException e) {
            throw new MyAppException(ErrorType.IO_ERROR, view);
        }
        String message = String.format("Lưu thành công key tại đường dẫn: %s", filePath);
        JOptionPane.showMessageDialog(view, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleOnChooseLocation_ForLoadPublicKey(File file) {
        String filePath = file.getAbsolutePath();
        if (filePath.contains(".")) {
            String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
            if (!fileExtension.equals("txt") && !fileExtension.equals("txt")) {
                // Quăng lỗi nếu đuôi file không phải .txt
                throw new MyAppException(ErrorType.WRONG_FILE_FORMAT_LOAD_KEY, view);
            }
        } else {
            // Thêm đuôi .txt nếu người dùng không định nghĩa đuôi file
            filePath += ".txt";
        }
        try {
            algorithm.loadPublicKey(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.IO_ERROR, view);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.UNKNOWN_ERROR, view);
        }
        String message = String.format("Load key thành công");
        JOptionPane.showMessageDialog(view, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        String publicKeyAsString = algorithm.getPublicKeyAsString();
        model.setPublicKey(publicKeyAsString);
        model.notifyObservers("load_public_key", Map.of(
                "current_public_key", publicKeyAsString
        ));
    }

    private void handleOnChooseLocation_ForSavePublicKey(File file) {
        String filePath = file.getAbsolutePath();
        if (filePath.contains(".")) {
            String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
            if (!fileExtension.equals("txt") && !fileExtension.equals("txt")) {
                // Quăng lỗi nếu đuôi file không phải .txt
                throw new MyAppException(ErrorType.WRONG_FILE_FORMAT_SAVE_LOCATION, view);
            }
        } else {
            // Thêm đuôi .txt nếu người dùng không định nghĩa đuôi file
            filePath += ".txt";
        }
        String currentPublicKey = model.getPublicKey();
        if ("".equals(currentPublicKey) || currentPublicKey == null) {
            throw new MyAppException(ErrorType.EMPTY_KEY_WHEN_SAVE, view);
        }
        try {
            algorithm.savePublicKey(filePath);
        } catch (IOException e) {
            throw new MyAppException(ErrorType.IO_ERROR, view);
        }
        String message = String.format("Lưu thành công key tại đường dẫn: %s", filePath);
        JOptionPane.showMessageDialog(view, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void initialModels() {
        algorithm = new AsymmetricAlgorithm();
        model = new AsymmetricScreen_Model();
        model.addObserver(view);
        model.initialize();
    }

    private void handleSaveKeyButton_Click(ActionEvent event) {

    }

    private void handleEncryptButtonClick() {
        loadEncryptModule();
        String inputText = model.getInputText();
        String output = null;
        try {
            output = algorithm.encryptText(inputText, model.getUsingKey());
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
        model.notifyObservers("text_encrypted", Map.of(
                "current_output", output
        ));
    }

    private void handleDecryptButtonClick() {
        loadEncryptModule();
        String input = model.getInputText();
        String output = null;
        try {
            output = algorithm.decryptText(input, model.getUsingKey());
            model.notifyObservers("text_decrypted", Map.of(
                    "current_output", output
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

    public void loadEncryptModule() {
        try {
            String privateKeyStr = model.getPrivateKey();
            String publicKeyStr = model.getPublicKey();
            algorithm.setPrivateKey(privateKeyStr);
            algorithm.setPublicKey(publicKeyStr);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.UNKNOWN_ERROR, view);
        }
    }

    private void handleGenerateKeyButtonClick(ActionEvent event) {
        try {
            var keyPair = algorithm.generateKeyPair(model.getKeySize());
            var privateKey = keyPair.getPrivate();
            var publicKey = keyPair.getPublic();
            algorithm.setPrivateKey(privateKey);
            algorithm.setPublicKey(publicKey);
            model.setPublicKey(algorithm.getPublicKeyAsString());
            model.setPrivateKey(algorithm.getPrivateKeyAsString());
            model.notifyObservers("generate_random", Map.of(
                    "current_public_key", model.getPublicKey(),
                    "current_private_key", model.getPrivateKey()
            ));
        } catch (NoSuchAlgorithmException e) {
            throw new MyAppException(ErrorType.UNSUPPORTED_ALGORITHM, view);
        }
    }
}
