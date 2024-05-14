package clientgui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminHomePageController {
	
	@FXML
    private void handleAuthenticateButtonClick(ActionEvent event) throws IOException {

		// Load the FXML file for the confirmation dialog
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-authentification.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
  
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        // Set the new scene on the current stage
        window.setScene(newScene);
        window.setResizable(false);
        window.show();           
        }

}
