package jsr268gp.sampleclient;

import javafx.util.Pair;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

import clientgui.Main;

public class SampleClient {

	
	public SampleClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args){
		CardChannel canal;
		CardTerminal cad = null;
		Card c = null;
		Pair <CardChannel, Card> cadPair = null;
 		
        // connecting to the card
        TerminalFactory tf = TerminalFactory.getDefault();
        cad = tf.terminals().getTerminal("ACS ACR1281 1S Dual Reader PICC 0");
		cadPair = APDUOps.connectAndSelect(cad);
 		canal = cadPair.getKey();
     	c = cadPair.getValue();
		SessionAdmin admin = new SessionAdmin(canal, "http://localhost:8080");
     	
		
		try {
			admin.addNewAdmin(canal, "admin", "admin", "image.png", 98219892818398l, "admin@gmail.com", "0779759059", "setif", "1234");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}