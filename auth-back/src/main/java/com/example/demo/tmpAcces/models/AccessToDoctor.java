package com.example.demo.tmpAcces.models;
import java.util.Date;

import jakarta.persistence.*;
//import jakarta.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
	
@Entity
@Table(name = "ACCESSES_TO_DOCOTR")
public class AccessToDoctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "access_id")
    private Long accessId;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "access_date")
    private Date accessDate;

    @Column(name = "access_duration")
    private int accessDuration;

	public AccessToDoctor() {

	}

	public Long getAccessId() {
		return accessId;
	}

	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public int getAccessDuration() {
		return accessDuration;
	}

	public void setAccessDuration(int accessDuration) {
		this.accessDuration = accessDuration;
	}


}
