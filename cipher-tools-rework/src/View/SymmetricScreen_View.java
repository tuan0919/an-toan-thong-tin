package View;

import Util.MyUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.function.Consumer;

public class SymmetricScreen_View extends AScreenView {
    private JTextField InputKeyTextField;
    private JComboBox<String> ModeComboBox;
    private JComboBox<String> PaddingComboBox;
    private JComboBox<String> KeyComboBox;
    private JButton GenerateKeyButton;
    private JButton SaveKeyButton;
    private JComboBox<String> AlgorithmButton;
    private JButton ChooseFileButton;
    private JButton CancelFileButton;
    private JLabel IsSelectedLabel;
    private JTextArea InputTextArea;
    private JTextArea OutputTextArea;
    private JButton EncryptButton;
    private JButton DecryptButton;

    public SymmetricScreen_View() {
        super();
    }

    @Override
    public void initialComponent() {
        // Khởi tạo các thành phần giao diện
        InputKeyTextField = new JTextField(35);
        ModeComboBox = new JComboBox<>();
        PaddingComboBox = new JComboBox<>();
        KeyComboBox = new JComboBox<>();
        GenerateKeyButton = new JButton("Tạo key");
        SaveKeyButton = new JButton("Lưu key mới nhập");
        AlgorithmButton = new JComboBox<>();
        ChooseFileButton = new JButton("Chọn file");
        CancelFileButton = new JButton("Hủy chọn file");
        IsSelectedLabel = new JLabel("Không có file nào được chọn");
        InputTextArea = new JTextArea(15, 30);
        InputTextArea.setLineWrap(true);
        InputTextArea.setWrapStyleWord(true);
        OutputTextArea = new JTextArea(15, 30);
        OutputTextArea.setLineWrap(true);
        OutputTextArea.setWrapStyleWord(true);
        EncryptButton = new JButton("Mã hóa");
        DecryptButton = new JButton("Giải mã");
    }

    @Override
    public void initialLayout() {
        // Panel chứa phần cài đặt key, mode, padding và kích thước key
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nhãn và trường nhập cho Key
        gbc.gridx = 0;
        gbc.gridy = 0;
        settingsPanel.add(new JLabel("Nhập key:"), gbc);
        gbc.gridx = 1;
        settingsPanel.add(InputKeyTextField, gbc);

        // Nhãn và lựa chọn Mode
        gbc.gridx = 0;
        gbc.gridy = 1;
        settingsPanel.add(new JLabel("Chọn mode:"), gbc);
        gbc.gridx = 1;
        settingsPanel.add(ModeComboBox, gbc);
        ModeComboBox.addItem("ECB");
        ModeComboBox.addItem("CBC");
        ModeComboBox.addItem("CFB");
        ModeComboBox.addItem("OFB");
        ModeComboBox.addItem("CTR");

        // Nhãn và lựa chọn Padding
        gbc.gridx = 0;
        gbc.gridy = 2;
        settingsPanel.add(new JLabel("Chọn padding:"), gbc);
        gbc.gridx = 1;
        settingsPanel.add(PaddingComboBox, gbc);
        PaddingComboBox.addItem("PKCS5Padding");
        PaddingComboBox.addItem("ISO10126Padding");
        PaddingComboBox.addItem("NoPadding");

        // Nhãn và lựa chọn giải thuật
        gbc.gridx = 0;
        gbc.gridy = 3;
        settingsPanel.add(new JLabel("Chọn giải thuật:"), gbc);
        gbc.gridx = 1;
        settingsPanel.add(AlgorithmButton, gbc);
        AlgorithmButton.addItem("AES");
        AlgorithmButton.addItem("Camellia");
        AlgorithmButton.addItem("TripleDES");
        AlgorithmButton.addItem("DES");
        AlgorithmButton.addItem("IDEA");

        // Nhãn và lựa chọn Key Size
        gbc.gridx = 0;
        gbc.gridy = 4;
        settingsPanel.add(new JLabel("Chọn key size:"), gbc);
        gbc.gridx = 1;
        settingsPanel.add(KeyComboBox, gbc);

        // Nút Tạo Key
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        settingsPanel.add(GenerateKeyButton, gbc);

        // Nút Lưu Key
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        settingsPanel.add(SaveKeyButton, gbc);

        // Panel chứa phần thuật toán và chọn file
        JPanel filePanel = new JPanel();
        filePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        filePanel.add(ChooseFileButton);
        filePanel.add(CancelFileButton);
        filePanel.add(IsSelectedLabel);

        // Panel chính cho phần cài đặt
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Cài đặt mã hóa"));
        topPanel.add(settingsPanel, BorderLayout.CENTER);
        topPanel.add(filePanel, BorderLayout.SOUTH);

        // Panel chứa các JTextArea cho văn bản nhập và kết quả
        JScrollPane inputScrollPane = new JScrollPane(InputTextArea);
        inputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        inputScrollPane.setBorder(BorderFactory.createTitledBorder("Văn bản nguồn"));

        JScrollPane outputScrollPane = new JScrollPane(OutputTextArea);
        outputScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        outputScrollPane.setBorder(BorderFactory.createTitledBorder("Kết quả mã hóa"));

        // Panel chứa hai JTextArea để nhập và xuất văn bản
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(1, 2, 10, 10));
        textPanel.add(inputScrollPane);
        textPanel.add(outputScrollPane);

        // Panel chứa nút Mã hóa và Giải mã
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(EncryptButton);
        buttonPanel.add(DecryptButton);

        // Panel chính chứa phần văn bản và các nút
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout(10, 10));
        bottomPanel.add(textPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Thêm các panel vào SymmetricScreen_View
        this.setLayout(new BorderLayout(10, 10));
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);
    }

    public void onInputTextArea_DocumentChange(Consumer<DocumentEvent> callback) {
        // Lắng nghe thay đổi trong JTextArea của văn bản nguồn
        InputTextArea.getDocument().addDocumentListener(new DocumentListener() {
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

    public void onFileButton_Click(Consumer<ActionEvent> callback) {
        // Thêm action listener cho nút "Chọn file"
        ChooseFileButton.addActionListener(e -> {
            callback.accept(e);
        });
    }

    public void onCancelFileButton_Click(Consumer<ActionEvent> callback) {
        // Thêm action listener cho nút "Hủy chọn file"
        CancelFileButton.addActionListener(e -> callback.accept(e));
    }

    public void onModeComboBox_Choose(Consumer<ActionEvent> callback) {
        ModeComboBox.addActionListener(e -> callback.accept(e));
    }

    public void onAlgorithmButton_Click(Consumer<ActionEvent> callback) {
        AlgorithmButton.addActionListener(e -> callback.accept(e));
    }

    public void onGenerateKeyButton_Click(Consumer<ActionEvent> callback) {
        // Thêm action listener cho nút "Tạo Key"
        GenerateKeyButton.addActionListener(e -> callback.accept(e));
    }

    public void onSaveKeyButton_Click(Consumer<ActionEvent> callback) {
        SaveKeyButton.addActionListener(e -> callback.accept(e));
    }

    public void onEncryptButton_Click(Consumer<ActionEvent> callback) {
        EncryptButton.addActionListener(e -> callback.accept(e));
    }

    public void onDecryptButton_Click(Consumer<ActionEvent> callback) {
        DecryptButton.addActionListener(e -> callback.accept(e));
    }

    public JTextField getInputKeyTextField() {
        return InputKeyTextField;
    }

    public JComboBox<String> getModeComboBox() {
        return ModeComboBox;
    }

    public JComboBox<String> getPaddingComboBox() {
        return PaddingComboBox;
    }

    public JComboBox<String> getKeyComboBox() {
        return KeyComboBox;
    }

    public JButton getGenerateKeyButton() {
        return GenerateKeyButton;
    }

    public JButton getSaveKeyButton() {
        return SaveKeyButton;
    }

    public JComboBox<String> getAlgorithmButton() {
        return AlgorithmButton;
    }

    public JButton getChooseFileButton() {
        return ChooseFileButton;
    }

    public JButton getCancelFileButton() {
        return CancelFileButton;
    }

    public JLabel getIsSelectedLabel() {
        return IsSelectedLabel;
    }

    public JTextArea getInputTextArea() {
        return InputTextArea;
    }

    public JTextArea getOutputTextArea() {
        return OutputTextArea;
    }

    public JButton getEncryptButton() {
        return EncryptButton;
    }

    public JButton getDecryptButton() {
        return DecryptButton;
    }

    public void toggleChooseFileButton() {
        ChooseFileButton.setEnabled(InputTextArea.getText().trim().isEmpty());
    }

    public void showWarnMessage(String message) {
        MyUtil.showWarnMessage(this, message, "Thông báo");
    }

    public void showInfoMessage(String message) {
        MyUtil.showWarnMessage(this, message, "Thông báo");
    }
}
