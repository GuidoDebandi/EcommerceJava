package com.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dto.external.out.ProductReportDTO;
import com.service.impl.SalesServiceImpl;

@SpringBootTest
public class SalesServiceImplTest {

	@Autowired
	SalesServiceImpl service;
	@Test
	void getMostSoldProductsTest() {
		List<ProductReportDTO> mostSoldProducts=service.getMostSoldProducts();
		
		assertThat(!mostSoldProducts.isEmpty());
	}
}
