package jsr268gp.sampleclient;

import java.util.Map;

import javax.smartcardio.CardChannel;

//admin session
public class SessionAdmin extends Session{
	
	public SessionAdmin(CardChannel canal, String url){
		super(canal, url);
	}
	
	public void getPersonalInfo() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	public void getAllDoctors() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	public void getAllPatients() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	// add new user either doctor or patient
	public void addNewUser(Map<String, String> mp) throws Exception{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
//		result = new String("");
//		requestStatus = UtilRequest.sendRequest("POST", UtilRequest.mapToJsonString(mp), this.url, result);
//		if(requestStatus != HttpURLConnection.HTTP_OK){
//			throw new ServerError(requestStatus);
//		}
		
	}
	
	public void modifyUser() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
	public void deleteUser() throws NotAuthenticatedError{
		if(this.K == null){
			throw new NotAuthenticatedError();
		}
	}
	
}
