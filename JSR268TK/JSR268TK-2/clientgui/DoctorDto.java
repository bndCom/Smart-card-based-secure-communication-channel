package clientgui;

import java.sql.Date;

public class DoctorDto {
    private Long doctorId;
    private String firstName;
    private String lastName;
    private int gender;
    private String picture;
    private Long nationalId;
    private String about;
    private String email;
    private String phoneNumber;
    private String address;
    private String hashedCodepin;
    private Date cardExpiringDate;
    private String doctorStatus;
	public DoctorDto() {

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

	public String getDoctorStatus() {
		return doctorStatus;
	}
	public void setDoctorStatus(String doctorStatus) {
		this.doctorStatus = doctorStatus;
	}


}
