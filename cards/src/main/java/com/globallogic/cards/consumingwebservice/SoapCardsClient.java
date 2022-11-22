package com.globallogic.cards.consumingwebservice;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.consumingwebservice.wsdl.Cards;

public class SoapCardsClient extends WebServiceGatewaySupport {

	public Cards getCards()	{
		
		JAXBElement<String> jaxbElement = new JAXBElement<String>(new QName("clienttId"), String.class, "99");
		
		Cards response = (Cards) getWebServiceTemplate()
				.marshalSendAndReceive("http://3.211.124.73:8088/mockGetCardsSoap"
						, 
						jaxbElement
						, 
						new SoapActionCallback("http://localhost/")
						);

		
		return (Cards) response;	
	
	}
}
