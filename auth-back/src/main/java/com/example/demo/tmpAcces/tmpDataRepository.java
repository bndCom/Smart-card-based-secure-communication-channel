package com.example.demo.tmpAcces;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.tmpAcces.tmpData;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface tmpDataRepository extends CrudRepository<tmpData, String> {

}