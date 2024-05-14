package clientgui;

import java.io.IOException;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import jsr268gp.sampleclient.CardAuthFailed;
import jsr268gp.sampleclient.CardNotFound;
import jsr268gp.sampleclient.ServerError;

public class AdminCodePinController {

    @FXML private TextField digit1, digit2, digit3, digit4;
    @FXML private Text dynamicText;
    private int attempts = 3 ;
    @FXML private Button verifyButton ;
    
    @FXML private AnchorPane parent;
	

    @FXML
    private void initialize() {
    	// Set IDs or directly use your setup methods
        setupTextField(digit1, digit2, null);
        setupTextField(digit2, digit3, digit1);
        setupTextField(digit3, digit4, digit2);
        setupTextField(digit4, null, digit3);
    }

    private void setupTextField(final TextField current, final TextField next, final TextField previous) {
        
        final boolean[] updating = {false}; // flag to prevent feedback loop

        // Initial styles to disable cursor, hide text, and remove focus border
        current.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

        current.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (updating[0]) return; // if already updating, do nothing

                updating[0] = true; // set updating flag
                String filtered = newValue.replaceAll("[^\\d]", ""); // filter out non-digits

                if (!filtered.equals(newValue) || filtered.length() > 1) {
                    current.setText(oldValue); // revert to old value if input is invalid
                } else {
                    if (!filtered.isEmpty()) {
                        current.setStyle("-fx-background-color: #185FA1; -fx-text-fill: transparent;"); // Style when digit is entered
                        if (next != null) {
                            next.requestFocus(); // move focus forward
                        }
                    }
                }
                updating[0] = false; // reset updating flag
            }
        });

        current.focusedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    if (current.getText().isEmpty()) {
                        current.setStyle("-fx-background-color: #518CC2; -fx-text-fill: transparent;"); // Style when focused and empty
                    }
                }
            }
        });

        current.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                	if (current == digit4) {
                		if (current.getText().isEmpty()){
                            current.setStyle("-fx-background-color: transparent; -fx-text-fill: transparent;"); // Style when focused and not empty
                			previous.setText("");
                			previous.requestFocus();
                			return ;
                		}
                		else {
                			current.setText("");
                            current.setStyle("-fx-background-color:  #518CC2; -fx-text-fill: transparent;"); // Style when focused and not empty
                            return;
                		}
                	}
                    if (current.getText().isEmpty() && previous!= null) {
                    	current.setText("");
                        current.setStyle("-fx-background-color:  transparent; -fx-text-fill: transparent;"); // Style when focused and not empty
                        previous.setText("");
                        previous.requestFocus();
                    }
                }
                if (event.getCode()== KeyCode.ENTER){
                	if (attempts>0) {
                    	handleEnterPressedButton(event);
                	}
                }
            }
        });
        
    }




    @FXML
    private void handleVerifyButton(ActionEvent event) throws Exception {
        String pin = digit1.getText() + digit2.getText() + digit3.getText() + digit4.getText();

        if (pin.length() < 4) {
            dynamicText.setText("Invalid PassCode" + "\nYou have to Enter 4 digits");
            dynamicText.setStyle("fx-alignment: center; -fx-fill: #d80000; ");
            return;
        } else {
            // hash pin code
        	// send to server
        	// get response
        	boolean auth = false;
        	try{
        		// launching the authentication
        		//auth = Main.admin.auth(pin);
        		try {
    	            // Load the code pin accepted scene
    	            Parent secondView = FXMLLoader.load(getClass().getResource("admin-code-pin-correct.fxml"));
    	            Scene secondScene = new Scene(secondView);
    	            
    	            // Get the current stage (window) using the event's source
    	            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

    	            // Set the new scene on the current stage
    	            window.setScene(secondScene);
    	            window.show();
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
        		
        		StageManager.closeAllStages();
        	}catch(Exception e){
    			dynamicText.setText("Unknown error happened");
    			dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
    			parent.getChildren().remove(verifyButton);
        	}
        		
//        	}catch(CardNotFound e){
//    			dynamicText.setText("Card not found");
//    			dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
//    			parent.getChildren().remove(verifyButton);
//
//
//        	}catch(ServerError e){
//    			dynamicText.setText("Server Error");
//    			dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
//    			parent.getChildren().remove(verifyButton);
//        	}catch(CardAuthFailed e){
//    			dynamicText.setText("Card couldn't authenticate the server");
//    			dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
//    			parent.getChildren().remove(verifyButton);
//        	}
        	
        }
    }
    
    @FXML
    private void handleEnterPressedButton(KeyEvent event) {
    		String pin = digit1.getText() + digit2.getText() + digit3.getText() + digit4.getText();
            if (pin.length() < 4) {
                dynamicText.setText("Invalid PassCode" + "\nYou have to Enter 4 digits");
                dynamicText.setStyle("fx-alignment: center; -fx-fill: #d80000;");
                return;
            } else {
                // hash pin code
            	// send to server
            	// get response
            	if (!pin.equals("1234")){
            		attempts -= 1; 
            		if (attempts > 0 ) {
            			dynamicText.setText("Wrong PassCode" + "\nAttempts Left:" + attempts);
                        dynamicText.setStyle("-fx-alignment: center; -fx-fill: #d80000; ");
                        return ;
            		}
            		dynamicText.setText("3 wrong attempts" + "\nCard is currently blocked");
                    dynamicText.setStyle("-fx-fill: #d80000; ");
                    parent.getChildren().remove(verifyButton);
            	}
            	else {
            		try {
        	            // Load the code pin accepted scene
            			
        	            Parent secondView = FXMLLoader.load(getClass().getResource("admin-code-pin-correct.fxml"));
        	            Scene secondScene = new Scene(secondView);
        	            
        	            // Get the current stage (window) using the event's source
        	            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        	            // Set the new scene on the current stage
        	            window.setScene(secondScene);
        	            window.show();
        	        } catch (Exception e) {
        	            e.printStackTrace();
        	        }
            		StageManager.closeAllStages();
            	}
            }
    }
}
