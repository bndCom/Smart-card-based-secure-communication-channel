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

import com.example.demo.tmpAcces.models.Doctor;
import com.example.demo.tmpAcces.models.DoctorDto;
import com.example.demo.tmpAcces.repositories.DoctorRepository;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addDoctor(@RequestBody DoctorDto doctorDto) {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(doctorDto.getDoctorId());
        doctor.setFirstName(doctorDto.getFirstName());
        doctor.setLastName(doctorDto.getLastName());
        doctor.setGender(doctorDto.getGender());
        doctor.setPicture(doctorDto.getPicture());
        doctor.setNationalId(doctorDto.getNationalId());
        doctor.setAbout(doctorDto.getAbout());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setPhoneNumber(doctorDto.getPhoneNumber());
        doctor.setAddress(doctorDto.getAddress());
        doctor.setHashedCodepin(doctorDto.getHashedCodepin());
        doctor.setCardExpiringDate(doctorDto.getCardExpiringDate());
        doctor.setDoctorStatus(doctorDto.getDoctorStatus());

        doctorRepository.save(doctor);
        return ResponseEntity.ok("Doctor ajouté avec succès.");
    }

    @GetMapping("/getbyid")
    public ResponseEntity<Doctor> getDoctorById(@RequestBody DoctorDto doctorDto) {
        long id = doctorDto.getDoctorId();
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        return doctorOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateDoctor(@RequestBody DoctorDto doctorDto) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorDto.getDoctorId());
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            doctor.setFirstName(doctorDto.getFirstName());
            doctor.setLastName(doctorDto.getLastName());
            doctor.setGender(doctorDto.getGender());
            doctor.setPicture(doctorDto.getPicture());
            doctor.setNationalId(doctorDto.getNationalId());
            doctor.setAbout(doctorDto.getAbout());
            doctor.setEmail(doctorDto.getEmail());
            doctor.setPhoneNumber(doctorDto.getPhoneNumber());
            doctor.setAddress(doctorDto.getAddress());
            doctor.setHashedCodepin(doctorDto.getHashedCodepin());
            doctor.setCardExpiringDate(doctorDto.getCardExpiringDate());
            doctor.setDoctorStatus(doctorDto.getDoctorStatus());

            doctorRepository.save(doctor);
            return ResponseEntity.ok("Doctor mis à jour avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = (List<Doctor>) doctorRepository.findAll();
        return ResponseEntity.ok(doctors);
    }

    @DeleteMapping("/deletebyid")
    public ResponseEntity<String> deleteDoctor(@RequestBody DoctorDto doctorDto) {
        if (doctorRepository.existsById(doctorDto.getDoctorId())) {
            doctorRepository.deleteById(doctorDto.getDoctorId());
            return ResponseEntity.ok("Doctor supprimé avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
