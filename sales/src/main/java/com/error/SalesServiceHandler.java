package com.error;


import java.sql.SQLIntegrityConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.controller.SalesServiceController;
import com.error.exception.InvalidOperationDatabaseException;
import com.error.exception.InvalidPaymentMethodException;
import com.error.exception.LackOfStockException;
import com.fasterxml.jackson.databind.JsonMappingException;

import feign.FeignException;



@RestControllerAdvice
public class SalesServiceHandler {
	
	private static Logger logger = Logger.getLogger(SalesServiceController.class);
	
	@ExceptionHandler({ InvalidOperationDatabaseException.class, InvalidPaymentMethodException.class })
	public ResponseEntity<Object> handleDatabaseException(InvalidOperationDatabaseException e) {
		logger.error("Se ha lanzado una excepcion de "+e.getClass()+" con el mensaje "+e.getMessage());
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.valueOf(e.getStatus()));
	}
	
	@ExceptionHandler({ NumberFormatException.class })
    public ResponseEntity<Object> handleNumberFormatException() {
        return new ResponseEntity<Object>("Dato no valido", HttpStatus.NOT_ACCEPTABLE);
    }
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccesException(){
		return new ResponseEntity<Object>("Id inexistente o no encontrado",HttpStatus.NOT_ACCEPTABLE);
		
	}
	
	@ExceptionHandler({FeignException.class})
	public ResponseEntity<Object> handleFeignException(FeignException e){
		logger.error("Se lanzo una FeignException");
		return new ResponseEntity<Object>(e.getMessage(),HttpStatus.valueOf(e.status()));
		
	}
	@ExceptionHandler({ LackOfStockException.class })
	public ResponseEntity<Object> handleLackOfStockException(LackOfStockException e) {
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler({ JsonMappingException.class })
	public ResponseEntity<Object> handleLackOfStockException(JsonMappingException e) {
		return new ResponseEntity<Object>("Uno o mas argumentos no son validos para el metodo.", HttpStatus.NOT_ACCEPTABLE);
	}

}
