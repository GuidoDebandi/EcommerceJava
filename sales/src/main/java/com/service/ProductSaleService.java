package com.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.entity.ProductSales;

public interface ProductSaleService {
		
	public Collection<ProductSales> getProductSales();
	
	public List<ProductSales> postProductSale(List<ProductSales> productSales);
	
	public List<ProductSales> getProductSalesByIdSale(Long idSale);
	
	
	//devuelvo un map porque es todo lo que necesito de ProductSaleService, asi no agrego acomplamiento
	public Map<String,Integer> getMostSoldItems();
	

}
