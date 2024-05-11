package com.example.demo.tmpAcces.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Base64;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.Arrays;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.tmpAcces.models.Admin;
import com.example.demo.tmpAcces.models.AdminDto;
import com.example.demo.tmpAcces.models.online;
import com.example.demo.tmpAcces.repositories.AdminRepository;

import com.example.demo.tmpAcces.repositories.OnlineRepository;

import com.example.demo.tmpAcces.Util;
import com.example.demo.tmpAcces.DH;
import com.example.demo.tmpAcces.MainController;
import com.example.demo.tmpAcces.RSAOps;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final  AdminRepository adminRepository;
    private final OnlineRepository onlineRepository;

    @Autowired
    public AdminController(AdminRepository adminRepository, OnlineRepository onlineRepository) {
        this.onlineRepository = onlineRepository;
        this.adminRepository = adminRepository;

    }

    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(
    		@RequestParam long uid,
            @RequestParam long timestamp,
            @RequestParam String hmac,
            @RequestParam String data ){

    	
        Optional<online> OptionalData = onlineRepository.findById(uid);

        if (OptionalData.isPresent()) {
            online authentedUser = OptionalData.get();
            String AesKey = authentedUser.getK();
            // once the user exists and is logged in validate the request
            try {
                if (!Util.validateRequest(timestamp, AesKey, data, hmac)) {
                    return ResponseEntity.status(401).build();
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException();
            }

            AdminDto adminDto = new AdminDto();
            try {
                adminDto = Util.adminDtoToObject(Util.aesByteToJson(Base64.getDecoder().decode(data), AesKey));
            } catch (Exception e) {
                throw new RuntimeException();
            }


            // generating the key parts
            RSAOps rsaOps = null;
            try {
                rsaOps = new RSAOps(1024);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }

            byte[] publicexponent = rsaOps.getPublicKeyExponent();
            byte[] privateKeyExponent = rsaOps.getPrivateKeyExponent();
            byte[] privateKeyModulus = rsaOps.getPublicKeyMod();
            
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("cardPublicMod", Base64.getEncoder().encodeToString(privateKeyModulus));
            map.put("cardPrivateExp", Base64.getEncoder().encodeToString(privateKeyExponent));
            
            map.put("serverPublicExp", MainController.base64publicexponent);
            map.put("serverPublicMod", MainController.base64privatemodulus);

            byte[] publicKey = new byte[publicexponent.length + privateKeyModulus.length];
            System.arraycopy(publicexponent, 0, publicKey, 0, publicexponent.length);
            System.arraycopy(privateKeyModulus, 0, publicKey, publicexponent.length , privateKeyModulus.length);
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey);
    	
	    	try{
	    		
	        // generating the card uid
	        Random random = new Random();
	        long userCardNumber = Math.abs(random.nextLong());
	        Optional<Admin> adminOptional;
	        while (adminRepository.findById(userCardNumber).isPresent()) {
	            // keep generating new card uid until getting unique uid
	        	userCardNumber = Math.abs(random.nextLong());
	        }
	    		
	    	Admin admin = new Admin();
	        admin.setAdminId(userCardNumber);
	    	admin.setNationalId(adminDto.getNationalId());
	        admin.setFirstName(adminDto.getFirstName());
	        admin.setLastName(adminDto.getLastName());
	        admin.setPicture(adminDto.getPicture());
	        admin.setEmail(adminDto.getEmail());
	        admin.setPhoneNumber(adminDto.getPhoneNumber());
	        admin.setAddress(adminDto.getAddress());
	        admin.setHashedCodepin(adminDto.getHashedCodepin());
	        admin.setCardExpiringDate(adminDto.getCardExpiringDate());
	        admin.setUserPublicKey(publicKeyString);
	        admin.setSessionKey(adminDto.getSessionKey());
	
	        adminRepository.save(admin);
	        
	        // adding the card uid
	        map.put("userCardNumber", Base64.getEncoder().encodeToString(DH.longToBytes(userCardNumber)));
	        String js = Util.mapToJsonString(map);
	        return ResponseEntity.ok(Base64.getEncoder().encodeToString(Util.aesJsonToByte(js, AesKey)));
	    	
	    	}catch (Exception e) {
	            throw new RuntimeException(e);
	        }
        }else {
            return ResponseEntity.status(505).build();
        }
    }

    @GetMapping("/getbyid")
    public ResponseEntity<Admin> getAdminById(@RequestBody AdminDto adminDto) {
    	 long id = adminDto.getAdminId();
         Optional<Admin> adminOptional = adminRepository.findById(id);
         return adminOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateAdmin(@RequestBody AdminDto adminDto) {
        Optional<Admin> adminOptional = adminRepository.findById(adminDto.getAdminId());
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            admin.setNationalId(adminDto.getNationalId());
            admin.setFirstName(adminDto.getFirstName());
            admin.setLastName(adminDto.getLastName());
            admin.setPicture(adminDto.getPicture());
            admin.setEmail(adminDto.getEmail());
            admin.setPhoneNumber(adminDto.getPhoneNumber());
            admin.setAddress(adminDto.getAddress());
            admin.setHashedCodepin(adminDto.getHashedCodepin());
            admin.setCardExpiringDate(adminDto.getCardExpiringDate());
            admin.setUserPublicKey(adminDto.getUserPublicKey());
            admin.setSessionKey(adminDto.getSessionKey());

            adminRepository.save(admin);
            return ResponseEntity.ok("Admin mis à jour avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/getall")
//    public ResponseEntity<List<Admin>> getAllAdmins() {
//        List<Admin> admins = (List<Admin>) adminRepository.findAll();
//        return ResponseEntity.ok(admins);
//    }
    
    @PostMapping("/getall")
    public ResponseEntity<String> getAllPatients(
    		@RequestParam long uid,
    		@RequestParam long timestamp,
    		@RequestParam String hmac
    		) {
        Optional<online> OptionalData = onlineRepository.findById(uid);

        if (OptionalData.isPresent()) {
            online authentedUser = OptionalData.get();
            String AesKey = authentedUser.getK();
            // once the user exists and is logged in validate the request
    	    try{
    		if(!Util.validateRequest(timestamp, AesKey, "", hmac)){
        		return ResponseEntity.status(401).build();
        	}
    	    }catch(NoSuchAlgorithmException e){
    		    throw new RuntimeException();
    	    }
                 List<Admin> admins = (List<Admin>) adminRepository.findAll();
                String js = Util.adminListToJson(admins);
//        return ResponseEntity.ok(js);
                try{
        	        return ResponseEntity.ok(Base64.getEncoder().encodeToString(Util.aesJsonToByte(js, AesKey)));
                }catch(Exception e){
        	        throw new RuntimeException();
                }
    }else{
            return ResponseEntity.status(505).build();
        }}
    

    @DeleteMapping("/deletebyid")
    public ResponseEntity<String> deleteAdmin(@RequestBody AdminDto adminDto) {
        if (adminRepository.existsById(adminDto.getAdminId())) {
            adminRepository.deleteById(adminDto.getAdminId());
            return ResponseEntity.ok("Admin supprimé avec succès.");
        } else {
        	return ResponseEntity.notFound().build();
        }
    }



    }