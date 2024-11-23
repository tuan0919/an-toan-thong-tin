package View;

import javax.swing.*;

public abstract class AScreenView extends JPanel {
    public abstract void initialComponent();
    public abstract void initialLayout();

    public AScreenView() {
        initialComponent();
        initialLayout();
    }
}
