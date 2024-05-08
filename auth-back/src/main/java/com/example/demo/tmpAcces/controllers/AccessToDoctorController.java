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

import com.example.demo.tmpAcces.models.AccessToDoctor;
import com.example.demo.tmpAcces.models.AccessToDoctorDto;
import com.example.demo.tmpAcces.repositories.AccessToDoctorRepository;

@RestController
@RequestMapping("/accesses-to-doctors")
public class AccessToDoctorController {

    @Autowired
     AccessToDoctorRepository accessToDoctorRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addAccessToDoctor(@RequestBody AccessToDoctorDto accessToDoctorDto) {
        AccessToDoctor accessToDoctor = new AccessToDoctor();
        accessToDoctor.setDoctorId(accessToDoctorDto.getDoctorId());
        accessToDoctor.setAccessDate(accessToDoctorDto.getAccessDate());
        accessToDoctor.setAccessDuration(accessToDoctorDto.getAccessDuration());

        accessToDoctorRepository.save(accessToDoctor);
        return ResponseEntity.ok("Accès au docteur ajouté avec succès.");
    }

    @GetMapping("/getbyid")
    public ResponseEntity<AccessToDoctor> getAccessToDoctorById(@RequestBody AccessToDoctorDto accessToDoctorDto) {
        long id = accessToDoctorDto.getAccessId();
        Optional<AccessToDoctor> accessToDoctorOptional = accessToDoctorRepository.findById(id);
        return accessToDoctorOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateAccessToDoctor(@RequestBody AccessToDoctorDto accessToDoctorDto) {
        Optional<AccessToDoctor> accessToDoctorOptional = accessToDoctorRepository.findById(accessToDoctorDto.getAccessId());
        if (accessToDoctorOptional.isPresent()) {
            AccessToDoctor accessToDoctor = accessToDoctorOptional.get();
            accessToDoctor.setDoctorId(accessToDoctorDto.getDoctorId());
            accessToDoctor.setAccessDate(accessToDoctorDto.getAccessDate());
            accessToDoctor.setAccessDuration(accessToDoctorDto.getAccessDuration());


            accessToDoctorRepository.save(accessToDoctor);
            return ResponseEntity.ok("Accès au docteur mis à jour avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<AccessToDoctor>> getAllAccessesToDoctors() {
        List<AccessToDoctor> accessToDoctors = (List<AccessToDoctor>) accessToDoctorRepository.findAll();
        return ResponseEntity.ok(accessToDoctors);
    }

    @DeleteMapping("/deletebyid")
    public ResponseEntity<String> deleteAccessToDoctor(@RequestBody AccessToDoctorDto accessToDoctorDto) {
        if (accessToDoctorRepository.existsById(accessToDoctorDto.getAccessId())) {
            accessToDoctorRepository.deleteById(accessToDoctorDto.getAccessId());
            return ResponseEntity.ok("Accès au docteur supprimé avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

