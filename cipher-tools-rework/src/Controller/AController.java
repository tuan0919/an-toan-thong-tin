package Controller;

import View.AScreenView;

public abstract class AController <V extends AScreenView> {
    protected final V view;
    protected abstract void initialCallbacks();
    protected abstract void initialModels();
    public AController (V view) {
        this.view = view;
        initialCallbacks();
        initialModels();
    }
}
