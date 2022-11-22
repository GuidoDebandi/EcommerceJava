package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.external.in.CardDTO;
import com.dto.external.in.ClientDTO;
import com.dto.external.in.ProductDTO;
import com.entity.ProductSales;
import com.entity.Sales;
import com.error.exception.InvalidOperationDatabaseException;
import com.error.exception.InvalidPaymentMethodException;
import com.error.exception.LackOfStockException;
import com.service.CardRestConsumer;
import com.service.ClientRestConsumer;
import com.service.ProductRestConsumer;
import com.service.impl.ProductSalesServiceImpl;
import com.service.impl.SalesServiceImpl;


import feign.FeignException;

@RefreshScope
@RestController
public class SalesServiceController {

	private static Logger logger = Logger.getLogger(SalesServiceController.class);

	@Autowired
	SalesServiceImpl salesService;

	@Autowired
	ProductSalesServiceImpl productService;

	@Autowired
	ClientRestConsumer clientConsumer;

	@Autowired
	CardRestConsumer cardConsumer;

	@Autowired
	ProductRestConsumer productConsumer;

	// READ - GET
	@RequestMapping(value = { "/sales" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getSales() {
		logger.debug("Se ingreso al metodo getSales");
		return new ResponseEntity<>(salesService.getSales(), HttpStatus.OK);
	}

	// CREATE - POST
	@RequestMapping(value = { "/sales" }, method = RequestMethod.POST)
	public ResponseEntity<Object> createSale(@RequestBody Sales sale) throws InvalidOperationDatabaseException {
		
		if (sale.getMontoTotal() == null || sale.getMontoTotal() < 0 ) {
			logger.debug("El monto total ingresado es invalido");
			throw new InvalidOperationDatabaseException("Monto total tiene un valor no valido",
					406);
			
		}
		logger.debug("Se ingreso al metodo createSales");
		salesService.createSale(sale);
		return new ResponseEntity<>("La venta fue creada correctamente.", HttpStatus.CREATED);
	}

	// UPDATE - PUT
	@RequestMapping(value = { "/sales" }, method = RequestMethod.PUT)
	public ResponseEntity<Object> updateSale(@RequestBody Sales sale)throws InvalidOperationDatabaseException {
		logger.debug("Estoy en el put" + sale.getMontoTotal());
		if (sale.getMontoTotal() == null || sale.getMontoTotal() < 0 ) {
			logger.debug("El monto total ingresado es invalido");
			throw new InvalidOperationDatabaseException("Monto total tiene un valor no valido",
					406);
		}
		try{
			logger.debug("Se ingreso al metodo updateSales");
			salesService.updateSale(sale);
		}catch(DataIntegrityViolationException e){
			logger.debug(e.getClass());
			throw new InvalidOperationDatabaseException("Uno o mas de los campos poseen valores no validos",
					406);
		}
		return new ResponseEntity<>("La venta fue actualizada correctamente.", HttpStatus.OK);
	}

	// DELETE - DELETE
	@RequestMapping(value = { "/sales/{id}" }, method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteSale(@PathVariable("id") Long id) throws InvalidOperationDatabaseException {
		try {
			logger.debug("Se ingreso al metodo deleteSales");
			salesService.deleteSale(id);
		} catch (DataIntegrityViolationException e) {
			logger.debug(e.getClass());
			throw new InvalidOperationDatabaseException("Hay productos asociados a la venta que se quiere eliminar",
					406);
		}
		logger.debug("Se elimino la venta " + id);
		return new ResponseEntity<>("La venta fue eliminada correctamente.", HttpStatus.OK);
	}

	// Servicio retonado por una venta por ID - GET
	@RequestMapping(value = { "/sales/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getSales(@PathVariable("id") Long id) throws InvalidOperationDatabaseException {
		logger.debug("Se ingreso al metodo getSales por id");
		return new ResponseEntity<>(salesService.getSalesById(id), HttpStatus.OK);
	}

	// READ - GET
	@RequestMapping(value = { "/sales/monto/{montoTotal}" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getSalesByMontoTotal(@PathVariable("montoTotal") Double montoTotal) {
		logger.debug("Se ingreso al metodo getSalesByMontoTotal");
		return new ResponseEntity<>(salesService.getSalesByMontoTotal(montoTotal), HttpStatus.OK);
	}

	// GET - COMM WITH CLIENTS
	@RequestMapping(value = { "/sales/clients" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getClientsFromSales() {
		return new ResponseEntity<>(clientConsumer.getCLients(), HttpStatus.OK);
	}

	// GET
	@RequestMapping(value = { "/sales/listOfProducts" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getProductsByListId(@RequestParam List<String> ids) {

		logger.debug("Se ingreso al metodo getProductsByListId");
		List<ProductDTO> productsList = new ArrayList<ProductDTO>();
		productsList = productConsumer.getProductByListId(ids);
		return new ResponseEntity<>(productsList, HttpStatus.OK);
	}

	// GET - REPORT SALES BY CLIENT DNI
	@RequestMapping(value = { "/sales/report-client/dni/{clientDni}" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getReportSalesByClientDni(@PathVariable("clientDni") Long clientDni) {
		return new ResponseEntity<>(salesService.getSalesByClient(clientConsumer.getClientByDni(clientDni).getId()),
				HttpStatus.OK);
	}

	// GET - REPORT SALES BY CLIENT ID
	@RequestMapping(value = { "/sales/report-client/{clientId}" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getReportSalesByClient(@PathVariable("clientId") Long clientId) {
		return new ResponseEntity<>(salesService.getSalesByClient(clientId), HttpStatus.OK);
	}

	// GET - REPORT MOST SOLD ITEMS
	@RequestMapping(value = { "/sales/report-products" }, method = RequestMethod.GET)
	public ResponseEntity<Object> getReportMostSoldProducts() {
		return new ResponseEntity<>(salesService.getMostSoldProducts(), HttpStatus.OK);
	}

	// POST - REGISTER A SALE
	@RequestMapping(value = { "/sales/sales-with-products/{clientId}/{metodoPago}" }, method = RequestMethod.POST)
	public ResponseEntity<Object> saveSaleWithProducts(@PathVariable("clientId") Long clientId,
			@PathVariable String metodoPago, @RequestBody List<ProductDTO> productDTOList,
			@RequestHeader String nroTarjeta) throws LackOfStockException, InvalidPaymentMethodException {

		if (!(metodoPago.equalsIgnoreCase("EFVO") || metodoPago.equalsIgnoreCase("Tarjeta"))) {
			throw new InvalidPaymentMethodException(
					"El metodo de pago ingresado no es valido.\n utilice EFVO para efectivo o Tarjeta para Tarjeta",
					406);
		}

		List<ProductSales> listaGuardadaADevolver = new ArrayList<ProductSales>();

		CardDTO card = new CardDTO();
		ClientDTO client = clientConsumer.getCLientById(clientId);
		logger.debug("Registracion de venta-> Se obtuvo el cliente del modulo Clients");

		Double montoTotal = 0.0;

		if (metodoPago.equalsIgnoreCase("TARJETA")) {
			card = cardConsumer.validarTarjetaCliente(String.valueOf(clientId), nroTarjeta);
		}

		for (ProductDTO product : productDTOList) {
			ProductDTO productFromDB = productConsumer.getProductById(product.getId());

			if (product.getQuantity() > productFromDB.getStock() || product.getQuantity() < 1) {
				String badProduct = product.getQuantity() > 0 ? "Error en compra: \n" + product.getName() + ":"
						+ " No cuenta con el stock suficiente para el pedido " + "\n" : "Quatity Invalido";
				throw new LackOfStockException(badProduct);
			}
			montoTotal = montoTotal + (productFromDB.getUnitPrice() * product.getQuantity());
		}
		logger.debug("Registracion de venta-> se calculo el monto total, es : " + montoTotal);

		Sales sale = new Sales();
		sale.setFechaDeVenta(new Date());
		sale.setIdCliente(client.getId());
		if (card != null) {
			sale.setIdMetodoDePago(card.getId());
		}
		sale.setMontoTotal(montoTotal);

		sale.setId(salesService.createSale(sale).getId());

		List<ProductSales> productSalesList = new ArrayList<ProductSales>();
		for (ProductDTO product : productDTOList) {
			ProductSales productSales = new ProductSales();
			productSales.setIdProduct(product.getId());
			productSales.setIdSale(sale.getId());
			productSales.setQuantity(product.getQuantity());
			productSalesList.add(productSales);
		}
		logger.debug("Registracion de venta-> se creo la venta : \n" + sale.toString());
		
		//WORKAROUND para eliminar la venta en caso de que haya un problema en el servicio de products
		//TODO implementar patron SAGA
		try {
			productConsumer.modifyStock(productSalesList);
		}catch(FeignException feignException) {
			salesService.deleteSale(sale.getId());
			throw feignException;
		}
		

		listaGuardadaADevolver = productService.postProductSale(productSalesList);

		logger.debug("Registracion de venta-> se va a devolver: \n" + listaGuardadaADevolver.toString());

		return new ResponseEntity<>(listaGuardadaADevolver, HttpStatus.OK);

	}

}
