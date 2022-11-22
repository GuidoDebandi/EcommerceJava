package com.dto.external.in;

public class ProductDTO {
	
	private String id;
	private String name;
	
	private Integer stock;
	
	private Integer quantity;
	
	private Double unitPrice;
	
	
	public ProductDTO() {}
	
	

	public ProductDTO(String id,String name, Integer stock, Integer quantity, Double unitPrice) {
		super();
		this.id = id;
		this.name = name;
		this.stock = stock;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", stock=" + stock + ", quantity=" + quantity
				+ ", unitPrice=" + unitPrice + "]";
	}

	
	
}
