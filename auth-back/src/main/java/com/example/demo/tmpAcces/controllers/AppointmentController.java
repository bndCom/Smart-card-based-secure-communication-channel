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

import com.example.demo.tmpAcces.models.Appointment;
import com.example.demo.tmpAcces.models.AppointmentDto;
import com.example.demo.tmpAcces.repositories.AppointmentRepository;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addAppointment(@RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        appointment.setPatientId(appointmentDto.getPatientId());
        appointment.setDoctorId(appointmentDto.getDoctorId());
        appointment.setTreatmentId(appointmentDto.getTreatmentId());
        appointment.setAppointmentDateTime(appointmentDto.getAppointmentDateTime());
        appointment.setDuration(appointmentDto.getDuration());
        appointment.setNotes(appointmentDto.getNotes());

        appointmentRepository.save(appointment);
        return ResponseEntity.ok("Rendez-vous ajouté avec succès.");
    }

    @GetMapping("/getbyid")
    public ResponseEntity<Appointment> getAppointmentById(@RequestBody AppointmentDto appointmentDto) {
        long id = appointmentDto.getAppointmentId();
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        return appointmentOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateAppointment(@RequestBody AppointmentDto appointmentDto) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentDto.getAppointmentId());
        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointment.setPatientId(appointmentDto.getPatientId());
            appointment.setDoctorId(appointmentDto.getDoctorId());
            appointment.setTreatmentId(appointmentDto.getTreatmentId());
            appointment.setAppointmentDateTime(appointmentDto.getAppointmentDateTime());
            appointment.setDuration(appointmentDto.getDuration());
            appointment.setNotes(appointmentDto.getNotes());


            appointmentRepository.save(appointment);
            return ResponseEntity.ok("Rendez-vous mis à jour avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = (List<Appointment>) appointmentRepository.findAll();
        return ResponseEntity.ok(appointments);
    }

    @DeleteMapping("/deletebyid")
    public ResponseEntity<String> deleteAppointment(@RequestBody AppointmentDto appointmentDto) {
        if (appointmentRepository.existsById(appointmentDto.getAppointmentId())) {
            appointmentRepository.deleteById(appointmentDto.getAppointmentId());
            return ResponseEntity.ok("Rendez-vous supprimé avec succès.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

