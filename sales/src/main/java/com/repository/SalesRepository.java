package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Sales;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {
	List<Sales> findByMontoTotalLessThanEqual(Double montoTotal);
	
	List<Sales> findByIdCliente(Long idCliente);
}
