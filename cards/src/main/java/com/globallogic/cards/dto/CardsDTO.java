package com.globallogic.cards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public  class CardsDTO {

	private Long id;
	private String cardIssuer;
	private String payMethodCode;
	private String cardNumber;
	private String clientId;
	
	
}
