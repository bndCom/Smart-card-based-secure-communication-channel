package com.example.demo.tmpAcces.controllers;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.tmpAcces.models.Patient;
import com.example.demo.tmpAcces.models.PatientDto;
import com.example.demo.tmpAcces.repositories.PatientRepository;
import com.example.demo.tmpAcces.Util;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addPatient(
    		@RequestParam long uid,
    		@RequestParam long timestamp,
    		@RequestParam String hmac,
    		@RequestParam String data
    		) {
    	try{
    		String js = Util.aesByteToJson(Base64.getDecoder().decode(data), "gFn/XoAfNz0LjSnrsHc3CA==");
    		return ResponseEntity.ok(js);
    	}catch(Exception e){
    		return ResponseEntity.ok("error");
    	}
//        Patient patient = new Patient();
//        // decrypt received data
//        PatientDto patientDto = new PatientDto();
//    	try{
//    		patientDto = Util.JsonToObject(Util.aesByteToJson(Base64.getDecoder().decode(data), "gFn/XoAfNz0LjSnrsHc3CA=="));
//    	}catch(Exception e){
//    		throw new RuntimeException();
//    	}
//        patient.setPatientId(patientDto.getPatientId());
//        patient.setFirstName(patientDto.getFirstName().getBytes());
//        patient.setLastName(patientDto.getLastName().getBytes());
//        patient.setDateOfBirth(patientDto.getDateOfBirth());
//        patient.setNationalId(patientDto.getNationalId());
//        patient.setGender(patientDto.getGender());
//        patient.setEmail(patientDto.getEmail().getBytes());
//        patient.setPhoneNumber(patientDto.getPhoneNumber().getBytes());
//        patient.setSessionKey(patientDto.getSessionKey().getBytes());
//        patient.setAddress(patientDto.getAddress().getBytes());
//
//        patientRepository.save(patient);
        //return ResponseEntity.ok("Patient ajouté avec succès.");
    }

    @GetMapping("/getbyid")
    public ResponseEntity<Patient> getPatientById(@RequestBody PatientDto patientDto) {
        long id = patientDto.getPatientId();
        Optional<Patient> patientOptional = patientRepository.findById(id);
        return patientOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<String> updatePatient(@RequestBody PatientDto patientDto) {
        Optional<Patient> patientOptional = patientRepository.findById(patientDto.getPatientId());
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            patient.setFirstName(patientDto.getFirstName().getBytes());
            patient.setLastName(patientDto.getLastName().getBytes());
            patient.setDateOfBirth(patientDto.getDateOfBirth());
            patient.setNationalId(patientDto.getNationalId());
            patient.setGender(patientDto.getGender());
            patient.setEmail(patientDto.getEmail().getBytes());
            patient.setPhoneNumber(patientDto.getPhoneNumber().getBytes());
            patient.setSessionKey(patientDto.getSessionKey().getBytes());
            patient.setAddress(patientDto.getAddress().getBytes());

            patientRepository.save(patient);
            return ResponseEntity.ok("Patient mis à jour avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = (List<Patient>) patientRepository.findAll();
        return ResponseEntity.ok(patients);
    }

    @DeleteMapping("/deletebyid")
    public ResponseEntity<String> deletePatient(@RequestBody PatientDto patientDto) {
        if (patientRepository.existsById(patientDto.getPatientId())) {
            patientRepository.deleteById(patientDto.getPatientId());
            return ResponseEntity.ok("Patient supprimé avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
