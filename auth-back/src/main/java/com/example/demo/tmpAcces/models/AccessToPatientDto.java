package com.example.demo.tmpAcces.models;

import java.sql.Date;

import jakarta.persistence.*;
//import javax.persistence.AccessType;

public class AccessToPatientDto {
    private Long accessId;
    private Long patientId;
    private Long doctorId;
    private Date accessDate;
    private AccessType accessType;
    private int accessDuration;
	public Long getAccessId() {
		return accessId;
	}
	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
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
