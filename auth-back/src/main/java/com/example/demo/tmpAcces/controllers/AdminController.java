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

import com.example.demo.tmpAcces.models.Admin;
import com.example.demo.tmpAcces.models.AdminDto;
import com.example.demo.tmpAcces.repositories.AdminRepository;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(@RequestBody AdminDto adminDto) {
        Admin admin = new Admin();
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
        return ResponseEntity.ok("Admin ajouté avec succès.");
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

    @GetMapping("/getall")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = (List<Admin>) adminRepository.findAll();
        return ResponseEntity.ok(admins);
    }

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