package com.example.demo.tmpAcces.models;

import java.sql.Date;

public class AccessToDoctorDto {
    private Long accessId;
    private Long doctorId;
    private Date accessDate;
    private int accessDuration;
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

