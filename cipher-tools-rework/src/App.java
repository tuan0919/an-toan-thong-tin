import MyException.GlobalExceptionHandler;
import View.Navigator.TabNavigator;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    private TabNavigator TabNavigator;
    private JPanel AppContentPane;

    public App(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Tool Mã Hóa");
        this.setVisible(true);

        initialComponents();
        initialLayout();
    }

    private void initialLayout() {
        this.setContentPane(AppContentPane);
        AppContentPane.setLayout(new BorderLayout());
        AppContentPane.add(TabNavigator, BorderLayout.CENTER);

        this.pack();
    }

    private void initialComponents() {
        this.TabNavigator = new TabNavigator();
        this.AppContentPane = new JPanel();
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        new App();
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            GlobalExceptionHandler.handleAppException((Exception) ex);
        });
    }

}
