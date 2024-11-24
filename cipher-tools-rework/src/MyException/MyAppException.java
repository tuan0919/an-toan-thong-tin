package MyException;

import java.awt.*;

public class MyAppException extends RuntimeException {
    private ErrorType errorType;
    private Component causedComponent;

    public MyAppException(ErrorType errorType, Component causedComponent) {
        this.errorType = errorType;
        this.causedComponent = causedComponent;
    }

    public Component getCausedComponent() {
        return causedComponent;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
}
