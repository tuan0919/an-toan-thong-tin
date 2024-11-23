package Util;

import javax.swing.*;
import java.awt.*;

public class MyUtil {
    public static void showInfoMessage(Component target, String message, String title) {
        JOptionPane.showMessageDialog(target, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showWarnMessage(Component target, String message, String title) {
        JOptionPane.showMessageDialog(target, message, title, JOptionPane.WARNING_MESSAGE);
    }
}
