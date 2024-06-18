package jsr268gp.sampleclient;

import java.io.IOException;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;



import javafx.util.Pair; 


public class APDUOps {

	// connect to simulator and select applet
		public static Pair<CardChannel, Card> connectAndSelect(CardTerminal cad){
			
			CardChannel canal = null;	
			Card c = null;
			try{
				c = cad.connect("T=0");
				canal = c.getBasicChannel();
				// select the applet
				CommandAPDU commande = new CommandAPDU(new byte[]{(byte) 0x00, (byte) 0xA4, (byte) 0x04,(byte) 0x00,(byte) 0x0F,(byte) 0xA0,(byte) 0x00,(byte) 0x00,(byte) 0x18,(byte) 0x50,(byte) 0x00,(byte) 0x00,(byte) 0x00,(byte) 0x00,(byte) 0x00,(byte) 0x00,(byte) 0x52,(byte) 0x41,(byte) 0x44,(byte) 0x43});
				ResponseAPDU reponse = canal.transmit(commande);
				
				if(reponse.getSW() != 36864){
					System.out.println("Error! Couldn't select the applet. APDU response:  " + reponse.getSW());
				}
				
			}catch(Exception e){
				System.out.println("Error! Couldn't connect to the reader!!");
				return null;
			}
		
			return new Pair<CardChannel, Card>(canal, c);
			
		}	
		
		// send APDU 
		public static ResponseAPDU sendApduToCard (byte cla , byte ins , byte p1 , byte p2 , byte[] data, CardChannel canal) throws CardException {
		    
			
			// putting data into buffer
			// Calculate the total length of the concatenated array
			int totalLength = 5 + (data != null ? data.length : 0);

			// Create a new byte array with the total length
			byte[] buffer = new byte[totalLength];

			// Copy cla, ins, p1, and p2 into the concatenated array
			buffer[0] = cla;
			buffer[1] = ins;
			buffer[2] = p1;
			buffer[3] = p2;
			buffer[4] = (byte)data.length;

			// If data is not null, copy its contents into the concatenated array
			if (data != null) {
			    System.arraycopy(data, 0, buffer, 5, data.length);
			}
			
			CommandAPDU command = new CommandAPDU(buffer);
			ResponseAPDU reponse = canal.transmit(command);
			
		    return reponse ;
		}
		// send APDU 
		public static ResponseAPDU sendApduToCard (byte cla , byte ins , byte p1 ,byte p2 , CardChannel canal) throws CardException {
		    
			
			// putting data into buffer
			// Create a new byte array with the total length
			byte[] buffer = new byte[5];

			// Copy cla, ins, p1, and p2 into the concatenated array
			buffer[0] = cla;
			buffer[1] = ins;
			buffer[2] = p1;
			buffer[3] = p2;
			buffer[4] = (byte)0x00;

			CommandAPDU command = new CommandAPDU(buffer);
			ResponseAPDU reponse = canal.transmit(command);
			
		    return reponse ;
		}
	
}