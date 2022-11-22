package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.ProductSales;

@Repository
public interface ProductSaleRepository extends JpaRepository<ProductSales, Long> {
	List<ProductSales> findByIdSale(Long IdSale);
	
	//@Query("SELECT  new com.dto.external.out.ProductSaleReportDTO(ps.idProduct as idProduct,sum(ps.quantity) as totalQuantity) FROM ProductSales ps group by idProduct order by sum(ps.quantity) desc")
//	List<ProductReportDTO> findMostSoldItems();
}
