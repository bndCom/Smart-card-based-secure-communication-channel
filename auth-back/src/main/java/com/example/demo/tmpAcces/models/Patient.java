package com.example.demo.tmpAcces.models;


import java.util.Date;

import jakarta.persistence.*;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;

@Entity
@Table(name = "PATIENTS")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PATIENT_ID")
    private Long patientId;

    @Column(name = "FIRST_NAME")
    private byte[] firstName;

    @Column(name = "LAST_NAME")
    private byte[] lastName;

    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "NATIONAL_ID", unique = true)
    private Long nationalId;

    @Column(name = "GENDER")
    private int gender;

    @Column(name = "EMAIL")
    private byte[] email;

    @Column(name = "PHONE_NUMBER")
    private byte[] phoneNumber;

    @Column(name = "SESSION_KEY")
    private byte[] sessionKey;

    @Column(name = "ADDRESS")
    private byte[] address;

	public Patient() {

	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public byte[] getFirstName() {
		return firstName;
	}

	public void setFirstName(byte[] firstName) {
		this.firstName = firstName;
	}

	public byte[] getLastName() {
		return lastName;
	}

	public void setLastName(byte[] lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Long getNationalId() {
		return nationalId;
	}

	public void setNationalId(Long nationalId) {
		this.nationalId = nationalId;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public byte[] getEmail() {
		return email;
	}

	public void setEmail(byte[] email) {
		this.email = email;
	}

	public byte[] getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(byte[] phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public byte[] getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(byte[] sessionKey) {
		this.sessionKey = sessionKey;
	}

	public byte[] getAddress() {
		return address;
	}

	public void setAddress(byte[] address) {
		this.address = address;
	}



}
