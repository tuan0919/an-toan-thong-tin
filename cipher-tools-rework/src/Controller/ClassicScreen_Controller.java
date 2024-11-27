package Controller;

import Model.Screen.ClassicScreen_Model;
import View.ClassicScreen_View;

import java.util.Map;

public class ClassicScreen_Controller extends AController<ClassicScreen_View> {
    private ClassicScreen_Model model;
    public ClassicScreen_Controller(ClassicScreen_View view) {
        super(view);
    }

    @Override
    protected void initialCallbacks() {
        view.onChangeAlgorithm(algorithmKey -> handleChangeAlgorithm(algorithmKey));
        view.onChangeAlphabet(alphabet -> model.setAlphabet(alphabet));
    }

    private void handleChangeAlgorithm(String algorithm) {
        model.setAlgorithm(algorithm);
        model.notifyObservers("change_algorithm", Map.of(
                "algorithm", algorithm
        ));
    }

    @Override
    protected void initialModels() {
        this.model = new ClassicScreen_Model();
        model.addObserver(view);
        model.notifyObservers("change_algorithm", Map.of(
                "algorithm", ClassicScreen_Model.AFFINE_ALGORITHM
        ));
    }

}
