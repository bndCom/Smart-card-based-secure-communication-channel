package com.example.demo.tmpAcces;

import org.springframework.beans.factory.support.ScopeNotActiveException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import com.example.demo.tmpAcces.models.PatientDto;
import com.example.demo.tmpAcces.models.Patient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class Util{
	
	public static String aesByteToJson(byte[] data, String key) throws Exception{
		return new String(AesCBCPad.decrypt_CBC(data, Base64.getDecoder().decode(key)), java.nio.charset.StandardCharsets.UTF_8);					
	}
	public static byte[] aesJsonToByte(String data, String key) throws Exception{
		return AesCBCPad.encrypt_CBC(data.getBytes(), Base64.getDecoder().decode(key));
	}
	
	// authorize a request
	public static boolean validateRequest(long timestamp , String keyAes , String encryptedJsonBody , String HashVerify)throws NoSuchAlgorithmException
    {
        boolean validTimeStamp = false;
        boolean validHash = false;
        boolean valid = false;
        long currentTime = System.currentTimeMillis();
        // 
        if ( (currentTime - timestamp) <30000){
            validTimeStamp = true;
            
            // verify the hash then

            if ( HashVerify.equals(requestHash(timestamp,keyAes,encryptedJsonBody)) ){
                validHash = true;
            }
        }

        valid = validHash & validTimeStamp;
        return valid;
    }
	
	 public static String Hash_256(String toHash) throws NoSuchAlgorithmException {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte [] digest = md.digest(toHash.getBytes(StandardCharsets.UTF_8));
	        return Base64.getEncoder().encodeToString(digest);

	    }
	
	// calculate the hmac of the message
	public static String requestHash(long timestamp , String keyAES , String encryptedJsonBody) throws NoSuchAlgorithmException {
     String toHash = keyAES + timestamp + encryptedJsonBody;
     return Hash_256(toHash);
 }
	
	public static String objectToJson(PatientDto o) throws Exception{
	        ObjectMapper mapper = new ObjectMapper();
	        return mapper.writeValueAsString(o);

	    }

    public static PatientDto JsonToObject(String s) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s ,PatientDto.class );
    }
    
    public static String listToJson(List<Patient> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
	
}