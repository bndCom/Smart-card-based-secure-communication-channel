package clientgui;

import java.io.IOException;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

import javacard.framework.CardException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

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

        // Create a new stage for the confirmation dialog
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 600, 500));
        stage.setTitle("Doctor Authentication");
        stage.setResizable(false); 

        stage.show();
        
           
        
    }

}