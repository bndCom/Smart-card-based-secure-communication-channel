package application;

import java.time.LocalDate;

public class Doctor {
	private String ID;
    private String lastName;
    private String name;
    private String tel;
    private String lastSession;
    private String record;
    //private long nationalID;
    //private String picture;
    //private String about;
    //private LocalDate cardExpiringDate;
    //private String doctorStatus;
    //private String email;
    
/*
    public long getNationalID() {
		return nationalID;
	}

	public void setNationalID(long nationalID) {
		this.nationalID = nationalID;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public LocalDate getCardExpiringDate() {
		return cardExpiringDate;
	}

	public void setCardExpiringDate(LocalDate cardExpiringDate) {
		this.cardExpiringDate = cardExpiringDate;
	}

	public String getDoctorStatus() {
		return doctorStatus;
	}

	public void setDoctorStatus(String doctorStatus) {
		this.doctorStatus = doctorStatus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
*/
	public Doctor(String ID,String lastName, String name, String tel, String lastSession, String record) {
        this.lastName = lastName;
        this.name = name;
        this.tel = tel;
        this.lastSession = lastSession;
        this.record = record;
        this.ID = ID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLastSession() {
        return lastSession;
    }

    public void setLastSession(String lastSession) {
        this.lastSession = lastSession;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
}

