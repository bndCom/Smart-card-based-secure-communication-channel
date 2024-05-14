package clientgui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class CardAuthFailedDoctorController {
	@FXML
	private Button cancelBtn;
	@FXML
	private Button retryBtn;

	// Event Listener on Button[#cancelBtn].onAction
	@FXML
	public void cancel(ActionEvent event) {
        try {
            // Load the Second side
            Parent secondView = FXMLLoader.load(getClass().getResource("homePage2.fxml"));
            Scene secondScene = new Scene(secondView);
            
            // Get the current stage (window) using the event's source
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            window.setScene(secondScene);
            window.setTitle("Login");
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	// Event Listener on Button[#retryBtn].onAction
	@FXML
	public void retry(ActionEvent event) {
        try {
            // Load the Second side
            Parent secondView = FXMLLoader.load(getClass().getResource("doctor-authentification.fxml"));
            Scene secondScene = new Scene(secondView);
            
            // Get the current stage (window) using the event's source
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            window.setScene(secondScene);
            window.setTitle("Authentication");
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
