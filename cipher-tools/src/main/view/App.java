package view;

import view.navigator.TabNavigator;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private JPanel appContentPane;
    public App(String title) throws HeadlessException {
        super(title);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.appContentPane = new JPanel(new BorderLayout());
        this.init();
    }

    public void init() {
        this.setContentPane( appContentPane);
        var tabNavigator = new TabNavigator();
        this.appContentPane.add(tabNavigator, BorderLayout.CENTER);
        this.pack();
    }

    public static void main(String[] args) {
        try {
            // Cài đặt Windows Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  // Sử dụng hệ thống Look and Feel (Windows)

        } catch (UnsupportedLookAndFeelException | ClassNotFoundException |
                 InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new App("NLU Cipher Tools"));
    }
}
