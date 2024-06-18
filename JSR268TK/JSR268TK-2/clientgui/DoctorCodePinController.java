package clientgui;

import java.io.IOException;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;

import javacard.framework.CardException;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import jsr268gp.sampleclient.APDUOps;
import jsr268gp.sampleclient.CardAuthFailed;
import jsr268gp.sampleclient.CardNotFound;
import jsr268gp.sampleclient.ServerError;
import jsr268gp.sampleclient.SessionAdmin;
import jsr268gp.sampleclient.SessionDoctor;

public class DoctorCodePinController {

    @FXML
    private Text dynamicText;

    @FXML
    private AnchorPane parent;

    @FXML
    private PasswordField pin;

    @FXML
    private Button verifyButton;
    
    static int attempts = 3;

    @SuppressWarnings("unused")
	@FXML
    void handleVerifyButton(ActionEvent event) throws IOException {
        if (pin.getText().length() < 4) {
            dynamicText.setText("Invalid PassCode" + "\nYou have to Enter 4 digits");
            dynamicText.setStyle("fx-alignment: center; -fx-fill: #d80000;");
            return;
        }else{
    		// connecting to the card
//    		Pair <CardChannel, Card> cadPair = null;
//     		cadPair = APDUOps.connectAndSelect(Main.cad);
//     		Main.canal = cadPair.getKey();
//         	Main.c = cadPair.getValue();
         	Main.doctor = new SessionDoctor(Main.canal, "http://localhost:8080");
         	boolean auth = false;
         	try{
				auth = Main.doctor.auth(pin.getText());
				try{
    	            nextStage(event);
    	           } catch (Exception e) {
    	            e.printStackTrace();
    	            StageManager.closeAllStages();
    	            }
         	   }catch(CardException e){
     		    dynamicText.setText("Card can't connect properly");
         	    dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
         	   }catch(CardNotFound e){
  			    dynamicText.setText("Card not found");
     			dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
     			
        		if (attempts > 0 ) {
        			dynamicText.setText(dynamicText.getText() + "\nAttempts Left:" + attempts);
                    dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
                    return ;
        		}
            		dynamicText.setText("3 wrong attempts" + "\nCard is currently blocked");
                    dynamicText.setStyle("-fx-fill: #d80000; ");
                    parent.getChildren().remove(verifyButton);
        		
         	}catch(CardAuthFailed e){
    			dynamicText.setText("Card couldn't authenticate the server");
     			dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
     			attempts --;
     
        		if (attempts > 0 ) {
        			dynamicText.setText("Wrong PassCode" + "\nAttempts Left:" + attempts);
                    dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
                    return ;
        		}
        		dynamicText.setText("3 wrong attempts" + "\nCard is currently blocked");
                dynamicText.setStyle("-fx-fill: #d80000; ");
                parent.getChildren().remove(verifyButton);
    			
       	    }catch(ServerError e){
       	    	
     			dynamicText.setText("Server Error");
    			dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
    			
        		if (attempts > 0 ) {
        			dynamicText.setText(dynamicText.getText() + "\nAttempts Left:" + attempts);
                    dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
                    return ;
        		}
        		dynamicText.setText("3 wrong attempts" + "\nCard is currently blocked");
                dynamicText.setStyle("-fx-fill: #d80000; ");
                parent.getChildren().remove(verifyButton);
        	}catch(Exception e){
        		attempts --;
    			dynamicText.setText("Card couldn't authenticate the server");
     			dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
        		if (attempts > 0 ) {
        			dynamicText.setText(dynamicText.getText() + "\nAttempts Left:" + attempts);
                    dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
                    return ;
        		}
        		dynamicText.setText("3 wrong attempts" + "\nCard is currently blocked");
                dynamicText.setStyle("-fx-fill: #d80000; ");
                parent.getChildren().remove(verifyButton);
       	    }
        }
    }

	public void nextStage(ActionEvent event) throws IOException {
		// Load the code pin accepted scene
		Parent secondView = FXMLLoader.load(getClass().getResource("doctor-page-dashboard.fxml"));
		Scene secondScene = new Scene(secondView);
		
		// Get the current stage (window) using the event's source
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

		// Set the new scene on the current stage
		window.setScene(secondScene);
		window.show();
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
    
