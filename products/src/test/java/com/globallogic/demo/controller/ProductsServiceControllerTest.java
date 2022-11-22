package com.globallogic.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.globallogic.demo.dto.external.in.ProductSalesDTO;
import com.globallogic.demo.entity.Product;
import com.globallogic.demo.entity.operator.StockOperator;
import com.globallogic.demo.error.exception.LackOfStockException;
import com.globallogic.demo.error.exception.NonExistentIdException;
import com.globallogic.demo.service.impl.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class ProductsServiceControllerTest {
	@InjectMocks
	ProductsServiceController controller;
	
	@Mock
	ProductsServiceImpl service;
	
	@Autowired
	ProductsServiceImpl serviceAutowired;

	Collection<Product> allProducts = new ArrayList<Product>();
	Product dto1 = new Product("descripcion 1","dto1",20.0,10);
	Product dto2 = new Product("descripcion 2","dto2",30.0,20);
	Product dto3 = new Product("descripcion 3","dto3",40.0,30);

	@Test
	public void getAllProductsTestOk() {

		MockHttpServletRequest request = new MockHttpServletRequest();
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	     
	    when(service.getProducts()).thenReturn(allProducts);
	
	    
	    ResponseEntity<Object> responseEntity = controller.getProducts();
	     
	    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void getProductsByIdTestOk() throws NonExistentIdException {
		MockHttpServletRequest request = new MockHttpServletRequest();
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	     
	    when(service.getProductById("1L")).thenReturn(dto1);
	     
	    
	    ResponseEntity<Object> responseEntity = controller.getProductById("1L");
	     
	    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		
	}
	
	@Test
	public void createProduct() {
		MockHttpServletRequest request = new MockHttpServletRequest();
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	    
	    when(service.createProduct(dto1)).thenReturn(dto1);
	    
	    ResponseEntity<Object> responseEntity = controller.createProduct(dto1);
	    
	    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
	}
	
	@Test
	public void deleteProduct() throws NonExistentIdException {
		MockHttpServletRequest request = new MockHttpServletRequest();
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	    

	    
	    ResponseEntity<Object> responseEntity = controller.deleteProduct("dto1");
	    
	    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	@Test
	public void updateProduct() {
		MockHttpServletRequest request = new MockHttpServletRequest();
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
	    when(service.updateProduct(dto1)).thenReturn(dto1);
	    
	    ResponseEntity<Object> responseEntity = controller.updateProduct(dto1);
	    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void reduceStockFromProducts() throws LackOfStockException{
		MockHttpServletRequest request = new MockHttpServletRequest();
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	    
	    List<ProductSalesDTO> ListProduct = new ArrayList<ProductSalesDTO>();
	    
	    service.modifyStock(ListProduct, StockOperator.DESCONTAR);
	    
		ResponseEntity<Object> responseEntity = controller.reduceStockFromProducts(ListProduct);
	    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	@Test
	public void addStockFromProducts() throws LackOfStockException {
		MockHttpServletRequest request = new MockHttpServletRequest();
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	    
	    List<ProductSalesDTO> ListProduct = new ArrayList<ProductSalesDTO>();
	    
	    service.modifyStock(ListProduct, StockOperator.SUMAR);
	    
	    ResponseEntity<Object> responseEntity = controller.addStockFromProducts(ListProduct);
	    assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
}









