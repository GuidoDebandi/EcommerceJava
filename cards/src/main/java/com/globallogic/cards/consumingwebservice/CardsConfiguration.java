package com.globallogic.cards.consumingwebservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CardsConfiguration {
	
	  @Bean
	  public Jaxb2Marshaller marshaller() {
	    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	    // this package must match the package in the <generatePackage> specified in
	    // pom.xml
	    marshaller.setContextPath("com.example.consumingwebservice.wsdl");
	    return marshaller;
	  }
	  
	  @Bean
	  public SoapCardsClient cardsClient(Jaxb2Marshaller marshaller) {
		SoapCardsClient cards = new SoapCardsClient();
	    cards.setDefaultUri("http://3.211.124.73:8088/mockGetCardsSoap");
	    cards.setMarshaller(marshaller);
	    cards.setUnmarshaller(marshaller);
	    return cards;
	  }
}
