package View;

import Model.Hash.MD5;
import Model.Hash.SHA.SHA;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;

public class HashScreen_View extends AScreenView {
    private JComboBox<String> SelectHash_ComboBox;
    private JButton SelectFile_Button;
    private JButton DeselectFile_Button;
    private JLabel IsSelectedFile_Label;
    private JTextArea InputTextArea;
    private JTextArea OutputTextArea;
    private JScrollPane InputTextWrap_ScrollPane;
    private JScrollPane OutputTextWrap_ScrollPane;
    private JButton HashButton;
    private JPanel SelectModeWrap_Panel;
    private JPanel OverallContentWrap_Panel;
    private JPanel ButtonWrap_Panel;

    public HashScreen_View() {
        super();
    }

    public void onInputTextArea_DocumentChange(Consumer<DocumentEvent> callback) {
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

    public void onSelectFileButton_Click(Consumer<ActionEvent> callback) {
        SelectFile_Button.addActionListener(e -> callback.accept(e));
    }

    public void onDeselectFileButton_Click(Consumer<ActionEvent> callback) {
        DeselectFile_Button.addActionListener(e -> callback.accept(e));
    }

    public void onHashButton_Click(Consumer<ActionEvent> callback) {
        HashButton.addActionListener(e -> callback.accept(e));
    }

    @Override
    public void initialComponent() {
        // Khởi tạo các thành phần giao diện
        SelectHash_ComboBox = new JComboBox<>();
        SelectFile_Button = new JButton("Chọn file");
        DeselectFile_Button = new JButton("Hủy chọn file");
        IsSelectedFile_Label = new JLabel("Không có file nào được chọn");

        InputTextArea = new JTextArea(20, 35);
        InputTextArea.setLineWrap(true);
        InputTextArea.setWrapStyleWord(true);
        InputTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        InputTextArea.setForeground(Color.BLACK);

        InputTextWrap_ScrollPane = new JScrollPane(InputTextArea);
        InputTextWrap_ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        InputTextWrap_ScrollPane.setBorder(BorderFactory.createTitledBorder("Nguồn"));

        OutputTextArea = new JTextArea(20, 35);
        OutputTextArea.setLineWrap(true);
        OutputTextArea.setWrapStyleWord(true);
        OutputTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        OutputTextArea.setForeground(Color.BLACK);

        OutputTextWrap_ScrollPane = new JScrollPane(OutputTextArea);
        OutputTextWrap_ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        OutputTextWrap_ScrollPane.setBorder(BorderFactory.createTitledBorder("Kết quả"));

        HashButton = new JButton("Hash");

        // Thêm các thuật toán hash vào ComboBox
        SelectHash_ComboBox.addItem("MD5");
        SelectHash_ComboBox.addItem("SHA-1");
        SelectHash_ComboBox.addItem("SHA-256");
        SelectHash_ComboBox.addItem("SHA-512");
        SelectHash_ComboBox.addItem("SHA3-256");
        SelectHash_ComboBox.addItem("SHA3-512");
    }

    @Override
    public void initialLayout() {
        // Panel chọn thuật toán và file
        SelectModeWrap_Panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        SelectModeWrap_Panel.add(new JLabel("Thuật toán:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        SelectModeWrap_Panel.add(SelectHash_ComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        SelectModeWrap_Panel.add(SelectFile_Button, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        SelectModeWrap_Panel.add(DeselectFile_Button, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        SelectModeWrap_Panel.add(IsSelectedFile_Label, gbc);

        // Panel hiển thị nội dung nguồn và kết quả
        OverallContentWrap_Panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        OverallContentWrap_Panel.add(InputTextWrap_ScrollPane);
        OverallContentWrap_Panel.add(OutputTextWrap_ScrollPane);

        // Panel nút băm
        ButtonWrap_Panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        ButtonWrap_Panel.add(HashButton);

        // Thiết lập bố cục chính
        this.setLayout(new BorderLayout(10, 10));
        this.add(SelectModeWrap_Panel, BorderLayout.NORTH);
        this.add(OverallContentWrap_Panel, BorderLayout.CENTER);
        this.add(ButtonWrap_Panel, BorderLayout.SOUTH);
    }

    public JComboBox<String> getSelectHash_ComboBox() {
        return SelectHash_ComboBox;
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

    public JTextArea getInputTextArea() {
        return InputTextArea;
    }

    public JTextArea getOutputTextArea() {
        return OutputTextArea;
    }

    public JScrollPane getInputTextWrap_ScrollPane() {
        return InputTextWrap_ScrollPane;
    }

    public JScrollPane getOutputTextWrap_ScrollPane() {
        return OutputTextWrap_ScrollPane;
    }

    public JButton getHashButton() {
        return HashButton;
    }

    public JPanel getSelectModeWrap_Panel() {
        return SelectModeWrap_Panel;
    }

    public JPanel getOverallContentWrap_Panel() {
        return OverallContentWrap_Panel;
    }

    public JPanel getButtonWrap_Panel() {
        return ButtonWrap_Panel;
    }

    public void toggleSelectFileButton() {
        // Nếu có nội dung trong textInputArea thì disable nút "Chọn file", ngược lại enable
        SelectFile_Button.setEnabled(InputTextArea.getText().trim().isEmpty());
    }
}
