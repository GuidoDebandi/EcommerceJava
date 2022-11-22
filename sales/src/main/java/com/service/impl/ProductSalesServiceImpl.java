package com.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.SalesServiceController;
import com.entity.ProductSales;
import com.repository.ProductSaleRepository;
import com.service.ProductSaleService;

@Service
public class ProductSalesServiceImpl implements ProductSaleService {

	private static Logger logger = Logger.getLogger(SalesServiceController.class);
    
    @Autowired
    private ProductSaleRepository productSalesRepository;

	@Override
	public Collection<ProductSales> getProductSales() {
		return productSalesRepository.findAll();
	}

	@Override
	public List<ProductSales> postProductSale(List<ProductSales> productSales) {	
		return productSalesRepository.saveAll(productSales);
	}

	@Override
	public List<ProductSales> getProductSalesByIdSale(Long idSale) {
		return productSalesRepository.findByIdSale(idSale);
	}

	@Override
	public Map<String,Integer> getMostSoldItems() {
		List<ProductSales> allProductSales=productSalesRepository.findAll();
		Map<String,Integer> quantityReport=new HashMap<String,Integer>();
		
		allProductSales.stream().forEach(prodSale->{
							quantityReport.computeIfPresent(prodSale.getIdProduct(), (idProduct,quantity)->(quantity+prodSale.getQuantity()));
							quantityReport.computeIfAbsent(prodSale.getIdProduct(), (idProduct)->(prodSale.getQuantity()));	
		});
		logger.debug("getMostSoldItems -> se obtuvieron las siguientes cantidades totales: " + quantityReport.toString());
		return quantityReport;
	}
}
