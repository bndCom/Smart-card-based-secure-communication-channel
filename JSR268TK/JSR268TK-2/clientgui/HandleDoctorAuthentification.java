package clientgui;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.concurrent.Task;


public class HandleDoctorAuthentification{
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
	            
	            @SuppressWarnings("unused")
				boolean flag = false;
	                flag = Util.isCardInserted();
	             return null;
	        }


	        @Override
	        protected void succeeded()  {
	            dynamicText.setText("Authentication ongoing");
	            // Proceed with authentication or further action
	            // Load the FXML file for the confirmation dialog
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("doctor-code-pin.fxml"));
	            Parent root = null;
				try {
					root = loader.load();
					// Create a new stage for the confirmation dialog
		            Stage stage = new Stage();
		            stage.setScene(new Scene(root));
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
	        
	        @Override
	        protected void failed()  {
	        	Util.showAlert("Auth Error", "Couldn't authenticate");
//	            FXMLLoader loader = new FXMLLoader(getClass().getResource("card-auth-failed-doctor.fxml"));
//	            Parent root = null;
//				try {
//					root = loader.load();
//					// Create a new stage for the confirmation dialog
//		            Stage stage = new Stage();
//		            stage.setScene(new Scene(root));
//		            stage.setResizable(false); 
//		            stage.show();
//		            // Get the scene from the parent node
//		            Scene scene = anchorPane.getScene();
//		            if (scene != null) {
//		                // Close the Scene if it's not null
//		                Stage stageToClose = (Stage) scene.getWindow();
//		                stageToClose.close();
//		            }
//
//		            
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
	        }
	        
	    };


	    new Thread(connectionToCardAndReader).start();
	}
	
    @FXML
    void onBack(ActionEvent event) throws Exception {
    	try{
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


	

}
