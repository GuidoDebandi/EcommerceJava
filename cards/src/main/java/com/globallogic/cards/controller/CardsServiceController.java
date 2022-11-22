package com.globallogic.cards.controller;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.consumingwebservice.wsdl.Card;
import com.globallogic.cards.consumingwebservice.SoapCardsClient;
import com.globallogic.cards.dto.CardsDTO;
import com.globallogic.cards.error.exception.WrongCardException;
import com.globallogic.cards.service.impl.CardsServiceImpl;

@RestController
public class CardsServiceController {

	private static Logger logger = Logger.getLogger(CardsServiceController.class);

	@Autowired
	CardsServiceImpl cardsService;
	@Autowired
	SoapCardsClient soapCardsClient;

	// READ - GET
	@RequestMapping(value = { "/cards" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getCards() {
		logger.debug("Se ingreso al metodo getCards");
		Collection<CardsDTO> cards = cardsService.getCards();
		logger.info("Se ingreso al metodo getCards y se encontraron todas las tarjetas");
		return new ResponseEntity<>(cards, HttpStatus.OK);
	}

	// READ - GET
	@RequestMapping(value = { "/cardsAll" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getCardsAll() {
		logger.debug("Se ingreso al metodo getCardsAll");
		logger.info("Se ingreso al metodo getCardsAll y se encontraron todas las tarjetas: ");
		return new ResponseEntity<>(soapCardsClient.getCards(), HttpStatus.OK);
	}

	// Validar tarjeta existente
	@RequestMapping(value = { "/cards/{cardNumber}" }, method = RequestMethod.GET)
	public ResponseEntity<Object> isCardValid(@PathVariable("cardNumber") String cardNumber) throws WrongCardException {
		try {
			logger.debug("Se ingreso al metodo isCardValid");
			String card = cardsService.validarTarjeta(cardNumber);
			logger.info("Se verifico correctamente el numero de tarjeta: " + card);
			return new ResponseEntity<>(card, HttpStatus.OK);
		} catch (WrongCardException e) {
			logger.debug("Se lanzo una WrongCardException");
			throw e;
		}
	}

	// Validar tarjeta perteneciente a un cliente
	@RequestMapping(value = { "/cards/{clientId}/{cardNumber}" }, method = RequestMethod.GET)
	public ResponseEntity<Object> validarTarjetaCliente(@PathVariable("cardNumber") String cardNumber,
													@PathVariable("clientId") String clientId) throws WrongCardException {
		try {
			logger.debug("Se ingreso al metodo isCardClientValid");
			Card card = cardsService.validarTarjetaCliente(clientId,cardNumber);
			logger.info("La tarjeta " + cardNumber + " corresponde al cliente: " + clientId + " y es valida");
			return new ResponseEntity<>(card, HttpStatus.OK);
		} catch (WrongCardException e) {
			logger.debug("Se lanzo una WrongCardException");
			throw e;
		}
	}

	// Retonar tarjeta por id de cliente
	@RequestMapping(value = { "/cards/clients/{clientId}" }, method = RequestMethod.GET)
	public ResponseEntity<Object> returnCardOfClient(@PathVariable("clientId") String clientId)
			throws WrongCardException {
		try {
			logger.debug("Se ingreso al metodo returnCardOfClient");
			List<CardsDTO> cards = cardsService.retonarTarjetaCliente(clientId);
			logger.info("La tarjetas que corresponden al cliente ingresado son: " + cards);
			return new ResponseEntity<>(cards, HttpStatus.OK);
		} catch (WrongCardException e) {
			logger.debug("Se lanzo una WrongCardException");
			throw e;
		}
	}

}
