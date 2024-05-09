package jsr268gp.sampleclient;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

class Employee {
    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    // Getters and setters (or lombok annotations) are typically used in real-world applications
}

public class test {

	public static void main(String[] args) throws Exception {
		
        // Sample list of objects
        List<Employee> employeeList = Arrays.asList(
                new Employee(1, "John Doe", 50000),
                new Employee(2, "Jane Smith", 60000),
                new Employee(3, "Mike Johnson", 70000)
        );

        // Convert list of objects to JSON
        String json = convertListToJson(employeeList);
        System.out.println(json);	
	}
	public static String convertListToJson(List<Employee> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
	
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
