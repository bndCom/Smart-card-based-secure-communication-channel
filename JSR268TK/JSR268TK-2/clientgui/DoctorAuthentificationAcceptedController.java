package clientgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DoctorAuthentificationAcceptedController {
	
	@FXML
    private void handleNextButton(ActionEvent event) {
		try {
            // Load the Second side
            Parent secondView = FXMLLoader.load(getClass().getResource("doctor-code-pin.fxml"));
            Scene secondScene = new Scene(secondView);
            
            // Get the current stage (window) using the event's source
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            StageManager.addStage(window);
            // Set the new scene on the current stage
            window.setScene(secondScene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
