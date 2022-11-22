package com.error.exception;

public class LackOfStockException extends Exception {

	private static final long serialVersionUID = -4273781601549971949L;

	private String message = "Error en stock";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LackOfStockException(String message) {
		super();
		this.message = message;
	}

}
