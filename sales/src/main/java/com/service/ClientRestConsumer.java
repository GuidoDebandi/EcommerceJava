package com.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.dto.external.in.ClientDTO;

@FeignClient(name = "CLIENTS-SERVICE")
public interface ClientRestConsumer {
	
	@GetMapping("/clients")
	public List<ClientDTO> getCLients();
	
	@GetMapping("/clients/id/{id}")
	public ClientDTO getCLientById(@PathVariable("id")Long id);
	
	@GetMapping( "/clients/dni/{dni}")
	public ClientDTO getClientByDni(@PathVariable("dni") Long dni);
}
