package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class AsymmetricScreen_View extends AScreenView {
    private JTextField PublicKey_TextField = new JTextField(35);
    private JTextField PrivateKey_TextField = new JTextField(35);
    private JTextArea Input_TextArea;
    private JTextArea Output_TextArea;
    private JButton GenerateKey_Button;
    private JButton SaveKey_Button;
    private JButton Encrypt_Button;
    private JButton Decrypt_Button;
    private JComboBox<String> SelectKeySize_ComboBox;

    @Override
    public void initialComponent() {
        // Khởi tạo các thành phần giao diện
        PublicKey_TextField = new JTextField(35);
        PrivateKey_TextField = new JTextField(35);
        Input_TextArea = new JTextArea(20, 35);
        Output_TextArea = new JTextArea(20, 35);

        Encrypt_Button = new JButton("Mã hóa");
        Decrypt_Button = new JButton("Giải mã");
        GenerateKey_Button = new JButton("Tạo key");
        SaveKey_Button = new JButton("Lưu key");

        SelectKeySize_ComboBox = new JComboBox<>();
        SelectKeySize_ComboBox.addItem("1024");
        SelectKeySize_ComboBox.addItem("2048");
        SelectKeySize_ComboBox.addItem("3072");
        SelectKeySize_ComboBox.addItem("4096");
    }

    @Override
    public void initialLayout() {
        // Cấu hình các JScrollPane cho Input và Output
        JScrollPane scrollPane = new JScrollPane(Input_TextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Nguồn"));

        JScrollPane scrollPane2 = new JScrollPane(Output_TextArea);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setBorder(BorderFactory.createTitledBorder("Kết quả"));

        // Cấu hình các JLabel
        JLabel label1 = new JLabel("Giải thuật:");
        JLabel label2 = new JLabel("RSA");
        JLabel label3 = new JLabel("Chọn kích thước:");
        JLabel label4 = new JLabel("Nhập key");
        JLabel label5 = new JLabel("Public key:");
        JLabel label6 = new JLabel("Private key:");

        // Panel cho các lựa chọn và key
        JPanel panelModeAndKey = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelModeAndKey.add(label1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelModeAndKey.add(label2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelModeAndKey.add(label3, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelModeAndKey.add(SelectKeySize_ComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panelModeAndKey.add(label4, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelModeAndKey.add(label5, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panelModeAndKey.add(PublicKey_TextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelModeAndKey.add(label6, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panelModeAndKey.add(PrivateKey_TextField, gbc);

        // Căn chỉnh nút "Tạo key" và "Lưu key"
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;  // Đặt gridwidth là 2 để nút chiếm toàn bộ chiều rộng
        panelModeAndKey.add(GenerateKey_Button, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;  // Đặt gridwidth là 2 để nút chiếm toàn bộ chiều rộng
        panelModeAndKey.add(SaveKey_Button, gbc);

        // Panel chứa nội dung
        JPanel panelContent = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelContent.add(scrollPane);
        panelContent.add(scrollPane2);

        // Panel chứa các nút mã hóa và giải mã
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelButton.add(Encrypt_Button);
        panelButton.add(Decrypt_Button);

        // Thiết lập bố cục chính
        this.setLayout(new BorderLayout(10, 10));
        this.add(panelModeAndKey, BorderLayout.NORTH);
        this.add(panelContent, BorderLayout.CENTER);
        this.add(panelButton, BorderLayout.SOUTH);
    }

    public void onGenerateKeyButtonClick(Consumer<ActionEvent> callback) {
        GenerateKey_Button.addActionListener(e -> callback.accept(e));
    }

    public void onSaveKeyButton_Click(Consumer<ActionEvent> callback) {
        SaveKey_Button.addActionListener(e -> callback.accept(e));
    }

    public void onEncryptButtonClick(Consumer<ActionEvent> callback) {
        Encrypt_Button.addActionListener(e -> callback.accept(e));
    }

    public void onDecryptButtonClick(Consumer<ActionEvent> callback) {
        Decrypt_Button.addActionListener(e -> callback.accept(e));
    }

    public AsymmetricScreen_View() {
        super();
    }

    public JTextField getPublicKey_TextField() {
        return PublicKey_TextField;
    }

    public JTextField getPrivateKey_TextField() {
        return PrivateKey_TextField;
    }

    public JTextArea getInput_TextArea() {
        return Input_TextArea;
    }

    public JTextArea getOutput_TextArea() {
        return Output_TextArea;
    }

    public JButton getGenerateKey_Button() {
        return GenerateKey_Button;
    }

    public JButton getSaveKey_Button() {
        return SaveKey_Button;
    }

    public JButton getEncrypt_Button() {
        return Encrypt_Button;
    }

    public JButton getDecrypt_Button() {
        return Decrypt_Button;
    }

    public JComboBox<String> getSelectKeySize_ComboBox() {
        return SelectKeySize_ComboBox;
    }
}
