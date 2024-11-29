package View;

import Model.Screen.ScreenObserver;
import Model.Screen.SignatureScreen_Model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;
import java.util.function.Consumer;
import java.util.List;

public class SignatureScreen_View extends AScreenView implements ScreenObserver {
    private JComboBox<String> HashAlgorithm_ComboBox;
    private JComboBox<Integer> KeySize_ComboBox;
    private JLabel ChosenFilePath_Label;
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

    public void onInputFileChosen(Consumer<File> callback) {
        EventFire_Support.addPropertyChangeListener("input_file_chosen", event -> {
            callback.accept((File) event.getNewValue());
        });
    }

    public void onLoadFileButton_Click(Consumer<Void> callback) {
        LoadFile_Button.addActionListener(x -> callback.accept(null));
    }

    public void onCheckingSignatureButton_Click(Consumer<Void> callback) {
        CheckSignature_Button.addActionListener(x -> callback.accept(null));
    }

    public void onChangeSignatureInputText(Consumer<String> callback) {
        Signature_TextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String signatureText = Signature_TextArea.getText();
                if (signatureText != null && signatureText.length() > 0) {
                    callback.accept(Signature_TextArea.getText());
                }
            }
        });
    }

    public void onChooseUsageKey(Consumer<Class<? extends Key>> callback) {
        ChooseUsageKey_RadioButtons[0].setActionCommand("Public_Key");
        ChooseUsageKey_RadioButtons[1].setActionCommand("Private_Key");
        ItemListener listener = event -> {
            JRadioButton sourceButton = (JRadioButton) event.getSource();
            String cmd = sourceButton.getActionCommand();
            var clazz = cmd.equals("Public_Key") ? PublicKey.class : PrivateKey.class;
            callback.accept(clazz);
        };
        ChooseUsageKey_RadioButtons[0].addItemListener(listener);
        ChooseUsageKey_RadioButtons[1].addItemListener(listener);
    }

    public void onGenerateKeyButton_Click(Consumer<Void> callback) {
        GenerateKey_Button.addActionListener(x -> callback.accept(null));
    }

    public void onChangeInputType(Consumer<Integer> callback) {
        ChooseInputType_RadioButtons[0].setActionCommand("file");
        ChooseInputType_RadioButtons[1].setActionCommand("text");
        ItemListener listener = (e) -> {
            callback.accept(ChooseInputType_RadioButtons[0].isSelected() ?
                    SignatureScreen_Model.INPUT_FILE : SignatureScreen_Model.INPUT_TEXT);
        };
        ChooseInputType_RadioButtons[0].addItemListener(listener);
        ChooseInputType_RadioButtons[1].addItemListener(listener);
    }

    public void onCreateSignatureButton_Click(Consumer<Void> callback) {
        CreateSignature_Button.addActionListener(x -> callback.accept(null));
    }

    public void onChooseAlgorithm_ComboBox(Consumer<String> callback) {
        HashAlgorithm_ComboBox.addItemListener(e -> {
            callback.accept(HashAlgorithm_ComboBox.getSelectedItem().toString());
        });
    }

    public void onInputTextChange(Consumer<String> callback) {
        InputText_TextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                callback.accept(InputText_TextArea.getText());
            }
        });
    }

    public void onChooseKeySize_ComboBox(Consumer<Integer> callback) {
        KeySize_ComboBox.addItemListener(e -> {
            int selectedIndex = KeySize_ComboBox.getSelectedIndex();
            callback.accept(KeySize_ComboBox.getItemAt(selectedIndex));
        });
    }

    @Override
    public void initialComponent() {
        HashAlgorithm_ComboBox = new JComboBox<>();
        KeySize_ComboBox = new JComboBox<>();
        GenerateKey_Button = new JButton("Generate key pair");
        SavePublicKey_Button = new JButton("Save key");
        LoadPublicKey_Button = new JButton("Load key");
        SavePrivateKey_Button = new JButton("Save key");
        LoadPrivateKey_Button = new JButton("Load key");
        LoadFile_Button = new JButton("Choose file");
        DeselectFile_Button = new JButton("Cancel file");
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
                new JRadioButton("Text"),
        };
        ChosenFilePath_Label = new JLabel("No file has been selected.");
        SignatureNavigator_TabbedPane = new JTabbedPane();
        UserFileChosen_FileChooser = new JFileChooser();
        EventFire_Support = new PropertyChangeSupport(this);
        CreateSignature_Button = new JButton("Create signature");
        CheckSignature_Button = new JButton("Validate signature");
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
                InputText_TextArea.addPropertyChangeListener("change_visible", e -> {
                    boolean isShow = (boolean) e.getNewValue();
                    scrollPane.setVisible(isShow);
                });
            }

            JPanel PanelFileWrapper = new JPanel(); {
                var layout = new FlowLayout(FlowLayout.LEFT, 5,0);
                PanelFileWrapper.setLayout(layout);
                PanelFileWrapper.add(LoadFile_Button);
                PanelFileWrapper.add(DeselectFile_Button);
                PanelFileWrapper.add(ChosenFilePath_Label);
            }
            AlgorithmSettingsPanel.add(scrollPane, gbc);
            AlgorithmSettingsPanel.add(PanelFileWrapper, gbc);
        }

        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.gridwidth = 1; {
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST; // Căn trái label
            AlgorithmSettingsPanel.add(new JLabel("Hash algorithm:"), gbc);
            gbc.gridx = 1;
            AlgorithmSettingsPanel.add(HashAlgorithm_ComboBox, gbc);
            gbc.gridx = 2;
            AlgorithmSettingsPanel.add(new JLabel("Key size:"), gbc);
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
            AlgorithmSettingsPanel.add(new JLabel("Usage key:"), gbc);

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
        var titledBorder  = BorderFactory.createTitledBorder("Algorithm settings");
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
            SignatureNavigator_TabbedPane.addTab("Signing input", SignWithKey_TabPanel);
            SignatureNavigator_TabbedPane.addTab("Validate signed input", CheckSignKey_TabPanel);
        }
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


    public void onChooseLocation_ForSavePrivateKey(Consumer<File> callback) {
        EventFire_Support.addPropertyChangeListener("user_choose_save_private_key_location", (event) -> {
            File file = (File) event.getNewValue();
            callback.accept(file);
        });
    }

    public void onChooseLocation_ForLoadPrivateKey(Consumer<File> callback) {
        EventFire_Support.addPropertyChangeListener("user_choose_load_private_key_location", (event) -> {
            File file = (File) event.getNewValue();
            callback.accept(file);
        });
    }

    public int openJFileChooser_ForSavePrivateKey() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files and PEM Files (*.txt, *.pem)", "txt", "pem");
        UserFileChosen_FileChooser.setFileFilter(filter);
        int result = UserFileChosen_FileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = UserFileChosen_FileChooser.getSelectedFile();
            EventFire_Support.firePropertyChange("user_choose_save_private_key_location", null, file);
        }
        return result;
    }

    public int openJFileChooser_ForLoadPrivateKey() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files and PEM Files (*.txt, *.pem)", "txt", "pem");
        UserFileChosen_FileChooser.setFileFilter(filter);
        int result = UserFileChosen_FileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = UserFileChosen_FileChooser.getSelectedFile();
            EventFire_Support.firePropertyChange("user_choose_load_private_key_location", null, file);
        }
        return result;
    }

    public void onChooseLocation_ForSavePublicKey(Consumer<File> callback) {
        EventFire_Support.addPropertyChangeListener("user_choose_save_public_key_location", (event) -> {
            File file = (File) event.getNewValue();
            callback.accept(file);
        });
    }

    public void onChooseLocation_ForLoadPublicKey(Consumer<File> callback) {
        EventFire_Support.addPropertyChangeListener("user_choose_load_public_key_location", (event) -> {
            File file = (File) event.getNewValue();
            callback.accept(file);
        });
    }

    public int openJFileChooser_ForSavePublicKey() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files and PEM Files (*.txt, *.pem)", "txt", "pem");
        UserFileChosen_FileChooser.setFileFilter(filter);
        int result = UserFileChosen_FileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = UserFileChosen_FileChooser.getSelectedFile();
            EventFire_Support.firePropertyChange("user_choose_save_public_key_location", null, file);
        }
        return result;
    }

    public int openJFileChooser_ForLoadPublicKey() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files and PEM Files (*.txt, *.pem)", "txt", "pem");
        UserFileChosen_FileChooser.setFileFilter(filter);
        int result = UserFileChosen_FileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = UserFileChosen_FileChooser.getSelectedFile();
            EventFire_Support.firePropertyChange("user_choose_load_public_key_location", null, file);
        }
        return result;
    }

    @Override
    public void update(String event, Map<String, Object> data) {
        switch (event) {
            case "first_load" -> {
                List<String> algorithms = (List<String>) data.get("available_algorithm");
                List<Integer> keySizes = (List<Integer>) data.get("available_key_size");
                Integer currentKeySize = (Integer) data.get("current_key_size");
                String currentAlgorithm = (String) data.get("current_algorithm");
                var clazz = (Class<? extends Key>) data.get("current_input_mode");
                if (clazz == PrivateKey.class) {
                    ChooseUsageKey_RadioButtons[1].setSelected(true);
                } else {
                    ChooseUsageKey_RadioButtons[0].setSelected(true);
                }
                for (String algorithm : algorithms) {
                    HashAlgorithm_ComboBox.addItem(algorithm);
                    if (currentAlgorithm.equals(algorithm)) HashAlgorithm_ComboBox.setSelectedItem(algorithm);
                }
                for (int keySize : keySizes) {
                    KeySize_ComboBox.addItem(keySize);
                    if (keySize == currentKeySize) KeySize_ComboBox.setSelectedItem(keySize);
                }
            }
            case "generated_key_pair" -> {
                String privateKey = (String) data.get("generated_private_key");
                String publicKey = (String) data.get("generated_public_key");
                PrivateKey_TextArea.setText(privateKey);
                PublicKey_TextArea.setText(publicKey);
            }
            case "change_input_mode" -> {
                int currentInputMode = (int) data.get("current_input_mode");
                if (currentInputMode == SignatureScreen_Model.INPUT_FILE) {
                    InputText_TextArea.firePropertyChange("change_visible", true, false);
                    LoadFile_Button.setVisible(true);
                    DeselectFile_Button.setVisible(true);
                    if (!ChooseInputType_RadioButtons[0].isSelected()) {
                        ChooseInputType_RadioButtons[0].setSelected(true);
                    }
                } else {
                    InputText_TextArea.firePropertyChange("change_visible", false, true);
                    LoadFile_Button.setVisible(false);
                    DeselectFile_Button.setVisible(false);
                    if (!ChooseInputType_RadioButtons[1].isSelected()) {
                        ChooseInputType_RadioButtons[1].setSelected(true);
                    }
                }
            }
            case "created_signature" -> {
                String signature = (String) data.get("signature_created");
                OutputText_TextArea.setText(signature);
            }
            case "open_file_chooser_for_chosen_file" -> {
                int result = UserFileChosen_FileChooser.showOpenDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    var file = UserFileChosen_FileChooser.getSelectedFile();
                    EventFire_Support.firePropertyChange("input_file_chosen", null, file);
                    ChosenFilePath_Label.setText(file.getAbsolutePath());
                }
            }
            case "load_private_key" -> {
                String secretKey = (String) data.get("current_private_key");
                PrivateKey_TextArea.setText(secretKey);
            }
            case "load_public_key" -> {
                String secretKey = (String) data.get("current_public_key");
                PublicKey_TextArea.setText(secretKey);
            }
        }
    }
}
