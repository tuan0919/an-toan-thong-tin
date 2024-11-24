package View;

import Model.Screen.ScreenObserver;
import Model.Screen.SymmetricScreen_Model;
import Util.MyUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class SymmetricScreen_View extends AScreenView implements ScreenObserver {
    private JTextField InputKeyTextField;
    private JTextField InputIVTextField;
    private JComboBox<String> ModeComboBox;
    private JComboBox<String> PaddingComboBox;
    private JComboBox<String> KeyComboBox;
    private JComboBox<String> AlgorithmComboBox;
    private JButton GenerateKeyButton;
    private JButton SaveKeyButton;
    private JButton LoadKeyButton;
    private JButton ChooseFileButton;
    private JButton CancelFileButton;
    private JLabel IsSelectedLabel;
    private JLabel InputIVLabel;
    private JTextArea InputTextArea;
    private JTextArea OutputTextArea;
    private JButton EncryptButton;
    private JButton DecryptButton;
    private JFileChooser FileChooser;
    private PropertyChangeSupport PropertyChangeSupport;

    public SymmetricScreen_View() {
        super();
    }

    @Override
    public void initialComponent() {
        // Khởi tạo các thành phần giao diện
        InputKeyTextField = new JTextField(35);
        InputIVTextField = new JTextField(35);
        ModeComboBox = new JComboBox<>();
        PaddingComboBox = new JComboBox<>();
        KeyComboBox = new JComboBox<>();
        GenerateKeyButton = new JButton("Tạo key");
        SaveKeyButton = new JButton("Lưu key");
        LoadKeyButton = new JButton("Load key");
        AlgorithmComboBox = new JComboBox<>();
        ChooseFileButton = new JButton("Chọn file");
        CancelFileButton = new JButton("Hủy chọn file");
        IsSelectedLabel = new JLabel("Không có file nào được chọn");
        InputIVLabel = new JLabel("Nhập IV:");
        InputTextArea = new JTextArea(15, 30);
        InputTextArea.setLineWrap(true);
        InputTextArea.setWrapStyleWord(true);
        OutputTextArea = new JTextArea(15, 30);
        OutputTextArea.setLineWrap(true);
        OutputTextArea.setWrapStyleWord(true);
        EncryptButton = new JButton("Mã hóa");
        DecryptButton = new JButton("Giải mã");
        FileChooser = new JFileChooser();
        PropertyChangeSupport = new PropertyChangeSupport(this);
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

        // Nhãn và trường nhập cho Key
        gbc.gridx = 0;
        gbc.gridy = 1;
        settingsPanel.add(InputIVLabel, gbc);
        gbc.gridx = 1;
        settingsPanel.add(InputIVTextField, gbc);

        // Nhãn và lựa chọn Mode
        gbc.gridx = 0;
        gbc.gridy = 2;
        settingsPanel.add(new JLabel("Chọn mode:"), gbc);
        gbc.gridx = 1;
        settingsPanel.add(ModeComboBox, gbc);

        // Nhãn và lựa chọn Padding
        gbc.gridx = 0;
        gbc.gridy = 3;
        settingsPanel.add(new JLabel("Chọn padding:"), gbc);
        gbc.gridx = 1;
        settingsPanel.add(PaddingComboBox, gbc);

        // Nhãn và lựa chọn giải thuật
        gbc.gridx = 0;
        gbc.gridy = 4;
        settingsPanel.add(new JLabel("Chọn giải thuật:"), gbc);
        gbc.gridx = 1;
        settingsPanel.add(AlgorithmComboBox, gbc);

        // Nhãn và lựa chọn Key Size
        gbc.gridx = 0;
        gbc.gridy = 5;
        settingsPanel.add(new JLabel("Chọn key size:"), gbc);
        gbc.gridx = 1;
        settingsPanel.add(KeyComboBox, gbc);

        // Nút Tạo Key
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        settingsPanel.add(GenerateKeyButton, gbc);


        // Panel chứa hai nút lưu và load key
        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new GridLayout(1,2,5,5));
        keyPanel.add(SaveKeyButton);
        keyPanel.add(LoadKeyButton);

        // Lưu & Load Key
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        settingsPanel.add(keyPanel, gbc);

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

    public void onInputKeyTextField_LostFocus(Consumer<String> callback) {
        InputKeyTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String currentText = InputKeyTextField.getText();
                callback.accept(currentText);
            }
        });
    }

    public void onInputIVTextField_LostFocus(Consumer<String> callback) {
        InputIVTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String currentText = InputIVTextField.getText();
                callback.accept(currentText);
            }
        });
    }

    public void onInputTextArea_LostFocus(Consumer<String> callback) {
        InputTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String currentText = InputTextArea.getText();
                callback.accept(currentText);
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

    public void onModeComboBox_Choose(Consumer<ItemEvent> callback) {
        ModeComboBox.addItemListener(e -> callback.accept(e));
    }

    public void onAlgorithmComboBox_Choose(Consumer<ItemEvent> callback) {
        AlgorithmComboBox.addItemListener(e -> callback.accept(e));
    }

    public void onKeySizeComboBox_Choose(Consumer<ItemEvent> callback) {
        KeyComboBox.addItemListener(e -> callback.accept(e));
    }

    public void onPaddingComboBox_Choose(Consumer<ItemEvent> callback) {
        PaddingComboBox.addItemListener(e -> callback.accept(e));
    }

    public void onGenerateKeyButton_Click(Consumer<ActionEvent> callback) {
        // Thêm action listener cho nút "Tạo Key"
        GenerateKeyButton.addActionListener(e -> callback.accept(e));
    }

    public void onSaveKeyButton_Click(Consumer<ActionEvent> callback) {
        SaveKeyButton.addActionListener(e -> callback.accept(e));
    }

    public void onLoadKeyButton_Click(Consumer<ActionEvent> callback) {
        LoadKeyButton.addActionListener(e -> callback.accept(e));
    }

    public void onEncryptButton_Click(Consumer<ActionEvent> callback) {
        EncryptButton.addActionListener(e -> callback.accept(e));
    }

    public void onDecryptButton_Click(Consumer<ActionEvent> callback) {
        DecryptButton.addActionListener(e -> callback.accept(e));
    }

    public void renderAlgorithmComboBox(List<String> algorithms) {
        AlgorithmComboBox.removeAllItems();
        for (String algorithm : algorithms) {
            AlgorithmComboBox.addItem(algorithm);
        }
    }

    public void renderKeyComboBox(List<String> keys) {
        KeyComboBox.removeAllItems();
        for (String key : keys) {
            KeyComboBox.addItem(key);
        }
    }

    public void renderPaddingComboBox(List<String> data) {
        PaddingComboBox.removeAllItems();
        for (String item : data) {
            PaddingComboBox.addItem(item);
        }
    }

    public void renderModeComboBox(List<String> data) {
        ModeComboBox.removeAllItems();
        for (String item : data) {
            ModeComboBox.addItem(item);
        }
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

    public JComboBox<String> getAlgorithmComboBox() {
        return AlgorithmComboBox;
    }

    public JTextField getInputIVTextField() {
        return InputIVTextField;
    }

    public JLabel getInputIVLabel() {
        return InputIVLabel;
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

    public JFileChooser getFileChooser() {
        return FileChooser;
    }

    public void toggleChooseFileButton() {
        ChooseFileButton.setEnabled(InputTextArea.getText().trim().isEmpty());
    }

    @Override
    public void update(String event, Map<String, Object> data) {
        switch (event) {
            case "init_state" -> {
                List<String> available_mode = (List<String>) data.get("available_mode");
                renderModeComboBox(available_mode);
            }
            case "change_mode" -> {
                String current_mode = (String) data.get("current_mode");
                List<String> available_algorithm = (List<String>) data.get("available_algorithm");
                renderAlgorithmComboBox(available_algorithm);
                AlgorithmComboBox.setSelectedIndex(0);
            }
            case "change_algorithm" -> {
                String current_algorithm = (String) data.get("current_algorithm");
                List<String> available_padding = (List<String>) data.get("available_padding");
                List<Integer> available_key_size = (List<Integer>) data.get("available_key_size");
                renderKeyComboBox(available_key_size.stream().map(n -> n+"").toList());
                renderPaddingComboBox(available_padding);
                PaddingComboBox.setSelectedIndex(0);
                KeyComboBox.setSelectedIndex(0);
            }
            case "change_file" -> {
                var optionalFile = (Optional<File>) data.get("file");
                if (optionalFile.isPresent()) {
                    File file = optionalFile.get();
                    IsSelectedLabel.setText(file.getAbsolutePath()); // Cập nhật đường dẫn file
                    // Disable cả hai JTextArea khi chọn file
                    InputTextArea.setEnabled(false);
                    OutputTextArea.setEnabled(false);
                } else {
                    IsSelectedLabel.setText("Không có file nào được chọn"); // Đặt lại nhãn về trạng thái mặc định
                    // Enable lại cả hai JTextArea
                    InputTextArea.setEnabled(true);
                    OutputTextArea.setEnabled(true);
                }
            }
            case "change_key" -> {
                Optional<String> optionalKey = (Optional<String>) data.get("current_key");
                if (optionalKey.isPresent()) {
                    InputKeyTextField.setText(optionalKey.get());
                }
            }
            case "change_iv" -> {
                Optional<String> optionalIV = (Optional<String>) data.get("current_iv");
                if (optionalIV.isPresent()) {
                    InputIVTextField.setText(optionalIV.get());
                }
            }
        }
    }

    public void onFileChosen(Consumer<File> callback) {
        PropertyChangeSupport.addPropertyChangeListener("user_picked_file", evt -> {
            File currentFile = (File) evt.getNewValue();
            callback.accept(currentFile);
        });
    }

    public void onSaveLocationChosen(Consumer<File> callback) {
        PropertyChangeSupport.addPropertyChangeListener("user_choose_save_location", evt -> {
            File currentFile = (File) evt.getNewValue();
            callback.accept(currentFile);
        });
    }

    public void onLoadKeyLocationChosen(Consumer<File> callback) {
        PropertyChangeSupport.addPropertyChangeListener("user_choose_load_key_location", evt -> {
            File currentFile = (File) evt.getNewValue();
            callback.accept(currentFile);
        });
    }

    public int showFileChooserForCipher() {
        int result = FileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            PropertyChangeSupport.firePropertyChange("user_picked_file", null, file);
        }
        return result;
    }

    public int showFileChooserForSaveLocation() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
        FileChooser.setFileFilter(filter);
        int result = FileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            PropertyChangeSupport.firePropertyChange("user_choose_save_location", null, file);
        }
        return result;
    }

    public int showFileChooserForLoadKey() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
        FileChooser.setFileFilter(filter);
        int result = FileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            PropertyChangeSupport.firePropertyChange("user_choose_load_key_location", null, file);
        }
        return result;
    }
}
