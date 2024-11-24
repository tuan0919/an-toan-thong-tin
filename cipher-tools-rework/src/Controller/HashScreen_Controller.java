package Controller;

import Model.Algorithm.Hash.MD5;
import Model.Algorithm.Hash.SHA.SHA;
import View.HashScreen_View;

import javax.swing.*;
import java.io.File;

public class HashScreen_Controller extends AController<HashScreen_View> {
    private MD5 md5;
    private SHA sha;

    public HashScreen_Controller(HashScreen_View view) {
        super(view);
    }

    @Override
    protected void initialCallbacks() {
        view.onInputTextArea_DocumentChange(e -> handleInputTextArea_DocumentChange());
        view.onSelectFileButton_Click(e -> handleSelectFileButton_Click());
        view.onDeselectFileButton_Click(e -> handleDeselectFileButton_Click());
        view.onHashButton_Click(e -> handleHashButton_Click());
    }

    @Override
    protected void initialModels() {
        md5 = new MD5();
        sha = new SHA();
    }

    private void handleInputTextArea_DocumentChange() {
        view.toggleSelectFileButton();
    }

    private void handleSelectFileButton_Click() {
        var IsSelectedFile_Label = view.getIsSelectedFile_Label();
        var InputTextArea = view.getInputTextArea();
        var OutputTextArea = view.getOutputTextArea();
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            IsSelectedFile_Label.setText(selectedFile.getAbsolutePath()); // Cập nhật đường dẫn file
            // Disable cả hai JTextArea khi chọn file
            InputTextArea.setEnabled(false);
            OutputTextArea.setEnabled(false);
        }
    }

    private void handleDeselectFileButton_Click() {
        var IsSelectedFile_Label = view.getIsSelectedFile_Label();
        var InputTextArea = view.getInputTextArea();
        var OutputTextArea = view.getOutputTextArea();
        IsSelectedFile_Label.setText("Không có file nào được chọn"); // Đặt lại nhãn về trạng thái mặc định
        // Enable lại cả hai JTextArea
        InputTextArea.setEnabled(true);
        OutputTextArea.setEnabled(true);
    }

    private void handleHashButton_Click() {
        var InputTextArea = view.getInputTextArea();
        var OutputTextArea = view.getOutputTextArea();
        var IsSelectedFile_Label = view.getIsSelectedFile_Label();
        var SelectHash_ComboBox = view.getSelectHash_ComboBox();
        try{
            if(InputTextArea.getText().isEmpty() && IsSelectedFile_Label.getText().equals("Không có file nào được chọn")) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn file muốn hash hoặc nhập văn bản mà bạn muốn hash", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            if(InputTextArea.getText().length() != 0){
                String modeTemp = SelectHash_ComboBox.getSelectedItem().toString();
                if(modeTemp.equals("MD5")) {
                    OutputTextArea.setText(md5.hashText(InputTextArea.getText()));
                }else{
                    OutputTextArea.setText(sha.hashText(InputTextArea.getText(),modeTemp));
                }
            }
            if(!IsSelectedFile_Label.getText().equals("Không có file nào được chọn")) {
                String modeTemp = SelectHash_ComboBox.getSelectedItem().toString();
                if(modeTemp.equals("MD5")) {
                    OutputTextArea.setText(md5.hashAFile(IsSelectedFile_Label.getText()));
                }else{
                    OutputTextArea.setText(sha.hashAFile(IsSelectedFile_Label.getText(),modeTemp));
                }
            }
            JOptionPane.showMessageDialog(view, "Model.Algorithm.Hash thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Model.Algorithm.Hash thất bại","Thông báo",JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException(ex);
        }
    }
}
