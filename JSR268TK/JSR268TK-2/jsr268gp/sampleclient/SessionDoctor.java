package jsr268gp.sampleclient;

import javax.smartcardio.CardChannel;

//doctor session
public class SessionDoctor extends Session{
	
	public SessionDoctor(CardChannel canal, String url){
		super(canal, url);
	}
	
	public void getPersonalInfo() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	// modify medical record
	public void modifyPatientMedicalRecord() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	// list all patients
	public void getAllPatients() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
		
}
