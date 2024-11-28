package View;

import Model.Algorithm.Classic.Alphabet;
import Model.Screen.ClassicScreen_Model;
import Model.Screen.ScreenObserver;
import MyException.ErrorType;
import MyException.MyAppException;
import Util.MyUtil;
import View.Component.MatrixTable_View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ClassicScreen_View extends AScreenView implements ScreenObserver {
    private JComboBox<String> AlgorithmSelector_ComboBox;
    private JTextArea InputTextArea;
    private JTextArea OutputTextArea;
    private JButton EncryptButton;
    private JButton DecryptButton;
    private JScrollPane InputTextWrap_ScrollPane;
    private JScrollPane OutputTextWrap_ScrollPane;
    private JPanel OverallContentWrap_Panel;
    private JPanel CipherContentWrap_Panel;
    private MatrixTable_View MatrixInput_Table;
    private JTextField[] MatrixSize_TextField;
    private JButton CreateMatrix_Button;
    private JButton SaveMatrix_Button;
    private JButton LoadMatrix_Button;
    private JComboBox<String> AlphabetChoose_ComboBox;
    private JTextField[] InputKeyAB_TextField;
    private JButton LoadKeyAB_Button;
    private JButton SaveKeyAB_Button;
    private JButton LoadKeyCipherText_Button;
    private JButton SaveKeyCipherText_Button;
    private JButton GenerateKeyCipherText_Button;
    private JTextField PlainTextAlphabet_TextField;
    private JTextField CipherTextAlphabet_TextField;
    private JTextField ShiftKey_TextField;
    private JButton LoadKeyShift_Button;
    private JButton SaveKeyShift_Button;
    private JButton GenerateKeyShift_Button;
    private JTextField VigenereKey_TextField;
    private JButton LoadVigenereKey_Button;
    private JButton SaveVigenereKey_Button;
    private JButton GenerateVigenerateKey_Button;
    private PropertyChangeSupport EventFire_Support;

    public ClassicScreen_View() {
        super();
    }

    @Override
    public void initialComponent() {
        AlgorithmSelector_ComboBox = new JComboBox<>(); {
            AlgorithmSelector_ComboBox.addItem("Caesar");
            AlgorithmSelector_ComboBox.addItem("Affine");
            AlgorithmSelector_ComboBox.addItem("Vigenere");
            AlgorithmSelector_ComboBox.addItem("Substitution");
            AlgorithmSelector_ComboBox.addItem("Hill");
        }
        EncryptButton = new JButton("Mã hóa");
        DecryptButton = new JButton("Giải mã");
        InputTextArea = new JTextArea(20, 35);
        OutputTextArea = new JTextArea(20, 35);
        InputTextWrap_ScrollPane = new JScrollPane(InputTextArea);
        OutputTextWrap_ScrollPane = new JScrollPane(OutputTextArea);
        OverallContentWrap_Panel = new JPanel(new GridLayout());
        CipherContentWrap_Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        MatrixInput_Table = new MatrixTable_View();
        CreateMatrix_Button = new JButton("Tạo ma trận");
        SaveMatrix_Button = new JButton("Lưu");
        LoadMatrix_Button = new JButton("Load");
        MatrixSize_TextField = new JTextField[] {
                new JTextField(),
                new JTextField(),
        };
        AlphabetChoose_ComboBox = new JComboBox<>(); {
            AlphabetChoose_ComboBox.addItem("EN Alphabet - Bảng chữ cái la tinh.");
            AlphabetChoose_ComboBox.addItem("VI Alphabet - Bảng chữ cái tiếng việt.");
            AlphabetChoose_ComboBox.addItem("Custom Alphabet - Bảng chữ cái tự định nghĩa.");
        }
        InputKeyAB_TextField = new JTextField[] {
                new JTextField(),
                new JTextField(),
        };
        LoadKeyAB_Button = new JButton("Load key");
        SaveKeyAB_Button = new JButton("Lưu key");
        PlainTextAlphabet_TextField = new JTextField();
        CipherTextAlphabet_TextField = new JTextField();
        LoadKeyCipherText_Button = new JButton("Load");
        SaveKeyCipherText_Button = new JButton("Lưu");
        GenerateKeyCipherText_Button = new JButton("Tạo ngẫu nhiên");
        ShiftKey_TextField = new JTextField();
        LoadKeyShift_Button = new JButton("Load");
        SaveKeyShift_Button = new JButton("Lưu");
        GenerateKeyShift_Button = new JButton("Tạo ngẫu nhiên");
        VigenereKey_TextField = new JTextField();
        LoadVigenereKey_Button = new JButton("Load");
        SaveVigenereKey_Button = new JButton("Lưu");
        GenerateVigenerateKey_Button = new JButton("Tạo ngẫu nhiên");
        EventFire_Support = new PropertyChangeSupport(this);
    }

    public void onChangeCaesarKey(Consumer<Integer> callback) {
        ShiftKey_TextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String text = ShiftKey_TextField.getText();
                if (text.trim().length() <= 0) return;
                if (!text.matches("^\\d+$"))
                    throw new MyAppException(ErrorType.WRONG_CAESAR_KEY, ClassicScreen_View.this);
                callback.accept(Integer.parseInt(text));
            }
        });
    }

    public void onChangeAlphabet(Consumer<String> callback) {
        var lostFocus = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                callback.accept(PlainTextAlphabet_TextField.getText());
            }
        };
        AlphabetChoose_ComboBox.addItemListener(event -> {
            int index = AlphabetChoose_ComboBox.getSelectedIndex();
            if (index == 0) {
                callback.accept(Alphabet.EN.getAlphabet());
                PlainTextAlphabet_TextField.setText(Alphabet.EN.getAlphabet());
                PlainTextAlphabet_TextField.addFocusListener(lostFocus);
                PlainTextAlphabet_TextField.setEditable(false);
            } else if (index == 1) {
                callback.accept(Alphabet.VN.getAlphabet());
                PlainTextAlphabet_TextField.setText(Alphabet.VN.getAlphabet());
                PlainTextAlphabet_TextField.addFocusListener(lostFocus);
                PlainTextAlphabet_TextField.setEditable(false);
            } else {
                PlainTextAlphabet_TextField.setEditable(true);
                PlainTextAlphabet_TextField.addFocusListener(lostFocus);
            }
        });
    }

    public void onChangeAlgorithm(Consumer<String> callback) {
        AlgorithmSelector_ComboBox.addItemListener(event -> {
            callback.accept(AlgorithmSelector_ComboBox.getSelectedItem().toString());
        });
    }

    public void onChangeVigenereKey(Consumer<String> callback) {
        VigenereKey_TextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                callback.accept(VigenereKey_TextField.getText());
            }
        });
    }

    public void onChangeAffineKey(BiConsumer<Integer, String> callback) {
        InputKeyAB_TextField[0].addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                JTextField txtField = (JTextField) e.getSource();
                String text = txtField.getText();
                if (text.trim().length() > 0 && !text.matches("^\\d+$"))
                    throw new MyAppException(ErrorType.WRONG_AFFINE_KEY, ClassicScreen_View.this);
                Integer key = text.trim().length() > 0 ? Integer.parseInt(text) : null;
                callback.accept(key, "a");
            }
        });
        InputKeyAB_TextField[1].addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                JTextField txtField = (JTextField) e.getSource();
                String text = txtField.getText();
                if (text.trim().length() > 0 && !text.matches("^\\d+$"))
                    throw new MyAppException(ErrorType.WRONG_AFFINE_KEY, ClassicScreen_View.this);
                Integer key = text.trim().length() > 0 ? Integer.parseInt(text) : null;
                callback.accept(key, "b");
            }
        });
    }

    @Override
    public void initialLayout() {
        InputTextWrap_ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        InputTextWrap_ScrollPane.setBorder(BorderFactory.createTitledBorder("Nguồn"));
        OutputTextWrap_ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        OutputTextWrap_ScrollPane.setBorder(BorderFactory.createTitledBorder("Kết quả"));

        JPanel AlgorithmSettings = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0; {
            gbc.gridx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            AlgorithmSettings.add(new JLabel("Chọn thuật toán:"), gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            AlgorithmSettings.add(AlgorithmSelector_ComboBox, gbc);
        }
        gbc.gridy = 1; {
            JPanel wrapper = new JPanel(new GridBagLayout()); {
                var gbc2 = new GridBagConstraints();
                gbc2.insets = new Insets(0, 5, 0, 5);
                gbc2.gridy = 0;
                gbc2.gridx = 0;
                gbc2.gridheight = GridBagConstraints.REMAINDER;
                gbc2.gridwidth = 1;
                gbc2.weightx = 1;
                gbc2.fill = GridBagConstraints.BOTH;
                gbc2.anchor = GridBagConstraints.WEST;
                MatrixInput_Table.setMinCellSize(50);
                MatrixInput_Table.setViewPortSize(200);
                var tableWrap = new JPanel(new GridLayout());
                tableWrap.add(MatrixInput_Table);
                JScrollPane scrollPane = new JScrollPane(tableWrap);
                scrollPane.setPreferredSize(new Dimension(200, 200));
                wrapper.add(scrollPane, gbc2);

                gbc2.gridy = 1;
                gbc2.gridx = 1;
                gbc2.anchor = GridBagConstraints.NORTHWEST;
                gbc2.gridheight = 1;
                gbc2.gridwidth = 1;
                wrapper.add(new JLabel("Ma trận:"), gbc2);

                gbc2.gridy = 2;
                gbc2.gridx = 1;
                gbc2.anchor = GridBagConstraints.NORTHWEST;
                gbc2.gridheight = 1;
                gbc2.gridwidth = 1;

                JPanel PanelRow1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
                PanelRow1.add(new JLabel("("));
                PanelRow1.add(MatrixSize_TextField[0]); {
                    MatrixSize_TextField[0].setColumns(1);
                    MatrixSize_TextField[0].setHorizontalAlignment(SwingConstants.CENTER);
                }
                PanelRow1.add(new JLabel("x"));
                PanelRow1.add(MatrixSize_TextField[1]); {
                    MatrixSize_TextField[1].setColumns(1);
                    MatrixSize_TextField[1].setHorizontalAlignment(SwingConstants.CENTER);
                }
                PanelRow1.add(new JLabel(")"));
                wrapper.add(PanelRow1, gbc2);

                gbc2.insets.top = 5;
                gbc2.gridy = 3;
                gbc2.gridx = 1;
                JPanel matrixActionWrapper = new JPanel(new FlowLayout()); {
                    matrixActionWrapper.add(CreateMatrix_Button);
                    matrixActionWrapper.add(SaveMatrix_Button);
                    matrixActionWrapper.add(LoadMatrix_Button);
                }
                wrapper.add(matrixActionWrapper, gbc2);

                gbc2.gridy = 4;
                gbc2.gridx = 1;
                gbc2.fill = GridBagConstraints.HORIZONTAL;
                JTextArea txtArea = new JTextArea(); {
                    txtArea.setEditable(false);
                    txtArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    txtArea.setColumns(30);
                    txtArea.setRows(5);
                    EventFire_Support.addPropertyChangeListener("change_matrix_key", evt -> {
                        String matrixString = (String) evt.getNewValue();
                        txtArea.setText(matrixString);
                    });
                }
                JScrollPane outputMatrix_ScrollPane = new JScrollPane(txtArea); {
                    outputMatrix_ScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                    outputMatrix_ScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                }
                outputMatrix_ScrollPane.setBorder(new TitledBorder("Ma trận hiện tại"));
                wrapper.add(outputMatrix_ScrollPane, gbc2);
            }
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            AlgorithmSettings.add(wrapper, gbc);
            EventFire_Support.addPropertyChangeListener("change_algorithm", evt -> {
                String algorithm = (String) evt.getNewValue();
                wrapper.setVisible(algorithm.equals(ClassicScreen_Model.HILL_ALGORITHM));
            });
        }
        gbc.gridy = 2; {
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = 1;
            AlgorithmSettings.add(new JLabel("Chọn bảng alphabet:"), gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.gridwidth = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            AlgorithmSettings.add(AlphabetChoose_ComboBox, gbc);
        }
        gbc.gridy = 3; {
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            AlgorithmSettings.add(new JLabel("Alphabet gốc:"), gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            AlgorithmSettings.add(PlainTextAlphabet_TextField, gbc); {
                PlainTextAlphabet_TextField.setEditable(false);
                PlainTextAlphabet_TextField.setText("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            }
        };
        gbc.gridy = 4; {
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            var wrapper = new JPanel(new FlowLayout());
            wrapper.add(new JLabel("Hệ số k ("));
            wrapper.add(InputKeyAB_TextField[0]); {
                InputKeyAB_TextField[0].setColumns(1);
                InputKeyAB_TextField[0].setHorizontalAlignment(SwingConstants.CENTER);
            }
            wrapper.add(new JLabel(","));
            wrapper.add(InputKeyAB_TextField[1]); {
                InputKeyAB_TextField[1].setColumns(1);
                InputKeyAB_TextField[1].setHorizontalAlignment(SwingConstants.CENTER);
            }
            wrapper.add(new JLabel(")"));
            wrapper.add(LoadKeyAB_Button);
            wrapper.add(SaveKeyAB_Button);
            AlgorithmSettings.add(wrapper, gbc);
            EventFire_Support.addPropertyChangeListener("change_algorithm", evt -> {
                String algorithm = (String) evt.getNewValue();
                wrapper.setVisible(algorithm.equals(ClassicScreen_Model.AFFINE_ALGORITHM));
            });
        }
        {
            gbc.gridy = 5;
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets.bottom = 1;
            var label = new JLabel("Alphabet thay thế:");
            AlgorithmSettings.add(label, gbc);
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            AlgorithmSettings.add(CipherTextAlphabet_TextField, gbc); {
                CipherTextAlphabet_TextField.setText("zyxwvutsrqponmlkjihgfedcba");
            }
            // reset
            gbc.insets.bottom = 5;
            gbc.gridy = 6;
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.insets.top = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            var wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            wrapper.add(LoadKeyCipherText_Button);
            wrapper.add(SaveKeyCipherText_Button);
            wrapper.add(GenerateKeyCipherText_Button);
            AlgorithmSettings.add(wrapper, gbc);
            //reset
            gbc.insets.top = 5;

            EventFire_Support.addPropertyChangeListener("change_algorithm", evt -> {
                String algorithm = (String) evt.getNewValue();
                label.setVisible(algorithm.equals(ClassicScreen_Model.SUBSTITUTION_ALGORITHM));
                CipherTextAlphabet_TextField.setVisible(algorithm.equals(ClassicScreen_Model.SUBSTITUTION_ALGORITHM));
                wrapper.setVisible(algorithm.equals(ClassicScreen_Model.SUBSTITUTION_ALGORITHM));
            });
        } // hàng 5 và 6
        gbc.gridy = 7; {
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            var wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
            wrapper.add(new JLabel("Dịch chuyển ("), gbc);
            wrapper.add(ShiftKey_TextField); {
                ShiftKey_TextField.setColumns(2);
            }
            wrapper.add(new JLabel(") kí tự alphabet "));
            wrapper.add(LoadKeyShift_Button);
            wrapper.add(SaveKeyShift_Button);
            wrapper.add(GenerateKeyShift_Button);
            AlgorithmSettings.add(wrapper, gbc);
            wrapper.setVisible(false);
            EventFire_Support.addPropertyChangeListener("change_algorithm", evt -> {
                String algorithm = (String) evt.getNewValue();
                wrapper.setVisible(algorithm.equals(ClassicScreen_Model.CAESAR_ALGORITHM));
            });
        }
        {
            gbc.gridy = 8;
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets.bottom = 1;
            var label = new JLabel("Khóa mã hóa:");
            AlgorithmSettings.add(label, gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            AlgorithmSettings.add(VigenereKey_TextField, gbc); {
                VigenereKey_TextField.setText("zyxwvutsrqponmlkjihgfedcba");
            }
            // reset
            gbc.insets.bottom = 5;

            gbc.gridy = 9;
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.insets.top = 1;
            var wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            wrapper.add(LoadVigenereKey_Button);
            wrapper.add(SaveVigenereKey_Button);
            wrapper.add(GenerateVigenerateKey_Button);

            AlgorithmSettings.add(wrapper, gbc);
            //reset
            gbc.insets.top = 5;
            EventFire_Support.addPropertyChangeListener("change_algorithm", evt -> {
                String algorithm = (String) evt.getNewValue();
                wrapper.setVisible(algorithm.equals(ClassicScreen_Model.VIGENERE_ALGORITHM));
                label.setVisible(algorithm.equals(ClassicScreen_Model.VIGENERE_ALGORITHM));
                VigenereKey_TextField.setVisible(algorithm.equals(ClassicScreen_Model.VIGENERE_ALGORITHM));
            });
        } // hàng 8 và 9
        OverallContentWrap_Panel.add(InputTextWrap_ScrollPane);
        OverallContentWrap_Panel.add(OutputTextWrap_ScrollPane);

        CipherContentWrap_Panel.add(EncryptButton);
        CipherContentWrap_Panel.add(DecryptButton);

        var AlgorithmSettingsWrapper = new JPanel(); {
            AlgorithmSettingsWrapper.add(AlgorithmSettings);
        }

        this.setLayout(new BorderLayout());
        this.add(AlgorithmSettingsWrapper, BorderLayout.NORTH);
        this.add(OverallContentWrap_Panel, BorderLayout.CENTER);
        this.add(CipherContentWrap_Panel, BorderLayout.SOUTH);
    }

    public record MatrixCell(int row, int column, int data) { }

    public void onGenerateMatrixKey(Consumer<Integer> callback) {
        CreateMatrix_Button.addActionListener(x -> callback.accept(9));
    }

    public void onInputTextChange(Consumer<String> callback) {
        InputTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                callback.accept(InputTextArea.getText());
            }
        });
    }

    public void onEncryptButton_Click(Consumer<Void> callback) {
        EncryptButton.addActionListener(e -> callback.accept(null));
    }

    public void onDecryptButton_Click(Consumer<Void> callback) {
        DecryptButton.addActionListener(e -> callback.accept(null));
    }

    public void onChangeMatrixSize(Consumer<Integer> callback) {
        FocusListener listener = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                JTextField txtField = (JTextField) e.getSource();
                boolean isMatch = false;
                if (txtField.getText().length() > 0) {
                    isMatch = txtField.getText().matches("^\\d$");
                }
                if (!isMatch) {
                    MatrixSize_TextField[0].setText("");
                    MatrixSize_TextField[1].setText("");
                    throw new MyAppException(ErrorType.WRONG_MATRIX_SIZE, ClassicScreen_View.this);
                }
                Integer size = Integer.parseInt(txtField.getText());
                if (size <= 0 || size >= 10) {
                    MatrixSize_TextField[0].setText("");
                    MatrixSize_TextField[1].setText("");
                    throw new MyAppException(ErrorType.WRONG_MATRIX_SIZE, ClassicScreen_View.this);
                }
                MatrixSize_TextField[0].setText(txtField.getText());
                MatrixSize_TextField[1].setText(txtField.getText());
                callback.accept(size);
            }
        };
        for (var txtField : MatrixSize_TextField) txtField.addFocusListener(listener);
    }

    public void onMatrixCellChange(BiConsumer<MatrixCell, Integer[][]> callback) {
        MatrixInput_Table.addTableModelChangeListener((event, data) -> {
            System.out.println("onMatrixCellChange");
            int row = event.getFirstRow();
            int col = event.getColumn();
            Integer[][] dataCopy = new Integer[data.length][];
            for (int i = 0; i < data.length; i++) {
                dataCopy[i] = data[i].clone();
            }
            MatrixCell cell = new MatrixCell(row, col, dataCopy[row][col]);
            callback.accept(cell, dataCopy);
        });
    }

    @Override
    public void update(String event, Map<String, Object> data) {
        switch (event) {
            case "change_algorithm" -> {
                String algorithm = (String) data.get("algorithm");
                String selectedAlgorithm = AlgorithmSelector_ComboBox.getSelectedItem().toString();
                if (!algorithm.equals(selectedAlgorithm)) {
                    AlgorithmSelector_ComboBox.setSelectedItem(algorithm);
                }
                EventFire_Support.firePropertyChange("change_algorithm", null, algorithm);
            }
            case "change_matrix_key" -> {
                String matrixString = (String) data.get("matrix_as_string");
                EventFire_Support.firePropertyChange("change_matrix_key", null, matrixString);
                MatrixInput_Table.loadModel((Integer[][]) data.get("matrix"));
            }
            case "alphabet_change" -> {
                int alphabetIndex = (int) data.get("alphabet_index");
                if (AlphabetChoose_ComboBox.getSelectedIndex() != alphabetIndex) {
                    AlphabetChoose_ComboBox.setSelectedIndex(alphabetIndex);
                }
            }
            case "encrypted" -> {
                String cipherText = (String) data.get("cipher_text");
                OutputTextArea.setText(cipherText);
            }
            case "decrypted" -> {
                String plainText = (String) data.get("plain_text");
                OutputTextArea.setText(plainText);
            }
        }
    }
}
