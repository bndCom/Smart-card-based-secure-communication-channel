package jsr268gp.sampleclient;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.smartcardio.CardChannel;

//doctor session
public class SessionDoctor extends Session{
	
	public SessionDoctor(CardChannel canal, String url){
		super(canal, url);
		this.isAdmin = false;
	}
	
	public void getPersonalInfo() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	// modify medical record
	public void modifyPatientMedicalRecord() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	// list all patients
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
	    			this.url+"/doctors/disconnect",
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
		
}
