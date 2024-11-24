package Controller;

import Model.Algorithm.MaHoaCoBan.CaesarCipher;
import Model.Algorithm.MaHoaCoBan.SubstitutionCipher;
import View.TraditionalScreen_View;

public class TraditionalScreen_Controller extends AController<TraditionalScreen_View> {
    private CaesarCipher caesarCipher;
    private SubstitutionCipher substitutionCipher;

    public TraditionalScreen_Controller(TraditionalScreen_View view) {
        super(view);
    }

    @Override
    protected void initialCallbacks() {
        view.onDecryptButton_Click(e -> handleEncrypt());
        view.onEncryptButton_Click(e -> handleDecrypt());
    }

    @Override
    protected void initialModels() {
        this.caesarCipher = new CaesarCipher();
        this.substitutionCipher = new SubstitutionCipher();
    }

    private void handleEncrypt() {
        String modeTemp = (String) view.getModeComboBox().getSelectedItem();
        String inputText = view.getInputTextArea().getText();
        if (inputText == null || inputText.isEmpty()) {
            view.showWarningEmptyMessage();
            return;
        }
        String outputText = switch (modeTemp) {
            case "Dịch chuyển" -> caesarCipher.encrypt(inputText);
            case "Thay thế" -> substitutionCipher.encrypt(inputText);
            default -> "";
        };
        view.showOutputTextArea(outputText);
    }

    private void handleDecrypt() {
        String modeTemp = (String) view.getModeComboBox().getSelectedItem();
        String inputText = view.getInputTextArea().getText();
        if (inputText == null || inputText.isEmpty()) {
            view.showWarningEmptyMessage();
            return;
        }
        String outputText = switch (modeTemp) {
            case "Dịch chuyển" -> caesarCipher.decrypt(inputText);
            case "Thay thế" -> substitutionCipher.decrypt(inputText);
            default -> "";
        };
        view.showOutputTextArea(outputText);
    }
}
