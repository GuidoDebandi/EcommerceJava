package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTS_SALE")
public class ProductSales {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDPRODUCTSALE")
	private Long id;
	
	@Column(name = "ID_SALE")
	private Long idSale;
	
	@Column(name = "ID_PRODUCT")
	private String idProduct;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	

}