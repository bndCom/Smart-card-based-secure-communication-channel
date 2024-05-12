package clientgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AdminCodePinCorrectController {
    @FXML
    private ImageView adminPicture;
    @FXML
    private Text adminName;

    @FXML
    private void initialize() {
        Image dynamicImage = getDynamicImageFromBackend(); // Method to get image from backend
        adminPicture.setImage(dynamicImage);
        adminName.setText(getDynamicTextFromBackEnd());

        // Calculate circle clip dimensions and position
        double radius = Math.min(adminPicture.getFitWidth(), adminPicture.getFitHeight()) / 2.0;
        double centerX = adminPicture.getFitWidth() / 2.0;
        double centerY = adminPicture.getFitHeight() / 2.0;

        // Create a Circle clip
        Circle clip = new Circle(centerX, centerY, radius);
        adminPicture.setClip(clip);
    }

    private Image getDynamicImageFromBackend() {
        // Simulate fetching image data from backend
        return new Image("file:C:/Users/ya727/Pictures/Me/IMG_20240510_173241_045.jpg");
    }

    private String getDynamicTextFromBackEnd() {
        return "Mokhati Mohcen";
    }
    
    @FXML
    private void handleStartAction(ActionEvent event) {
    	
    	try {
            // Load the Second side
            Parent secondView = FXMLLoader.load(getClass().getResource("AdminDashBoardController.fxml"));
            Scene secondScene = new Scene(secondView,1000,650);
            
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
