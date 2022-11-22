package com.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dto.external.in.ProductDTO;
import com.entity.ProductSales;

@FeignClient(name = "PRODUCTS-SERVICE")
public interface ProductRestConsumer { 
	
	@GetMapping("/products")
	public List<ProductDTO> getProducts();
	
	@GetMapping("/products/{id}")
	public ProductDTO getProductById(@PathVariable String id);
	
	@RequestMapping(method = RequestMethod.GET, value={"/products/listProducts"})
	public List<ProductDTO> getProductByListId(@RequestParam List<String> ids);
	
	@PutMapping("/products/stock/descontar")
	public String modifyStock(@RequestBody List<ProductSales> details);

}
