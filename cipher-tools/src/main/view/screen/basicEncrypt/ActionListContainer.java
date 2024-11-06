package view.screen.basicEncrypt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ActionListContainer extends JPanel {
    private JLabel[] buttons;
    public ActionListContainer() {
        buttons = new JLabel[] {
                new JLabel("Thuật toán 1"),
                new JLabel("Thuật toán 2")
        };
        initialState();
    }
    public void initialState() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon("C:\\Users\\Nguyen Tuan\\Downloads\\icons8-lock-48.png");
        Image resizedImg = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImg);
        for (var btn : buttons) {
            btn.setBackground(Color.WHITE);
            btn.setIcon(resizedIcon);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.setBorder(new EmptyBorder(0,5,0,5));
            this.add(btn);
        }
    }
}
