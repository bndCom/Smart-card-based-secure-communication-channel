package jsr268gp.sampleclient;

public class ServerError extends Exception{
	private int requestStatus;
	
	public ServerError(int requestStatus){
		this.requestStatus = requestStatus;
	}
	
	public void printError(){
		System.out.println("Error happened in the server with status code: " + this.requestStatus);
	}
}