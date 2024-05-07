package clientgui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GetStartedController {

    @FXML
    private Button doctorButton;

    @FXML
    private Button adminButton;

    @FXML
    private void handleAdminButtonClick(ActionEvent event) {
        // Logic for handling the "Doctor" button click
    }

    @FXML
    private void handleDoctorButtonClick(ActionEvent event) {
    	try {
            // Load the FXML file for the confirmation dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("doctor-home-page.fxml"));
            Parent root = loader.load();

            // Create a new stage for the confirmation dialog
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1000, 650));
            stage.setTitle("Doctor Welcome Page");
            stage.setResizable(false); 
            
            Stage stage2 = (Stage) doctorButton.getScene().getWindow();
            stage2.close();
            stage.show(); // Wait for the confirmation dialog to closel

            // Optionally, you can handle the result after the confirmation dialog is closed
            // For example, refresh the table view if the deletion was confirmed
            //refreshTableView();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}
