package com.example.demo.tmpAcces.models;
import java.util.Date;

import jakarta.persistence.*;
//import javax.persistence.AccessType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;

@Entity
@Table(name = "ACCESSES_TO_PATIENT")
public class AccessToPatient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_id")
    private Long accessId;


    @Column(name = "patient_id")
    private long patientId;


    @Column(name = "doctor_id")
    private long doctorId;

    @Column(name = "access_date")
    private Date accessDate;

    @Column(name = "access_type")
    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    @Column(name = "access_duration")
    private int accessDuration;

	public AccessToPatient() {

	}

	public Long getAccessId() {
		return accessId;
	}

	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public AccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	public int getAccessDuration() {
		return accessDuration;
	}

	public void setAccessDuration(int accessDuration) {
		this.accessDuration = accessDuration;
	}



}
