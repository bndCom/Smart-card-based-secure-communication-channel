package com.example.demo.tmpAcces;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class PatientController {

    @Autowired
     PatientRepository patientRepository;

    @PostMapping("/patientsignup")
    public ResponseEntity<String> addPatient(@RequestBody byte[] data) {
        
    	String js;
    	PatientDto patientDto ;
    	try{
    		js = Util.aesByteToJson(data, "gFn/XoAfNz0LjSnrsHc3CA==");
    		patientDto = (PatientDto)Util.JsonToObject(js);
    	}catch(Exception e){
    		throw new RuntimeException();
    	}
    	
    	Patient patient = new Patient();
        patient.setNom(patientDto.getNom());
        patient.setPrenom(patientDto.getPrenom());
        patient.setAge(patientDto.getAge());
        patient.setAdresse(patientDto.getAdresse());
        patient.setNumdoss(patientDto.getNumdoss());
        patient.setNbseances(patientDto.getNbseances());
        patientRepository.save(patient);
        return ResponseEntity.ok("Patient ajouté avec succès.");
    }

    @PostMapping("/deletepatientbyid")
    public ResponseEntity<String> deletePatientById(@RequestBody PatientDto patientDto) {
        long id = patientDto.getId();
        patientRepository.deleteById(id);
        return ResponseEntity.ok("Patient supprimé avec succès.");
    }

    @PostMapping("/findpatientbyid")
    public ResponseEntity<Patient> findPatientById(@RequestBody PatientDto patientDto) {
        long id = patientDto.getId();
        Optional<Patient> patientOptional = patientRepository.findById(id);
        return patientOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getallpatients")
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = (List<Patient>) patientRepository.findAll();
        return ResponseEntity.ok(patients);
    }
    
    @GetMapping("/doesexistbyid")
    public ResponseEntity<String> doesExistById(@RequestBody PatientDto patientDto) {
        if (patientRepository.existsById(patientDto.getId())) {
            return ResponseEntity.ok("Patient with ID " + patientDto.getId() + " exists.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Patient with ID " + patientDto.getId() + " does not exist.");
        }
    }
    
    
}
