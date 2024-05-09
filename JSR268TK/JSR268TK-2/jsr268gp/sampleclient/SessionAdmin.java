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
	}
	
	public void getPersonalInfo() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	public void getAllDoctors() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
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
	
	// add new user either doctor or patient
	public boolean addNewUser(String firstName, String lastName, String dateOfBirth, long nationalId, int gender, String email, String phoneNumber, String address) throws Exception{
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
	
	public void modifyUser() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	public void deleteUser() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
}
