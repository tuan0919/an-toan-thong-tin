package View;

import Util.MyUtil;
import View.Component.MatrixTable_View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class TraditionalScreen_View extends AScreenView {
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

    public TraditionalScreen_View() {
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
            gbc.weightx = 0.3;
            gbc.gridx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            AlgorithmSettings.add(new JLabel("Chọn thuật toán:"), gbc);

            gbc.weightx = 0.5;
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            AlgorithmSettings.add(AlgorithmSelector_ComboBox, gbc);
        }

        gbc.gridy = 1; {
            Integer[][] data = new Integer[15][15]; // Ma trận 5x5
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    data[i][j] = i * data.length + j + 1; // Điền số từ 1 đến 25
                }
            }
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
                MatrixInput_Table.loadModel(data);
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
                PanelRow1.add(MatrixSize_TextField[0]);
                MatrixSize_TextField[0].setColumns(1);
                PanelRow1.add(new JLabel("x"));
                PanelRow1.add(MatrixSize_TextField[1]);
                MatrixSize_TextField[1].setColumns(1);
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
                    txtArea.setText(MyUtil.formatMatrix(data));
                    txtArea.setColumns(30);
                    txtArea.setRows(5);
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
        }
        gbc.gridy = 2; {
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            AlgorithmSettings.add(new JLabel("Chọn bảng alphabet:"), gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            AlgorithmSettings.add(AlphabetChoose_ComboBox, gbc);
        }
        gbc.gridy = 3; {
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.REMAINDER;
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
        }
        gbc.gridy = 4; {
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
        gbc.gridy = 5; {
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets.bottom = 1;
            AlgorithmSettings.add(new JLabel("Alphabet thay thế:"), gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            AlgorithmSettings.add(CipherTextAlphabet_TextField, gbc); {
                CipherTextAlphabet_TextField.setText("zyxwvutsrqponmlkjihgfedcba");
            }
            // reset
            gbc.insets.bottom = 5;
        }
        gbc.gridy = 6; {
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.EAST;
            gbc.insets.top = 1;
            var wrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            wrapper.add(LoadKeyCipherText_Button);
            wrapper.add(SaveKeyCipherText_Button);
            wrapper.add(GenerateKeyCipherText_Button);

            AlgorithmSettings.add(wrapper, gbc);
            //reset
            gbc.insets.top = 5;
        }
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
        }
        gbc.gridy = 8; {
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets.bottom = 1;
            AlgorithmSettings.add(new JLabel("Khóa mã hóa:"), gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            AlgorithmSettings.add(VigenereKey_TextField, gbc); {
                VigenereKey_TextField.setText("zyxwvutsrqponmlkjihgfedcba");
            }
            // reset
            gbc.insets.bottom = 5;
        }
        gbc.gridy = 9; {
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
        }

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

    public void onEncryptButton_Click(Consumer<ActionEvent> callback) {
        EncryptButton.addActionListener(e -> callback.accept(e));
    }

    public void onDecryptButton_Click(Consumer<ActionEvent> callback) {
        DecryptButton.addActionListener(e -> callback.accept(e));
    }
}
