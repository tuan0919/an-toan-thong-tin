package MyException;

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
            throw new RuntimeException(exception);
        }
    }
}
