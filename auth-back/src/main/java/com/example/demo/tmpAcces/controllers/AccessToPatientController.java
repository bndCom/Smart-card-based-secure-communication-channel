package com.example.demo.tmpAcces.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.tmpAcces.models.AccessToPatient;
import com.example.demo.tmpAcces.models.AccessToPatientDto;
import com.example.demo.tmpAcces.repositories.AccessToPatientRepository;

@RestController
@RequestMapping("/accesses-to-patients")
public class AccessToPatientController {

    @Autowired
    private AccessToPatientRepository accessToPatientRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addAccessToPatient(@RequestBody AccessToPatientDto accessToPatientDto) {
        AccessToPatient accessToPatient = new AccessToPatient();
        accessToPatient.setPatientId(accessToPatientDto.getPatientId());
        accessToPatient.setDoctorId(accessToPatientDto.getDoctorId());
        accessToPatient.setAccessDate(accessToPatientDto.getAccessDate());
        accessToPatient.setAccessType(accessToPatientDto.getAccessType());
        accessToPatient.setAccessDuration(accessToPatientDto.getAccessDuration());

        accessToPatientRepository.save(accessToPatient);
        return ResponseEntity.ok("Accès au patient ajouté avec succès.");
    }

    @GetMapping("/getbyid")
    public ResponseEntity<AccessToPatient> getAccessToPatientById(@RequestBody AccessToPatientDto accessToPatientDto) {
        long id = accessToPatientDto.getAccessId();
        Optional<AccessToPatient> accessToPatientOptional = accessToPatientRepository.findById(id);
        return accessToPatientOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateAccessToPatient(@RequestBody AccessToPatientDto accessToPatientDto) {
        Optional<AccessToPatient> accessToPatientOptional = accessToPatientRepository.findById(accessToPatientDto.getAccessId());
        if (accessToPatientOptional.isPresent()) {
            AccessToPatient accessToPatient = accessToPatientOptional.get();
            accessToPatient.setPatientId(accessToPatientDto.getPatientId());
            accessToPatient.setDoctorId(accessToPatientDto.getDoctorId());
            accessToPatient.setAccessDate(accessToPatientDto.getAccessDate());
            accessToPatient.setAccessType(accessToPatientDto.getAccessType());
            accessToPatient.setAccessDuration(accessToPatientDto.getAccessDuration());

            accessToPatientRepository.save(accessToPatient);
            return ResponseEntity.ok("Accès au patient mis à jour avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<AccessToPatient>> getAllAccessesToPatients() {
        List<AccessToPatient> accessToPatients = (List<AccessToPatient>) accessToPatientRepository.findAll();
        return ResponseEntity.ok(accessToPatients);
    }

    @DeleteMapping("/deletebyid")
    public ResponseEntity<String> deleteAccessToPatient(@RequestBody AccessToPatientDto accessToPatientDto) {
        if (accessToPatientRepository.existsById(accessToPatientDto.getAccessId())) {
            accessToPatientRepository.deleteById(accessToPatientDto.getAccessId());
            return ResponseEntity.ok("Accès au patient supprimé avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
