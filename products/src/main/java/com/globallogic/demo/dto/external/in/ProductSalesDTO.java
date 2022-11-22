package com.globallogic.demo.dto.external.in;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSalesDTO {
	private long idSale;
	private String idProduct;
	@Min(0)
	private Integer quantity;

}
