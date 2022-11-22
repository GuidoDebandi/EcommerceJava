package com.globallogic.demo.service;

import java.util.Collection;
import java.util.List;

import com.globallogic.demo.dto.external.in.ProductSalesDTO;
import com.globallogic.demo.dto.external.out.ProductDTO;
import com.globallogic.demo.entity.Product;
import com.globallogic.demo.entity.operator.StockOperator;
import com.globallogic.demo.error.exception.LackOfStockException;
import com.globallogic.demo.error.exception.NonExistentIdException;

public interface ProductsService {

	public Collection<Product> getProducts();

	public Product getProductById(String id)throws NonExistentIdException ;

	public Product createProduct(Product product);

	public Product updateProduct(Product product);

	public void deleteProduct(String id)throws NonExistentIdException;

	public void modifyStock(List<ProductSalesDTO> listaProductSales,StockOperator operator) throws LackOfStockException;
	
	public List<ProductDTO> getListofProducts(List<String> ids)throws NonExistentIdException;
	
	

}
