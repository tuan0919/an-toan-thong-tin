package view.screen.symmetricEncrypt;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class InputContainer extends JPanel {
    private final JLabel LabelInputFile;
    private final JTextField TextFieldInputFile;
    private final JButton FileBrowserButton;
    private final JLabel LabelFunction;
    private final JComboBox<String> FunctionComboBox;
    private final JLabel LabelInputText;
    private final JTextArea TextAreaInputTextArea;
    private final JLabel LabelMode;
    private final JComboBox<String> ModeComboBox;
    private final JLabel LabelKey;
    private final JTextField TextFieldKey;
    private final JButton ButtonEncrypt;
    private final JButton ButtonDecrypt;
    private final JLabel LabelKeySize;
    private final JComboBox<String> KeySizeComboBox;
    private final JLabel LabelPadding;
    private final JComboBox<String> PaddingComboBox;
    private final JButton RollKeyButton;
    private final JButton AcceptKeyButton;
    private final JLabel LabelOutputText;
    private final JTextArea TextAreaOutputTextArea;

    public InputContainer(JComponent observable) {
        this.LabelInputFile = new JLabel("File");
        this.TextFieldInputFile = new JTextField();
        this.FileBrowserButton = new JButton("browse");
        this.LabelInputText = new JLabel("Input text");
        this.TextAreaInputTextArea = new JTextArea();
        this.LabelFunction = new JLabel("Function");
        this.FunctionComboBox = new JComboBox<>(new String[]{"AES", "DES"});
        this.LabelMode = new JLabel("Mode");
        this.ModeComboBox = new JComboBox<>(new String[]{"ECB", "CBC", "OFB", "CFB"});
        this.LabelKey = new JLabel("Key");
        this.TextFieldKey = new JTextField();
        this.RollKeyButton = new JButton("Roll New Key");
        this.AcceptKeyButton = new JButton("Accept Key");
        this.ButtonEncrypt = new JButton("Encrypt");
        this.ButtonDecrypt = new JButton("Decrypt");
        this.LabelKeySize = new JLabel("Key size in Bits");
        this.KeySizeComboBox = new JComboBox<>(new String[]{"128", "192", "256"});
        this.LabelPadding = new JLabel("Padding");
        this.PaddingComboBox = new JComboBox<>(new String[]{"NoPadding", "PKCS5Padding", "PKCS7Padding"});
        this.LabelOutputText = new JLabel("Output text");
        this.TextAreaOutputTextArea = new JTextArea();

        observable.addPropertyChangeListener("inputType", event -> {
            String mode = event.getNewValue().toString();
            this.removeAll();
            switch (mode) {
                case "Encrypt Text" -> {
                    System.out.println("Encrypt Text");
                    renderEncryptTextMode();
                    break;
                }
                case "Encrypt File" -> {
                    System.out.println("Encrypt File");
                    renderEncryptFileMode();
                    break;
                }
            }
            this.revalidate();
            this.repaint();
        });
        this.initialState();
    }

    private void renderEncryptFileMode() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;

        {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.3;
            this.add(LabelInputFile, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.6;
            TextFieldInputFile.setEditable(false);
            this.add(TextFieldInputFile, gbc);

            gbc.gridx = 2;
            gbc.weightx = 0.1;
            this.add(FileBrowserButton, gbc);
        }

        {
            gbc.gridy = 2;
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            this.add(LabelFunction, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            gbc.gridwidth = 3;
            this.add(FunctionComboBox, gbc);
        }

        {
            gbc.gridy = 3;
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            this.add(LabelMode, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            gbc.gridwidth = 3;
            this.add(ModeComboBox, gbc);
        }

        {
            gbc.gridy = 4;
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            this.add(LabelKey, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.5;
            gbc.gridwidth = 1;
            this.add(TextFieldKey, gbc);

            gbc.gridx = 2;
            gbc.weightx = 0.1;
            gbc.gridwidth = 1;
            this.add(RollKeyButton, gbc);

            gbc.gridx = 3;
            gbc.weightx = 0.1;
            gbc.gridwidth = 1;
            this.add(AcceptKeyButton, gbc);
        }

        {
            gbc.gridy = 5;
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            this.add(LabelKeySize, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            gbc.gridwidth = 2;
            this.add(KeySizeComboBox, gbc);
        }

        {
            gbc.gridy = 6;
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            this.add(LabelPadding, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            gbc.gridwidth = 2;
            this.add(PaddingComboBox, gbc);
        }

        {
            gbc.gridy = 7;
            gbc.gridx = 1;
            gbc.weightx = 0.7;
            var ButtonWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
            ButtonWrapper.add(this.ButtonEncrypt);
            ButtonWrapper.add(this.ButtonDecrypt);
            this.add(ButtonWrapper, gbc);
        }

        gbc.gridy = 8;
        gbc.weighty = 1.0;
        this.add(new JPanel(), gbc);
    }

    private void renderEncryptTextMode() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;

        {
            gbc.gridy = 1;
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            this.add(LabelInputText, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            gbc.gridwidth = 3;
            this.add(TextAreaInputTextArea, gbc);
            TextAreaInputTextArea.setRows(8);
            TextAreaInputTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED));
        }

        {
            gbc.gridy = 2;
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            this.add(LabelFunction, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            gbc.gridwidth = 3;
            this.add(FunctionComboBox, gbc);
        }

        {
            gbc.gridy = 3;
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            this.add(LabelMode, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            gbc.gridwidth = 3;
            this.add(ModeComboBox, gbc);
        }

        {
            gbc.gridy = 4;
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            this.add(LabelKey, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.5;
            gbc.gridwidth = 1;
            this.add(TextFieldKey, gbc);

            gbc.gridx = 2;
            gbc.weightx = 0.1;
            gbc.gridwidth = 1;
            this.add(RollKeyButton, gbc);

            gbc.gridx = 3;
            gbc.weightx = 0.1;
            gbc.gridwidth = 1;
            this.add(AcceptKeyButton, gbc);
        }

        {
            gbc.gridy = 5;
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            this.add(LabelKeySize, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            gbc.gridwidth = 2;
            this.add(KeySizeComboBox, gbc);
        }

        {
            gbc.gridy = 6;
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            this.add(LabelPadding, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            gbc.gridwidth = 2;
            this.add(PaddingComboBox, gbc);
        }

        {
            gbc.gridy = 7;
            gbc.gridx = 1;
            gbc.weightx = 0.7;
            var ButtonWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
            ButtonWrapper.add(this.ButtonEncrypt);
            ButtonWrapper.add(this.ButtonDecrypt);
            this.add(ButtonWrapper, gbc);
        }

        {
            gbc.gridy = 8;
            gbc.gridx = 0;
            gbc.weightx = 0.3;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            this.add(LabelOutputText, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            gbc.gridwidth = 3;
            this.add(TextAreaOutputTextArea, gbc);
            TextAreaOutputTextArea.setRows(8);
            TextAreaOutputTextArea.setBorder(new BevelBorder(BevelBorder.LOWERED));
        }
    }

    public void initialState() {
        var layout = new GridBagLayout();
        this.setLayout(layout);
    }
}
