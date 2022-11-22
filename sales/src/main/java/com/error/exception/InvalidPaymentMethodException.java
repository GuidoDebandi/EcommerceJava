package com.error.exception;

public class InvalidPaymentMethodException extends InvalidOperationDatabaseException {
	private static final long serialVersionUID = -4273781601549971949L;

	private String message = "Metodo de pago no valido";
	private int status = 406;

	public String getMessage() {
		return message;
	}
	
	public int getStatus() {
		return status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	public InvalidPaymentMethodException(String message,int status) {
		super();
		this.status = status;
		this.message = message;
	}
	
}
