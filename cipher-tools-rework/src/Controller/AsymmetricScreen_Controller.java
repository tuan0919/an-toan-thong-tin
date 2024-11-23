package Controller;

import Model.Hash.MD5;
import Model.Hash.SHA.SHA;
import Model.MaHoaHienDai.MaHoaBatDoiXung.RSA;
import View.AsymmetricScreen_View;
import View.HashScreen_View;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AsymmetricScreen_Controller extends AController<AsymmetricScreen_View> {
    private RSA rsa;

    public AsymmetricScreen_Controller(AsymmetricScreen_View view) {
        super(view);
    }

    @Override
    protected void initialCallbacks() {
        view.onGenerateKeyButtonClick(event -> handleGenerateKeyButtonClick(event));
        view.onDecryptButtonClick(event -> handleDecryptButtonClick(event));
        view.onEncryptButtonClick(event -> handleEncryptButtonClick(event));
        view.onSaveKeyButton_Click(event -> handleSaveKeyButton_Click(event));
    }

    @Override
    protected void initialModels() {
        rsa = new RSA();
    }

    private void handleSaveKeyButton_Click(ActionEvent event) {
        var PublicKey_TextField = view.getPublicKey_TextField();
        var PrivateKey_TextField = view.getPrivateKey_TextField();
        String publicKey = PublicKey_TextField.getText();
        String privateKey = PrivateKey_TextField.getText();
        try {
            boolean result = rsa.loadKeyFromUser(publicKey, privateKey);
            if (result) {
                JOptionPane.showMessageDialog(view, "Lưu key thành công");
            } else {
                JOptionPane.showMessageDialog(view, "Lưu key thất bại");
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void handleEncryptButtonClick(ActionEvent event) {
        var Input_TextArea = view.getInput_TextArea();
        var SelectKeySize_ComboBox = view.getSelectKeySize_ComboBox();
        var Output_TextArea = view.getOutput_TextArea();
        String plainText = Input_TextArea.getText();
        int keySize = Integer.valueOf(SelectKeySize_ComboBox.getSelectedItem().toString());
        String cipherText = null;
        try {
            cipherText = rsa.encrypt(keySize, plainText);
            Output_TextArea.setText(cipherText);
            JOptionPane.showMessageDialog(view, "Mã hóa thành công");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchPaddingException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidKeyException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalBlockSizeException ex) {
            throw new RuntimeException(ex);
        } catch (BadPaddingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void handleDecryptButtonClick(ActionEvent event) {
        var PublicKey_TextField = view.getPublicKey_TextField();
        var PrivateKey_TextField = view.getPrivateKey_TextField();
        String publicKey = PublicKey_TextField.getText();
        String privateKey = PrivateKey_TextField.getText();
        try {
            boolean result = rsa.loadKeyFromUser(publicKey, privateKey);
            if (result) {
                JOptionPane.showMessageDialog(view, "Lưu key thành công");
            } else {
                JOptionPane.showMessageDialog(view, "Lưu key thất bại");
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void handleGenerateKeyButtonClick(ActionEvent event) {
        var SelectKeySize_ComboBox = view.getSelectKeySize_ComboBox();
        var PublicKey_TextField = view.getPublicKey_TextField();
        var PrivateKey_TextField = view.getPrivateKey_TextField();
        int keySize = Integer.valueOf(SelectKeySize_ComboBox.getSelectedItem().toString());
        rsa.loadKey(keySize);
        String publicKey = rsa.getPublicKey();
        String privateKey = rsa.getPrivateKey();
        PublicKey_TextField.setText(publicKey);
        PrivateKey_TextField.setText(privateKey);
    }
}
