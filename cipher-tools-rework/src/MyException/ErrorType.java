package MyException;

import javax.swing.*;

public enum ErrorType {
    EMPTY_INPUT_FOR_ENCRYPT("Thông báo", "Vui lòng chọn file cần mã hóa hoặc nhập đoạn văn bản bạn muốn mã hóa", JOptionPane.WARNING_MESSAGE),
    EMPTY_INPUT_FOR_DECRYPT("Thông báo", "Vui lòng chọn file cần giải mã hoặc nhập đoạn văn bản bạn muốn giải mã", JOptionPane.WARNING_MESSAGE),
    UNKNOWN_ERROR("Lỗi", "Something went wrong.", JOptionPane.ERROR_MESSAGE),
    BAD_INPUT_ALGORITHM("Mã hóa hoặc giải mã thất bại", "Thiết lập thuật toán không hợp lệ. \nVui lòng kiểm tra lại.", JOptionPane.ERROR_MESSAGE),
    FILE_FAILED_TO_ENCRYPT("Thông báo", "Mã hóa không thành công", JOptionPane.WARNING_MESSAGE),
    FILE_FAILED_TO_DECRYPT("Thông báo", "Giải mã không thành công", JOptionPane.WARNING_MESSAGE),
    BAD_INPUT_FOR_NO_PADDING("Cảnh báo", "Ở chế độ NoPadding, văn bản cần phải có số byte là bội số của 8", JOptionPane.WARNING_MESSAGE),
    UNSUPPORTED_ALGORITHM("Thông báo", "Thuật toán không được hỗ trợ", JOptionPane.WARNING_MESSAGE),
    EMPTY_KEY_WHEN_SAVE("Thông báo", "Vui lòng nhập key trước khi lưu", JOptionPane.WARNING_MESSAGE),
    WRONG_FILE_FORMAT_SAVE_LOCATION("Lỗi", "Không thể lưu key dưới định dạng bạn đã chọn", JOptionPane.ERROR_MESSAGE),
    SAVE_FILE_FAILED("Thông báo", "Lưu key thất bại", JOptionPane.WARNING_MESSAGE),
    WRONG_FILE_FORMAT_LOAD_KEY("Lỗi", "Không thể load file key ở định dạng bạn đã chọn", JOptionPane.ERROR_MESSAGE),
    IO_ERROR("Lỗi", "Lỗi không đọc được file", JOptionPane.ERROR_MESSAGE),
    WRONG_MATRIX_SIZE("Ma trận không hợp lệ", "Chương trình chỉ chấp nhận ma trận có kích thước <= 9.", JOptionPane.WARNING_MESSAGE),
    WRONG_AFFINE_KEY("Affine Key không hợp lệ", "Key chỉ có thể là 2 cặp số", JOptionPane.WARNING_MESSAGE),
    WRONG_CAESAR_KEY("Caesar Key không hợp lệ", "Key phải là một số đại diện cho số kí tự cần dịch chuyển", JOptionPane.WARNING_MESSAGE),
    WRONG_SUBSTITUTION_KEY("Substitution Key không hợp lệ", "Key phải có số lượng kí tự bằng với alphabet được sử dụng", JOptionPane.WARNING_MESSAGE)
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
