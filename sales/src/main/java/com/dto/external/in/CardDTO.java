package com.dto.external.in;

public class CardDTO {

	private Long id;
	private String cardIssuer;
	private String payMethodCode;
	private String cardNumber;
	private String clientId;
	
	public CardDTO() {
		
	}
	
	public CardDTO(Long id, String cardIssuer, String payMethodCode, String cardNumber, String clientId) {
		super();
		this.id = id;
		this.cardIssuer = cardIssuer;
		this.payMethodCode = payMethodCode;
		this.cardNumber = cardNumber;
		this.clientId = clientId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardIssuer() {
		return cardIssuer;
	}
	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}
	public String getPayMethodCode() {
		return payMethodCode;
	}
	public void setPayMethodCode(String payMethodCode) {
		this.payMethodCode = payMethodCode;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "CardDTO [id=" + id + ", cardIssuer=" + cardIssuer + ", payMethodCode=" + payMethodCode + ", cardNumber="
				+ cardNumber + ", clientId=" + clientId + "]";
	}
	

	
}
