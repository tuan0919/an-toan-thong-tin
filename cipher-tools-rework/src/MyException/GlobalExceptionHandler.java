package MyException;

import Main.App;

import javax.swing.*;

public class GlobalExceptionHandler {
    public static void handleAppException(Exception exception) {
        if (exception instanceof MyAppException) {
            MyAppException myAppException = (MyAppException) exception;
            var caused = myAppException.getCausedComponent();
            ErrorType errorType = myAppException.getErrorType();
            JOptionPane.showMessageDialog(caused, errorType.getMessage(), errorType.getTitle(), errorType.getCode());
        } else {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(App.instance, exception.getMessage(), "Oops!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void handleIllegalArgumentException(IllegalArgumentException exception) {
        JOptionPane.showMessageDialog(App.instance, exception.getMessage(), "Illegal argument", JOptionPane.WARNING_MESSAGE);
    }
}
