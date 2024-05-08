package com.example.demo.tmpAcces.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.tmpAcces.models.Treatment;
@Repository
public interface TreatmentRepository extends CrudRepository<Treatment, Long> {

}
