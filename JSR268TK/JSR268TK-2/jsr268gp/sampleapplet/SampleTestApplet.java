package jsr268gp.sampleapplet;

import javacard.framework.*;

public class SampleTestApplet extends Applet {

	public static final short MODULUS_SIZE = (short)128;
	public static final short HASH_SIZE = (short)32;
	public static final short AES_KEY_SIZE = (short)16;
	public static final byte CLA = (byte) 0x80;
	public static final byte INS_CS_RSA_CARD_PUBLIC_MOD= (byte)0x01;
	public static final byte INS_CS_RSA_CARD_PUBLIC_EXP= (byte)0x02;
	public static final byte INS_CS_RSA_SERVER_PUBLIC_MOD= (byte)0x03;
	public static final byte INS_CS_RSA_SERVER_PUBLIC_EXP= (byte)0x04;
	public static final byte INS_CS_RSA_CARD_PRIVATE_EXP= (byte)0x07;
	public static final byte INS_SC_RSA_CARD_PUBLIC_MOD= (byte)0x08;
	public static final byte INS_SC_UID = (byte)0x09;
	public static final byte INS_CS_DH_PUBLIC_KEY = (byte)0x12;
	public static final byte INS_CS_DH_B = (byte)0x0B;
	public static final byte INS_CS_UID =(byte) 0x11;
	public static final byte INS_CS_DH_SIGN = (byte)0x0D;
	public static final byte INS_SC_SIGN_STATUS = (byte)0x0E;
	public static final byte INS_SC_DH_SIGN = (byte)0x0F;
	public static final byte INS_TEST = (byte)0x68;
	public static final byte INS_SC_K = (byte)0x86;
	

	private  byte[] P=new byte[MODULUS_SIZE];
	private  byte[] n=new byte[MODULUS_SIZE];
	private byte[] K =new byte[MODULUS_SIZE];
	private byte[] A =new byte[MODULUS_SIZE];
	private byte[] B =new byte[MODULUS_SIZE];
	private byte[] AB;
	private byte[] cardPrivateKeyMod = new byte[MODULUS_SIZE];
	private byte[] serverPublicKeyMod = new byte[MODULUS_SIZE];
	private byte[] serverPublicKeyExp = new byte[MODULUS_SIZE];
	private byte[] cardPrivateKeyExp = new byte[MODULUS_SIZE];
	private byte[] tmpp = new byte[MODULUS_SIZE];
	private byte[] hashb = new byte[HASH_SIZE];
	private byte[] out = new byte[MODULUS_SIZE];
	private byte[] cardUID = new byte[64];
	public static byte[] cpt={(byte) 0x00};
	public static final byte[] SUCCESS = {(byte)0x90};
	public static final byte[] FAIL = {(byte)0x67};

	
	static byte[] G = {
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
		    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02
		    };
	private byte[] signEnc=new byte[MODULUS_SIZE];
	private boolean isVerified;
	private byte[] signDec;
	AES AESIns;
	
	public boolean select () {
		
		return (true);
	}
	
	public void deselect () {
	}
	
	public SampleTestApplet () {		

	}
	
	public static void install (byte [] bArray, short bOffset, byte bLength) throws ISOException {
		SampleTestApplet s = new SampleTestApplet ();
		
		s.register();
	}
	
	public void process(APDU apdu) throws ISOException{
		
		
		byte[] buffer = apdu.getBuffer();
		if (selectingApplet()) return;
		
		if (buffer[ISO7816.OFFSET_CLA] != CLA)
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
		
		
		switch (buffer[ISO7816.OFFSET_INS]) {
		
		// These cases are for saving the keys when customizing the card
		case INS_CS_RSA_CARD_PUBLIC_MOD:
			cardPrivateKeyMod = UtilAPDU.receiveFromClient(apdu);
			break;
			
		case INS_CS_RSA_CARD_PRIVATE_EXP:
			cardPrivateKeyExp = UtilAPDU.receiveFromClient(apdu);
			break;
			
		case INS_CS_RSA_SERVER_PUBLIC_MOD:
			serverPublicKeyMod = UtilAPDU.receiveFromClient(apdu);
			break;
			
		case INS_CS_RSA_SERVER_PUBLIC_EXP:
			serverPublicKeyExp = UtilAPDU.receiveFromClient(apdu);
			break;
		
		case INS_SC_UID:
			cardUID = UtilAPDU.receiveFromClient(apdu);
			break;
		
		case INS_CS_DH_PUBLIC_KEY:
			// getting DH public key
			 P = UtilAPDU.receiveFromClient(apdu);
			 // generating card DH private
			 n = Utils.generateRandomNumber();
			 // calculate DH card public key
			A = jcDH.modPowRSA(G, n, P);
			// sending A to the client
			UtilAPDU.sendToClient(apdu, A);

			break;
		case INS_CS_UID :
			// sending card uid to the client so the server can fetch the card public key from the database
			UtilAPDU.sendToClient(apdu,cardUID);
			break;
			
		case INS_CS_DH_B:
			// getting B from the server
			B = UtilAPDU.receiveFromClient(apdu);
			// calculate session key ( symmetric encryption key )
			K = Utils.masqueFunction(jcDH.modPowRSA(B,n,P));

			break;

		case(INS_CS_DH_SIGN):
			
			// getting the encrypted signature from the client
			signEnc = UtilAPDU.receiveFromClient(apdu);
			// decrypting signature from AES
			signDec = new byte[MODULUS_SIZE];
			AESIns = new AES();
			AESIns.keySetUp(K);
			AESIns.AesDecrypt(signEnc, signDec, (short)signEnc.length);
			// concatenate A and B
			AB = Utils.concat(A, B);
			//verifying signature
			isVerified = false;
			isVerified = jcDH.verifySignRSA(AB, signDec, serverPublicKeyExp, serverPublicKeyMod);

			//isVerified = verifySignature(B, signEnc, publicKey);
			
			break;
			
        case(INS_SC_DH_SIGN):
			
        	// signing data
			tmpp = new byte[MODULUS_SIZE];
			hashb = jcDH.hash(AB);
			Util.arrayCopyNonAtomic(hashb, (short)0, tmpp, (short)96, (short)hashb.length);
			signDec = jcDH.signRSA(tmpp, cardPrivateKeyExp, cardPrivateKeyMod);
    		// encrypt the signature before sending it to the client
    		AESIns = new AES();
    		AESIns.keySetUp(K);
    		out = new byte[MODULUS_SIZE];
    		AESIns.AesEncryption(out, signDec, (short)signDec.length);
    		// sending encrypted signature to the client
    		UtilAPDU.sendToClient(apdu, out);
		break;
		
        case(INS_SC_SIGN_STATUS):

			if (isVerified == true){
				UtilAPDU.sendToClient(apdu, SUCCESS);
			}else{
				UtilAPDU.sendToClient(apdu, FAIL);
			}
			
			break;

        case(INS_SC_K):
        	UtilAPDU.sendToClient(apdu, K);
        	break;
        	
		case(INS_TEST):

			break;

			
		default :
			ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
		}
	}
    
  }