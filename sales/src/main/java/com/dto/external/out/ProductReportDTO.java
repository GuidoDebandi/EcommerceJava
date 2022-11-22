package com.dto.external.out;

public class ProductReportDTO {
	private String idProduct;
	private String description;
	private Integer quantity;
	private Double unitPrice;
	public String getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public ProductReportDTO() {
	}
	public ProductReportDTO(String idProduct, String description, Integer quantity, Double unitPrice) {
		this.idProduct = idProduct;
		this.description = description;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}
	@Override
	public String toString() {
		return "ProductReportDTO [idProduct=" + idProduct + ", description=" + description + ", quantity=" + quantity
				+ ", unitPrice=" + unitPrice + "]\n";
	}
	  
	
	
}
