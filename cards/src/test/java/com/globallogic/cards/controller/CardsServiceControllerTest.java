package com.globallogic.cards.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.consumingwebservice.wsdl.Cards;
import com.globallogic.cards.dto.CardsDTO;
import com.globallogic.cards.error.exception.WrongCardException;
import com.globallogic.cards.service.impl.CardsServiceImpl;

@SpringBootTest
public class CardsServiceControllerTest {
	@InjectMocks
	CardsServiceController controller;

	@Mock
	CardsServiceImpl service;

	Collection<CardsDTO> allCards = new ArrayList<CardsDTO>();
	CardsDTO card1 = new CardsDTO(1L, "Martin Hernandez", "Card", "123456789", "1");
	CardsDTO card2 = new CardsDTO(2L, "Guido Debandi", "Card", "987654321", "2");
	CardsDTO card3 = new CardsDTO(3L, "Rodrigo Sanchis", "Card", null, null);

	@Test
	public void getCardsTestOk() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(service.getCards()).thenReturn(allCards);

		ResponseEntity<Object> responseEntity = controller.getCards();

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

	}

	@Test
	public void isCardValidTestOk() throws WrongCardException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(service.validarTarjeta("123")).thenReturn("Tarjeta Valida");
		
		ResponseEntity<Object> responseEntity = controller.isCardValid("123");
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		
	}

	@Test
	public void isCardValidTestNoOk() throws WrongCardException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(controller.isCardValid("123456")).thenThrow(WrongCardException.class);
		
		assertThrows(WrongCardException.class, ()->controller.isCardValid("123456"));
	}
	
	@Test
	public void isCardClientValidTestOk() throws WrongCardException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(service.getCards()).thenReturn(allCards);
		
		ResponseEntity<Object> responseEntity = controller.validarTarjetaCliente("123","1");
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void returnCardOfClientTestOk() throws WrongCardException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(service.getCards()).thenReturn(allCards);
		
		ResponseEntity<Object> responseEntity = controller.returnCardOfClient("1");
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	
	
	
}
