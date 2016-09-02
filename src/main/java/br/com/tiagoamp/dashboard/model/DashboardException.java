package br.com.tiagoamp.dashboard.model;

public class DashboardException extends Exception {

	private static final long serialVersionUID = -68256761796404778L;
	
	private String message;
	
	public DashboardException() {		
	}
	
	public DashboardException(String msg) {
		setMessage(msg);
	}

	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}	

}
