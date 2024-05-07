package clientgui;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;

import javacard.framework.CardException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Pair;
import jsr268gp.sampleclient.APDUOps;
import jsr268gp.sampleclient.SessionAdmin;
import jsr268gp.sampleclient.Session;
import jsr268gp.sampleclient.ServerError;
import jsr268gp.sampleclient.CardAuthFailed;
import jsr268gp.sampleclient.CardNotFound;

public class PlugInCardController{
	@FXML
    private Button cancelButton;
	
	@FXML
    private void handleCancelButtonAction() throws IOException, Exception {
		
    	TerminalFactory tf = TerminalFactory.getDefault();
 		CardTerminal cad = null;
 		Pair <CardChannel, Card> cadPair = null;
 		
 		Card c = null;
 		// detecting reader
 		while(cad == null){
 			cad = tf.terminals().getTerminal("ACS ACR1281 1S Dual Reader PICC 0");

 		}
 		cadPair = APDUOps.connectAndSelect(cad);
 		CardChannel canal = cadPair.getKey();
     	c = cadPair.getValue();
        // Close the current window
		 Session admin = new SessionAdmin(canal, "http://localhost:8080");
		 FXMLLoader loader = null;
     	Parent root = null;
     	Stage stage = null ;
	        try{
	        	
	        	if(admin.auth()){
	                // Load the FXML file for the confirmation dialog
	        		loader = new FXMLLoader(getClass().getResource("auth-accepted.fxml"));
	                root = loader.load();

	                // Create a new stage for the confirmation dialog
	                stage = new Stage();
	                stage.setScene(new Scene(root, 600, 500));
	                stage.setResizable(false); 

	                stage.show();
	        	}
	        	
	        }catch(ServerError e){
	        	e.printError();
	        	loader = new FXMLLoader(getClass().getResource("auth-failed.fxml"));
                root = loader.load();

                // Create a new stage for the confirmation dialog
                stage = new Stage();
                stage.setScene(new Scene(root, 600, 500));
                stage.setResizable(false); 

                stage.show();
	        }catch(CardAuthFailed e){
	        	loader = new FXMLLoader(getClass().getResource("card-auth-failed.fxml"));
                root = loader.load();

                // Create a new stage for the confirmation dialog
                stage = new Stage();
                stage.setScene(new Scene(root, 600, 500));
                stage.setResizable(false); 

                stage.show();
	        	
	        }catch(CardNotFound e){
	        	System.out.println("Card not found!");
	        }

        
    }
	
//	@FXML 
//	public void initialize() throws Exception{
//		
//        
//		
//	}

	

}
