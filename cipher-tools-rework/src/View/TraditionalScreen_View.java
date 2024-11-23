package View;

import Util.MyUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class TraditionalScreen_View extends AScreenView {
    private JComboBox<String> ModeComboBox;
    private JTextArea InputTextArea;
    private JTextArea OutputTextArea;
    private JButton EncryptButton;
    private JButton DecryptButton;
    private JScrollPane InputTextWrap_ScrollPane;
    private JScrollPane OutputTextWrap_ScrollPane;
    private JPanel SelectModeWrap_Panel;
    private JPanel OverallContentWrap_Panel;
    private JPanel CipherContentWrap_Panel;

    public TraditionalScreen_View() {
        super();
    }

    @Override
    public void initialComponent() {
        ModeComboBox = new JComboBox<>();
        EncryptButton = new JButton("Mã hóa");
        DecryptButton = new JButton("Giải mã");
        InputTextArea = new JTextArea(20, 35);
        OutputTextArea = new JTextArea(20, 35);
        InputTextWrap_ScrollPane = new JScrollPane(InputTextArea);
        OutputTextWrap_ScrollPane = new JScrollPane(OutputTextArea);
        SelectModeWrap_Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        OverallContentWrap_Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        CipherContentWrap_Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    }

    @Override
    public void initialLayout() {
        ModeComboBox.addItem("Dịch chuyển");
        ModeComboBox.addItem("Thay thế");

        InputTextWrap_ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        InputTextWrap_ScrollPane.setBorder(BorderFactory.createTitledBorder("Nguồn"));
        OutputTextWrap_ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        OutputTextWrap_ScrollPane.setBorder(BorderFactory.createTitledBorder("Kết quả"));

        SelectModeWrap_Panel.add(new JLabel("Chọn chế độ"));
        SelectModeWrap_Panel.add(ModeComboBox);

        OverallContentWrap_Panel.add(InputTextWrap_ScrollPane);
        OverallContentWrap_Panel.add(OutputTextWrap_ScrollPane);

        CipherContentWrap_Panel.add(EncryptButton);
        CipherContentWrap_Panel.add(DecryptButton);

        this.setLayout(new BorderLayout());
        this.add(SelectModeWrap_Panel, BorderLayout.NORTH);
        this.add(OverallContentWrap_Panel, BorderLayout.CENTER);
        this.add(CipherContentWrap_Panel, BorderLayout.SOUTH);
    }

    public JComboBox<String> getModeComboBox() {
        return ModeComboBox;
    }

    public JTextArea getInputTextArea() {
        return InputTextArea;
    }

    public JTextArea getOutputTextArea() {
        return OutputTextArea;
    }

    public JButton getEncryptButton() {
        return EncryptButton;
    }

    public JButton getDecryptButton() {
        return DecryptButton;
    }

    public JScrollPane getInputTextWrap_ScrollPane() {
        return InputTextWrap_ScrollPane;
    }

    public JScrollPane getOutputTextWrap_ScrollPane() {
        return OutputTextWrap_ScrollPane;
    }

    public JPanel getSelectModeWrap_Panel() {
        return SelectModeWrap_Panel;
    }

    public JPanel getOverallContentWrap_Panel() {
        return OverallContentWrap_Panel;
    }

    public JPanel getCipherContentWrap_Panel() {
        return CipherContentWrap_Panel;
    }

    public void showOutputTextArea(String content) {
        this.OutputTextArea.setText(content);
    }

    public void showWarningEmptyMessage() {
        MyUtil.showWarnMessage(this, "Vui lòng nhập nội dung văn bản", "Thông báo");
    }

    public void onEncryptButton_Click(Consumer<ActionEvent> callback) {
        EncryptButton.addActionListener(e -> callback.accept(e));
    }

    public void onDecryptButton_Click(Consumer<ActionEvent> callback) {
        DecryptButton.addActionListener(e -> callback.accept(e));
    }
}
