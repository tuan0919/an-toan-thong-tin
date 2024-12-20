package View;

import Model.Screen.ScreenObserver;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.List;

public class HashScreen_View extends AScreenView implements ScreenObserver {
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
    private JFileChooser JFileChooser;
    private PropertyChangeSupport EventFireSupport;

    public HashScreen_View() {
        super();
    }

    public void onInputTextArea_DocumentChange(Consumer<Void> callback) {
        InputTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                callback.accept(null);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                callback.accept(null);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                callback.accept(null);
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

    public void onSelectHashComboBox_Chosen(Consumer<String> callback) {
        SelectHash_ComboBox.addItemListener(e -> {
            callback.accept(e.getItem().toString());
        });
    }

    public void onInputTextArea_LostFocus(Consumer<String> callback) {
        InputTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                callback.accept(InputTextArea.getText());
            }
        });
    }

    @Override
    public void initialComponent() {
        // Khởi tạo các thành phần giao diện
        SelectHash_ComboBox = new JComboBox<>();
        SelectFile_Button = new JButton("Choose file");
        DeselectFile_Button = new JButton("Cancel file");
        IsSelectedFile_Label = new JLabel("No file has been chosen.");

        InputTextArea = new JTextArea(5, 35);
        InputTextArea.setLineWrap(true);
        InputTextArea.setWrapStyleWord(true);

        InputTextWrap_ScrollPane = new JScrollPane(InputTextArea);
        InputTextWrap_ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        InputTextWrap_ScrollPane.setBorder(BorderFactory.createTitledBorder("Source"));

        OutputTextArea = new JTextArea(5, 35);
        OutputTextArea.setLineWrap(true);
        OutputTextArea.setWrapStyleWord(true);

        OutputTextWrap_ScrollPane = new JScrollPane(OutputTextArea);
        OutputTextWrap_ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        OutputTextWrap_ScrollPane.setBorder(BorderFactory.createTitledBorder("Output"));

        JFileChooser = new JFileChooser();
        EventFireSupport = new PropertyChangeSupport(this);

        HashButton = new JButton("Hash");
    }

    public void renderSelectHash_ComboBox(List<String> data) {
        SelectHash_ComboBox.removeAllItems();
        for (var item : data) {
            SelectHash_ComboBox.addItem(item);
        }
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
        SelectModeWrap_Panel.add(new JLabel("Algorithm:"), gbc);

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
        OverallContentWrap_Panel = new JPanel(new GridLayout(1,2,1,1));
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

    public void toggleSelectFileButton() {
        // Nếu có nội dung trong textInputArea thì disable nút "Chọn file", ngược lại enable
        SelectFile_Button.setEnabled(InputTextArea.getText().trim().isEmpty());
    }

    @Override
    public void update(String event, Map<String, Object> data) {
        switch (event) {
            case "first_load" -> {
                List<String> availableAlgorithms = (List<String>) data.get("available_algorithm");
                renderSelectHash_ComboBox(availableAlgorithms);
            }
            case "change_choose_file" -> {
                Optional<File> optionalFile = (Optional<File>) data.get("current_choose_file");
                if (optionalFile.isPresent()) {
                    InputTextArea.setEditable(false);
                    IsSelectedFile_Label.setText(optionalFile.get().getAbsolutePath());
                } else {
                    InputTextArea.setEditable(true);
                    IsSelectedFile_Label.setText("No file has been chosen.");
                }
            }
            case "hash_file" -> {
                String output = (String) data.get("output");
                OutputTextArea.setText(output);
                OutputTextArea.setEditable(false);
            }
        }
    }

    public void onFileChosen(Consumer<File> callback) {
        EventFireSupport.addPropertyChangeListener("user_choose_file", event -> {
            File file = (File) event.getNewValue();
            callback.accept(file);
        });
    }

    public int openFileChooser_ForPickFile() {
        int result = JFileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = JFileChooser.getSelectedFile();
            EventFireSupport.firePropertyChange("user_choose_file", null, file);
        }
        return result;
    }
}
