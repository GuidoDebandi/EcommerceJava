package com.globallogic.cards.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.consumingwebservice.wsdl.Card;
import com.example.consumingwebservice.wsdl.Cards;
import com.globallogic.cards.consumingwebservice.SoapCardsClient;
import com.globallogic.cards.dto.CardsDTO;
import com.globallogic.cards.error.exception.WrongCardException;
import com.globallogic.cards.service.CardsService;

@Service
public class CardsServiceImpl implements CardsService {

	@Autowired
	private SoapCardsClient soapCardsClient;

	@Override
	public Collection<CardsDTO> getCards() {

		Cards response = soapCardsClient.getCards();

		List<CardsDTO> listDto = new ArrayList<CardsDTO>();

		for (Card card : response.getCard()) {
			CardsDTO dto = new CardsDTO();
			dto.setCardIssuer(card.getCardIssuer());
			dto.setCardNumber(card.getCardNumber());
			dto.setId(Long.valueOf(card.getId()));
			dto.setClientId(card.getClientId());
			card.setClientId(card.getClientId());
			listDto.add(dto);
		}
		return listDto;
	}

	@Override
	public String validarTarjeta(String cardNumber) throws WrongCardException {

		Cards response = soapCardsClient.getCards();

		String mensajeRetonar = "Tarjeta no valida";
		
		for (Card elementoCard : response.getCard()) {
			if (elementoCard.getCardNumber().equalsIgnoreCase(cardNumber)) {
				mensajeRetonar = "Tarjeta valida";
				return mensajeRetonar;
			} 
		}
		throw new WrongCardException(mensajeRetonar);
	}
	
	@Override
	public Card validarTarjetaCliente(String clientId, String cardNumber) throws WrongCardException {
		
		Cards response = soapCardsClient.getCards();
		
		String mensajeRetonar = "La tarjeta no corresponde al cliente";
		
		for (Card elementoCard : response.getCard()) {
			if (elementoCard.getClientId().equalsIgnoreCase(clientId)
					&& elementoCard.getCardNumber().equalsIgnoreCase(cardNumber)) {
				mensajeRetonar = "La tarjeta corresponde al cliente";
				return elementoCard;
			} 
		}
		throw new WrongCardException(mensajeRetonar);
	}
	
	@Override
	public List<CardsDTO> retonarTarjetaCliente(String clientId) throws WrongCardException{
		
		//Cards response = soapCardsClient.getCards();
		Collection<CardsDTO> listaCards = getCards();
		List<CardsDTO> clientCards=new ArrayList<CardsDTO> ();
		String mensajeRetonar = "No hay tarjeta existente para este cliente";
		
		for (CardsDTO elementoCard : listaCards) {
			if (elementoCard.getClientId().equalsIgnoreCase(clientId)) {	
				clientCards.add(elementoCard);
			} 
	}
		return clientCards;
	}
}


