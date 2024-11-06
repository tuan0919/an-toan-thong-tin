package view.screen.basicEncrypt;

import view.screen.Screen;

import javax.swing.*;
import java.awt.*;

/**
 * Màn hình mã hoá cơ bản
 */
public class TraditionalEncryptScreen extends JPanel implements Screen {
    private final JSplitPane Split;
    private final EncryptSection EncryptSection;
    private final DecryptSection DecryptSection;
    private final ActionListContainer ActionListContainer;

    public TraditionalEncryptScreen() {
        this.setLayout(new BorderLayout());
        this.EncryptSection = new EncryptSection();
        this.DecryptSection = new DecryptSection();
        this.ActionListContainer = new ActionListContainer();
        this.Split = new JSplitPane();
        initialState();
    }

    public void initialState() {
        Split.setOrientation(JSplitPane.VERTICAL_SPLIT);
        Split.setTopComponent(this.EncryptSection);
        Split.setBottomComponent(this.DecryptSection);
        this.add(Split, BorderLayout.CENTER);
        this.add(ActionListContainer, BorderLayout.NORTH);
        EncryptSection.setPreferredSize(new Dimension(400, 200));
        DecryptSection.setPreferredSize(new Dimension(400, 200));
    }
}
