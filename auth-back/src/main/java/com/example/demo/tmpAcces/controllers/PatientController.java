package com.example.demo.tmpAcces.controllers;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.security.NoSuchAlgorithmException;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.tmpAcces.models.Patient;
import com.example.demo.tmpAcces.models.PatientDto;
import com.example.demo.tmpAcces.repositories.PatientRepository;
import com.example.demo.tmpAcces.Util;
import com.example.demo.tmpAcces.models.online;
import com.example.demo.tmpAcces.repositories.OnlineRepository;
import com.example.demo.tmpAcces.Util;




@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository patientRepository;
    private final OnlineRepository onlinerepository;

    @Autowired
    public PatientController(PatientRepository patientRepository, OnlineRepository onlinerepository) {
        this.patientRepository = patientRepository;
        this.onlinerepository = onlinerepository;
    }

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<String> addPatient(
            @RequestParam long uid,
            @RequestParam long timestamp,
            @RequestParam String hmac,
            @RequestParam String data
    ) {
        //verify if the user exists in the table , if so get the key else refuse the request
        Optional<online> OptionalData = onlinerepository.findById(uid);

        if (OptionalData.isPresent()) {
            online authentedUser = OptionalData.get();
            String AesKey = authentedUser.getK();


            // we have to validate the request

            try {
                if (!Util.validateRequest(timestamp, AesKey, data, hmac)) {
                    return ResponseEntity.status(401).build();
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException();
            }

            Patient patient = new Patient();
            // decrypt received data
            PatientDto patientDto = new PatientDto();
            try {
                patientDto = Util.JsonToObject(Util.aesByteToJson(Base64.getDecoder().decode(data), AesKey));
            } catch (Exception e) {
                throw new RuntimeException();
            }
            patient.setPatientId(patientDto.getPatientId());
            patient.setFirstName(patientDto.getFirstName());
            patient.setLastName(patientDto.getLastName());
            patient.setDateOfBirth(patientDto.getDateOfBirth());
            patient.setNationalId(patientDto.getNationalId());
            patient.setGender(patientDto.getGender());
            patient.setEmail(patientDto.getEmail());
            patient.setPhoneNumber(patientDto.getPhoneNumber());
            patient.setSessionKey(patientDto.getSessionKey());
            patient.setAddress(patientDto.getAddress());
            // saving patient to the database
            patientRepository.save(patient);
            return ResponseEntity.ok("Patient has been added");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//
//    @GetMapping("/getbyid")
//    public ResponseEntity<Patient> getPatientById(@RequestBody PatientDto patientDto) {
//        long id = patientDto.getPatientId();
//        Optional<Patient> patientOptional = patientRepository.findById(id);
//        return patientOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @PostMapping("/getbyid")
    public ResponseEntity<String > getPatientById(
            @RequestParam long uid,
            @RequestParam long timestamp,
            @RequestParam String hmac,
            @RequestParam String data) {
        Optional<online> OptionalData = onlinerepository.findById(uid);

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
        // decrypting the data and turning it into a valid object
            // all the body has is the id of the parinet that's basically all we need
            // in order to not complicate things we are going to use the same format and
            // basically only use the id
            PatientDto patientDto = new PatientDto();
            try {
                patientDto = Util.JsonToObject(Util.aesByteToJson(Base64.getDecoder().decode(data), AesKey));
            } catch (Exception e) {
                throw new RuntimeException();
            }

        Optional<Patient> patientOptional = patientRepository.findById(patientDto.getPatientId());
            if (patientOptional.isPresent()) {
                boolean done = false;
                Patient patient = patientOptional.get();
                try {
                    String js = Util.patientToJson(patient);
                    String jsEncrypted = Base64.getEncoder().encodeToString(Util.aesJsonToByte(js,AesKey));
                    done = true;
                    return ResponseEntity.ok(jsEncrypted);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }else {
                return ResponseEntity.notFound().build();
            }
    }else {
            return ResponseEntity.notFound().build();
        }}


    @PostMapping("/update")
    public ResponseEntity<String> updatePatient(
            @RequestParam long uid,
            @RequestParam long timestamp,
            @RequestParam String hmac,
            @RequestParam String data
    ){
            // verification de l'usulisateur
        Optional<online> OptionalData = onlinerepository.findById(uid);

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

            PatientDto patientDto = new PatientDto();
            try {
                patientDto = Util.JsonToObject(Util.aesByteToJson(Base64.getDecoder().decode(data), AesKey));
            } catch (Exception e) {
                throw new RuntimeException();
            }
            Optional<Patient> patientOptional = patientRepository.findById(patientDto.getPatientId());
            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
                patient.setFirstName(patientDto.getFirstName());
                patient.setLastName(patientDto.getLastName());
                //patient.setDateOfBirth(patientDto.getDateOfBirth());
                patient.setNationalId(patientDto.getNationalId());
                patient.setGender(patientDto.getGender());
                patient.setEmail(patientDto.getEmail());
                patient.setPhoneNumber(patientDto.getPhoneNumber());
                patient.setSessionKey(patientDto.getSessionKey());
                patient.setAddress(patientDto.getAddress());

                patientRepository.save(patient);
                return ResponseEntity.ok("Patient mis à jour avec succès.");
            } else {
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.status(505).build();
        }


    }

//    @PostMapping("/update")
//    public ResponseEntity<String> updatePatient(@RequestBody PatientDto patientDto){
//
//        Optional<Patient> patientOptional = patientRepository.findById(patientDto.getPatientId());
//        if (patientOptional.isPresent()) {
//            Patient patient = patientOptional.get();
//            patient.setFirstName(patientDto.getFirstName());
//            patient.setLastName(patientDto.getLastName());
//            //patient.setDateOfBirth(patientDto.getDateOfBirth());
//            patient.setNationalId(patientDto.getNationalId());
//            patient.setGender(patientDto.getGender());
//            patient.setEmail(patientDto.getEmail());
//            patient.setPhoneNumber(patientDto.getPhoneNumber());
//            patient.setSessionKey(patientDto.getSessionKey());
//            patient.setAddress(patientDto.getAddress());
//
//            patientRepository.save(patient);
//            return ResponseEntity.ok("Patient mis à jour avec succès.");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//
//
//    }

    @PostMapping("/getall")
    public ResponseEntity<String> getAllPatients(
    		@RequestParam long uid,
    		@RequestParam long timestamp,
    		@RequestParam String hmac
    		) {
        Optional<online> OptionalData = onlinerepository.findById(uid);

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
                 List<Patient> patients = (List<Patient>) patientRepository.findAll();
                String js = Util.listToJson(patients);
//        return ResponseEntity.ok(js);
                try{
        	        return ResponseEntity.ok(Base64.getEncoder().encodeToString(Util.aesJsonToByte(js, AesKey)));
                }catch(Exception e){
        	        throw new RuntimeException();
                }
    }else{
            return ResponseEntity.status(505).build();
        }}


//    @PostMapping("/getall")
//    public ResponseEntity<String> getAllPatients(
//            @RequestParam long uid,
//            @RequestParam long timestamp,
//            @RequestParam String hmac
//    ) {
//
//        try{
//            if(!Util.validateRequest(timestamp, "gFn/XoAfNz0LjSnrsHc3CA==", "", hmac)){
//                return ResponseEntity.status(401).build();
//            }
//        }catch(NoSuchAlgorithmException e){
//            throw new RuntimeException();
//        }
//        List<Patient> patients = (List<Patient>) patientRepository.findAll();
//        String js = Util.listToJson(patients);
////        return ResponseEntity.ok(js);
//        try{
//            return ResponseEntity.ok(Base64.getEncoder().encodeToString(Util.aesJsonToByte(js, "gFn/XoAfNz0LjSnrsHc3CA==")));
//        }catch(Exception e){
//            throw new RuntimeException();
//        }
//    }


    @DeleteMapping("/deletebyid")
    public ResponseEntity<String> deletePatient(
            @RequestParam long uid,
            @RequestParam long timestamp,
            @RequestParam String hmac,
            @RequestParam String data
    ) {
        Optional<online> OptionalData = onlinerepository.findById(uid);

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

            PatientDto patientDto = new PatientDto();
            try {
                patientDto = Util.JsonToObject(Util.aesByteToJson(Base64.getDecoder().decode(data), AesKey));
            } catch (Exception e) {
                throw new RuntimeException();
            }

        if (patientRepository.existsById(patientDto.getPatientId())) {
            patientRepository.deleteById(patientDto.getPatientId());
            return ResponseEntity.ok("Patient supprimé avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }else {
            return ResponseEntity.status(505).build();

        }
    }
}
