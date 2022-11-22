package com.error.exception;



public class InvalidOperationDatabaseException extends Exception{
	
	
	private static final long serialVersionUID = -4273781601549971949L;
	
	private String message = "Operacion no valida sobre la base de datos";
	private int status;

	public String getMessage() {
		return message;
	}
	
	public int getStatus() {
		return status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public InvalidOperationDatabaseException(String message,int status) {
		super();
		this.status = status;
		this.message = message;
	}

	public InvalidOperationDatabaseException() {
		super();
	}
	
	

}
