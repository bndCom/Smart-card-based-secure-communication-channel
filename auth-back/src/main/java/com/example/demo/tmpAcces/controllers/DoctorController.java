package com.example.demo.tmpAcces.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.example.demo.tmpAcces.RSAOps;
import com.example.demo.tmpAcces.Util;
import com.example.demo.tmpAcces.models.PatientDto;
import com.example.demo.tmpAcces.models.online;
import com.example.demo.tmpAcces.repositories.PatientRepository;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.tmpAcces.models.Doctor;
import com.example.demo.tmpAcces.models.DoctorDto;
import com.example.demo.tmpAcces.repositories.DoctorRepository;
import com.example.demo.testApp.AesCBCPad ;
import com.example.demo.tmpAcces.repositories.OnlineRepository;


@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final  DoctorRepository doctorRepository;
    private final OnlineRepository onlineRepository;




    @Autowired
    public DoctorController(DoctorRepository doctorRepository, OnlineRepository onlineRepository) {
        this.onlineRepository = onlineRepository;
        this.doctorRepository = doctorRepository;

    }

    @PostMapping("/add")
    public ResponseEntity<String> addDoctor(@RequestParam long uid,
                                            @RequestParam long timestamp,
                                            @RequestParam String hmac,
                                            @RequestParam String data) {

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

            DoctorDto doctorDto = new DoctorDto();
            try {
                doctorDto = Util.doctorDtoToObject(Util.aesByteToJson(Base64.getDecoder().decode(data), AesKey));
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
//        String publicexponentString = Base64.getEncoder().encodeToString(publicexponent);
//        String privateKeyExponentString = Base64.getEncoder().encodeToString(privateKeyExponent);
//        String privateKeyMod = Base64.getEncoder().encodeToString(privateKeyModulus);

            // creating the public key and the private key
            //**********************************************

            // key = exponent + modulus what ever the key is

            byte[] privateKey = new byte[privateKeyExponent.length + privateKeyModulus.length];
            System.arraycopy(privateKeyExponent, 0, privateKey, 0, privateKeyExponent.length);
            System.arraycopy(privateKeyModulus, 0, privateKey, privateKeyExponent.length , privateKeyModulus.length);
            //based on the documentation we have created the private key
            // turn it to base64 and store it in the DB table


            // creating the public key
            // public key = exponent + modulus

            byte[] publicKey = new byte[publicexponent.length + privateKeyModulus.length];
            System.arraycopy(publicexponent, 0, publicKey, 0, publicexponent.length);
            System.arraycopy(privateKeyModulus, 0, publicKey, publicexponent.length , privateKeyModulus.length);
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey);

            // encrypting with aes and then byte array to send to user side

            try {
                byte[] privateKeyEncrypted = AesCBCPad.encrypt_CBC(privateKey, Base64.getDecoder().decode(AesKey));
                String privatekeyEncryptedString = Base64.getEncoder().encodeToString(privateKeyEncrypted);

                Doctor doctor = new Doctor();
                //doctor.setDoctorId(doctorDto.getDoctorId());
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
                doctor.setUserPublicKey(publicKeyString);

                doctorRepository.save(doctor);
                return ResponseEntity.ok(privatekeyEncryptedString);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // generating the Base64


        }else {
            return ResponseEntity.status(505).build();
        }
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
