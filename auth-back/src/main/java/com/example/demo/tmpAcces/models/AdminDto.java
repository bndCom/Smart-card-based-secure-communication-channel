package com.example.demo.tmpAcces.models;

import java.sql.Date;

public class AdminDto {
    private Long adminId;
    private Long nationalId;
    private String firstName;
    private String lastName;
    private String picture;
    private String email;
    private String phoneNumber;
    private String address;
    private String hashedCodepin;
    private Date cardExpiringDate;
    private String userPublicKey;
    private String sessionKey;
	public AdminDto() {

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
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
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
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}


}
