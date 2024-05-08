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

import com.example.demo.tmpAcces.models.Treatment;
import com.example.demo.tmpAcces.models.TreatmentDto;
import com.example.demo.tmpAcces.repositories.TreatmentRepository;

@RestController
@RequestMapping("/treatments")
public class TreatmentController {

    @Autowired
    private TreatmentRepository treatmentRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addTreatment(@RequestBody TreatmentDto treatmentDto) {
        Treatment treatment = new Treatment();
        treatment.setPatientId(treatmentDto.getPatientId());
        treatment.setDoctorId(treatmentDto.getDoctorId());
        treatment.setTreatmentDate(treatmentDto.getTreatmentDate());
        treatment.setTreatmentType(treatmentDto.getTreatmentType());
        treatment.setDescription(treatmentDto.getDescription());

        treatmentRepository.save(treatment);
        return ResponseEntity.ok("Traitement ajouté avec succès.");
    }

    @GetMapping("/getbyid")
    public ResponseEntity<Treatment> getTreatmentById(@RequestBody TreatmentDto treatmentDto) {
        long id = treatmentDto.getTreatmentId();
        Optional<Treatment> treatmentOptional = treatmentRepository.findById(id);
        return treatmentOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateTreatment(@RequestBody TreatmentDto treatmentDto) {
        Optional<Treatment> treatmentOptional = treatmentRepository.findById(treatmentDto.getTreatmentId());
        if (treatmentOptional.isPresent()) {
            Treatment treatment = treatmentOptional.get();
            treatment.setDoctorId(treatmentDto.getDoctorId());
            treatment.setTreatmentDate(treatmentDto.getTreatmentDate());
            treatment.setTreatmentType(treatmentDto.getTreatmentType());
            treatment.setDescription(treatmentDto.getDescription());

            treatmentRepository.save(treatment);
            return ResponseEntity.ok("Traitement mis à jour avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Treatment>> getAllTreatments() {
        List<Treatment> treatments = (List<Treatment>) treatmentRepository.findAll();
        return ResponseEntity.ok(treatments);
    }

    @DeleteMapping("/deletebyid")
    public ResponseEntity<String> deleteTreatment(@RequestBody TreatmentDto treatmentDto) {
        if (treatmentRepository.existsById(treatmentDto.getTreatmentId())) {
            treatmentRepository.deleteById(treatmentDto.getTreatmentId());
            return ResponseEntity.ok("Traitement supprimé avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

