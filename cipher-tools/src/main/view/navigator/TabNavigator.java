package view.navigator;

import view.screen.basicEncrypt.BasicEncryptionScreen;
import view.screen.Screen;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tab container của ứng dụng. Chứa các màn hình khác nhhau của ứng dụng, mỗi màn hình trong tab sẽ là một phương pháp hóa.
 */
public class TabNavigator extends JTabbedPane {
    List<Screen> screens = new ArrayList<>();
    public TabNavigator() {
        initialState();
    }
    public void initialState() {
        var screen1 = new BasicEncryptionScreen();
        var screen2 = new BasicEncryptionScreen();
        var screen3 = new BasicEncryptionScreen();
        var screen4 = new BasicEncryptionScreen();
        screens.addAll(List.of(screen1, screen2, screen3, screen4));
        this.addTab("Mã hóa cơ bản", screen1);
        this.addTab("Mã hóa đối xứng", screen2);
        this.addTab("Mã hóa bất đối xứng", screen3);
        this.addTab("Chữ ký điện tử", screen4);
    }
}
