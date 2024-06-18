package clientgui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;

import jsr268gp.sampleclient.APDUOps;
import jsr268gp.sampleclient.CardNotFound;

public class Util {
    public static Long doubleToLong(Double num){
        String s = num.toString();
        return Long.parseLong(s.replace(".","").substring(0, s.length() - 2));
    }

    public static Integer doubleToInt(Double num){
        String s = num.toString();
        return Integer.parseInt((s.substring(0, s.length() - 2)));
    }

    public static boolean isCardReaderConnected() throws CardNotFound {
        TerminalFactory tf = TerminalFactory.getDefault();
        CardTerminal cad = tf.terminals().getTerminal("ACS ACR1281 1S Dual Reader PICC 0");
        if(cad == null) throw new CardNotFound();
        return cad != null;
    }

    public static boolean isCardInserted() throws CardNotFound{
        TerminalFactory tf = TerminalFactory.getDefault();
        CardTerminal cad = null;
        // verifying reader
 		while(cad == null){
 			cad = tf.terminals().getTerminal("ACS ACR1281 1S Dual Reader PICC 0");

 		}
 		// verifying the card
//        Pair<CardChannel, Card> cadPair = null;
//        Card c = null;
//        while(c == null){
//        	try {
//				c = cad.connect("T=0");
//			} catch (CardException e) {
//				// TODO Auto-generated catch block
//				System.out.println("card not found!");
//			}
//        	
//        }
 		Pair<CardChannel, Card> cadPair = APDUOps.connectAndSelect(cad);
        CardChannel canal = cadPair.getKey();
        Card c = cadPair.getValue();
        if(cad == null | canal == null ) throw new CardNotFound();
        Main.canal = canal;
        Main.c= c;
        return canal != null;
    }

      public static void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Apply custom styles
        alert.getDialogPane().setStyle(
            "-fx-background-color: #f0f0f0; " +
            "-fx-border-color: #3498db; " +
            "-fx-border-width: 2px; " +
            "-fx-font-size: 14px;");

        // Show the alert
        alert.initStyle(StageStyle.UNDECORATED);
        alert.showAndWait();
    }
      
  
}
