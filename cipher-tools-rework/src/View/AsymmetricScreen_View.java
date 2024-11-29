package View;

import Model.Screen.ScreenObserver;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AsymmetricScreen_View extends AScreenView implements ScreenObserver {
    private JTextField PublicKey_TextField = new JTextField(35);
    private JTextField PrivateKey_TextField = new JTextField(35);
    private JTextArea PublicKey_TextArea;
    private JTextArea PrivateKey_TextArea;
    private JTextArea Input_TextArea;
    private JTextArea Output_TextArea;
    private JButton SavePrivateKey_Button;
    private JButton SavePublicKey_Button;
    private JButton LoadPrivateKey_Button;
    private JButton LoadPublicKey_Button;
    private JButton GenerateKey_Button;
    private JButton SaveKey_Button;
    private JButton Encrypt_Button;
    private JButton Decrypt_Button;
    private JComboBox<Integer> SelectKeySize_ComboBox;
    private JRadioButton PublicKey_RadioButton;
    private JRadioButton PrivateKey_RadioButton;
    private ButtonGroup KeyButtonGroup;
    private JFileChooser FileChooser;
    private PropertyChangeSupport EventFireSupport;

    @Override
    public void initialComponent() {
        // Khởi tạo các thành phần giao diện
        PublicKey_TextField = new JTextField(35);
        PrivateKey_TextField = new JTextField(35);
        Input_TextArea = new JTextArea(10, 35);
        Input_TextArea.setLineWrap(true);
        Input_TextArea.setWrapStyleWord(true);
        Output_TextArea = new JTextArea(10, 35);
        Output_TextArea.setLineWrap(true);
        Output_TextArea.setWrapStyleWord(true);
        PrivateKey_TextArea = new JTextArea(5, 20);
        PublicKey_TextArea = new JTextArea(5, 35);
        PublicKey_TextArea.setWrapStyleWord(true);
        PublicKey_TextArea.setLineWrap(true);
        PrivateKey_TextArea.setWrapStyleWord(true);
        PrivateKey_TextArea.setLineWrap(true);
        SavePrivateKey_Button = new JButton("Save key");
        LoadPrivateKey_Button = new JButton("Load key");
        SavePublicKey_Button = new JButton("Save key");
        LoadPublicKey_Button = new JButton("Load key");
        Encrypt_Button = new JButton("Encrypt");
        Decrypt_Button = new JButton("Decrypt");
        GenerateKey_Button = new JButton("Generate random key");
        SaveKey_Button = new JButton("Save key");
        PublicKey_RadioButton = new JRadioButton("Public key");
        PrivateKey_RadioButton = new JRadioButton("Private key");
        KeyButtonGroup = new ButtonGroup();
        KeyButtonGroup.add(PublicKey_RadioButton);
        KeyButtonGroup.add(PrivateKey_RadioButton);
        FileChooser = new JFileChooser();
        EventFireSupport = new PropertyChangeSupport(this);
        SelectKeySize_ComboBox = new JComboBox<>();
    }

    public void renderSelectKeySize_ComboBox(List<Integer> data) {
        SelectKeySize_ComboBox.removeAllItems();
        for (var item : data ) {
            SelectKeySize_ComboBox.addItem(item);
        }
    }

    @Override
    public void initialLayout() {
        // Cấu hình các JScrollPane cho Input và Output
        JScrollPane scrollPane = new JScrollPane(Input_TextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Source"));

        JScrollPane scrollPane2 = new JScrollPane(Output_TextArea);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setBorder(BorderFactory.createTitledBorder("Output"));

        // Cấu hình các JLabel
        JLabel label1 = new JLabel("Algorithm:");
        JLabel label2 = new JLabel("RSA");
        JLabel label3 = new JLabel("Key size:");
        JLabel label4 = new JLabel("Key input fields");
        JLabel label5 = new JLabel("Public key:");
        JLabel label6 = new JLabel("Private key:");

        // Panel cho các lựa chọn và key
        JPanel panelModeAndKey = new JPanel(new GridBagLayout());
        panelModeAndKey.setBorder(BorderFactory.createTitledBorder("Algorithm settings"));
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
        JScrollPane scrollPanePublicKey = new JScrollPane(PublicKey_TextArea);
        panelModeAndKey.add(scrollPanePublicKey, gbc);

        JPanel panelPublicKeyAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelPublicKeyAction.add(SavePublicKey_Button);
        panelPublicKeyAction.add(LoadPublicKey_Button);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panelModeAndKey.add(panelPublicKeyAction, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panelModeAndKey.add(label6, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        JScrollPane scrollPanePrivateKey = new JScrollPane(PrivateKey_TextArea);
        panelModeAndKey.add(scrollPanePrivateKey, gbc);

        JPanel panelPrivateKeyAction = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelPrivateKeyAction.add(SavePrivateKey_Button);
        panelPrivateKeyAction.add(LoadPrivateKey_Button);

        gbc.gridx = 1;
        gbc.gridy = 6;
        panelModeAndKey.add(panelPrivateKeyAction, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panelModeAndKey.add(new JLabel("Usage key:"), gbc);

        JPanel RadioKeyButtonWrap = new JPanel(new FlowLayout(FlowLayout.LEFT));
        RadioKeyButtonWrap.add(PublicKey_RadioButton);
        RadioKeyButtonWrap.add(PrivateKey_RadioButton);

        gbc.gridx = 1;
        gbc.gridy = 7;
        panelModeAndKey.add(RadioKeyButtonWrap, gbc);

        // Căn chỉnh nút "Tạo key" và "Lưu key"
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 2;  // Đặt gridwidth là 2 để nút chiếm toàn bộ chiều rộng
        panelModeAndKey.add(GenerateKey_Button, gbc);

        // Panel chứa nội dung
        JPanel panelWrapTextArea = new JPanel(new GridLayout());

        panelWrapTextArea.add(scrollPane);
        panelWrapTextArea.add(scrollPane2);

        // Panel chứa các nút mã hóa và giải mã
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelButton.add(Encrypt_Button);
        panelButton.add(Decrypt_Button);

        // Thiết lập bố cục chính
        this.setLayout(new BorderLayout(10, 10));
        this.add(panelModeAndKey, BorderLayout.NORTH);
        this.add(panelWrapTextArea, BorderLayout.CENTER);
        this.add(panelButton, BorderLayout.SOUTH);
    }

    public void onGenerateKeyButtonClick(Consumer<ActionEvent> callback) {
        GenerateKey_Button.addActionListener(e -> callback.accept(e));
    }

    public void onSaveKeyButton_Click(Consumer<ActionEvent> callback) {
        SaveKey_Button.addActionListener(e -> callback.accept(e));
    }

    public void onDecryptButton_Click(Consumer<ActionEvent> callback) {
        Decrypt_Button.addActionListener(e -> callback.accept(e));
    }

    public void onSavePrivateKeyButton_Click(Consumer<Void> callback) {
        SavePrivateKey_Button.addActionListener(e -> callback.accept(null));
    }

    public void onLoadPrivateKeyButton_Click(Consumer<Void> callback) {
        LoadPrivateKey_Button.addActionListener(e -> callback.accept(null));
    }

    public void onSavePublicKeyButton_Click(Consumer<Void> callback) {
        SavePublicKey_Button.addActionListener(e -> callback.accept(null));
    }

    public void onLoadPublicKeyButton_Click(Consumer<Void> callback) {
        LoadPublicKey_Button.addActionListener(e -> callback.accept(null));
    }

    public void onChooseKeySize(BiConsumer<Integer, Integer> callback) {
        SelectKeySize_ComboBox.addItemListener(e -> {
            int key = (Integer) SelectKeySize_ComboBox.getSelectedItem();
            int index = SelectKeySize_ComboBox.getSelectedIndex();
            callback.accept(key, index);
        });
    }

    public void onChooseUsageKey(Consumer<Class<? extends Key>> callback) {
        PublicKey_RadioButton.setActionCommand("Public_Key");
        PrivateKey_RadioButton.setActionCommand("Private_Key");
        ItemListener listener = event -> {
            JRadioButton sourceButton = (JRadioButton) event.getSource();
            String cmd = sourceButton.getActionCommand();
            var clazz = cmd.equals("Public_Key") ? PublicKey.class : PrivateKey.class;
            callback.accept(clazz);
        };
        PublicKey_RadioButton.addItemListener(listener);
        PrivateKey_RadioButton.addItemListener(listener);
    }

    public AsymmetricScreen_View() {
        super();
    }

    @Override
    public void update(String event, Map<String, Object> data) {
        switch (event) {
            case "first_load" -> {
                List<Integer> initialKeySizes = (List<Integer>) data.get("available_key_size");
                renderSelectKeySize_ComboBox(initialKeySizes);
            }
            case "generate_random" -> {
                String publicKey = (String) data.get("current_public_key");
                String privateKey = (String) data.get("current_private_key");
                PublicKey_TextArea.setText(publicKey);
                PrivateKey_TextArea.setText(privateKey);
            }
            case "load_private_key" -> {
                String secretKey = (String) data.get("current_private_key");
                PrivateKey_TextArea.setText(secretKey);
            }
            case "load_public_key" -> {
                String secretKey = (String) data.get("current_public_key");
                PublicKey_TextArea.setText(secretKey);
            }
            case "text_encrypted", "text_decrypted" -> {
                String output = (String) data.get("current_output");
                Output_TextArea.setText(output);
            }
        }
    }

    public void onEncryptButton_Click(Consumer<Void> callback) {
        Encrypt_Button.addActionListener(e -> callback.accept(null));
    }

    public void onInputTextArea_LostFocus(Consumer<String> callback) {
        Input_TextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                callback.accept(Input_TextArea.getText());
            }
        });
    }

    public void onPublicKeyTextArea_LostFocus(Consumer<String> callback) {
        PublicKey_TextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                callback.accept(PublicKey_TextArea.getText());
            }
        });
    }

    public void onPrivateKeyTextArea_LostFocus(Consumer<String> callback) {
        PrivateKey_TextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                callback.accept(PrivateKey_TextArea.getText());
            }
        });
    }

    public void onChooseLocation_ForSavePrivateKey(Consumer<File> callback) {
        EventFireSupport.addPropertyChangeListener("user_choose_save_private_key_location", (event) -> {
            File file = (File) event.getNewValue();
            callback.accept(file);
        });
    }

    public void onChooseLocation_ForLoadPrivateKey(Consumer<File> callback) {
        EventFireSupport.addPropertyChangeListener("user_choose_load_private_key_location", (event) -> {
            File file = (File) event.getNewValue();
            callback.accept(file);
        });
    }

    public int openJFileChooser_ForSavePrivateKey() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files and PEM Files (*.txt, *.pem)", "txt", "pem");
        FileChooser.setFileFilter(filter);
        int result = FileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            EventFireSupport.firePropertyChange("user_choose_save_private_key_location", null, file);
        }
        return result;
    }

    public int openJFileChooser_ForLoadPrivateKey() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files and PEM Files (*.txt, *.pem)", "txt", "pem");
        FileChooser.setFileFilter(filter);
        int result = FileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            EventFireSupport.firePropertyChange("user_choose_load_private_key_location", null, file);
        }
        return result;
    }

    public void onChooseLocation_ForSavePublicKey(Consumer<File> callback) {
        EventFireSupport.addPropertyChangeListener("user_choose_save_public_key_location", (event) -> {
            File file = (File) event.getNewValue();
            callback.accept(file);
        });
    }

    public void onChooseLocation_ForLoadPublicKey(Consumer<File> callback) {
        EventFireSupport.addPropertyChangeListener("user_choose_load_public_key_location", (event) -> {
            File file = (File) event.getNewValue();
            callback.accept(file);
        });
    }

    public int openJFileChooser_ForSavePublicKey() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files and PEM Files (*.txt, *.pem)", "txt", "pem");
        FileChooser.setFileFilter(filter);
        int result = FileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            EventFireSupport.firePropertyChange("user_choose_save_public_key_location", null, file);
        }
        return result;
    }

    public int openJFileChooser_ForLoadPublicKey() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files and PEM Files (*.txt, *.pem)", "txt", "pem");
        FileChooser.setFileFilter(filter);
        int result = FileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            EventFireSupport.firePropertyChange("user_choose_load_public_key_location", null, file);
        }
        return result;
    }
}
