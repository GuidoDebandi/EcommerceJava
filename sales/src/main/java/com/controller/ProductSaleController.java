package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dto.external.in.ProductDTO;
import com.entity.ProductSales;
import com.service.ProductRestConsumer;
import com.service.ProductSaleService;
import com.service.SalesService;


@RestController
public class ProductSaleController {
	
	@Autowired
	ProductSaleService productSaleService;
	
	@Autowired
	SalesService saleService;
	
	@Autowired
	ProductRestConsumer productConsumer;
	
	//READ - GET
	@RequestMapping(value={"/products-sale"}, method = RequestMethod.GET)
	public ResponseEntity<Object> getProductSales(){
		return new ResponseEntity<>(productSaleService.getProductSales(), HttpStatus.OK);
	}
	
	@RequestMapping(value= {"/products-by-sale"}, method = RequestMethod.GET)
	public ResponseEntity<Object> getProductsBySale(){
//		Collection<Sales> salesList = saleService.getSales();
		List<ProductDTO> productDTOList = productConsumer.getProducts();
		
		return new ResponseEntity<>(productDTOList, HttpStatus.OK);
	}
	
	@RequestMapping(value= {"/products-by-sale"}, method = RequestMethod.POST)
	public ResponseEntity<Object> createProductSale(@RequestBody List<ProductSales> productSales){
		return new ResponseEntity<>(productSaleService.postProductSale(productSales), HttpStatus.CREATED);	
	}

}
