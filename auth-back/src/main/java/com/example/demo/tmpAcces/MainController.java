package com.example.demo.tmpAcces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.Base64.Decoder;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;

import com.example.demo.tmpAcces.repositories.OnlineRepository;
import com.example.demo.tmpAcces.repositories.DoctorRepository;
import com.example.demo.tmpAcces.repositories.AdminRepository;
import com.example.demo.tmpAcces.models.online;
import com.example.demo.tmpAcces.models.Doctor;
import com.example.demo.tmpAcces.models.Admin;


@Controller

@RequestMapping(path ="/connect")

public class MainController{
    public static final int MODULUS_SIZE = 128;
    // the server's rsa key pair
    public static final String base64privatemodulus = "uVXpTTzwM5iXhM+aC5cAjDvUc/qQGJmUfCj6uY/1P8os9Lvm89RKsdIW5E13aiksQAF9LZkvWXfQpdiMlSY2vtHtnYgyF7QhFrIWhRWhpGjk9cNrXyX4f3W3uDE4YohFZfWkfUzOmeyFt09R8kSqWUBG3g2I58QzqzHiK7Rd+DM=";
    public static final String base64privateexponent = "JJUYn+5PW1/bSJPRzEfaC9Qjc2EZ4EEwVfGgy8/mkNjPVt9gDvDwbXkSm63OzF2kJl4k30NFXVuRC6ta1HXeiCVP17in/6qUQRNQB4r8rI2Kl+k/OY2H4UwZ1XastzHSxh8oKODByu2PuJaYx0KdcCeNaQy+RKrP7986qjQR8cE=";
    public static final String base64publicexponent = "AQAB";

    @Autowired
    private tmpDataRepository tmpDataRepository;
@PostMapping(path = "/phase1") // map the first part of the request to this method e
    public @ResponseBody Response1 begin (@RequestParam boolean isAdmin, @RequestParam long uid, @RequestParam String p , @RequestParam String a){

    byte[] G = {
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
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x02};
    

    byte [] privatekeyexponent = Base64.getDecoder().decode(base64privateexponent);
    byte [] privatekeymodulus = Base64.getDecoder().decode(base64privatemodulus);
    byte [] publickeyexponent = Base64.getDecoder().decode(base64publicexponent);
    
    byte[] mm = DH.generateRandom(1024).toByteArray();
    mm = DH.adjustArray(mm, 128);
    

    // for the time being we are generating the p in the server side for testing purposes we will change it later
    //since we are supposed to get it from the client side based on the diagram provided
    byte[] PP = new byte[128];
    byte[] AA = new byte[128];

    PP = Base64.getDecoder().decode(p);
    AA = Base64.getDecoder().decode(a);

    
    

    byte[] BB = DH.modularExponentiation(G, mm, PP);

    //K the symetric key will be derived from this :)
    byte[] KK = DH.masqueFunction(DH.modularExponentiation(AA, mm, PP));



    // signature


    // concatenate A and B to be signed
    byte[] AB = DH.concat(AA, BB);


    byte[] toBeSigned = DH.hash(AB);

    byte[] sign = new byte[128];
    try {
        sign = DH.signDataRSA(toBeSigned, RSAOps.createPrivateKey(privatekeymodulus, privatekeyexponent));
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    } catch (InvalidKeySpecException e) {
        throw new RuntimeException(e);
    }
    


    //byte[] sign = DH.signDataRSA(toBeSigned, RSAOps.createPrivateKey(concatenatedPrivateKey[0], concatenatedPrivateKey[1]));
    byte[] KAes = new byte[16];
    System.arraycopy(KK,0,KAes,0,16);

    try {

        sign = AesCBC.encrypt_CBC(sign, KAes);
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
//    System.out.println("<< Signature >> encrypted in AES -------------------: ");
//    DH.printByteArray(sign);

    tmpData entity = new tmpData() ;
    entity.setIsAdmin(isAdmin);
    entity.setUID(uid);
    entity.setK(Base64.getEncoder().encodeToString(KAes));
    entity.setA(Base64.getEncoder().encodeToString(toBeSigned));
    entity.setTimestamp(1);
    this.tmpDataRepository.save(entity);
    
    String B = Base64.getEncoder().encodeToString(BB);
    String Sign = Base64.getEncoder().encodeToString(sign);
    
    return new Response1(Sign ,B);


}

private final tmpDataRepository tmpdataRepository;
private final OnlineRepository onlinerepository;
private final DoctorRepository doctorRepository;
private final AdminRepository adminRepository;

@Autowired
public MainController(tmpDataRepository tmpdataRepository, OnlineRepository onlinerepository, DoctorRepository doctorRepository, AdminRepository adminRepository) {
    this.tmpdataRepository =  tmpdataRepository;
    this.onlinerepository = onlinerepository;
    this.doctorRepository = doctorRepository;
    this.adminRepository = adminRepository;
}


@PostMapping(path = "/phase2")
	public @ResponseBody String phase2(@RequestParam String sign , @RequestParam long UID, @RequestParam String pin){
	byte[] publickeyexponent = new byte[3];
	byte[] privatekeymodulus = new byte[128];
	String hashPin;
	
    boolean valid = false;
    Optional<tmpData> optionalData = tmpdataRepository.findById(UID);
    if (optionalData.isPresent()) {

        //We should get the public key of the user based on its identity ( admin or doctor)
    	if(optionalData.get().getIsAdmin()){
    		Optional<Admin> adminOptional = adminRepository.findById(UID);
    		if(adminOptional.isPresent()){
    			hashPin = adminOptional.get().getHashedCodepin();
    			
    			String base64CardPublicKey = adminOptional.get().getUserPublicKey();
    			byte[] cardPublicKey = Base64.getDecoder().decode(base64CardPublicKey);
    			// getting the exponent and modulus separately
    			System.arraycopy(cardPublicKey, 0, publickeyexponent, 0, 3);
    			System.arraycopy(cardPublicKey, 3, privatekeymodulus, 0, 128);
    			
    		}else{
    			return "Error not found";
    		}
    	}else{ // this means that the user is doctor not admin
    		Optional<Doctor> doctorOptional = doctorRepository.findById(UID);
    		if(doctorOptional.isPresent()){
    			hashPin = doctorOptional.get().getHashedCodepin();
    			String base64CardPublicKey = doctorOptional.get().getUserPublicKey();
    			
    			byte[] cardPublicKey = Base64.getDecoder().decode(base64CardPublicKey);
    			// getting the exponent and modulus separately
    			System.arraycopy(cardPublicKey, 0, publickeyexponent, 0, 3);
    			System.arraycopy(cardPublicKey, 3, privatekeymodulus, 0, 128);
    			
    		}else{
    			return "Error not found";
    		}
    	}
//        byte [] privatekeymodulus = Base64.getDecoder().decode(base64privatemodulus);
//        byte [] publickeyexponent = Base64.getDecoder().decode(base64publicexponent);

        //getting the Symetric key K
        tmpData data = optionalData.get();
        byte [] Kaes = Base64.getDecoder().decode(data.getK());
        //Kaes = data.getK().getBytes();

        //eventually retrieving the public key form the DB else for now we have it stored right here
        // decrypting and verifying

         byte [] Sign = Base64.getDecoder().decode(sign);
    
         
        //decrypting sign using the aes key
        try {
            Sign = AesCBC.decrypt_CBC(Sign, Kaes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
          
         // dehashing the data using the rsa public key that is stored in the DB
         //for the time being we are not going to use the DB since it is saved in here

        byte  [] SingDecry;
        byte [] SignDecryFinal = new byte [32] ;

        try {
            SingDecry = DH.decrypt(Sign , RSAOps.createPublicKey(privatekeymodulus , publickeyexponent));
            System.arraycopy(SingDecry,128 - 32 ,SignDecryFinal,0,32 );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        

        byte[] toverify = Base64.getDecoder().decode(data.getA());

        valid = Arrays.equals(SignDecryFinal , toverify);
        
        if (valid == true) {
        	// verifying the code pin before adding the card to the online table
        	String pinDec;
        	try{
            	pinDec = new String(AesCBCPad.decrypt_CBC(Base64.getDecoder().decode(pin), Kaes), java.nio.charset.StandardCharsets.UTF_8);
            	// hashing pin after decrypting it
            	pinDec = Util.Hash_256(pinDec);

        	}catch(Exception e){
        		throw new RuntimeException(e);
        	}
        	if(hashPin.equals(pinDec)){
    			online Online = new online();
    			Online.setK(Base64.getEncoder().encodeToString(Kaes));
    			Online.setUID(UID);
    			Online.setTimestamp(System.currentTimeMillis());
    			try{
    				onlinerepository.save(Online);
    			}catch(Exception e){
    			}
                return "OK";
        	}else{
        		return "Pin not valid!";
        	}

        }
        else {
            return "Authentification Denied";
        }
   } 
    else {
        return "Phase 1 not done";
    }
    
}

}