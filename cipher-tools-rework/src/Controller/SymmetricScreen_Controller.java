package Controller;

import Model.Algorithm.MaHoaHienDai.MaHoaDoiXung.*;
import Model.Algorithm.Symmetric.SymmetricAlgorithm;
import Model.Screen.SymmetricScreen_Model;
import MyException.ErrorType;
import MyException.MyAppException;
import View.SymmetricScreen_View;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class SymmetricScreen_Controller extends AController<SymmetricScreen_View> {
    SymmetricAlgorithm algorithm;
    private SymmetricScreen_Model model;

    public SymmetricScreen_Controller(SymmetricScreen_View view) {
        super(view);
    }

    @Override
    protected void initialCallbacks() {
        view.onFileButton_Click(e -> view.showFileChooserForCipher());
        view.onCancelFileButton_Click(e -> model.setChooseFile(null));
        view.onModeComboBox_Choose(e -> handleModeComboBox_Choose(e));
        view.onAlgorithmComboBox_Choose(e -> handleAlgorithmCombobox_Choose(e));
        view.onKeySizeComboBox_Choose(e -> model.setKeySize(Integer.parseInt(e.getItem().toString())));
        view.onPaddingComboBox_Choose(e -> model.setPadding(e.getItem().toString()));
        view.onInputTextArea_DocumentChange(e -> view.toggleChooseFileButton());
        view.onInputTextArea_LostFocus(text -> handleInputTextArea_LostFocus(text));
        view.onInputKeyTextField_LostFocus(text -> model.setKey(text));
        view.onInputIVTextField_LostFocus(text -> model.setIv(text));
        view.onGenerateKeyButton_Click(e -> handleGenerateKeyButton_Click(e));
        view.onSaveKeyButton_Click(e -> view.showFileChooserForSaveLocation());
        view.onLoadKeyButton_Click(e -> view.showFileChooserForLoadKey());
        view.onEncryptButton_Click(e -> handleEncryptButton_Click(e));
        view.onDecryptButton_Click(e -> handleDecryptButton_Click(e));
        view.onFileChosen(file -> model.setChooseFile(file));
        view.onSaveLocationChosen(file -> handleSaveLocationChosen(file));
        view.onLoadKeyLocationChosen(file -> handleLoadKeyLocationChosen(file));
    }

    private void handleLoadKeyLocationChosen(File file) {
        loadEncryptModule();
        // Đảm bảo file có đuôi .txt
        String filePath = file.getAbsolutePath();
        String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
        if (!fileExtension.equals("txt")) {
            // Quăng lỗi nếu đuôi file không phải .txt
            throw new MyAppException(ErrorType.WRONG_FILE_FORMAT_LOAD_KEY, view);
        }
        try {
            algorithm.loadKeyFromFile(filePath);
        } catch (IOException e) {
            throw new MyAppException(ErrorType.IO_ERROR, view);
        }
        // ok
        JOptionPane.showMessageDialog(view, "Load key thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        String currentKey = algorithm.getSecretKeyAsString();
        String currentIV = algorithm.getIVAsString();
        model.setKey(currentKey);
        model.setIv(currentIV);
        loadEncryptModule();
    }

    private void handleSaveLocationChosen(File file) {
        // Đảm bảo file có đuôi .txt
        String filePath = file.getAbsolutePath();
        // Kiểm tra đuôi file:
        if (filePath.contains(".")) {
            String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
            if (!fileExtension.equals("txt")) {
                // Quăng lỗi nếu đuôi file không phải .txt
                throw new MyAppException(ErrorType.WRONG_FILE_FORMAT_SAVE_LOCATION, view);
            }
        } else {
            // Thêm đuôi .txt nếu người dùng không định nghĩa đuôi file
            filePath += ".txt";
        }
        model.setSaveFilePath(filePath);
        String currentKey = model.getKey();
        if ("".equals(currentKey) || currentKey == null) {
            throw new MyAppException(ErrorType.EMPTY_KEY_WHEN_SAVE, view);
        }
        loadEncryptModule();
        boolean isSuccess = algorithm.saveToFile(filePath);
        if (isSuccess) {
            String message = String.format("Lưu thành công key tại đường dẫn: %s", filePath);
            JOptionPane.showMessageDialog(view, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            throw new MyAppException(ErrorType.SAVE_FILE_FAILED, view);
        }
    }

    private void handleInputTextArea_LostFocus(String inputText) {
        model.setInputText(inputText);
        if ("NoPadding".equals(model.getPadding())) {
            byte[] bytes = inputText.getBytes(StandardCharsets.UTF_8);;
            boolean isValid = bytes.length % 8 == 0;
            if (!isValid) {
                throw new MyAppException(ErrorType.BAD_INPUT_FOR_NO_PADDING, view);
            }
        }
    }

    private void handleDecryptButton_Click(ActionEvent event) {
        var InputTextArea = view.getInputTextArea();
        String inputText = InputTextArea.getText();
        if (model.getChooseFile() == null && (inputText == null || "".equals(inputText))) {
            throw new MyAppException(ErrorType.EMPTY_INPUT_FOR_ENCRYPT, view);
        }
        if (model.getChooseFile() == null) {
            _decryptText();
        }
        else {
            _decryptFile();
        }
    }

    private void _decryptFile() {
        String currentFilePath = model.getChooseFile().getAbsolutePath();
        {
            int lastDotIndex = currentFilePath.lastIndexOf('.');
            // Thêm "_encrypted" vào trước phần mở rộng
            String baseName = currentFilePath.substring(0, lastDotIndex);
            String extension = currentFilePath.substring(lastDotIndex); // Gồm cả dấu '.'
            model.setSaveFilePath(baseName + "_decrypted" + extension); // tạm fix cứng
        }
        loadEncryptModule();
        String saveFilePath = model.getSaveFilePath();
        boolean isSuccess = false;
        try {
            isSuccess = algorithm.decryptFile(currentFilePath, saveFilePath);
        } catch (InvalidAlgorithmParameterException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchPaddingException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (InvalidKeyException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.UNSUPPORTED_ALGORITHM, view);
        }
        if (!isSuccess) {
            throw new MyAppException(ErrorType.FILE_FAILED_TO_DECRYPT, view);
        }
        JOptionPane.showMessageDialog(view, "Giải mã thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void _decryptText() {
        var OutputTextArea = view.getOutputTextArea();
        String inputText = model.getInputText();
        String result = "";
        loadEncryptModule();
        try {
            result = algorithm.decrypt(inputText);
            OutputTextArea.setText(result);
        } catch (InvalidAlgorithmParameterException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchPaddingException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (IllegalBlockSizeException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (BadPaddingException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchProviderException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (InvalidKeyException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        }
    }

    private void _encryptText() {
        var InputTextArea = view.getInputTextArea();
        var OutputTextArea = view.getOutputTextArea();
        String inputText = InputTextArea.getText();
        String result = "";
        loadEncryptModule();
        try {
            result = algorithm.encrypt(inputText);
            OutputTextArea.setText(result);
        } catch (InvalidAlgorithmParameterException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchPaddingException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (IllegalBlockSizeException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (BadPaddingException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchProviderException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (InvalidKeyException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        }
    }

    private void _encryptFile() {
        String currentFilePath = model.getChooseFile().getAbsolutePath();
        {
            int lastDotIndex = currentFilePath.lastIndexOf('.');
            // Thêm "_encrypted" vào trước phần mở rộng
            String baseName = currentFilePath.substring(0, lastDotIndex);
            String extension = currentFilePath.substring(lastDotIndex); // Gồm cả dấu '.'
            model.setSaveFilePath(baseName + "_encrypted" + extension); // tạm fix cứng
        }
        loadEncryptModule();
        String saveFilePath = model.getSaveFilePath();
        boolean isSuccess = false;
        try {
            isSuccess = algorithm.encryptFile(currentFilePath, saveFilePath);
        } catch (InvalidAlgorithmParameterException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchPaddingException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (InvalidKeyException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new MyAppException(ErrorType.BAD_INPUT_ALGORITHM, view);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.UNSUPPORTED_ALGORITHM, view);
        }
        if (!isSuccess) {
            throw new MyAppException(ErrorType.FILE_FAILED_TO_ENCRYPT, view);
        }
        JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleEncryptButton_Click(ActionEvent event) {
        var InputTextArea = view.getInputTextArea();
        String inputText = InputTextArea.getText();
        if (model.getChooseFile() == null && (inputText == null || "".equals(inputText))) {
            throw new MyAppException(ErrorType.EMPTY_INPUT_FOR_ENCRYPT, view);
        }
        if (model.getChooseFile() == null) {
            _encryptText();
        }
        else {
            _encryptFile();
        }
    }

    private void handleAlgorithmCombobox_Choose(ItemEvent event) {
        model.setAlgorithm(event.getItem().toString());
    }

    private void handleModeComboBox_Choose(ItemEvent event) {
        model.setMode(event.getItem().toString());
        var InputIVLabel = view.getInputIVLabel();
        var InputIVTextField = view.getInputIVTextField();
        if ("ECB".equals(event.getItem().toString())) {
            InputIVLabel.setVisible(false);
            InputIVTextField.setVisible(false);
        } else {
            InputIVLabel.setVisible(true);
            InputIVTextField.setVisible(true);
        }
    }

    private void loadEncryptModule() {
        String currentAlgorithm = model.getAlgorithm();
        String currentMode = model.getMode();
        String currentPadding = model.getPadding();
        String currentIV = model.getIv();
        String currentKey = model.getKey();

        algorithm.setAlgorithm(currentAlgorithm);
        algorithm.setMode(currentMode);
        algorithm.setPadding(currentPadding);
        // nullable
        if (currentKey != null) {
            algorithm.setSecretKey(currentKey);
        }
        // nullable
        if (currentIV != null) {
            algorithm.setIvParameterSpec(currentIV);
        }
    }

    private void handleGenerateKeyButton_Click(ActionEvent event) {
        var InputKeyTextField = view.getInputKeyTextField();
        var InputIVTextField = view.getInputIVTextField();
        String keyTemp = "";
        String iv = null;
        loadEncryptModule();
        int currentKeySize = model.getKeySize();
        if (!"ECB".equals(model.getAlgorithm())) { // case này cần quan tâm IV
            var ivSpec = algorithm.generateRandomIV();
            iv = algorithm.parseToString(ivSpec);
        }
        try {
            var secretKey = algorithm.generateRandomKey(currentKeySize);
            keyTemp = algorithm.parseToString(secretKey);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyAppException(ErrorType.UNKNOWN_ERROR, view);
        }
        if (iv != null)  {
            InputIVTextField.setText(iv);
            model.setIv(iv);
        }
        InputKeyTextField.setText(keyTemp);
        model.setKey(keyTemp);
    }

    @Override
    protected void initialModels() {
        algorithm = new SymmetricAlgorithm();
        model = new SymmetricScreen_Model();
        model.addObserver(view);
        model.initialize();
    }
}
