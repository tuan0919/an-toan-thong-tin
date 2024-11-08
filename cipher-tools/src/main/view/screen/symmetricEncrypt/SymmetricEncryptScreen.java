package view.screen.symmetricEncrypt;

import view.screen.Screen;

import javax.swing.*;
import java.awt.*;

/**
 * Màn hình mã hoá cơ bản
 */
public class SymmetricEncryptScreen extends JPanel implements Screen {
    private String inputType;
    private String function;
    private final InputTypeListContainer InputTypeListContainer;
    private final InputContainer InputContainer;

    public SymmetricEncryptScreen() {
        this.InputTypeListContainer = new InputTypeListContainer(this, this::setInputType);
        this.InputContainer = new InputContainer();
        initialState();
    }

    public void setFunction(String function) {
        this.firePropertyChange("setFunction", this.function, function);
        this.function = function;
    }

    public void setInputType(String inputType) {
        this.firePropertyChange("setInputType", this.inputType, inputType);
        this.inputType = inputType;
    }

    public void initialState() {
        var layout = new BorderLayout();
        this.setLayout(layout);
        this.add(InputTypeListContainer, BorderLayout.NORTH);
        this.add(InputContainer, BorderLayout.CENTER);
        setInputType("Mã hóa văn bản");
    }
}
