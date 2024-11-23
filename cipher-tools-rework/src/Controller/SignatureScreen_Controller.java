package Controller;

import Model.ChuKySo.ChuKySo;
import Model.MaHoaHienDai.MaHoaBatDoiXung.RSA;
import View.AsymmetricScreen_View;
import View.SignatureScreen_View;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignatureScreen_Controller extends AController<SignatureScreen_View> {
    private ChuKySo chuKySo;
    public SignatureScreen_Controller(SignatureScreen_View view) {
        super(view);
    }

    @Override
    protected void initialCallbacks() {
        view.onSourceTextArea_DocumentChange(e -> handleSourceTextArea_DocumentChange());
        view.onSelectFileButton_Click(e -> handleSelectFileButton_Click());
        view.onDeselectFileButton_Click(e -> handleDeselectFileButton_Click());
        view.onOutputTextArea_DocumentChange(e -> handleOutputTextArea_DocumentChange());
        view.onSelectFileButton2_Click(e -> handleSelectFileButton2_Click());
        view.onDeselectFileButton2_Click(e -> handleDeselectFileButton2_Click());
        view.onCreateSignatureButton_Click(e -> handleCreateSignatureButton_Click());
        view.onCheckSignatureButton_Click(e -> handleCheckSignatureButton_Click());
    }

    private void handleCheckSignatureButton_Click() {
        var Source_TextArea = view.getSource_TextArea();
        var Output_TextArea = view.getOutput_TextArea();
        var IsSelectedFile_Label = view.getIsSelectedFile_Label();
        var IsSelectedFile_Label2 = view.getIsSelectedFile_Label2();
        var Signature_TextArea = view.getSignature_TextArea();
        var DecryptedSignature_TextArea = view.getDecryptedSignature_TextArea();

        try{
            if(((Source_TextArea.getText().equals("") || Source_TextArea.getText().length() == 0)) && IsSelectedFile_Label.getText().equals("Không có file nào được chọn") ){
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đoạn văn bản mà bạn muốn ký hoặc chọn file mà bạn muốn ký","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            }else {
                if(((Output_TextArea.getText().equals("") || Output_TextArea.getText().length() == 0)) && IsSelectedFile_Label2.getText().equals("Không có file nào được chọn") ){
                    JOptionPane.showMessageDialog(view, "Vui lòng nhập đoạn văn bản mà bạn muốn kiểm tra chữ ký hoặc chọn file mà bạn muốn kiểm tra chữ ký ","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                }
                if( Output_TextArea.getText().length() != 0 ){
                    boolean result = chuKySo.verifySignatureWithText(Output_TextArea.getText(), Signature_TextArea.getText());
                    if(result){
                        JOptionPane.showMessageDialog(view, "Chữ ký vẹn toàn không thay đổi","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                        DecryptedSignature_TextArea.setText(chuKySo.getRsaSignature());
                    }else {
                        JOptionPane.showMessageDialog(view, "Chữ ký bị thay đổi","Thông báo",JOptionPane.WARNING_MESSAGE);
                        DecryptedSignature_TextArea.setText(chuKySo.getRsaSignature());
                    }
                }
                if(!IsSelectedFile_Label2.getText().equals("Không có file nào được chọn")){
                    boolean result = chuKySo.verifySignatureWithFile(IsSelectedFile_Label2.getText(), Signature_TextArea.getText());
                    if(result){
                        JOptionPane.showMessageDialog(view, "Chữ ký vẹn toàn không thay đổi","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                        DecryptedSignature_TextArea.setText(chuKySo.getRsaSignature());
                    }else {
                        JOptionPane.showMessageDialog(view, "Chữ ký bị thay đổi","Thông báo",JOptionPane.WARNING_MESSAGE);
                        DecryptedSignature_TextArea.setText(chuKySo.getRsaSignature());
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Kiểm tra chữ ký số bị lỗi","Thông báo",JOptionPane.WARNING_MESSAGE);
            throw new RuntimeException(ex);
        }
    }

    private void handleCreateSignatureButton_Click() {
        var Source_TextArea = view.getOutput_TextArea();
        var IsSelectedFile_Label = view.getIsSelectedFile_Label();
        var Signature_TextArea = view.getSignature_TextArea();
        try {
            if(((Source_TextArea.getText().equals("") || Source_TextArea.getText().length() == 0)) && IsSelectedFile_Label.getText().equals("Không có file nào được chọn") ){
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đoạn văn bản mà bạn muốn ký hoặc chọn file mà bạn muốn ký","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            }
            if(!(Source_TextArea.getText().equals("") || Source_TextArea.getText().length() == 0)){
                String textTemp = Source_TextArea.getText();
                Signature_TextArea.setText( chuKySo.createSignatureWithText(textTemp));
                JOptionPane.showMessageDialog(view, "Tạo chữ ký số thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);

            }
            if(!IsSelectedFile_Label.getText().equals("Không có file nào được chọn")){
                String filePath = IsSelectedFile_Label.getText();
                Signature_TextArea.setText( chuKySo.createSignatureWithFile(filePath));
                JOptionPane.showMessageDialog(view, "Tạo chữ ký số thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Tạo chữ ký số bị lỗi","Thông báo",JOptionPane.WARNING_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void handleDeselectFileButton2_Click() {
        var IsSelectedFile_Label2 = view.getIsSelectedFile_Label2();
        var Output_TextArea = view.getOutput_TextArea();
        var Source_TextArea = view.getSource_TextArea();

        IsSelectedFile_Label2.setText("Không có file nào được chọn"); // Đặt lại nhãn về trạng thái mặc định
        // Enable lại cả  JTextArea
        Output_TextArea.setEnabled(true);
        Source_TextArea.setEnabled(true);
    }

    private void handleSelectFileButton2_Click() {
        var IsSelectedFile_Label2 = view.getIsSelectedFile_Label2();
        var Output_TextArea = view.getOutput_TextArea();
        var Source_TextArea = view.getSource_TextArea();
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(view);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            IsSelectedFile_Label2.setText(selectedFile.getAbsolutePath()); // Cập nhật đường dẫn file
            // Disable cả  JTextArea khi chọn file
            Output_TextArea.setEnabled(false);
            Source_TextArea.setEnabled(false);
            IsSelectedFile_Label2.revalidate();
            IsSelectedFile_Label2.repaint();
        }
    }

    private void handleOutputTextArea_DocumentChange() {
        var Output_TextArea = view.getOutput_TextArea();
        view.toggleFileButton(Output_TextArea);
    }

    private void handleDeselectFileButton_Click() {
        var IsSelectedFile_Label = view.getIsSelectedFile_Label();
        var Source_TextArea = view.getSource_TextArea();
        var Output_TextArea  = view.getOutput_TextArea();
        IsSelectedFile_Label.setText("Không có file nào được chọn"); // Đặt lại nhãn về trạng thái mặc định
        // Enable lại cả  JTextArea
        Source_TextArea.setEnabled(true);
        Output_TextArea.setEnabled(true);
    }

    private void handleSourceTextArea_DocumentChange() {
        var Source_TextArea = view.getSource_TextArea();
        view.toggleFileButton(Source_TextArea);
    }

    private void handleSelectFileButton_Click() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(view);
        var IsSelectedFile_Label = view.getIsSelectedFile_Label();
        var Source_TextArea = view.getSource_TextArea();
        var Output_TextArea  = view.getOutput_TextArea();
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            IsSelectedFile_Label.setText(selectedFile.getAbsolutePath()); // Cập nhật đường dẫn file

            // Disable cả  JTextArea khi chọn file
            Source_TextArea.setEnabled(false);
            Output_TextArea.setEnabled(false);
            IsSelectedFile_Label.revalidate();
            IsSelectedFile_Label.repaint();
        }
    }

    @Override
    protected void initialModels() {
        chuKySo = new ChuKySo();
    }

}
