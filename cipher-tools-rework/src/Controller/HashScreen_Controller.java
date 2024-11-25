package Controller;

import Model.Algorithm.Hash.HashAlgorithm;
import Model.Algorithm.Hash.MD5;
import Model.Algorithm.Hash.SHA.SHA;
import Model.Screen.HashScreen_Model;
import MyException.ErrorType;
import MyException.MyAppException;
import View.HashScreen_View;

import javax.swing.*;
import java.io.File;
import java.util.Map;
import java.util.Optional;

public class HashScreen_Controller extends AController<HashScreen_View> {
    private HashScreen_Model model;
    private HashAlgorithm algorithm;
    public HashScreen_Controller(HashScreen_View view) {
        super(view);
    }

    @Override
    protected void initialCallbacks() {
        view.onInputTextArea_DocumentChange(_ -> view.toggleSelectFileButton());
        view.onSelectFileButton_Click(e -> view.openFileChooser_ForPickFile());
        view.onDeselectFileButton_Click(e -> setChooseFile_FireEvent(null));
        view.onHashButton_Click(e -> handleHashButton_Click());
        view.onFileChosen(file -> setChooseFile_FireEvent(file));
        view.onSelectHashComboBox_Chosen(algorithm -> model.setAlgorithm(algorithm));
        view.onInputTextArea_LostFocus(input -> model.setInputText(input));
    }

    private void setChooseFile_FireEvent(File file) {
        model.setFile(file);
        model.notifyObservers("change_choose_file", Map.of(
                "current_choose_file", Optional.ofNullable(file)
        ));
    }

    private void handleHashButton_Click() {
        var file = model.getFile();
        String currentAlgo = model.getAlgorithm();
        String hashText = "";
        if (file != null) {
            hashText = algorithm.hashFile(file.getAbsolutePath(), currentAlgo);
        }
        else {
            String inputText = model.getInputText();
            if (inputText == null || inputText.isEmpty()) {
                throw new MyAppException(ErrorType.EMPTY_INPUT_FOR_ENCRYPT, view);
            }
            hashText = algorithm.hashText(inputText, currentAlgo);
        }
        model.notifyObservers("hash_file", Map.of(
                "output", hashText
        ));
    }

    @Override
    protected void initialModels() {
        algorithm = new HashAlgorithm();
        model = new HashScreen_Model();
        model.addObserver(view);
        model.notifyObservers("first_load", Map.of(
                "available_algorithm", model.getAvailableAlgorithm()
        ));
    }
}
