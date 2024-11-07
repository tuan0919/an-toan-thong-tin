package view.screen.basicEncrypt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DecryptSection extends JPanel {
    private JPanel Header;
    private JPanel Body;

    public DecryptSection() {
        this.Header = createHeader();
        this.Body = createBody();
        this.setLayout(new BorderLayout());
        this.add(this.Header, BorderLayout.NORTH);
        this.add(this.Body, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        var header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        header.setBorder(new EmptyBorder(5, 5, 5, 5));
        var lbPlainText = new JLabel("Encrypted text: ");
        var lbPLainTextSize = new JLabel("69.8 KB");
        var space = new JPanel(null);
        header.add(lbPlainText);
        header.add(lbPLainTextSize);
        header.add(space);
        var encodeButton = new JButton("decrypt");
        header.add(encodeButton);
        return header;
    }

    private JPanel createBody() {
        var scrollbarContainer = new JPanel(new BorderLayout());
        var scrollBar = new JScrollPane();
        var textArea = new JTextArea();
        scrollBar.setViewportView(textArea);
        scrollbarContainer.add(scrollBar, BorderLayout.CENTER);
        return scrollbarContainer;
    }
}
