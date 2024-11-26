package View;

import Model.Screen.ScreenObserver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;
import java.util.Map;
import java.util.function.Consumer;

public class SignatureScreen_View extends AScreenView implements ScreenObserver {
    private JComboBox<String> HashAlgorithm_ComboBox;
    private JComboBox<Integer> KeySize_ComboBox;
    private JButton GenerateKey_Button,
            SavePublicKey_Button,
            LoadPublicKey_Button,
            SavePrivateKey_Button,
            LoadPrivateKey_Button,
            LoadFile_Button,
            DeselectFile_Button,
            CreateSignature_Button,
            CheckSignature_Button
    ;
    private JTextArea PublicKey_TextArea,
            PrivateKey_TextArea,
            Signature_TextArea,
            InputText_TextArea,
            OutputText_TextArea;
    ;
    private JRadioButton[] ChooseUsageKey_RadioButtons,
            ChooseInputType_RadioButtons
    ;
    private JTabbedPane SignatureNavigator_TabbedPane;
    private JSplitPane MainSplitter_SplitPane;
    private JFileChooser UserFileChosen_FileChooser;
    private PropertyChangeSupport EventFire_Support;
    @Override
    public void initialComponent() {
        HashAlgorithm_ComboBox = new JComboBox<>();
        KeySize_ComboBox = new JComboBox<>();
        GenerateKey_Button = new JButton("Tạo cặp key");
        SavePublicKey_Button = new JButton("Lưu key");
        LoadPublicKey_Button = new JButton("Load key");
        SavePrivateKey_Button = new JButton("Lưu key");
        LoadPrivateKey_Button = new JButton("Load key");
        LoadFile_Button = new JButton("Load file");
        DeselectFile_Button = new JButton("Hủy bỏ file");
        SignatureNavigator_TabbedPane = new JTabbedPane();
        PublicKey_TextArea = new JTextArea();
        PrivateKey_TextArea = new JTextArea();
        Signature_TextArea = new JTextArea();
        InputText_TextArea = new JTextArea();
        OutputText_TextArea = new JTextArea();
        ChooseUsageKey_RadioButtons = new JRadioButton[]{
                new JRadioButton("Public key"),
                new JRadioButton("Private key"),
        };
        ChooseInputType_RadioButtons = new JRadioButton[]{
                new JRadioButton("File"),
                new JRadioButton("Văn bản"),
        };
        SignatureNavigator_TabbedPane = new JTabbedPane();
        UserFileChosen_FileChooser = new JFileChooser();
        EventFire_Support = new PropertyChangeSupport(this);
        CreateSignature_Button = new JButton("Tạo chữ ký số");
        CheckSignature_Button = new JButton("Xác thực chữ ký số");
        MainSplitter_SplitPane = new JSplitPane();
        MainSplitter_SplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    }
    @Override
    public void initialLayout() {
        JPanel AlgorithmSettingsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần

        gbc.gridy = 0; {
            gbc.gridx = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.NONE;
            AlgorithmSettingsPanel.add(new JLabel("Input type:"), gbc);

            gbc.gridx = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT)); {
                ButtonGroup group = new ButtonGroup(); {
                    group.add(ChooseInputType_RadioButtons[0]);
                    group.add(ChooseInputType_RadioButtons[1]);
                }
                wrapper.add(ChooseInputType_RadioButtons[0]);
                wrapper.add(ChooseInputType_RadioButtons[1]);
            }
            AlgorithmSettingsPanel.add(wrapper, gbc);
        }

        gbc.gridy = 1; {
            gbc.gridx = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.NONE;
            AlgorithmSettingsPanel.add(new JLabel("Input:"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            JScrollPane scrollPane = new JScrollPane(InputText_TextArea); {
                InputText_TextArea.setColumns(35);
                InputText_TextArea.setRows(5);
                InputText_TextArea.setLineWrap(true);
                InputText_TextArea.setWrapStyleWord(true);
            }

            JPanel PanelFileWrapper = new JPanel(); {
                var layout = new FlowLayout(FlowLayout.LEFT, 5,0);
                PanelFileWrapper.setLayout(layout);
                PanelFileWrapper.add(LoadFile_Button);
                PanelFileWrapper.add(DeselectFile_Button);
            }
            AlgorithmSettingsPanel.add(scrollPane, gbc);
            AlgorithmSettingsPanel.add(PanelFileWrapper, gbc);
            LoadFile_Button.setVisible(false);
            DeselectFile_Button.setVisible(false);
        }

        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.gridwidth = 1; {
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST; // Căn trái label
            AlgorithmSettingsPanel.add(new JLabel("Thuật toán băm:"), gbc);
            gbc.gridx = 1;
            AlgorithmSettingsPanel.add(HashAlgorithm_ComboBox, gbc);
            gbc.gridx = 2;
            AlgorithmSettingsPanel.add(new JLabel("Kích thước khóa:"), gbc);
            gbc.gridx = 3;
            AlgorithmSettingsPanel.add(KeySize_ComboBox, gbc);
            gbc.gridx = 4;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            AlgorithmSettingsPanel.add(GenerateKey_Button, gbc);
        }

        gbc.gridy = 3; {
            gbc.gridx = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.NONE;
            AlgorithmSettingsPanel.add(new JLabel("Public key:"), gbc);

            gbc.gridx = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER; // Chiếm hết cột còn lại
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JPanel wrapper = new JPanel(new BorderLayout()); {
                JScrollPane publicKeyScrollPane = new JScrollPane(PublicKey_TextArea);
                PublicKey_TextArea.setWrapStyleWord(true);
                PublicKey_TextArea.setLineWrap(true);
                PublicKey_TextArea.setColumns(35);
                PublicKey_TextArea.setRows(5);
                publicKeyScrollPane.setViewportView(PublicKey_TextArea);
                wrapper.add(publicKeyScrollPane, BorderLayout.CENTER);

                JPanel PublicKeyWrapper_JPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                PublicKeyWrapper_JPanel.add(LoadPublicKey_Button);
                PublicKeyWrapper_JPanel.add(SavePublicKey_Button);
                wrapper.add(PublicKeyWrapper_JPanel, BorderLayout.SOUTH);
            }
            AlgorithmSettingsPanel.add(wrapper, gbc);
        }

        gbc.gridy = 4; {
            gbc.gridx = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.fill = GridBagConstraints.NONE;
            AlgorithmSettingsPanel.add(new JLabel("Private key:"), gbc);

            gbc.gridx = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER; // Chiếm hết cột còn lại
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JPanel wrapper = new JPanel(new BorderLayout()); {
                JScrollPane privateKeyScrollPane = new JScrollPane(PrivateKey_TextArea);
                PrivateKey_TextArea.setWrapStyleWord(true);
                PrivateKey_TextArea.setLineWrap(true);
                PrivateKey_TextArea.setColumns(35);
                PrivateKey_TextArea.setRows(5);
                privateKeyScrollPane.setViewportView(PrivateKey_TextArea);
                wrapper.add(privateKeyScrollPane, BorderLayout.CENTER);

                JPanel PrivateKeyWrapper_JPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                PrivateKeyWrapper_JPanel.add(LoadPrivateKey_Button);
                PrivateKeyWrapper_JPanel.add(SavePrivateKey_Button);
                wrapper.add(PrivateKeyWrapper_JPanel, BorderLayout.SOUTH);
            }
            AlgorithmSettingsPanel.add(wrapper, gbc);
        }

        gbc.gridy = 5; {
            gbc.gridx = 0;
            gbc.weightx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.NONE;
            AlgorithmSettingsPanel.add(new JLabel("Key sử dụng:"), gbc);

            gbc.gridx = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT)); {
                ButtonGroup group = new ButtonGroup(); {
                    group.add(ChooseUsageKey_RadioButtons[0]);
                    group.add(ChooseUsageKey_RadioButtons[1]);
                }
                wrapper.add(ChooseUsageKey_RadioButtons[0]);
                wrapper.add(ChooseUsageKey_RadioButtons[1]);
            }
            AlgorithmSettingsPanel.add(wrapper, gbc);
        }


        // Đóng gói panel vào một JPanel Wrapper để căn giữa
        var titledBorder  = BorderFactory.createTitledBorder("Cài đặt mã hóa");
        {
            titledBorder.setTitlePosition(TitledBorder.TOP); // Tiêu đề nằm ở phía trên
            titledBorder.setTitleJustification(TitledBorder.LEFT); // Căn giữa tiêu đề

            // Thêm padding (margin bên trong) cho TitledBorder
            titledBorder.setBorder(BorderFactory.createCompoundBorder(
                   new EmptyBorder(0, 10, 0, 10), // Thêm padding vào các cạnh của tiêu đề
                    titledBorder.getBorder()
            ));
        }
        JPanel AlgorithmSettingsPanelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane HeaderScrollPane = new JScrollPane(AlgorithmSettingsPanelWrapper);
        AlgorithmSettingsPanelWrapper.add(AlgorithmSettingsPanel);
        AlgorithmSettingsPanelWrapper.setBorder(titledBorder);

        MainSplitter_SplitPane.setTopComponent(HeaderScrollPane);
        MainSplitter_SplitPane.setBottomComponent(SignatureNavigator_TabbedPane);

        // Thêm vào layout chính của cửa sổ
        this.setLayout(new BorderLayout());
        this.add(MainSplitter_SplitPane, BorderLayout.CENTER); {
            titledBorder  = BorderFactory.createTitledBorder(""); {
                titledBorder.setTitlePosition(TitledBorder.TOP); // Tiêu đề nằm ở phía trên
                titledBorder.setTitleJustification(TitledBorder.LEFT); // Căn giữa tiêu đề

                // Thêm padding (margin bên trong) cho TitledBorder
                titledBorder.setBorder(BorderFactory.createCompoundBorder(
                        new EmptyBorder(10, 10, 10, 10), // Thêm padding vào các cạnh của tiêu đề
                        titledBorder.getBorder()
                ));
            }
            var SignWithKey_TabPanel = new JPanel(new BorderLayout()); {
                JScrollPane scrollPane = new JScrollPane();
                titledBorder  = BorderFactory.createTitledBorder(""); {
                    titledBorder.setTitlePosition(TitledBorder.TOP); // Tiêu đề nằm ở phía trên
                    titledBorder.setTitleJustification(TitledBorder.LEFT); // Căn giữa tiêu đề

                    // Thêm padding (margin bên trong) cho TitledBorder
                    titledBorder.setBorder(BorderFactory.createCompoundBorder(
                            new EmptyBorder(10, 10, 10, 10), // Thêm padding vào các cạnh của tiêu đề
                            titledBorder.getBorder()
                    ));
                }
                scrollPane.setBorder(titledBorder);
                scrollPane.setViewportView(OutputText_TextArea);
                OutputText_TextArea.setWrapStyleWord(true);
                OutputText_TextArea.setLineWrap(true);
                OutputText_TextArea.setEditable(false);
                SignWithKey_TabPanel.add(scrollPane, BorderLayout.CENTER);
                JPanel ButtonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
                    ButtonWrapper.add(CreateSignature_Button);
                }
                SignWithKey_TabPanel.add(ButtonWrapper, BorderLayout.SOUTH);
            }
            var CheckSignKey_TabPanel = new JPanel(new BorderLayout()); {
                JScrollPane scrollPane = new JScrollPane();
                titledBorder  = BorderFactory.createTitledBorder(""); {
                    titledBorder.setTitlePosition(TitledBorder.TOP); // Tiêu đề nằm ở phía trên
                    titledBorder.setTitleJustification(TitledBorder.LEFT); // Căn giữa tiêu đề

                    // Thêm padding (margin bên trong) cho TitledBorder
                    titledBorder.setBorder(BorderFactory.createCompoundBorder(
                            new EmptyBorder(10, 10, 10, 10), // Thêm padding vào các cạnh của tiêu đề
                            titledBorder.getBorder()
                    ));
                }
                scrollPane.setBorder(titledBorder);
                scrollPane.setViewportView(Signature_TextArea);
                Signature_TextArea.setWrapStyleWord(true);
                Signature_TextArea.setLineWrap(true);
                CheckSignKey_TabPanel.add(scrollPane, BorderLayout.CENTER);
                JPanel ButtonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
                    ButtonWrapper.add(CheckSignature_Button);
                }
                CheckSignKey_TabPanel.add(ButtonWrapper, BorderLayout.SOUTH);
            }
            SignatureNavigator_TabbedPane.setBorder(titledBorder);
            SignatureNavigator_TabbedPane.addTab("Tạo chữ ký", SignWithKey_TabPanel);
            SignatureNavigator_TabbedPane.addTab("Xác thực chữ ký", CheckSignKey_TabPanel);
        }
    }

    @Override
    public void update(String event, Map<String, Object> data) {

    }
}
