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
@Table(name = "DOCTORS")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCTOR_ID")
    private Long doctorId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "GENDER")
    private int gender;

    @Column(name = "PICTURE")
    private String picture;

    @Column(name = "NATIONAL_ID", unique = true, nullable = false)
    private Long nationalId;

    @Column(name = "ABOUT", columnDefinition = "TEXT")
    private String about;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "HASHED_CODEPIN", nullable = false)
    private String hashedCodepin;

    @Column(name = "CARD_EXPIRING_DATE", nullable = false)
    private Date cardExpiringDate;

    @Column(name = "USER_PUBLIC_KEY")
    private String userPublicKey;

    @Column(name = "DOCTOR_STATUS")
    private String doctorStatus;

	public Doctor() {

	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Long getNationalId() {
		return nationalId;
	}

	public void setNationalId(Long nationalId) {
		this.nationalId = nationalId;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHashedCodepin() {
		return hashedCodepin;
	}

	public void setHashedCodepin(String hashedCodepin) {
		this.hashedCodepin = hashedCodepin;
	}

	public Date getCardExpiringDate() {
		return cardExpiringDate;
	}

	public void setCardExpiringDate(Date cardExpiringDate) {
		this.cardExpiringDate = cardExpiringDate;
	}

	public String getUserPublicKey() {
		return userPublicKey;
	}

	public void setUserPublicKey(String userPublicKey) {
		this.userPublicKey = userPublicKey;
	}

	public String getDoctorStatus() {
		return doctorStatus;
	}

	public void setDoctorStatus(String doctorStatus) {
		this.doctorStatus = doctorStatus;
	}


}
