
package com.globallogic.cards.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.consumingwebservice.wsdl.Card;
import com.globallogic.cards.dto.CardsDTO;
import com.globallogic.cards.error.exception.WrongCardException;
import com.globallogic.cards.service.impl.CardsServiceImpl;

@SpringBootTest
public class CardsServiceImplTest {

	@Autowired
	CardsServiceImpl cardsServiceImpl;

	CardsDTO dto1 = new CardsDTO(5L, "Master Card", "null", "5323654125478956", "10");
	CardsDTO dto2 = new CardsDTO(3L, "Master Card", "null", "5323458712364589", "5");

	String mensajeEsperado;

	@Test
	public void validarTarjetaValida() throws WrongCardException {
		mensajeEsperado = "Tarjeta valida";
		assertEquals(mensajeEsperado, cardsServiceImpl.validarTarjeta(dto1.getCardNumber()));
	}

	@Test
	public void validarTarjetaNoValida() {
		assertThrows(WrongCardException.class, () -> cardsServiceImpl.validarTarjeta("123123123123123"));
	}

	@Test
	public void validarTarjetaCliente() throws WrongCardException {
		Card card = new Card();
		card.setId("5");
		card.setCardIssuer("Master Card");
		card.setClientId("10");
		card.setCardNumber("5323654125478956");
		assertThat(card.equals(cardsServiceImpl.validarTarjetaCliente(dto1.getClientId(), dto1.getCardNumber())));
	}

	@Test
	public void validarTarjetaNoCliente() {
		mensajeEsperado = "La tarjeta no corresponde al cliente";
		assertThrows(WrongCardException.class,
				() -> cardsServiceImpl.validarTarjetaCliente(dto1.getClientId(), "123123123123"));
	}

	@Test
	public void retonarTarjetaCliente() throws WrongCardException {
		assertEquals(dto1.toString(), cardsServiceImpl.retonarTarjetaCliente(dto1.getClientId()).get(0).toString());
	}

	@Test
	public void errorAlRetonarTarjetaCliente() throws WrongCardException {
		mensajeEsperado = "No hay tarjeta existente para este cliente";
		assertThat(cardsServiceImpl.retonarTarjetaCliente("123123123123123").isEmpty());
	}
}
