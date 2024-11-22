package view.screen.symmetricEncrypt;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class InputTypeListContainer extends JPanel {
    private final SwitchModeButton[] buttons;

    public InputTypeListContainer(JComponent observable, Consumer<String> onModeChange) {
        buttons = new SwitchModeButton[] {
                new SwitchModeButton(observable, "Encrypt Text", onModeChange),
                new SwitchModeButton(observable, "Encrypt File", onModeChange)
        };
        initialState();
    }

    public void initialState() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setBackground(Color.WHITE);

        for (var btn : buttons) {
            this.add(btn);
        }
    }

    static class SwitchModeButton extends JLabel {
        private final String name;
        private boolean isHover = false;
        private boolean isClicked = false;
        private final Consumer<String> onClickHandler;

        public SwitchModeButton(JComponent observable, String name, Consumer<String> onClickHandler) {
            this.name = name;
            this.onClickHandler = onClickHandler;
            observable.addPropertyChangeListener("inputType", (event) ->{
                String newValue = event.getNewValue().toString();
                if (newValue.equals(name)) {
                    isClicked = true;
                    setBorder(loweredBorder);
                } else {
                    isClicked = false;
                    setBorder(raisedBorder);
                }
            });
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
            this.setText(name);
            ImageIcon icon = new ImageIcon("C:\\Users\\Nguyen Tuan\\Downloads\\icons8-lock-48.png");
            Image resizedImg = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImg);
            this.setBackground(Color.WHITE);
            this.setIcon(resizedIcon);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setFont(new Font("Sitka Text", Font.BOLD, 13));

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    if (isClicked) return;
                    isHover = false;
                    setBorder(raisedBorder);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    isHover = true;
                    setBorder(loweredBorder);
                    super.mouseEntered(e);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    onClickHandler.accept(name);
                }

            });
        }
    }
}
