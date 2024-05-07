package jsr268gp.sampleapplet;

import javacard.framework.APDU;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.Util;

public class UtilAPDU {
	// sending data in APDU to the client
	static public void sendToClient(APDU apdu, byte[] data){
		
		// sending data to the client
		byte[] buffer = apdu.getBuffer();
		short dataLength = (short)data.length;
		Util.arrayCopyNonAtomic(data, (short)0, buffer, (short)0, dataLength);
		apdu.setOutgoingAndSend((short) 0, dataLength);
		
	}
	// receiving data in APDU from the client
	static public byte[] receiveFromClient(APDU apdu){
		
		// receiving data from the client
		byte[] buffer = apdu.getBuffer();
		short dataLength = apdu.setIncomingAndReceive();
		byte[] data = new byte[dataLength];
		Util.arrayCopyNonAtomic(buffer, (short)ISO7816.OFFSET_CDATA, data, (short)0, dataLength);
		
		if(data.length < dataLength){
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
		}
		
		return data;
		
	}
}
