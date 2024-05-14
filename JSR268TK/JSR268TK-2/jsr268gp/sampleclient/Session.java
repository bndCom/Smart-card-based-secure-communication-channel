package jsr268gp.sampleclient;

import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.ResponseAPDU;

public abstract class Session {
	
	// constants
	public static final int MODULUS_SIZE = 128;
	public static final int HASH_SIZE = 32;
	public static final int AES_KEY_SIZE = 16;
	public static final byte CLA_APPLET = (byte)0x80;
	public static final byte INS_CS_DH_PUBLIC_KEY = (byte)0x12;
	public static final byte INS_CS_UID = (byte)0x11;
	public static final byte INS_CS_DH_SIGN = (byte)0x0D;
	public static final byte INS_SC_SIGN_STATUS = (byte)0x0E;
	public static final byte INS_SC_DH_SIGN = (byte)0x0F;
	public static final byte INS_CS_DH_B = (byte)0x0B;
	public static final byte INS_SC_K = (byte)0x86;
	public static final byte INS_CS_RSA_CARD_PUBLIC_MOD= (byte)0x01;
	public static final byte INS_CS_RSA_CARD_PUBLIC_EXP= (byte)0x02;
	public static final byte INS_CS_RSA_SERVER_PUBLIC_MOD= (byte)0x03;
	public static final byte INS_CS_RSA_SERVER_PUBLIC_EXP= (byte)0x04;
	public static final byte INS_CS_RSA_CARD_PRIVATE_EXP= (byte)0x07;
	public static final byte INS_SC_UID = (byte)0x09;


	// attributes
	protected String url;
	protected CardChannel canal;
	protected ResponseAPDU respApdu;
	protected String K;
	protected long UID;
	protected boolean isAdmin;
	// used variables
	protected String data = new String("");
	protected String hmac = new String(""); 
	protected Decoder b64Decoder = Base64.getDecoder();
	protected Encoder b64Encoder = Base64.getEncoder();
	protected UtilRequest.Response response;
	protected long timestamp;
	protected LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
	protected List<Map<String, Object>> mapList = new LinkedList<Map<String, Object>>();
	
	public Session(CardChannel canal, String url){
		this.url = url;
		this.canal = canal;
		this.K = null;
	}
	
	// authenticate to the remote
	public boolean auth(String pin) throws Exception, CardAuthFailed, ServerError{
		
		if(this.canal == null){
			throw new CardNotFound();
		}
		// generate P
		byte[] P = DH.generateRandomPrime(1024).toByteArray();
    	P = DH.adjustArray(P, MODULUS_SIZE);
    	
    	// get A from card
    	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_DH_PUBLIC_KEY, (byte)0x00, (byte)0x00, P, canal);
    	byte[] A = respApdu.getData();
    	
    	// getting card uid
    	respApdu =APDUOps.sendApduToCard(CLA_APPLET, INS_CS_UID, (byte)0x00, (byte)0x00, canal);
    	this.UID = DH.byteArrayToLong(respApdu.getData());
    	// sending auth first phase to the server
    	data="isAdmin="+isAdmin+"&uid="+Long.toString(UID)+"&p="+URLEncoder.encode(b64Encoder.encodeToString(P), StandardCharsets.UTF_8.toString())+"&a="+URLEncoder.encode(b64Encoder.encodeToString(A), StandardCharsets.UTF_8.toString());
    	response = UtilRequest.sendRequest("POST", data, url+"/connect/phase1", "application/x-www-form-urlencoded");
    	if(response.getCode() != HttpURLConnection.HTTP_OK){
    		throw new ServerError(response.getCode());
    	}
    	
    	// getting other protocol parameters
    	Map<String, String>mp = UtilRequest.jsonStringToMap(response.getBody());
    	byte[] B = Base64.getDecoder().decode(mp.get("B"));
    	byte[] sign = Base64.getDecoder().decode(mp.get("Sign"));

    	
    	// sending B to the card
    	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_DH_B, (byte)0x00, (byte)0x00, B, canal);
    	// sending encrypted signature to the card
    	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_DH_SIGN, (byte)0x00, (byte)0x00, sign, canal);

    	// checking the signature verification status
    	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_SC_SIGN_STATUS, (byte)0x00, (byte)0x00, canal);
    	if(respApdu.getData()[0] != (byte)0x90){
    		throw new CardAuthFailed();
    	}
    	
    	// getting the signature from the card
    	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_SC_DH_SIGN, (byte)0x00, (byte)0x00, canal);

    	// sending the signature to the server
    	sign = respApdu.getData();
    	
    	// getting K from the card
    	respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_SC_K, (byte)0x00, (byte)0x00, canal);
    	byte[] tmpK = respApdu.getData();
    	this.K = b64Encoder.encodeToString(tmpK);
    	String pinEnc = Base64.getEncoder().encodeToString(AesCBCPad.encrypt_CBC(pin.getBytes(StandardCharsets.UTF_8), tmpK));
    	
    	data="UID="+UID+"&sign="+URLEncoder.encode(b64Encoder.encodeToString(sign), StandardCharsets.UTF_8.toString())+"&pin="+URLEncoder.encode(pinEnc, StandardCharsets.UTF_8.toString());
    	response = UtilRequest.sendRequest("POST", data, url+"/connect/phase2", "application/x-www-form-urlencoded");
    	// check server authentication status
    	if(response.getCode() != HttpURLConnection.HTTP_OK | !response.getBody().equals("OK")){
    		throw new ServerError(response.getCode());
    	}
    	
    	
		return true; // auth successful
		
	}
	
	public void endSession(){
		this.K = null;
	}
	
	public long getUID(){
		return this.UID;
	}
	
	// this methode must be reimplemented by all users
	abstract public void getPersonalInfo() throws NotAuthenticatedError;

}









