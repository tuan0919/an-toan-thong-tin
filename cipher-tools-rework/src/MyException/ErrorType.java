package MyException;

import javax.swing.*;

public enum ErrorType {
    EMPTY_INPUT("Thông báo", "Vui lòng chọn file cần mã hóa hoặc nhập đoạn văn bản bạn muốn mã hóa", JOptionPane.WARNING_MESSAGE),
    UNKNOWN_ERROR("Lỗi", "Something went wrong.", JOptionPane.ERROR_MESSAGE),
    BAD_INPUT_ALGORITHM("Lỗi", "Input cho module mã hóa bị sai", JOptionPane.ERROR_MESSAGE),
    FILE_FAILED_ENCRYPT("Thông báo", "Mã hóa không thành công", JOptionPane.WARNING_MESSAGE),
    BAD_INPUT_FOR_NO_PADDING("Cảnh báo", "Ở chế độ NoPadding, văn bản cần phải có số byte là bội số của 8", JOptionPane.WARNING_MESSAGE),
    UNSUPPORT_ALGORITHM("Thông báo", "Thuật toán không được hỗ trợ", JOptionPane.WARNING_MESSAGE),
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
