package Controller;

import View.SignatureScreen_View;

import javax.swing.*;
import java.io.File;

public class SignatureScreen_Controller extends AController<SignatureScreen_View> {
    public SignatureScreen_Controller(SignatureScreen_View view) {
        super(view);
    }

    @Override
    protected void initialCallbacks() {
        view.onSourceTextArea_DocumentChange(e -> handleSourceTextArea_DocumentChange());
        view.onSelectFileButton_Click(e -> handleSelectFileButton_Click());
        view.onDeselectFileButton_Click(e -> handleDeselectFileButton_Click());
        view.onOutputTextArea_DocumentChange(e -> handleOutputTextArea_DocumentChange());
        view.onSelectFileButton2_Click(e -> handleSelectFileButton2_Click());
        view.onDeselectFileButton2_Click(e -> handleDeselectFileButton2_Click());
        view.onCreateSignatureButton_Click(e -> handleCreateSignatureButton_Click());
        view.onCheckSignatureButton_Click(e -> handleCheckSignatureButton_Click());
    }

    private void handleCheckSignatureButton_Click() {

    }

    private void handleCreateSignatureButton_Click() {

    }

    private void handleDeselectFileButton2_Click() {

    }

    private void handleSelectFileButton2_Click() {

    }

    private void handleOutputTextArea_DocumentChange() {

    }

    private void handleDeselectFileButton_Click() {

    }

    private void handleSourceTextArea_DocumentChange() {

    }

    private void handleSelectFileButton_Click() {

    }

    @Override
    protected void initialModels() {

    }

}
