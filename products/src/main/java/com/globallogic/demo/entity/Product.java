package com.globallogic.demo.entity;





import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {
	@Id
	private String id;
	@Field(name = "description")
	private String description;
	@Field(name = "name")
	private String name;
	@Field(name = "unitPrice")
	@Min(0)
	@NotNull(message = "unitPrice no puede ser nulo")
	private Double unitPrice;
	@Field(name = "stock")
	@NotNull(message = "stock no puede ser nulo")
	@Min(0)
	private Integer stock;


	@PersistenceConstructor
	public Product(String description, String name, double unitPrice, int stock) {
		super();
		this.description = description;
		this.name = name;
		this.unitPrice = unitPrice;
		this.stock = stock;
	}


}
