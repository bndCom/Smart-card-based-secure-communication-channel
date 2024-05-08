package com.example.demo.tmpAcces;

import org.springframework.beans.factory.support.ScopeNotActiveException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;

import com.example.demo.tmpAcces.models.PatientDto;

public class Util{
	
	public static String aesByteToJson(byte[] data, String key) throws Exception{
		return new String(AesCBCPad.decrypt_CBC(data, Base64.getDecoder().decode(key)), java.nio.charset.StandardCharsets.UTF_8);					
	}
	public static byte[] aesJsonToByte(String data, String key) throws Exception{
		return AesCBCPad.encrypt_CBC(data.getBytes(), Base64.getDecoder().decode(key));
	}
	
	 public static String objectToJson(PatientDto o) throws Exception{
	        ObjectMapper mapper = new ObjectMapper();
	        return mapper.writeValueAsString(o);

	    }

    public static PatientDto JsonToObject(String s) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s ,PatientDto.class );
    }
	
}