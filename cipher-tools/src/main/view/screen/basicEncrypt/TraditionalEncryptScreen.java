package view.screen.basicEncrypt;

import view.component.DecryptSection;
import view.component.EncryptSection;
import view.screen.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Màn hình mã hoá cơ bản
 */
public class TraditionalEncryptScreen extends JPanel implements Screen {
    private final JSplitPane Split;
    private final view.component.EncryptSection EncryptSection;
    private final view.component.DecryptSection DecryptSection;
    private final ActionListContainer ActionListContainer;
    private final InputContainer InputContainer;
    private String currentMode; // state

    public TraditionalEncryptScreen() {
        this.setLayout(new BorderLayout());
        this.EncryptSection = new EncryptSection();
        this.DecryptSection = new DecryptSection();
        this.ActionListContainer = new ActionListContainer(this, this::setCurrentMode);
        this.Split = new JSplitPane();
        this.InputContainer = new InputContainer(this);
        initialState();
    }

    public void setCurrentMode(String mode) {
        firePropertyChange("currentMode", currentMode, mode);
        this.currentMode = mode;
    }


    public void initialState() {
        var HeaderWrapper = new JPanel();
        HeaderWrapper.setLayout(new BoxLayout(HeaderWrapper, BoxLayout.Y_AXIS));
        Split.setOrientation(JSplitPane.VERTICAL_SPLIT);
        Split.setTopComponent(this.EncryptSection);
        Split.setBottomComponent(this.DecryptSection);
        this.add(Split, BorderLayout.CENTER);
        EncryptSection.setPreferredSize(new Dimension(400, 200));
        DecryptSection.setPreferredSize(new Dimension(400, 200));
        HeaderWrapper.add(ActionListContainer);
        HeaderWrapper.add(InputContainer);
        this.add(HeaderWrapper, BorderLayout.NORTH);
        setCurrentMode("Mã hóa dịch vòng");
    }
}
