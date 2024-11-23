package Controller;

import Model.Algorithm.MaHoaHienDai.MaHoaDoiXung.*;
import Model.Screen.SymmetricScreen_Model;
import View.SymmetricScreen_View;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class SymmetricScreen_Controller extends AController<SymmetricScreen_View> {
    private AES aes;
    private Camellia camellia;
    private TripleDES tripleDES;
    private DES des;
    private IDEA idea;
    private SymmetricScreen_Model model;

    public SymmetricScreen_Controller(SymmetricScreen_View view) {
        super(view);
    }

    @Override
    protected void initialCallbacks() {
        view.onFileButton_Click(e -> handleFileButton_Click(e));
        view.onCancelFileButton_Click(e -> handleCancelFileButton_Click(e));
        view.onModeComboBox_Choose(e -> handleModeComboBox_Choose(e));
        view.onAlgorithmComboBox_Choose(e -> handleAlgorithmCombobox_Choose(e));
        view.onKeySizeComboBox_Choose(e -> handleKeySizeComboBox_Choose(e));
        view.onInputTextArea_DocumentChange(e -> handleInputTextAreaDocumentChange(e));
        view.onGenerateKeyButton_Click(e -> handleGenerateKeyButton_Click(e));
        view.onSaveKeyButton_Click(e -> handleSaveKeyButton_Click(e));
        view.onEncryptButton_Click(e -> handleEncryptButton_Click(e));
        view.onDecryptButton_Click(e -> handleDecryptButton_Click(e));
    }

    private void handleKeySizeComboBox_Choose(ItemEvent e) {
        model.setKeySize(Integer.parseInt(e.getItem().toString()));
    }

    private void handleDecryptButton_Click(ActionEvent event) {
        var IsSelectedLabel = view.getIsSelectedLabel();
        var InputTextArea = view.getInputTextArea();
        var OutputTextArea = view.getOutputTextArea();
        var ModeComboBox = view.getModeComboBox();
        var PaddingComboBox = view.getPaddingComboBox();
        var KeyComboBox = view.getKeyComboBox();
        var AlgorithmButton = view.getAlgorithmComboBox();
        if (IsSelectedLabel.getText().equals("Không có file nào được chọn")
                && (InputTextArea.getText().length() == 0 || InputTextArea.getText() == null)

        ) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn file cần giải mã hóa hoặc nhập đoạn văn bản bạn muốn giải mã hóa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        }
        if ( InputTextArea.getText().length() >= 1) {
            String keyTemp = "";
            String modeText = (String) ModeComboBox.getSelectedItem();
            String paddingText = (String) PaddingComboBox.getSelectedItem();
            int keySizeOutput = Integer.valueOf(KeyComboBox.getSelectedItem().toString());
            String textInputAreaTemp = InputTextArea.getText();
            String result = "";
            if (AlgorithmButton.getSelectedItem().equals("AES")) {
                result = aes.decryptText(textInputAreaTemp, modeText, paddingText);
            }
            if (AlgorithmButton.getSelectedItem().equals("Camellia")) {
                result = camellia.decryptText(textInputAreaTemp, modeText, paddingText);
            }
            if (AlgorithmButton.getSelectedItem().equals("TripleDES")) {
                result = tripleDES.decryptText(textInputAreaTemp, modeText, paddingText);
            }
            if (AlgorithmButton.getSelectedItem().equals("DES")) {
                result = des.decryptText(textInputAreaTemp, modeText, paddingText);
            }
            if (AlgorithmButton.getSelectedItem().equals("IDEA")) {
                result = idea.decryptText(textInputAreaTemp, modeText, paddingText);
            }
            OutputTextArea.setText(result);
        }
        if (!IsSelectedLabel.getText().equals("Không có file nào được chọn")) {

            String modeText = (String) ModeComboBox.getSelectedItem();
            String paddingText = (String) PaddingComboBox.getSelectedItem();


            boolean result = false;
            if (AlgorithmButton.getSelectedItem().equals("AES")) {
                result = aes.decryptAFile(IsSelectedLabel.getText(), modeText, paddingText);

            }
            if (AlgorithmButton.getSelectedItem().equals("Camellia")) {
                result = camellia.decryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
            }
            if (AlgorithmButton.getSelectedItem().equals("TripleDES")) {
                result = tripleDES.decryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
            }
            if (AlgorithmButton.getSelectedItem().equals("DES")) {
                result = des.decryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
            }
            if (AlgorithmButton.getSelectedItem().equals("IDEA")) {
                result = idea.decryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
            }

            if (result) {
                JOptionPane.showMessageDialog(view, "Giải mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Giải mã hóa  thất bại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    private void handleEncryptButton_Click(ActionEvent event) {
        var IsSelectedLabel = view.getIsSelectedLabel();
        var InputTextArea = view.getInputTextArea();
        var OutputTextArea = view.getOutputTextArea();
        var ModeComboBox = view.getModeComboBox();
        var PaddingComboBox = view.getPaddingComboBox();
        var AlgorithmButton = view.getAlgorithmComboBox();
        var KeyComboBox = view.getKeyComboBox();
        try {
            if (IsSelectedLabel.getText().equals("Không có file nào được chọn")
                    && (InputTextArea.getText().length() == 0 || InputTextArea.getText() == null)

            ) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn file cần mã hóa hoặc nhập đoạn văn bản bạn muốn mã hóa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            }
            if ( InputTextArea.getText().length() >= 1) {
                String keyTemp = "";
                String modeText = (String) ModeComboBox.getSelectedItem();
                String paddingText = (String) PaddingComboBox.getSelectedItem();
                int keySizeOutput = Integer.valueOf(KeyComboBox.getSelectedItem().toString());
                String textInputAreaTemp = InputTextArea.getText();
                String result = "";
                if (AlgorithmButton.getSelectedItem().equals("AES")) {
                    result = aes.encrypt(textInputAreaTemp, modeText, paddingText);
                }
                if (AlgorithmButton.getSelectedItem().equals("Camellia")) {
                    result = camellia.encrypt(textInputAreaTemp, modeText, paddingText);
                }
                if (AlgorithmButton.getSelectedItem().equals("TripleDES")) {
                    result = tripleDES.encrypt(textInputAreaTemp, modeText, paddingText);
                }
                if (AlgorithmButton.getSelectedItem().equals("DES")) {
                    result = des.encrypt(textInputAreaTemp, modeText, paddingText);
                }
                if (AlgorithmButton.getSelectedItem().equals("IDEA")) {
                    result = idea.encrypt(textInputAreaTemp, modeText, paddingText);
                }

                OutputTextArea.setText(result);
            }
            if (!IsSelectedLabel.getText().equals("Không có file nào được chọn")) {
                String modeText = (String) ModeComboBox.getSelectedItem();
                String paddingText = (String) PaddingComboBox.getSelectedItem();
                int keySizeOutput = Integer.valueOf(KeyComboBox.getSelectedItem().toString());
                if (AlgorithmButton.getSelectedItem().equals("AES")) {
                    if (ModeComboBox.getSelectedItem().equals("ECB")) {

                        boolean result = aes.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                        if (result) {
                            JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        if (ModeComboBox.getSelectedItem().equals("CBC")) {

                            boolean result = aes.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                            if (result) {
                                JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {

                            boolean result = aes.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                            if (result) {
                                JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                }
                if (AlgorithmButton.getSelectedItem().equals("Camellia")) {
                    if (ModeComboBox.getSelectedItem().equals("ECB")) {

                        boolean result = camellia.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                        if (result) {
                            JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        if (ModeComboBox.getSelectedItem().equals("CBC")) {

                            boolean result = camellia.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                            if (result) {
                                JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {

                            boolean result = camellia.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                            if (result) {
                                JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                }
                if (AlgorithmButton.getSelectedItem().equals("TripleDES")) {
                    if (ModeComboBox.getSelectedItem().equals("ECB")) {

                        boolean result = tripleDES.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                        if (result) {
                            JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        if (ModeComboBox.getSelectedItem().equals("CBC")) {

                            boolean result = tripleDES.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                            if (result) {
                                JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {

                            boolean result = tripleDES.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                            if (result) {
                                JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                }
                if (AlgorithmButton.getSelectedItem().equals("DES")) {
                    if (ModeComboBox.getSelectedItem().equals("ECB")) {

                        boolean result = des.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                        if (result) {
                            JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        if (ModeComboBox.getSelectedItem().equals("CBC")) {

                            boolean result = des.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                            if (result) {
                                JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {

                            boolean result = des.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                            if (result) {
                                JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                }
                if (AlgorithmButton.getSelectedItem().equals("IDEA")) {
                    if (ModeComboBox.getSelectedItem().equals("ECB")) {

                        boolean result = idea.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                        if (result) {
                            JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        if (ModeComboBox.getSelectedItem().equals("CBC")) {

                            boolean result = idea.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                            if (result) {
                                JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {

                            boolean result = idea.encryptAFile(IsSelectedLabel.getText(), modeText, paddingText);
                            if (result) {
                                JOptionPane.showMessageDialog(view, "Mã hóa thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(view, "Mã hóa không thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                }
            }

        } catch (InvalidAlgorithmParameterException exception) {
            throw new RuntimeException(exception);
        } catch (NoSuchPaddingException exception) {
            throw new RuntimeException(exception);
        } catch (IllegalBlockSizeException exception) {
            throw new RuntimeException(exception);
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException(exception);
        } catch (BadPaddingException exception) {
            throw new RuntimeException(exception);
        } catch (NoSuchProviderException exception) {
            throw new RuntimeException(exception);
        } catch (InvalidKeyException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void handleSaveKeyButton_Click(ActionEvent event) {
        String keyUser = view.getInputKeyTextField().getText();
        String mode = model.getMode();
        String algorithm = model.getAlgorithm();
        try {
            if (keyUser.length() == 0 || keyUser == null) {
                view.showWarnMessage("Vui lòng nhập key");
            } else {
                if ("AES".equals(algorithm)) {
                    if ("ECB".equals(mode)) {
                        aes.loadKeyFromUser(keyUser, "AES");
                    } else {
                        aes.loadKeyAndIVFromUser(keyUser);
                    }
                }
                if ("Camellia".equals(algorithm)) {
                    if ("ECB".equals(mode)) {
                        camellia.loadKeyFromUser(keyUser);
                    } else {
                        camellia.loadKeyAndIVFromUser(keyUser);
                    }
                }
                if ("TripleDES".equals(algorithm)) {
                    if ("ECB".equals(mode)) {
                        tripleDES.loadKeyFromUser(keyUser);
                    } else {
                        tripleDES.loadKeyAndIVFromUser(keyUser);
                    }
                }
                if ("DES".equals(algorithm)) {
                    if ("ECB".equals(mode)) {
                        des.loadKeyFromUser(keyUser);
                    } else {
                        des.loadKeyAndIVFromUser(keyUser);
                    }
                }
                if ("IDEA".equals(algorithm)) {
                    if ("ECB".equals(mode)) {
                        idea.loadKeyFromUser(keyUser);
                    } else {
                        idea.loadKeyAndIVFromUser(keyUser);
                    }
                }
                view.showInfoMessage("Lưu key thành công");
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void handleInputTextAreaDocumentChange(DocumentEvent event) {
        view.toggleChooseFileButton();
    }

    private void handleAlgorithmCombobox_Choose(ItemEvent event) {
        var AlgorithmButton = view.getAlgorithmComboBox();
        if (AlgorithmButton.getSelectedItem() != null) {
            model.setAlgorithm(event.getItem().toString());
        }
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

    private void handleCancelFileButton_Click(ActionEvent event) {
        var IsSelectedLabel = view.getIsSelectedLabel();
        var InputTextArea = view.getInputTextArea();
        var OutputTextArea = view.getOutputTextArea();
        IsSelectedLabel.setText("Không có file nào được chọn"); // Đặt lại nhãn về trạng thái mặc định
        // Enable lại cả hai JTextArea
        InputTextArea.setEnabled(true);
        OutputTextArea.setEnabled(true);
    }

    private void handleFileButton_Click(ActionEvent event) {
        var IsSelectedLabel = view.getIsSelectedLabel();
        var InputTextArea = view.getInputTextArea();
        var OutputTextArea = view.getOutputTextArea();
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            IsSelectedLabel.setText(selectedFile.getAbsolutePath()); // Cập nhật đường dẫn file
            // Disable cả hai JTextArea khi chọn file
            InputTextArea.setEnabled(false);
            OutputTextArea.setEnabled(false);
        }
    }

    private void handleGenerateKeyButton_Click(ActionEvent event) {
        var InputKeyTextField = view.getInputKeyTextField();
        var InputIVTextField = view.getInputIVTextField();
        String keyTemp = "";
        String iv = null;
        try {
            switch (model.getAlgorithm()) {
                case "AES" -> {
                    if ("ECB".equals(model.getMode())) {
                        aes.loadKey(model.getKeySize());
                    } else {
                        aes.loadKeyAndIV(model.getKeySize());
                        iv = aes.getIv();
                    }
                    keyTemp = aes.getSecretKey().toString();
                }
                case "Camellia" -> {
                    if ("ECB".equals(model.getMode())) {
                        camellia.loadKey(model.getKeySize());
                    } else {
                        camellia.loadKeyAndIV(model.getKeySize(), "Camellia");
                        iv = camellia.getIv();
                    }
                    keyTemp = camellia.getSecretKey().toString();
                }
                case "TripleDES" -> {
                    if ("ECB".equals(model.getMode())) {
                        tripleDES.loadKey(model.getKeySize());
                    } else {
                        iv = tripleDES.getIv();
                        tripleDES.loadKeyAndIV(model.getKeySize());
                    }
                    keyTemp = tripleDES.getSecretKey().toString();
                }
                case "DES" -> {
                    if ("ECB".equals(model.getMode())) {
                        des.loadKey(model.getKeySize());
                    } else {
                        iv = des.getIv();
                        des.loadKeyAndIV(model.getKeySize());
                    }
                    keyTemp = des.getSecretKey().toString();
                }
                case "IDEA" -> {
                    if ("ECB".equals(model.getMode())) {
                        idea.loadKey(model.getKeySize(), "IDEA");
                    } else {
                        iv = idea.getIv();
                        idea.loadKeyAndIV(model.getKeySize(), "IDEA");
                    }
                    keyTemp = des.getSecretKey().toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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
        aes = new AES();
        camellia = new Camellia();
        tripleDES = new TripleDES();
        des = new DES();
        idea = new IDEA();
        model = new SymmetricScreen_Model();
        model.addObserver(view);
        model.initialize();
    }
}
