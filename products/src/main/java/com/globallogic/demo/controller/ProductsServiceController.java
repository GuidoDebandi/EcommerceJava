package com.globallogic.demo.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.demo.dto.external.in.ProductSalesDTO;
import com.globallogic.demo.entity.Product;
import com.globallogic.demo.entity.operator.StockOperator;
import com.globallogic.demo.error.exception.LackOfStockException;
import com.globallogic.demo.error.exception.NonExistentIdException;
import com.globallogic.demo.service.impl.ProductsServiceImpl;

@RestController
public class ProductsServiceController {
	
	private static Logger logger = Logger.getLogger(ProductsServiceController.class);


	@Autowired
	ProductsServiceImpl productsService;

	// READ - GET
	@RequestMapping(value = { "/products" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getProducts() {
		logger.debug("Entrando al metodo getProducts");
		Collection<Product> product = productsService.getProducts();
		logger.info("Se encontraron todos los productos.");
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	// READ - GET
	@RequestMapping(value = { "/products/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getProductById(@PathVariable("id") String id)throws NonExistentIdException {
		logger.debug("Entrando al metodo getProductsById");
		Product product = productsService.getProductById(id);
		logger.info("El producto " + product + " corresponde al id " + id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	
	//GET LIST OF PRODUCTS - GET
	@RequestMapping(value = { "/products/listProducts" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getProductsByIds(@RequestParam List<String> ids)throws NonExistentIdException {
		logger.debug("Se accedio al metodo de recuperar una lista de productos");
		return new ResponseEntity<>(productsService.getListofProducts(ids), HttpStatus.OK);
	}

	// CREATE - POST
	@RequestMapping(value = { "/products" }, method = RequestMethod.POST)
	public ResponseEntity<Object> createProduct(@Valid @RequestBody Product product) {
		logger.debug("Entrando al metodo createProducts");
		Product producto = productsService.createProduct(product);
		logger.info("El producto " + product + " fue creado correctamente.");
		return new ResponseEntity<>("El producto fue creado correctamente: " + producto, HttpStatus.CREATED);
	}

	// UPDATE - PUT
	@RequestMapping(value = { "/products" }, method = RequestMethod.PUT)
	public ResponseEntity<Object> updateProduct(@Valid @RequestBody Product product) {
		logger.debug("Entrando al metodo updateProducts");
		Product producto = productsService.updateProduct(product);
		logger.info("El producto " + product + " fue actualizado correctamente.");
		return new ResponseEntity<>("El producto fue actualizado correctamente: " + producto, HttpStatus.OK);
	}

	// DELETE - DELETE
	@RequestMapping(value = { "/products/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteProduct(@PathVariable("id") String id) throws NonExistentIdException{
		logger.debug("Entrando al metodo deleteProducts");
		Product producto = productsService.getProductById(id);
		productsService.deleteProduct(id);
		logger.info("El producto " + producto + " fue eliminado correctamente.");
		return new ResponseEntity<>("Producto eliminado correctamente: " + producto, HttpStatus.OK);
	}

	// REDUCE STOCK - PUT
	@RequestMapping(value = { "/products/stock/descontar" }, method = RequestMethod.PUT)
	public ResponseEntity<Object> reduceStockFromProducts(@Valid @RequestBody List<ProductSalesDTO> listaProductSales)
			throws LackOfStockException {
		logger.debug("Entrando al metodo reduceFromStockProducts");
		productsService.modifyStock(listaProductSales,StockOperator.DESCONTAR);
		logger.info("El stock del producto fue descontado correctamente.");
		return new ResponseEntity<Object>("Stock reducido correctamente.", HttpStatus.OK);
	}
	// ADD STOCK - PUT
	@RequestMapping(value = { "/products/stock/sumar" }, method = RequestMethod.PUT)
	public ResponseEntity<Object> addStockFromProducts(@Valid @RequestBody List<ProductSalesDTO> listaProductSales)
			throws LackOfStockException {
		logger.debug("Entrando al metodo addStockFromProducts");
		productsService.modifyStock(listaProductSales,StockOperator.SUMAR);
		logger.info("El stock del producto fue sumado correctamente.");
		return new ResponseEntity<Object>("Stock agregado correctamente.", HttpStatus.OK);
	}
}
