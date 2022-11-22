package com.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.service.impl.ProductSalesServiceImpl;

@SpringBootTest
public class ProductSalesServiceImplTest {

	
	
	@Autowired
	ProductSalesServiceImpl service;
	
	@Test
	void getMostSoldItemOkTest() {
		Map<String,Integer> quantityReport=service.getMostSoldItems();
		
		assertThat(!quantityReport.isEmpty());
	}
}
