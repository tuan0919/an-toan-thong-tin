package view.screen.basicEncrypt;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ActionListContainer extends JPanel {
    private State<String> state;
    private SwitchModeButton[] buttons;

    public ActionListContainer() {
        state = new State<>("Method_1");
        buttons = new SwitchModeButton[] {
                new SwitchModeButton(state, "Method_1"),
                new SwitchModeButton(state, "Method_2"),
                new SwitchModeButton(state, "Method_3"),
        };
        initialState();
    }

    public void initialState() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setBackground(Color.WHITE);

        var onClickHandler = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwitchModeButton casted = (SwitchModeButton) e.getComponent();
                state.setValue(casted.name);
            }
        };

        for (var btn : buttons) {
            this.add(btn);
            btn.addMouseListener(onClickHandler);
        }

    }

    static class SwitchModeButton extends JLabel {
        private boolean isClicked;
        private final String name;

        public SwitchModeButton(State<String> stateClick, String name) {
            this.name = name;
            this.isClicked = stateClick.getValue().equals(name);
            stateClick.onChangeValue((newValue) -> {
                renderUI();
            });
            this.setText(name);
            ImageIcon icon = new ImageIcon("C:\\Users\\Nguyen Tuan\\Downloads\\icons8-lock-48.png");
            Image resizedImg = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImg);
            this.setBackground(Color.WHITE);
            this.setIcon(resizedIcon);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setFont(new Font("Sitka Text", Font.BOLD, 13));
            renderUI();
        }

        private static final CompoundBorder raisedBorder = new CompoundBorder(
                new CompoundBorder(
                        new EmptyBorder(0, 5, 0, 5),
                        new BevelBorder(BevelBorder.RAISED)
                ),
                new EmptyBorder(5, 5, 5, 5)
        );

        private static final CompoundBorder loweredBorder = new CompoundBorder(
                new CompoundBorder(
                        new EmptyBorder(0, 5, 0, 5),
                        new BevelBorder(BevelBorder.LOWERED)
                ),
                new EmptyBorder(5, 5, 5, 5)
        );

        private void renderUI () {
            if (isClicked) {
                this.setBorder(loweredBorder);
            }
            else {
                this.setBorder(raisedBorder);
            }
        }


    }
}
