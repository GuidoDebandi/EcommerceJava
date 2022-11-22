package com.globallogic.clients.error.exception;

public class NonExistentIdException extends Exception{
	
	
	private static final long serialVersionUID = -4273781601549971949L;
	
	private String message = "Id no encontrado o inexistente";

	public String getMessage() {
		return message;
	}
	public NonExistentIdException(String message) {
		super();
		this.message = message;
	}
	
	

}
