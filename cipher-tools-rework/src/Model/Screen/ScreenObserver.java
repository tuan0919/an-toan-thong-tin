package Model.Screen;

import java.util.Map;

public interface ScreenObserver {
    void update(String event, Map<String, Object> data);
}
