package clientgui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class HomePage1Controller{
	

    @FXML
    private Button figmaBtn;

    @FXML
    private Button githubBtn;

    @FXML
    private Button linkedinBtn;

	 @FXML
	    private void handleGetStartedAction(ActionEvent event) {
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

	    @FXML
	    void toFigma(ActionEvent event) throws URISyntaxException {
	    	try {
                Desktop.getDesktop().browse(new URI("https://www.figma.com/design/vVZcwllrL2ZSFykBbeuoOD/app_UI?node-id=3-2"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch(URISyntaxException e){
            	e.printStackTrace();
            }
	    }

	    @FXML
	    void toGithub(ActionEvent event) throws URISyntaxException {
	    	try {
                Desktop.getDesktop().browse(new URI("https://github.com/bndCom/Smart-card-based-secure-communication-channel.git"));
            } catch (IOException e) {
                e.printStackTrace();
            }catch(URISyntaxException e){
            	e.printStackTrace();
            }
	    }

	    @FXML
	    void toLinkedin(ActionEvent event) throws URISyntaxException {
	    	try {
                Desktop.getDesktop().browse(new URI("https://www.linkedin.com/in/mahdi-ali-59b08829b"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch(URISyntaxException e){
            	e.printStackTrace();
            }
	    }

}



