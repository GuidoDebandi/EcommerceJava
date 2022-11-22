package com.controller;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.controller.ProductSaleController;
import com.dto.external.in.ProductDTO;
import com.entity.ProductSales;
import com.service.ProductRestConsumer;
import com.service.impl.ProductSalesServiceImpl;

@SpringBootTest
public class ProductSaleControllerTest {
	@InjectMocks
	ProductSaleController controller;
	
	@Mock
	ProductRestConsumer consumer;
	
	@Mock
	ProductSalesServiceImpl service;
	
	Collection<ProductSales> allProductSales = new ArrayList<ProductSales>();
	List<ProductDTO> allProducts = new ArrayList<>();
	
	
	@Test
	public void getProductSalesTestOk() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(service.getProductSales()).thenReturn(allProductSales);

		ResponseEntity<Object> responseEntity = controller.getProductSales();

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}	
	
	@Test
	public void getProductBySaleTestOk() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(consumer.getProducts()).thenReturn(allProducts);
		
		ResponseEntity<Object> responseEntity = controller.getProductsBySale();
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void createProductSaleTestOk() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(consumer.getProducts()).thenReturn(allProducts);
		
		ResponseEntity<Object> responseEntity = controller.createProductSale(null);
	}
	
	
	
}	