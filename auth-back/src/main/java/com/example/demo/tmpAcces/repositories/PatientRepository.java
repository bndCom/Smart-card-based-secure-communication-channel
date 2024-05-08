package com.example.demo.tmpAcces.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.tmpAcces.models.Patient;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {


}