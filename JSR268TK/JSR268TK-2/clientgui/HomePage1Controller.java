package clientgui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class HomePage1Controller{

	 @FXML
	    private void handleGetStartedAction(ActionEvent event) {
	        try {
	            // Load the new FXML file
	            Parent secondView = FXMLLoader.load(getClass().getResource("homePage2.fxml"));
	            Scene secondScene = new Scene(secondView);
	            
	            // Get the current stage (window) using the event's source
	            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

	            // Set the new scene on the current stage
	            window.setScene(secondScene);
	            window.setTitle("Log In");
	            window.show();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}



