package clientgui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminHomePageController {
	
	@FXML
    private void handleAuthenticateButtonClick(ActionEvent event) {

            try {
                // Load the FXML file for the confirmation dialog
                FXMLLoader loader = new FXMLLoader(getClass().getResource("doctor-authentification.fxml"));
                Parent root = loader.load();

                // Create a new stage for the confirmation dialog
                Stage stage = new Stage();
                stage.setScene(new Scene(root, 600, 500));
                stage.setTitle("Admin Authentication");
                stage.setResizable(false); 

                stage.show(); // Wait for the confirmation dialog to closel

            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
        }

}
