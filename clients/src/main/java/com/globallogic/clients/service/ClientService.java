package com.globallogic.clients.service;


import java.util.List;

import com.globallogic.clients.dto.ClientDTO;
import com.globallogic.clients.error.exception.NonExistentDniException;
import com.globallogic.clients.error.exception.NonExistentIdException;

public interface ClientService {
	
	public ClientDTO getClientById(Long id) throws NonExistentIdException;
	public ClientDTO getClientByDni(Long dni) throws NonExistentDniException;
	public List<ClientDTO> getClientsAll();
}
