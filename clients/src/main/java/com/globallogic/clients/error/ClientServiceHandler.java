package com.globallogic.clients.error;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.globallogic.clients.controller.ClientServiceController;
import com.globallogic.clients.error.exception.NonExistentDniException;
import com.globallogic.clients.error.exception.NonExistentIdException;

@RestControllerAdvice
public class ClientServiceHandler {
	
	private static Logger logger = Logger.getLogger(ClientServiceController.class);
	
	@ExceptionHandler({ NonExistentIdException.class })
	public ResponseEntity<Object> handleLackOfStockException(NonExistentIdException e) {
		logger.debug("Se lanzo una NonExistentIdException");
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler({ NonExistentDniException.class })
	public ResponseEntity<Object> handleLackOfStockException(NonExistentDniException e) {
		logger.debug("Se lanzo una NonExistentDniException");
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler({ NumberFormatException.class })
    public ResponseEntity<Object> handleMethodArgumentNotValidException() {
		logger.debug("Se lanzo una NumberFormatException");
        return new ResponseEntity<Object>("Dato no valido", HttpStatus.NOT_ACCEPTABLE);
    }
}
