package clientgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DoctorHomePageController {
	
	
	
    @FXML
    private Pane rootPane;

    @FXML
    private Button authenticateButton;
    @FXML
    private void handleAuthenticateButtonClick(ActionEvent event) throws Exception {
    			

 		
           
        // Load the FXML file for the confirmation dialog
        FXMLLoader loader = new FXMLLoader(getClass().getResource("doctor-authentification.fxml"));
        Parent root = loader.load();
        Scene newScene = new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        // Set the new scene on the current stage
        window.setScene(newScene);
        window.show();           
        
    }

}