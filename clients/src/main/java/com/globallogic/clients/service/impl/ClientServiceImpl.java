package com.globallogic.clients.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.clients.dto.ClientDTO;
import com.globallogic.clients.error.exception.NonExistentDniException;
import com.globallogic.clients.error.exception.NonExistentIdException;
import com.globallogic.clients.repository.ClientRepository;
import com.globallogic.clients.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
	ClientRepository clientRepository;

	
	@Override
	public ClientDTO getClientById(Long id) throws NonExistentIdException {
		return clientRepository.findById(id);
	}

	@Override
	public ClientDTO getClientByDni(Long dni) throws NonExistentDniException{
		return clientRepository.findByDni(dni);
	}
	
	@Override
	public List<ClientDTO> getClientsAll() {
		return clientRepository.getAllclients();
	}
}
