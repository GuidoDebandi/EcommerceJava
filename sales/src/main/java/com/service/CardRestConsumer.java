package com.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dto.external.in.CardDTO;

@FeignClient(name = "CARDS-SERVICE")
public interface CardRestConsumer {
	
		@GetMapping("/cards")
		public List<CardDTO> getCards();
		
		@GetMapping("/cards/clients/{clientId}")
		public List<CardDTO> getCardByClientId(@PathVariable("clientId") String clientId);
		
		@GetMapping("/cards/{clientId}/{cardNumber}")
		public CardDTO validarTarjetaCliente(@PathVariable("clientId")String clientId,@PathVariable("cardNumber")String cardNumber);
}
