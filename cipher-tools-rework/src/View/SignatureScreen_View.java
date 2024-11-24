package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class SignatureScreen_View extends AScreenView {
    private JButton SelectFile_Button;
    private JButton DeselectFile_Button;
    private JLabel IsSelectedFile_Label;
    private JTextArea Source_TextArea;
    private JTextArea Signature_TextArea;
    private JButton CreateSignature_Button;
    private JButton SelectFile_Button2;
    private JButton DeselectFile_Button2;
    private JLabel IsSelectedFile_Label2;
    private JTextArea Output_TextArea;
    private JTextArea DecryptedSignature_TextArea;
    private JButton CheckSignature_Button;
    private JPanel benNhanPanel;
    private JPanel benGuiPanel;

    @Override
    public void initialComponent() {
        // Khởi tạo các thành phần UI
        SelectFile_Button = new JButton("Chọn file");
        DeselectFile_Button = new JButton("Hủy chọn file");
        IsSelectedFile_Label = new JLabel("Không có file nào được chọn");
        Source_TextArea = new JTextArea(10, 25);
        Signature_TextArea = new JTextArea(10, 25);
        CreateSignature_Button = new JButton("Tạo chữ ký");
        SelectFile_Button2 = new JButton("Chọn file");
        DeselectFile_Button2 = new JButton("Hủy chọn file");
        IsSelectedFile_Label2 = new JLabel("Không có file nào được chọn");
        Output_TextArea = new JTextArea(10, 25);
        DecryptedSignature_TextArea = new JTextArea(10, 25);
        CheckSignature_Button = new JButton("Kiểm tra");
    }

    @Override
    public void initialLayout() {
        // Panel bên nhận
        benNhanPanel = new JPanel();
        benNhanPanel.setLayout(new BorderLayout());

        // Set kích thươớc cho 2 JLabel isSelected
        IsSelectedFile_Label.setPreferredSize(new Dimension(200, 20));
        IsSelectedFile_Label2.setPreferredSize(new Dimension(200, 20));

        // Chọn file
        JPanel chonFilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chonFilePanel.add(SelectFile_Button);
        chonFilePanel.add(DeselectFile_Button);
        chonFilePanel.add(IsSelectedFile_Label);

        // Văn bản nguồn
        Source_TextArea.setLineWrap(true);
        Source_TextArea.setWrapStyleWord(true);
        JScrollPane inputScrollPane = new JScrollPane(Source_TextArea);
        inputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        inputScrollPane.setBorder(BorderFactory.createTitledBorder("Văn bản nguồn"));

        // Chữ ký được tạo
        Signature_TextArea.setLineWrap(true);
        Signature_TextArea.setWrapStyleWord(true);
        JScrollPane chuKyScrollPane = new JScrollPane(Signature_TextArea);
        chuKyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chuKyScrollPane.setBorder(BorderFactory.createTitledBorder("Chữ ký được tạo"));

        JPanel chuKyPanel = new JPanel(new BorderLayout());
        chuKyPanel.add(chuKyScrollPane, BorderLayout.CENTER);
        chuKyPanel.add(CreateSignature_Button, BorderLayout.SOUTH);

        // Thêm vào panel bên nhận
        benNhanPanel.add(chonFilePanel, BorderLayout.NORTH);
        benNhanPanel.add(inputScrollPane, BorderLayout.CENTER);
        benNhanPanel.add(chuKyPanel, BorderLayout.SOUTH);

        // Panel bên gửi
        benGuiPanel = new JPanel();
        benGuiPanel.setLayout(new BorderLayout());

        // Chọn file
        JPanel chonFilePanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chonFilePanel1.add(SelectFile_Button2);
        chonFilePanel1.add(DeselectFile_Button2);
        chonFilePanel1.add(IsSelectedFile_Label2);

        // Văn bản dịch
        Output_TextArea.setLineWrap(true);
        Output_TextArea.setWrapStyleWord(true);
        JScrollPane outputScrollPane = new JScrollPane(Output_TextArea);
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        outputScrollPane.setBorder(BorderFactory.createTitledBorder("Văn bản đích"));

        // Giải mã chữ ký
        DecryptedSignature_TextArea.setLineWrap(true);
        DecryptedSignature_TextArea.setWrapStyleWord(true);
        JScrollPane giaiMaScrollPane = new JScrollPane(DecryptedSignature_TextArea);
        giaiMaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        giaiMaScrollPane.setBorder(BorderFactory.createTitledBorder("Giải mã chữ ký"));

        JPanel chuKyPanel1 = new JPanel(new BorderLayout());
        chuKyPanel1.add(giaiMaScrollPane, BorderLayout.CENTER);
        chuKyPanel1.add(CheckSignature_Button, BorderLayout.SOUTH);

        // Thêm vào panel bên gửi
        benGuiPanel.add(chonFilePanel1, BorderLayout.NORTH);
        benGuiPanel.add(outputScrollPane, BorderLayout.CENTER);
        benGuiPanel.add(chuKyPanel1, BorderLayout.SOUTH);

        // Tạo container chính
        JPanel mainContainer = new JPanel(new GridLayout(1, 2, 10, 0)); // Chia làm 2 cột với khoảng cách 10px
        mainContainer.add(benNhanPanel);
        mainContainer.add(benGuiPanel);

        // Thêm khoảng cách giữa `mainContainer` và rìa cửa sổ
        mainContainer.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding: top, left, bottom, right

        // Bọc `mainContainer` trong `JScrollPane` để kích hoạt thanh cuộn
        JScrollPane mainScrollPane = new JScrollPane(mainContainer);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Thêm vào panel chính
        this.setLayout(new BorderLayout());
        this.add(mainScrollPane, BorderLayout.CENTER);
    }

    public void onSourceTextArea_DocumentChange(Consumer<DocumentEvent> callback) {
        Source_TextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                callback.accept(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                callback.accept(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                callback.accept(e);
            }
        });
    }

    public void onSelectFileButton_Click(Consumer<ActionEvent> callback) {
        SelectFile_Button.addActionListener(e -> callback.accept(e));
    }

    public void onDeselectFileButton_Click(Consumer<ActionEvent> callback) {
        DeselectFile_Button.addActionListener(e -> callback.accept(e));
    }

    public void onOutputTextArea_DocumentChange(Consumer<DocumentEvent> callback) {
        Output_TextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                callback.accept(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                callback.accept(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                callback.accept(e);
            }
        });
    }

    public void onSelectFileButton2_Click(Consumer<ActionEvent> callback) {
        SelectFile_Button2.addActionListener(e -> callback.accept(e));
    }

    public void onDeselectFileButton2_Click(Consumer<ActionEvent> callback) {
        DeselectFile_Button2.addActionListener(e -> callback.accept(e));
    }

    public void onCreateSignatureButton_Click(Consumer<ActionEvent> callback) {
        CreateSignature_Button.addActionListener(e -> callback.accept(e));
    }

    public void onCheckSignatureButton_Click(Consumer<ActionEvent> callback) {
        CheckSignature_Button.addActionListener(e -> callback.accept(e));
    }

    public SignatureScreen_View() {
       super();
    }

    public JButton getSelectFile_Button() {
        return SelectFile_Button;
    }

    public JButton getDeselectFile_Button() {
        return DeselectFile_Button;
    }

    public JLabel getIsSelectedFile_Label() {
        return IsSelectedFile_Label;
    }

    public JTextArea getSource_TextArea() {
        return Source_TextArea;
    }

    public JTextArea getSignature_TextArea() {
        return Signature_TextArea;
    }

    public JButton getCreateSignature_Button() {
        return CreateSignature_Button;
    }

    public JButton getSelectFile_Button2() {
        return SelectFile_Button2;
    }

    public JButton getDeselectFile_Button2() {
        return DeselectFile_Button2;
    }

    public JLabel getIsSelectedFile_Label2() {
        return IsSelectedFile_Label2;
    }

    public JTextArea getOutput_TextArea() {
        return Output_TextArea;
    }

    public JTextArea getDecryptedSignature_TextArea() {
        return DecryptedSignature_TextArea;
    }

    public JButton getCheckSignature_Button() {
        return CheckSignature_Button;
    }

    public JPanel getBenNhanPanel() {
        return benNhanPanel;
    }

    public JPanel getBenGuiPanel() {
        return benGuiPanel;
    }

    public void toggleFileButton(JTextArea textArea) {
        // Nếu có nội dung trong textInputArea thì disable nút "Chọn file", ngược lại enable
        SelectFile_Button.setEnabled(textArea.getText().trim().isEmpty());
        SelectFile_Button2.setEnabled(textArea.getText().trim().isEmpty());
    }


}
