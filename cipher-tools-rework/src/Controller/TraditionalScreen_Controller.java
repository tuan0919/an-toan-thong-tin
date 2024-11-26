package Controller;

import View.TraditionalScreen_View;

public class TraditionalScreen_Controller extends AController<TraditionalScreen_View> {


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

    }

    private void handleEncrypt() {

    }

    private void handleDecrypt() {

    }
}
