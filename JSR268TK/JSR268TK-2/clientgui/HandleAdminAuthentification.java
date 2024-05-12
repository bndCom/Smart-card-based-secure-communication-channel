package clientgui;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.concurrent.Task;


public class HandleAdminAuthentification{
	@FXML
    private AnchorPane anchorPane;
	@FXML
    private Button connectButton;
	@FXML
    private Text dynamicText;
	
	
	@FXML
	private void handleConnectButtonAction() {
	    // Remove the connect button
	    anchorPane.getChildren().remove(connectButton);

	    // Change button into a progress indicator
	    final ProgressIndicator progressIndicator = new ProgressIndicator();
	    progressIndicator.setLayoutX(connectButton.getLayoutX()); // Set the position
	    progressIndicator.setLayoutY(connectButton.getLayoutY()); // Set the position
	    progressIndicator.setPrefSize(connectButton.getWidth(), connectButton.getHeight()); // Set the size

	    // Add the ProgressIndicator to the anchorPane
	    dynamicText.setText("Connecting to Card & Reader...");
	    anchorPane.getChildren().add(progressIndicator);

	    Task<Void> connectionToCardAndReader = new Task<Void>() {
	        @Override
	        protected Void call() throws Exception {
	        	// function that will check if reader and card are connected
	            return null;
	        }

	        @Override
	        protected void succeeded()  {
	            dynamicText.setText("Authentication ongoing");
	            // Proceed with authentication or further action
	            // Load the FXML file for the confirmation dialog
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-code-pin.fxml"));
	            Parent root = null;
				try {
					root = loader.load();
					// Create a new stage for the confirmation dialog
		            Stage stage = new Stage();
		            stage.setScene(new Scene(root, 600, 500));
		            stage.setResizable(false); 
		            stage.show();
		            // Get the scene from the parent node
		            Scene scene = anchorPane.getScene();
		            if (scene != null) {
		                // Close the Scene if it's not null
		                Stage stageToClose = (Stage) scene.getWindow();
		                stageToClose.close();
		            }

		            
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            
	        }
	    };


	    new Thread(connectionToCardAndReader).start();
	}


	

}
