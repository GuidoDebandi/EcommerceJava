package com.globallogic.cards.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.globallogic.cards.error.exception.WrongCardException;


@RestControllerAdvice
public class CardsErrorHandler {

	@ExceptionHandler({ WrongCardException.class })
	public ResponseEntity<Object> handleWrongCardException(WrongCardException e) {
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}
}
