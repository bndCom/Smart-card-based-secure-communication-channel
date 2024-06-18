package jsr268gp.sampleclient;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.smartcardio.CardChannel;

//admin session
public class SessionAdmin extends Session{
	
	public SessionAdmin(CardChannel canal, String url){
		super(canal, url);
		this.isAdmin = true;
	}
	
	public void getPersonalInfo() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	// getting all the doctors
	public List<Map<String, Object>> getAllDoctors() throws Exception{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
		timestamp = System.currentTimeMillis();
    	hmac = UtilRequest.requestHash(timestamp, this.K, "");
    	// url encode data
    	hmac = URLEncoder.encode(hmac, StandardCharsets.UTF_8.toString());
    	// sending the request
    	response = UtilRequest.sendRequest(
    			"POST",
    			"uid="+this.UID+"&timestamp="+timestamp+"&hmac="+hmac,
    			this.url+"/doctors/getall",
    			"application/x-www-form-urlencoded"
    			);
    	// checking the status of the request
    	if(response.getCode() != HttpURLConnection.HTTP_OK){
    		// the user is not authenticated
    		if(response.getCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
    			throw new NotAuthenticatedError();
    		}
    		throw new ServerError(response.getCode());
    	}
    	// decrypting the received data
    	data = new String(AesCBCPad.decrypt_CBC(b64Decoder.decode(response.getBody()), b64Decoder.decode(this.K)), StandardCharsets.UTF_8);
    //convert json to a map
    	mapList = UtilRequest.jsonObjStringToMap(data);
    	return mapList;
		
	}
	
	public List<Map<String, Object>> getAllPatients() throws Exception{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
		
		timestamp = System.currentTimeMillis();
    	hmac = UtilRequest.requestHash(timestamp, this.K, "");
    	// url encode data
    	hmac = URLEncoder.encode(hmac, StandardCharsets.UTF_8.toString());
    	// sending the request
    	response = UtilRequest.sendRequest(
    			"POST",
    			"uid="+this.UID+"&timestamp="+timestamp+"&hmac="+hmac,
    			this.url+"/patients/getall",
    			"application/x-www-form-urlencoded"
    			);
    	// checking the status of the request
    	if(response.getCode() != HttpURLConnection.HTTP_OK){
    		// the user is not authenticated
    		if(response.getCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
    			throw new NotAuthenticatedError();
    		}
    		throw new ServerError(response.getCode());
    	}
    	// decrypting the received data
    	data = new String(AesCBCPad.decrypt_CBC(b64Decoder.decode(response.getBody()), b64Decoder.decode(this.K)), StandardCharsets.UTF_8);
    //convert json to a map
    	mapList = UtilRequest.jsonObjStringToMap(data);
    	return mapList;
    	
	}
	
	// get all  the existing admins
	public List<Map<String, Object>> getAllAdmins() throws Exception{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
		
		timestamp = System.currentTimeMillis();
    	hmac = UtilRequest.requestHash(timestamp, this.K, "");
    	// url encode data
    	hmac = URLEncoder.encode(hmac, StandardCharsets.UTF_8.toString());
    	// sending the request
    	response = UtilRequest.sendRequest(
    			"POST",
    			"uid="+this.UID+"&timestamp="+timestamp+"&hmac="+hmac,
    			this.url+"/admins/getall",
    			"application/x-www-form-urlencoded"
    			);
    	// checking the status of the request
    	if(response.getCode() != HttpURLConnection.HTTP_OK){
    		// the user is not authenticated
    		if(response.getCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
    			throw new NotAuthenticatedError();
    		}
    		throw new ServerError(response.getCode());
    	}
    	// decrypting the received data
    	data = new String(AesCBCPad.decrypt_CBC(b64Decoder.decode(response.getBody()), b64Decoder.decode(this.K)), StandardCharsets.UTF_8);
    //convert json to a map
    	mapList = UtilRequest.jsonObjStringToMap(data);
    	return mapList;
    	
	}
	
	// add new patient to the server
	public boolean addNewPatient(String firstName, String lastName, String dateOfBirth, long nationalId, int gender, String email, String phoneNumber, String address) throws Exception{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
		// creating the map
		if(map != null){
			map.clear();
		}	
    	map.put("firstName", firstName);
    	map.put("lastName", lastName);
    	map.put("dateOfBirth", dateOfBirth);
    	map.put("nationalId", nationalId);
    	map.put("gender", gender);
    	map.put("email", email);
    	map.put("phoneNumber", phoneNumber);
    	map.put("sessionKey", this.K);
    	map.put("address", address);
		// converting the map containing user data to json
		String js = UtilRequest.mapToJsonString(this.map);
		// encrypt the data using AES with the admin's session key
		// then base64
		try{
			data = Base64.getEncoder().encodeToString(UtilRequest.aesJsonToByte(js, this.K));
		}catch(Exception e){
			throw new UnknownClientError();
		}
		// other useful paramters for the request
    	timestamp = System.currentTimeMillis();
    	hmac = UtilRequest.requestHash(timestamp, this.K, data);
    	// url encode data
    	data = URLEncoder.encode(data, StandardCharsets.UTF_8.toString());
    	hmac = URLEncoder.encode(hmac, StandardCharsets.UTF_8.toString());
    	// sending the request
    	response = UtilRequest.sendRequest(
    			"POST",
    			"uid="+this.UID+"&timestamp="+timestamp+"&hmac="+hmac+"&data="+data,
    			this.url+"/patients/add",
    			"application/x-www-form-urlencoded"
    			);
    	// checking the status of the request
    	if(response.getCode() != HttpURLConnection.HTTP_OK){
    		// the user is not authenticated
    		if(response.getCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
    			throw new NotAuthenticatedError();
    		}
    		throw new ServerError(response.getCode());
    	}
    	
    	return true;
		
	}
	
	// add new doctor and customizing its card
	public boolean addNewDoctor(CardChannel newCanal, String firstName, String lastName, String picture, long nationalId, int gender, String email, String phoneNumber, String address, String about, String hashCodePin, String doctorStatus) throws Exception{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
		// check in case the card is not inserted
		if(newCanal == null){
			throw new CardNotFound();
		}
		// creating the map
		if(map != null){
			map.clear();
		}	
    	map.put("firstName", firstName);
    	map.put("lastName", lastName);
    	map.put("gender", gender);
    	map.put("picture", picture);
    	map.put("nationalId", nationalId);
    	map.put("about", about);
    	map.put("email", email);
    	map.put("phoneNumber", phoneNumber);
    	map.put("address", address);
    	map.put("hashedCodepin", hashCodePin);
    	map.put("cardExpiringDate", "2024-12-30");
    	map.put("doctorStatus", doctorStatus);
    	
		// converting the map containing user data to json
		String js = UtilRequest.mapToJsonString(this.map);
		// encrypt the data using AES with the admin's session key
		// then base64
		data = Base64.getEncoder().encodeToString(UtilRequest.aesJsonToByte(js, this.K));
		// other useful paramters for the request
    	timestamp = System.currentTimeMillis();
    	hmac = UtilRequest.requestHash(timestamp, this.K, data);
    	// url encode data
    	data = URLEncoder.encode(data, StandardCharsets.UTF_8.toString());
    	hmac = URLEncoder.encode(hmac, StandardCharsets.UTF_8.toString());
    	// sending the request
    	response = UtilRequest.sendRequest(
    			"POST",
    			"uid="+this.UID+"&timestamp="+timestamp+"&hmac="+hmac+"&data="+data,
    			this.url+"/doctors/add",
    			"application/x-www-form-urlencoded"
    			);
    	// checking the status of the request
    	if(response.getCode() != HttpURLConnection.HTTP_OK){
    		// the user is not authenticated
    		if(response.getCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
    			throw new NotAuthenticatedError();
    		}
    		throw new ServerError(response.getCode());
    	}
    	
    	// retreiving card keys and the card UID from the request response
    	data = new String(AesCBCPad.decrypt_CBC(b64Decoder.decode(response.getBody()), b64Decoder.decode(this.K)), StandardCharsets.UTF_8);
    	Map<String, String> mp = UtilRequest.jsonStringToMap(data);
    	try{
    		// card private keys
    		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PUBLIC_MOD, (byte)0x00, (byte)0x00, b64Decoder.decode(mp.get("cardPublicMod")), newCanal);
    		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PRIVATE_EXP, (byte)0x00, (byte)0x00, b64Decoder.decode(mp.get("cardPrivateExp")), newCanal);
    		// server public keys
    		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_SERVER_PUBLIC_EXP, (byte)0x00, (byte)0x00, b64Decoder.decode(mp.get("serverPublicExp")), newCanal);
    		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_SERVER_PUBLIC_MOD, (byte)0x00, (byte)0x00, b64Decoder.decode(mp.get("serverPublicMod")), newCanal);
    		// sending the UID of the card
    		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_SC_UID, (byte)0x00, (byte)0x00, b64Decoder.decode(mp.get("userCardNumber")), newCanal);
    	
    	}catch(Exception e){
    		return false;
    	}
    	
    	
    	return true;
		
	}
	
	// add new admin and customizing its card
	public boolean addNewAdmin(CardChannel newCanal, String firstName, String lastName, String picture, long nationalId, String email, String phoneNumber, String address, String hashCodePin) throws Exception{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
		// check in case the card is not inserted
		if(newCanal == null){
			throw new CardNotFound();
		}
		// creating the map
		if(map != null){
			map.clear();
		}	
    	map.put("nationalId", nationalId);
    	map.put("firstName", firstName);
    	map.put("lastName", lastName);
    	map.put("picture", picture);
    	map.put("email", email);
    	map.put("phoneNumber", phoneNumber);
    	map.put("address", address);
    	map.put("hashedCodepin", hashCodePin);
    	map.put("cardExpiringDate", "2024-12-30");
    	map.put("userPublicKey", null);
    	map.put("sessionKey", this.K);
    	
		// converting the map containing user data to json
		String js = UtilRequest.mapToJsonString(this.map);
		// encrypt the data using AES with the admin's session key
		// then base64
		data = Base64.getEncoder().encodeToString(UtilRequest.aesJsonToByte(js, this.K));
		// other useful paramters for the request
    	timestamp = System.currentTimeMillis();
    	hmac = UtilRequest.requestHash(timestamp, this.K, data);
    	// url encode data
    	data = URLEncoder.encode(data, StandardCharsets.UTF_8.toString());
    	hmac = URLEncoder.encode(hmac, StandardCharsets.UTF_8.toString());
    	// sending the request
    	response = UtilRequest.sendRequest(
    			"POST",
    			"uid="+this.UID+"&timestamp="+timestamp+"&hmac="+hmac+"&data="+data,
    			this.url+"/admins/add",
    			"application/x-www-form-urlencoded"
    			);
    	// checking the status of the request
    	if(response.getCode() != HttpURLConnection.HTTP_OK){
    		// the user is not authenticated
    		if(response.getCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
    			throw new NotAuthenticatedError();
    		}
    		throw new ServerError(response.getCode());
    	}
    	
    	// retreiving card keys and the card UID from the request response
    	data = new String(AesCBCPad.decrypt_CBC(b64Decoder.decode(response.getBody()), b64Decoder.decode(this.K)), StandardCharsets.UTF_8);
    	
    	Map<String, String> mp = UtilRequest.jsonStringToMap(data);
    	// this must be uncommented when working with card
    	try{
    		// card private keys
    		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PUBLIC_MOD, (byte)0x00, (byte)0x00, b64Decoder.decode(mp.get("cardPublicMod")), newCanal);
    		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_CARD_PRIVATE_EXP, (byte)0x00, (byte)0x00, b64Decoder.decode(mp.get("cardPrivateExp")), newCanal);
    		// server public keys
    		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_SERVER_PUBLIC_EXP, (byte)0x00, (byte)0x00, b64Decoder.decode(mp.get("serverPublicExp")), newCanal);
    		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_CS_RSA_SERVER_PUBLIC_MOD, (byte)0x00, (byte)0x00, b64Decoder.decode(mp.get("serverPublicMod")), newCanal);
    		// sending the UID of the card
    		respApdu = APDUOps.sendApduToCard(CLA_APPLET, INS_SC_UID, (byte)0x00, (byte)0x00, b64Decoder.decode(mp.get("userCardNumber")), newCanal);
    		// printing variables
    		System.out.println("card public mod");
    		DH.printByteArray(b64Decoder.decode(mp.get("cardPublicMod")));
    		System.out.println("card private mod");
    		DH.printByteArray(b64Decoder.decode(mp.get("cardPrivateExp")));
    		System.out.println("server public exp");
    		DH.printByteArray(b64Decoder.decode(mp.get("serverPublicExp")));
    		System.out.println("server public mod");
    		DH.printByteArray(b64Decoder.decode(mp.get("serverPublicMod")));
    		System.out.println("card num");
    		DH.printByteArray(b64Decoder.decode(mp.get("userCardNumber")));
    		
    	}catch(Exception e){
    		return false;
    	}
    	
    	
    	return true;
		
	}
	
	public void modifyUser() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	// add new doctor and customizing its card
	public boolean deleteDoctor(long doctorId)throws Exception{//, String firstName, String lastName, String picture, long nationalId, int gender, String email, String phoneNumber, String address, String about, String hashCodePin, String doctorStatus) throws Exception{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}

		// creating the map
		if(map != null){
			map.clear();
		}
		map.put("doctorId", doctorId);
//    	map.put("firstName", firstName);
//    	map.put("lastName", lastName);
//    	map.put("gender", gender);
//    	map.put("picture", picture);
//    	map.put("nationalId", nationalId);
//    	map.put("about", about);
//    	map.put("email", email);
//    	map.put("phoneNumber", phoneNumber);
//    	map.put("address", address);
//    	map.put("hashedCodepin", hashCodePin);
//    	map.put("cardExpiringDate", "2024-12-30");
//    	map.put("doctorStatus", doctorStatus);
    	
		// converting the map containing user data to json
		String js = UtilRequest.mapToJsonString(this.map);
		// encrypt the data using AES with the admin's session key
		// then base64
		data = Base64.getEncoder().encodeToString(UtilRequest.aesJsonToByte(js, this.K));
		// other useful paramters for the request
    	timestamp = System.currentTimeMillis();
    	hmac = UtilRequest.requestHash(timestamp, this.K, data);
    	// url encode data
    	data = URLEncoder.encode(data, StandardCharsets.UTF_8.toString());
    	hmac = URLEncoder.encode(hmac, StandardCharsets.UTF_8.toString());
    	// sending the request
    	response = UtilRequest.sendRequest(
    			"POST",
    			"uid="+this.UID+"&timestamp="+timestamp+"&hmac="+hmac+"&data="+data,
    			this.url+"/doctors/deletebyid",
    			"application/x-www-form-urlencoded"
    			);
    	// checking the status of the request
    	if(response.getCode() != HttpURLConnection.HTTP_OK){
    		// the user is not authenticated
    		if(response.getCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
    			throw new NotAuthenticatedError();
    		}
    		throw new ServerError(response.getCode());
    	}
    	
    	return true;
		
	}
	
	// disconnect
	public boolean disconnect() throws Exception{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
		
		timestamp = System.currentTimeMillis();
    	hmac = UtilRequest.requestHash(timestamp, this.K, "");
    	// url encode data
    	hmac = URLEncoder.encode(hmac, StandardCharsets.UTF_8.toString());
    	// sending the request
    	response = UtilRequest.sendRequest(
    			"POST",
    			"uid="+this.UID+"&timestamp="+timestamp+"&hmac="+hmac,
    			this.url+"/admins/disconnect",
    			"application/x-www-form-urlencoded"
    			);
    	// checking the status of the request
    	if(response.getCode() != HttpURLConnection.HTTP_OK){
    		// the user is not authenticated
    		if(response.getCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
    			throw new NotAuthenticatedError();
    		}
    		throw new ServerError(response.getCode());
    	}
    	// decrypting the received data
    	//data = new String(AesCBCPad.decrypt_CBC(b64Decoder.decode(response.getBody()), b64Decoder.decode(this.K)), StandardCharsets.UTF_8);
    //convert json to a map
    	//mapList = UtilRequest.jsonObjStringToMap(data);
    	return true;
    	
	}
	
}
