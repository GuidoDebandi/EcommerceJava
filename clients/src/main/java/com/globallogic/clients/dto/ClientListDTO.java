package com.globallogic.clients.dto;

import java.util.List;

public class ClientListDTO {
	List<ClientDTO> clients;

	public List<ClientDTO> getClients() {
		return clients;
	}

	public void setClients(List<ClientDTO> clients) {
		this.clients = clients;
	}

	public ClientListDTO(List<ClientDTO> clients) {
		super();
		this.clients = clients;
	}

	public ClientListDTO() {
		super();
	}
	
}
