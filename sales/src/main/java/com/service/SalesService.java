package com.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.dto.external.out.ProductReportDTO;
import com.dto.external.out.ReportDTO;
import com.entity.Sales;
import com.error.exception.InvalidOperationDatabaseException;

public interface SalesService {

	public Collection<Sales> getSales();
	
	public Sales createSale(Sales sale);

	public void updateSale(Sales sale);
	
	public void deleteSale(Long id);
	
	public Optional<Sales> getSalesById(Long id) throws InvalidOperationDatabaseException;
	
	public List<Sales> getSalesByMontoTotal(Double montoTotal);
	
	public ReportDTO getSalesByClient(Long  clientId);
	
	public List<ProductReportDTO> getMostSoldProducts();
}
