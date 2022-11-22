package com.globallogic.clients.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.globallogic.clients.dto.ClientListDTO;
import com.globallogic.clients.error.exception.NonExistentDniException;
import com.globallogic.clients.error.exception.NonExistentIdException;
import com.globallogic.clients.dto.ClientDTO;

@Repository
public class ClientRepository {

	
	private static final String baseUrl = "https://demo3800346.mockable.io";
	private WebClient consumer = WebClient.create(baseUrl);

	public List<ClientDTO> getAllclients() {

		ClientListDTO listClients = consumer.get().uri("/clients").retrieve().bodyToMono(ClientListDTO.class).block();
		return listClients.getClients();
	}

	public ClientDTO findById(Long id) throws NonExistentIdException{
		List<ClientDTO> allClients = getAllclients();
		for (ClientDTO client : allClients) {
			if (client.getId().equals(id))
				return client;
		}
		throw new NonExistentIdException("El id: " + id +" no se ha encontrado o es inexistente");
		
	}

	public ClientDTO findByDni(Long dni) throws NonExistentDniException {
		List<ClientDTO> allClients = getAllclients();
		for (ClientDTO client : allClients) {
			if (client.getDni().equals(dni))
				return client;
		}
		throw new NonExistentDniException("El dni: " + dni + " no se ha encontrado o es inexistente");
	}

}
