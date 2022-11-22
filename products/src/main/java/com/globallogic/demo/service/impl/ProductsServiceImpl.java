package com.globallogic.demo.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.demo.controller.ProductsServiceController;
import com.globallogic.demo.dto.external.in.ProductSalesDTO;
import com.globallogic.demo.dto.external.out.ProductDTO;
import com.globallogic.demo.entity.Product;
import com.globallogic.demo.entity.operator.StockOperator;
import com.globallogic.demo.error.exception.LackOfStockException;
import com.globallogic.demo.error.exception.NonExistentIdException;
import com.globallogic.demo.repository.ProductRepository;
import com.globallogic.demo.service.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {
	
	Logger logger=LoggerFactory.getLogger(ProductsServiceController.class);
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public Collection<Product> getProducts() {
		return productRepository.findAll();

	}

	@Override
	public Product getProductById(String id) throws NonExistentIdException {
		validateIdProduct(id);
		logger.debug("Se ha encontrado el producto buscado");
		return productRepository.findById(id).get();
	}

	public void validateIdProduct(String id) throws NonExistentIdException {
		Optional<Product> product = productRepository.findById(id);
		if (product.isEmpty()){
			logger.debug("No se ha encontrado el producto buscado");
			throw new NonExistentIdException("Producto Inexistente");
		}
	}

	@Override
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(String id) throws NonExistentIdException {
		validateIdProduct(id);
		logger.debug("Se ha encontrado el producto ha eliminar");
		productRepository.deleteById(id);
	}

	public List<Product> searchProductsByDetails(List<ProductSalesDTO> listaProductSales) {
		Collection<Product> products = this.getProducts();
		
		List<Product>exactProducts= products.stream()
				.filter(prod -> listaProductSales.stream().anyMatch(det -> det.getIdProduct().equals(prod.getId())))
				.collect(Collectors.toList());
		
		logger.debug("Se filtraron los productos por los detalles:" + exactProducts.toString());
		
		return exactProducts;
	}

	@Override
	public void modifyStock(List<ProductSalesDTO> listaProductSales, StockOperator operator) throws LackOfStockException{

			List<Product> exactProducts = searchProductsByDetails(listaProductSales);
			
			validateStock(listaProductSales, exactProducts, operator);
			
			listaProductSales.stream().forEach(det -> exactProducts.stream()
					.filter(prod -> prod.getId().equals(det.getIdProduct())).forEach(prod -> {
						prod.setStock(operator.calculateStock(prod.getStock(), det.getQuantity()));
						this.updateProduct(prod);
						logger.debug("Se ha modificado el stock del producto "+prod.getName());
					}));
			
			
			logger.debug("Se ha modificado el stock de los productos.");
			
	}

	public void validateStock(List<ProductSalesDTO> listaProductSales, List<Product> exactProducts, StockOperator operator) throws LackOfStockException {
		List<ProductSalesDTO> erroresDTO = listaProductSales.stream()
				.filter(det -> exactProducts.stream().noneMatch(prod -> det.getIdProduct().equals(prod.getId()))
						|| det.getQuantity() < 0)
				.collect(Collectors.toList());
		logger.debug("Se encontraron los siguientes errores en los detalles" + erroresDTO.toString());

		List<Product> erroresProduct = exactProducts.stream()
				.filter(prod -> listaProductSales.parallelStream().anyMatch(
						det -> det.getIdProduct().equals(prod.getId()) && checkQuantity(prod.getStock(), det.getQuantity(), operator)))
				.collect(Collectors.toList());
		
		logger.debug("Se encontraron los siguientes errores en los productos" + erroresProduct.toString());

		if (erroresProduct.size() > 0 || erroresDTO.size() > 0) {
			String badProducts = "No se ha podido efectuar el descuento de stock por los siguientes productos:\n";

			for (Product prod : erroresProduct) {
				badProducts += prod.getName() + ":" + " No cuenta con el stock suficiente para el pedido " + "\n";
			}
			for (ProductSalesDTO det : erroresDTO) {
				String detError = det.getQuantity() < 0 ? "Quantity no valida para este metodo"
						: "idProduct inexistente";

				badProducts += det.toString() + ":" + detError + "\n";
			}
			throw new LackOfStockException(badProducts);
		}

	}
	public boolean checkQuantity(Integer stock, Integer quantity, StockOperator operator) {
		switch (operator.getIndicator()) {
		case "+":
			logger.debug("Se reconocio al operador como sumar");
			return  (stock + quantity<0);
		case "-":
			logger.debug("Se reconocio al operador como sumar");
			return (stock - quantity<0);
		default:
			logger.debug("No se reconocio al operador");
			return false;
		}
	}
	
	@Override
	public List<ProductDTO> getListofProducts(List<String> ids) throws NonExistentIdException  {
		
		Collection<Product> productsByIds= (Collection<Product>) productRepository.findAllById(ids);
		List<ProductDTO> prodListDTO = new ArrayList<ProductDTO>();
		for(Product p : productsByIds) {
			ProductDTO pDTO = new ProductDTO();
			pDTO.setId(p.getId());
			pDTO.setName(p.getName());
			pDTO.setStock(p.getStock());
			pDTO.setUnitPrice(p.getUnitPrice());
			prodListDTO.add(pDTO);
		}
		if(productsByIds.size()<((List<String>)ids).size()) {
			throw new NonExistentIdException("Uno o mas elementos no fueron encontrados en la BD");
		}
		return prodListDTO;
	}
}
