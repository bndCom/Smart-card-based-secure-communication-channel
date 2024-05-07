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


@Controller

@RequestMapping(path ="/connect")

public class MainController{
    public static final int MODULUS_SIZE = 64;
    @Autowired
    private tmpDataRepository tmpDataRepository;
@PostMapping(path = "/phase1") // map the first part of the request to this method e
    public @ResponseBody Response1 begin (@RequestParam String uid, @RequestParam String p , @RequestParam String a){

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
    
    String base64privateexponent = "CONGv75Ot9SaLtbMX18VPNPuN7N0LORewNZ2BsXHmZUX8alSNJazQP4nts2tZUxlkgGtBCWJPJaNi+zdsttmSXi8p3XSJ3psem8U2Pnz/L/DXnz/atPIsxMXTQsfUbWGRL7a6+7c6S3lTaFKRyriDbq23qQY+RzEjvnZ5B1KwQE=";
    String base64privatemodulus = "pv7MjUneZTwJpaZmnjLVbLWnmyHAnM+ImjeyStkg1XtqLxC5Y0u7R7yHaOjSa5TC0Y8xv5mkj0AoG8X9oYxqDUoMJBzOG7dotXIQg0MaHJBxy5Hya9HJoj1YEpz1nbaYdbZ5XTrWRWSepDJzQOhjNGRWDRYyxdNjs6OTgzirlj8=";
    String base64publicexponent = "AQAB";
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
    entity.setUID(uid);
    entity.setK(Base64.getEncoder().encodeToString(KAes));
    entity.setA(Base64.getEncoder().encodeToString(toBeSigned));
    entity.setTimestamp(1);
    this.tmpDataRepository.save(entity);
    
    String B = Base64.getEncoder().encodeToString(BB);
    String Sign = Base64.getEncoder().encodeToString(sign);
    
    return new Response1(Sign ,B);


}

@Autowired
private tmpDataRepository TmpDataRepository;
private onlineRepository OnlineRepository;
@PostMapping(path = "/phase2")
    public @ResponseBody String phase2(@RequestParam String sign , @RequestParam String UID){

    boolean valid = false;
    Optional<tmpData> optionalData = TmpDataRepository.findById(UID);
    if (optionalData.isPresent()) {
        //We should declare the card public key data here
        String base64privatemodulus = "lrg5VH5V2wZ6KkIRcTsVTCqYeNyzuHJI5MF7ZxRbTLBE/U8UBS0R83y+Z1eil25U/K9Bfw8F5WrMgx94nDh5qncU4DHyLBpBgqls7rijrM5S04tqrZ4r/ZSCS8qTTDm2zADsAZUMkChZ1uRbR5V7gA57i+hF8geStrmayi2KEI8=";
        String base64publicexponent = "AQAB";
        byte [] privatekeymodulus = Base64.getDecoder().decode(base64privatemodulus);
        byte [] publickeyexponent = Base64.getDecoder().decode(base64publicexponent);

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
        
//        if (valid == true) {
//			online Online = new online();
//			Online.setK(Kaes.toString());
//			Online.setUID(UID);
//			Online.setTimestamp(1);
//			OnlineRepository.save(Online);
//            return "OK";
        if(valid){
        	return "OK";
    	} else {
            return "Authentification Denied";
        }
   } 
    else {
        return "Phase 1 not done";
    }
    
}

@GetMapping(path="/hi")
    public @ResponseBody String hello(){
        return "hello";

    }

    @GetMapping(path="/fuckoff")
    public @ResponseBody String foff(){
        return "fuckoff";

    }
}