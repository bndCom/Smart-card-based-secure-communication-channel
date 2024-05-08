package jsr268gp.sampleclient;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class test {

	public static void main(String[] args) throws Exception {
		// -------------- encryption routin ---------
		PatientDto patientDto = new PatientDto();
    	try{
    		patientDto = Util.JsonToObject(Util.aesByteToJson(Base64.getDecoder().decode(data), "gFn/XoAfNz0LjSnrsHc3CA=="));
    	}catch(Exception e){
    		throw new RuntimeException();
    	}
	}
	
	public static byte[] padZero(byte[] data){
		
		int nbr = 0;
		if(data.length % 16 != 0){
			nbr = 16 - (data.length % 16);
		}
		byte[] out = new byte[data.length + nbr];
		System.arraycopy(data, 0, out, 0, data.length);
		
		return out;
		
	}
	public static byte[] unpadZero(byte[] data){
		
		int idx = data.length; 
		for (int i = data.length - 1; i >= 0; i--){
			if(data[i] == 0){
				idx--;
			}else{
				break;
			}
		}
		byte[] out = new byte[idx];
		System.arraycopy(data, 0, out, 0, idx);
		
		return out;
	}
	
	public static String aesByteToJson(byte[] data, String key) throws Exception{
		return new String(AesCBCPad.decrypt_CBC(data, Base64.getDecoder().decode(key)), java.nio.charset.StandardCharsets.UTF_8);					
	}
	public static byte[] aesJsonToByte(String data, String key) throws Exception{
		return AesCBCPad.encrypt_CBC(data.getBytes(), Base64.getDecoder().decode(key));
	}

}
