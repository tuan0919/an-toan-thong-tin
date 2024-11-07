package view.screen.basicEncrypt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InputContainer extends JPanel {
    private final JLabel LabelKeyShift;
    private final JTextField TextFieldShift;
    private final JLabel LabelKeyReplace;
    private final JTextField TextFieldReplace;
    private final JLabel LabelKeyA;
    private final JTextField TextFieldKeyA;
    private final JLabel LabelKeyB;
    private final JTextField TextFieldKeyB;

    private final JLabel LabelCurrentMode;

    public InputContainer(JComponent observable) {
        this.LabelKeyShift = new JLabel("Số kí tự cần dịch chuyển:");
        this.LabelKeyReplace = new JLabel("Dãy kí tự thay thế cho alphabet");
        this.LabelKeyA = new JLabel("Khóa A");
        this.LabelKeyB = new JLabel("Khóa B");
        this.TextFieldKeyA = new JTextField();
        this.TextFieldKeyB = new JTextField();
        this.TextFieldReplace = new JTextField();
        this.TextFieldShift = new JTextField();
        this.LabelCurrentMode = new JLabel();

        observable.addPropertyChangeListener("currentMode", (event) -> {
            String mode = event.getNewValue().toString();
            this.removeAll();
            LabelCurrentMode.setText(mode);
            this.add(LabelCurrentMode);
            switch (mode) {
                case "Mã hóa dịch vòng" -> {
                    renderMode1();
                    break;
                }
                case "Mã hóa thay thế" -> {
                    renderMode2();
                    break;
                }
                case "Mã hóa Affine" -> {
                    renderMode3();
                    break;
                }
            }
            this.revalidate();
            this.repaint();
        });
        renderUI();
    }

    public void renderUI() {
        this.setBorder(new EmptyBorder(5,5,5,5));
    }

    public void renderMode1() {
        this.setLayout(new GridLayout(3, 0));
        this.add(LabelKeyShift);
        this.add(TextFieldShift);
    }

    public void renderMode2() {
        this.setLayout(new GridLayout(3, 0));
        this.add(LabelKeyReplace);
        this.add(TextFieldReplace);
    }

    public void renderMode3() {
        this.setLayout(new GridLayout(5, 0));
        this.add(LabelKeyA);
        this.add(TextFieldKeyA);
        this.add(LabelKeyB);
        this.add(TextFieldKeyB);
    }
}
