package com.globallogic.demo.repository;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.globallogic.demo.entity.Product;
import com.globallogic.demo.error.exception.NonExistentIdException;
import com.globallogic.demo.service.ProductsService;

@SpringBootTest
public class ProductsRepositoryTest {

	
	@Autowired
	ProductRepository repository;
	
	@Autowired 
	ProductsService service;
	
	Product p = new Product();
	
	@Test
	public void deleteProduct() {
		
		p = repository.save(p);
		
		repository.deleteById(p.getId());
		
	    assertThrows(NonExistentIdException.class, ()->service.getProductById(p.getId()));
	}
    
	
	@Test
	public void updateProduct() {
		
		Product dto1 = new Product();
		
		Product p =  repository.save(dto1);
		p.setUnitPrice(1000.0);
	    p = repository.save(p);
	    
	    
	    assertEquals(1000.0,p.getUnitPrice());
	    
	    repository.deleteById(p.getId());
	}
}
