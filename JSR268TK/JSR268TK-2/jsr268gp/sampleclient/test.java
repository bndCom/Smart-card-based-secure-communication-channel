package jsr268gp.sampleclient;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class test {

	public static void main(String[] args) throws Exception {
		// -------------- encryption routin ---------
		Map<String, String> mp = new HashMap<String, String>();
		mp.put("name",  "anes");
		String js = UtilRequest.mapToJsonString(mp);
//		byte[] res = AesCBC.encrypt_CBC(padZero(js.getBytes()), Base64.getDecoder().decode("gFn/XoAfNz0LjSnrsHc3CA=="));
//		DH.printByteArray(js.getBytes());
//		DH.printByteArray(res);
//		// ------------ decryption routin -----------
//		byte[] out = AesCBC.decrypt_CBC(res, Base64.getDecoder().decode("gFn/XoAfNz0LjSnrsHc3CA=="));
//		js = new String(unpadZero(out), java.nio.charset.StandardCharsets.UTF_8);
//		System.out.println(js);
		
		System.out.println(aesByteToJson(aesJsonToByte(js, "gFn/XoAfNz0LjSnrsHc3CA=="), "gFn/XoAfNz0LjSnrsHc3CA=="));
		
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
