package com.globallogic.demo.error;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.globallogic.demo.error.exception.NonExistentIdException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.globallogic.demo.controller.ProductsServiceController;
import com.globallogic.demo.error.exception.LackOfStockException;

@RestControllerAdvice
public class ProductsErrorHandler {
	
	private static Logger logger = Logger.getLogger(ProductsServiceController.class);

	@ExceptionHandler({ LackOfStockException.class })
	public ResponseEntity<Object> handleLackOfStockException(LackOfStockException e) {
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler({ NonExistentIdException.class })
	public ResponseEntity<Object> handleLackOfStockException(NonExistentIdException e) {
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler({ NumberFormatException.class,MethodArgumentNotValidException.class,JsonMappingException.class })
    public ResponseEntity<Object> handleMethodArgumentNotValidException() {
		logger.debug("Se lanzo una NumberFormatException");
        return new ResponseEntity<Object>("Uno o mas argumentos no son validos para el metodo", HttpStatus.NOT_ACCEPTABLE);
    }

}
