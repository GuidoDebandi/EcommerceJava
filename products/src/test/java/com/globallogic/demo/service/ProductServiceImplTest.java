package com.globallogic.demo.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.globallogic.demo.dto.external.in.ProductSalesDTO;
import com.globallogic.demo.entity.Product;
import com.globallogic.demo.entity.operator.StockOperator;
import com.globallogic.demo.error.exception.LackOfStockException;
import com.globallogic.demo.error.exception.NonExistentIdException;
import com.globallogic.demo.service.impl.ProductsServiceImpl;

@SpringBootTest
public class ProductServiceImplTest {

	@Autowired
	ProductsServiceImpl service;

	Product p = new Product("desc1","p",10.0,10);
	ProductSalesDTO dto1 = new ProductSalesDTO(1, "622b5b18e9d27f7c4330a9c2", 2);
	ProductSalesDTO dto2 = new ProductSalesDTO(1, "623235d053addd3c33fa788f", 5);
	ProductSalesDTO dto3 = new ProductSalesDTO(1, "623332f29986be0866322d4d", 4);

	ProductSalesDTO dto4 = new ProductSalesDTO(1, "622b5b18e9d27f7c4330a9c2", 2);
	ProductSalesDTO dto5 = new ProductSalesDTO(1, "623235d053addd3c33fa788f", 5);
	ProductSalesDTO dto6 = new ProductSalesDTO(1, "623332f29986be0866322d4d", 4);

	List<ProductSalesDTO> productsSales = new ArrayList<ProductSalesDTO>();

	@Test
	public void createProduct() {
		
		service.createProduct(p);
		try {
			assertNotNull(p.getId(), service.getProductById(p.getId()));
		} catch (NonExistentIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteProduct() {
		
		p = service.createProduct(p);
		
		try {
			service.deleteProduct(p.getId());
			assertThrows(NonExistentIdException.class, ()->service.getProductById(p.getId()));
		} catch (NonExistentIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	@Test
	public void searchProductsTest() {
		productsSales.add(dto1);
		productsSales.add(dto2);
		productsSales.add(dto3);

		List<Product> products = new ArrayList<Product>();
		try {
			products.add(service.getProductById(dto1.getIdProduct()));
			products.add(service.getProductById(dto2.getIdProduct()));
			products.add(service.getProductById(dto3.getIdProduct()));
		} catch (NonExistentIdException e) {

		}

		List<Product> exactProducts = service.searchProductsByDetails(productsSales);

		assertEquals(products.toString(), exactProducts.toString());
	}

	@Test
	public void reduceStockTest() throws NonExistentIdException, LackOfStockException {

		productsSales.add(dto1);
		productsSales.add(dto2);
		productsSales.add(dto3);
		Integer stockActual = service.getProductById("622b5b18e9d27f7c4330a9c2").getStock();

		service.modifyStock(productsSales, StockOperator.DESCONTAR);

		assertEquals(stockActual - dto1.getQuantity(), service.getProductById("622b5b18e9d27f7c4330a9c2").getStock());

	}
	
//	TODO add stock validation on Sales module, this will be deprecated
//	@Test
//	@Disabled
//	public void reduceStockNoOKTest() throws NonExistentIdException {
//		Product product = service.getProductById("622b5b18e9d27f7c4330a9c9");
//		ProductSalesDTO dto4 = new ProductSalesDTO(1, "622b5b18e9d27f7c4330a9c9", product.getStock() + 1);
//		productsSales.add(dto1);
//		productsSales.add(dto2);
//		productsSales.add(dto3);
//		productsSales.add(dto4);
//
//		Integer stockActualDTO4 = product.getStock();
//		Integer stockActualDTO1 = service.getProductById("622b5b18e9d27f7c4330a9c2").getStock();
//
//		service.modifyStock(productsSales, StockOperator.DESCONTAR);
//
//		assertEquals(stockActualDTO4, service.getProductById("622b5b18e9d27f7c4330a9c9").getStock(),"Se desconto el producto que tenia error");
//		assertEquals(stockActualDTO1, service.getProductById("622b5b18e9d27f7c4330a9c2").getStock(),"Se desconto el producto que no tenia error");
//
//	}

//	TODO add stock validation on Sales module, this will be deprecated
//	@Test
//	@Disabled
//	public void LackOfStockExceptionTest() throws NonExistentIdException {
//		ProductSalesDTO dto4 = new ProductSalesDTO(1, "622b5b18e9d27f7c4330a9c9",
//				service.getProductById("622b5b18e9d27f7c4330a9c9").getStock() + 1);
//
//		productsSales.add(dto4);
//		assertThrows(LackOfStockException.class, () -> service.validateStock(productsSales,
//				service.searchProductsByDetails(productsSales), StockOperator.DESCONTAR));
//	}

//	TODO add stock validation on Sales module, this will be deprecated
//	@Test
//	@Disabled
//	public void invalidProductIdTest() {
//		ProductSalesDTO dto4 = new ProductSalesDTO(1, "2", 1);
//		productsSales.add(dto1);
//		productsSales.add(dto2);
//		productsSales.add(dto3);
//		productsSales.add(dto4);
//		assertThrows(LackOfStockException.class, () -> service.modifyStock(productsSales, StockOperator.DESCONTAR));
//	}

	@Test
	public void addStockTest() throws NonExistentIdException, LackOfStockException {
		productsSales.add(dto4);
		productsSales.add(dto5);
		productsSales.add(dto6);
		Integer stockActual = service.getProductById("623235d053addd3c33fa788f").getStock();

		service.modifyStock(productsSales, StockOperator.SUMAR);

		assertEquals(stockActual + dto5.getQuantity(), service.getProductById("623235d053addd3c33fa788f").getStock());

	}
}
