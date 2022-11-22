package com.globallogic.clients.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.clients.dto.ClientDTO;
import com.globallogic.clients.error.exception.NonExistentDniException;
import com.globallogic.clients.error.exception.NonExistentIdException;
import com.globallogic.clients.service.impl.ClientServiceImpl;

@RefreshScope
@RestController
public class ClientServiceController {

	private static Logger logger = Logger.getLogger(ClientServiceController.class);

	@Autowired
	ClientServiceImpl clientService;

	// READ - GET
	@RequestMapping(value = { "/clients" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getClients() {
		logger.debug("Se ingreso al metodo getClients");
		logger.info("Se ingreso al metodo get clients y se encontraron todos los clientes: ");
		return new ResponseEntity<>(clientService.getClientsAll(), HttpStatus.OK);
	}

	// READ - GET
	@RequestMapping(value = { "/clients/id/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getClientById(@PathVariable("id") Long id) throws NonExistentIdException {
		try {
			logger.debug("Se ingreso al metodo getClientById");
			ClientDTO client = clientService.getClientById(id);
			logger.info("Se ingreso el id:" + id + " y se encontro al cliente " + client.getName() + " "
					+ client.getLastName());
			return new ResponseEntity<>(client, HttpStatus.OK);
		} catch (NonExistentIdException e) {
			logger.info("Se ingreso al metodo getClientsById y no se encontro ningun cliente con ese id.");
			throw e;
		}
	}

	// READ - GET
	@RequestMapping(value = { "/clients/dni/{dni}" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getClientByDni(@Valid @PathVariable("dni") Long dni) throws NonExistentDniException {
		try {
			logger.debug("Se ingreso al metodo getClientByDni");
			ClientDTO client = clientService.getClientByDni(dni);
			logger.info("Se ingreso el dni:" + dni + " y se encontro al cliente: " + client.getName() + " "
					+ client.getLastName());
			return new ResponseEntity<>(client, HttpStatus.OK);
		} catch (NonExistentDniException e) {
			logger.info("Se ingreso al metodo getClientByDni y el dni no coincide con ningun cliente.");
			throw e;
		}
	}

}
