package clientgui;

import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;

public class StageManager {
    private static final List<Stage> stages = new ArrayList<Stage>();

    public static void addStage(Stage stage) {
        stages.add(stage);
    }

    public static void removeStage(Stage stage) {
        stages.remove(stage);
        stage.close();
    }

    public static void closeAllStages() {
        for (Stage stage : new ArrayList<Stage>()) {
            stage.close();
        }
    }
}

