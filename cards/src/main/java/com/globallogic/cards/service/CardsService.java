package com.globallogic.cards.service;

import java.util.Collection;
import java.util.List;

import com.example.consumingwebservice.wsdl.Card;
import com.globallogic.cards.dto.CardsDTO;
import com.globallogic.cards.error.exception.WrongCardException;

public interface CardsService {

	public Collection<CardsDTO> getCards();
	
	public String validarTarjeta(String cardNumber) throws WrongCardException;
	
	public Card validarTarjetaCliente(String clientId, String cardNumber ) throws WrongCardException ;
	
	public List<CardsDTO> retonarTarjetaCliente(String clientId)throws WrongCardException;
	
}
