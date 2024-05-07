package clientgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePage2Controller {
	@FXML
    private void handleAdminAction(ActionEvent event) {
        try {
            // Load the new FXML file
            Parent secondView = FXMLLoader.load(getClass().getResource("admin-home-page.fxml"));
            Scene secondScene = new Scene(secondView);

            // Get the current stage (window) using the event's source
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            window.setScene(secondScene);
            window.setTitle("Admin Space");
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	@FXML
    private void handleDoctorAction(ActionEvent event) {
        try {
            // Load the new FXML file
            Parent secondView = FXMLLoader.load(getClass().getResource("doctor-home-page.fxml"));
            Scene secondScene = new Scene(secondView);

            // Get the current stage (window) using the event's source
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            window.setScene(secondScene);
            window.setTitle("Doctor Space");
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	@FXML
    private void handleHomeAction(ActionEvent event) {
        try {
            // Load the new FXML file
            Parent secondView = FXMLLoader.load(getClass().getResource("homePage1.fxml"));
            Scene secondScene = new Scene(secondView);

            // Get the current stage (window) using the event's source
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            // Set the new scene on the current stage
            window.setScene(secondScene);
            window.setTitle("Home Page");
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
