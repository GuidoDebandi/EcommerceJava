package com.globallogic.cards.error.exception;

public class WrongCardException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message = "Tarjeta no valida";
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public WrongCardException(String message) {
		super();
		this.message = message;
	}

}
