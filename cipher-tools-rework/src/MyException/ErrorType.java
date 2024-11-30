package MyException;

import javax.swing.*;

public enum ErrorType {
    EMPTY_INPUT_FOR_ENCRYPT("Failed to encrypt", "Input field is missing. Please choose a file or enter some plain texts before encrypting.", JOptionPane.WARNING_MESSAGE),
    EMPTY_INPUT_FOR_DECRYPT("Failed to decrypt", "Input field is missing. Please choose a file or enter some plain texts before decrypting.", JOptionPane.WARNING_MESSAGE),
    UNKNOWN_ERROR("Oops!", "Something went wrong.", JOptionPane.ERROR_MESSAGE),
    BAD_INPUT_ALGORITHM("Failed to execute", "Your settings is not valid. \nPlease check again.", JOptionPane.ERROR_MESSAGE),
    FILE_FAILED_TO_ENCRYPT("Failed to encrypt", "Failed to encrypt your input. \nPlease make sure your current settings is valid.", JOptionPane.WARNING_MESSAGE),
    FILE_FAILED_TO_DECRYPT("Failed to decrypt", "Failed to decrypt your input. \nPlease make sure your current settings is valid.", JOptionPane.WARNING_MESSAGE),
    BAD_INPUT_FOR_NO_PADDING("Warning!", "In this mode, you must be ensure by yourself that your input is multiples of 8.", JOptionPane.WARNING_MESSAGE),
    UNSUPPORTED_ALGORITHM("Failed to execute", "Current algorithm is not supported.", JOptionPane.WARNING_MESSAGE),
    EMPTY_KEY_WHEN_SAVE("Failed to execute", "Your key is empty. \nPlease make sure that your key is valid before saving.", JOptionPane.WARNING_MESSAGE),
    WRONG_FILE_FORMAT_SAVE_LOCATION("Failed to save", "Your current file's format is not supported.", JOptionPane.ERROR_MESSAGE),
    SAVE_FILE_FAILED("Failed to save", "Failed when saving your file \nPlease try again.", JOptionPane.WARNING_MESSAGE),
    WRONG_FILE_FORMAT_LOAD_KEY("Failed to load", "Your key is empty. \nPlease make sure that your key is valid before saving.", JOptionPane.ERROR_MESSAGE),
    IO_ERROR("Failed to access file", "Something went wrong when accessing your file.", JOptionPane.ERROR_MESSAGE),
    WRONG_MATRIX_SIZE("Wrong key input", "Sorry, our program only accept size <= 9 when creating key for this algorithm.", JOptionPane.WARNING_MESSAGE),
    WRONG_AFFINE_KEY("Wrong key input", "Key must be an integer", JOptionPane.WARNING_MESSAGE),
    WRONG_CAESAR_KEY("Wrong key input", "Key must be an integer", JOptionPane.WARNING_MESSAGE),
    WRONG_SUBSTITUTION_KEY("Wrong key input", "Key's size must be the same as applying alphabets.", JOptionPane.WARNING_MESSAGE),
    UNKNOWN_SIGNATURE_FORMAT("Cannot recognize signature", "Your digital signature is in invalid format and cannot be validated.", JOptionPane.WARNING_MESSAGE),
    PADDING_CHARACTER_IS_NOT_VALID("Wrong padding input", "Padding must be one character", JOptionPane.WARNING_MESSAGE)
    ;
    private final String message;
    private final String title;
    private final int code;

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public int getCode() {
        return code;
    }

    ErrorType(String title, String message, int code) {
        this.message = message;
        this.title = title;
        this.code = code;
    }
}
