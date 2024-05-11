package com.example.demo.tmpAcces.repositories;


import org.springframework.data.repository.CrudRepository;

import com.example.demo.tmpAcces.models.online;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface OnlineRepository extends CrudRepository<online, Long> {

}
