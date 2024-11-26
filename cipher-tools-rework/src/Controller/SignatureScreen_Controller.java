package Controller;

import Model.Algorithm.Asymmetric.AsymmetricAlgorithm;
import Model.Algorithm.Hash.HashAlgorithm;
import Model.Screen.SignatureScreen_Model;
import View.SignatureScreen_View;

public class SignatureScreen_Controller extends AController<SignatureScreen_View> {
    private SignatureScreen_Model model;
    private HashAlgorithm hashAlgorithm;
    private AsymmetricAlgorithm asymmetricAlgorithm;
    public SignatureScreen_Controller(SignatureScreen_View view) {
        super(view);
    }

    @Override
    protected void initialCallbacks() {

    }

    @Override
    protected void initialModels() {
        hashAlgorithm = new HashAlgorithm();
        asymmetricAlgorithm = new AsymmetricAlgorithm();
        model = new SignatureScreen_Model();
        model.addObserver(view);
    }

}
