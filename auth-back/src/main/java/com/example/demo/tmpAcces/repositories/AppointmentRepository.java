package com.example.demo.tmpAcces.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.tmpAcces.models.Appointment;
@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

}
