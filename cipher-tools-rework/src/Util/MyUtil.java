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
    public static String formatMatrix(Integer[][] matrix) {
        StringBuilder sb = new StringBuilder();

        // Tính toán chiều rộng của cột dựa trên số lớn nhất trong ma trận
        int maxNumber = findMaxNumber(matrix);
        int cellWidth = String.valueOf(maxNumber).length() + 3; // Tăng thêm khoảng cách giữa các số

        // Duyệt qua các hàng và cột để xây dựng ma trận dưới dạng chuỗi
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int number = matrix[i][j];

                // Căn trái và thêm khoảng trắng giữa các số
                sb.append(String.format("%-" + cellWidth + "d", number));
            }
            // Thêm dấu xuống dòng sau mỗi hàng, nhưng không thêm xuống dòng cuối cùng
            if (i < matrix.length - 1) {
                sb.append("\n");
            }
        }

        // Trả về chuỗi ma trận
        return sb.toString();
    }

    // Hàm tìm số lớn nhất trong ma trận để tính chiều rộng cột
    private static int findMaxNumber(Integer[][] matrix) {
        int max = matrix[0][0];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Integer[][] matrix = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}
        };
        System.out.println(formatMatrix(matrix));
    }
}
