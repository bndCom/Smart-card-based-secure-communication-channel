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
@Table(name = "ADMINS")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADMIN_ID")
    private Long adminId;

    @Column(name = "NATIONAL_ID", unique = true)
    private Long nationalId;

    @Column(name = "FIRST_NAME")
    private byte[] firstName;

    @Column(name = "LAST_NAME")
    private byte[] lastName;

    @Column(name = "PICTURE")
    private byte[] picture;

    @Column(name = "EMAIL")
    private byte[] email;

    @Column(name = "PHONE_NUMBER")
    private byte[] phoneNumber;

    @Column(name = "ADDRESS")
    private byte[] address;

    @Column(name = "HASHED_CODEPIN")
    private String hashedCodepin;

    @Column(name = "CARD_EXPIRING_DATE")
    private Date cardExpiringDate;

    @Column(name = "USER_PUBLIC_KEY")
    private byte[] userPublicKey;

    @Column(name = "SESSION_KEY")
    private byte[] sessionKey;

	public Admin() {

	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Long getNationalId() {
		return nationalId;
	}

	public void setNationalId(Long nationalId) {
		this.nationalId = nationalId;
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

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
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

	public byte[] getAddress() {
		return address;
	}

	public void setAddress(byte[] address) {
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

	public byte[] getUserPublicKey() {
		return userPublicKey;
	}

	public void setUserPublicKey(byte[] userPublicKey) {
		this.userPublicKey = userPublicKey;
	}

	public byte[] getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(byte[] sessionKey) {
		this.sessionKey = sessionKey;
	}


}
