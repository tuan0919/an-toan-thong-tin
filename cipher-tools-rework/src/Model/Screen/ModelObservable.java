package Model.Screen;

import java.util.Map;

public interface ModelObservable {
    void addObserver(ScreenObserver observer);
    void removeObserver(ScreenObserver observer);
    void notifyObservers(String event, Map<String, Object> data);
}
