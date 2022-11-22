package com.globallogic.clients.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.globallogic.clients.dto.ClientDTO;

@FeignClient(name = "CLIENT-SERVICE", url = "http://localhost:9200")
public interface ClientRestConsumer {
		@GetMapping("/clients")
		public List<ClientDTO> getClients();	
}
