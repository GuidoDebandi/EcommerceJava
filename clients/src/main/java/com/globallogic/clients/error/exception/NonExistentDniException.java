package com.globallogic.clients.error.exception;

public class NonExistentDniException extends Exception{

	private static final long serialVersionUID = -4273781601549971949L;

	private String message = "Dni no encontrado o inexistente";

	public String getMessage() {
		return message;
	}
	public NonExistentDniException(String message) {
		super();
		this.message = message;
	}
}
