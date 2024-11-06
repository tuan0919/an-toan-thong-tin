package view.screen.basicEncrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class State <T> {
    private T value;
    private List<Consumer<T>> callbacks = new ArrayList<Consumer<T>>();

    public State(T value) {
        this.value = value;
    }

    public void setValue(T newValue) {
        this.value = newValue;
        callbacks.forEach(cb -> cb.accept(this.value));
    }

    public void onChangeValue(Consumer<T> callback) {
        callbacks.add(callback);
    }

    public T getValue() {
        return value;
    }
}
