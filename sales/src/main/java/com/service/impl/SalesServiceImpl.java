package com.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.SalesServiceController;
import com.dto.external.in.CardDTO;
import com.dto.external.in.ClientDTO;
import com.dto.external.in.ProductDTO;
import com.dto.external.out.ProductReportDTO;
import com.dto.external.out.ReportDTO;
import com.dto.external.out.SaleDTO;
import com.entity.ProductSales;
import com.entity.Sales;
import com.error.exception.InvalidOperationDatabaseException;
import com.repository.SalesRepository;
import com.service.CardRestConsumer;
import com.service.ClientRestConsumer;
import com.service.ProductRestConsumer;
import com.service.SalesService;

@Service
public class SalesServiceImpl implements SalesService {

	private static Logger logger = Logger.getLogger(SalesServiceController.class);

	@Autowired
	private SalesRepository salesRepository;

	@Autowired
	ClientRestConsumer clientConsumer;

	@Autowired
	CardRestConsumer cardConsumer;

	@Autowired
	ProductRestConsumer productConsumer;

	@Autowired
	ProductSalesServiceImpl productSaleService;

	@Override
	public Collection<Sales> getSales() {
		return salesRepository.findAll();

	}

	@Override
	public Sales createSale(Sales sale) {
		return salesRepository.save(sale);

	}

	@Override
	public void updateSale(Sales sale) {
		salesRepository.save(sale);

	}

	@Override
	public void deleteSale(Long id) {
		salesRepository.deleteById(id);
	}

	@Override
	public Optional<Sales> getSalesById(Long id) throws InvalidOperationDatabaseException {
		Optional<Sales> sale = salesRepository.findById(id);
		if (sale.isEmpty()) {
			throw new InvalidOperationDatabaseException("No se encontro venta con el id: " + id, 406);
		}
		return sale;
	}

	@Override
	public List<Sales> getSalesByMontoTotal(Double montoTotal) {
		return salesRepository.findByMontoTotalLessThanEqual(montoTotal);
	}

	@Override
	public ReportDTO getSalesByClient(Long clientId) {

		ReportDTO report = new ReportDTO();

		ClientDTO client = clientConsumer.getCLientById(clientId);

		List<Sales> clientSales = salesRepository.findByIdCliente(clientId);
		logger.debug("getSalesByClient-> se recuperaron las ventas para el cliente: " + clientId + ", las ventas son "
				+ clientSales.toString());

		List<SaleDTO> items = new ArrayList<SaleDTO>();

		List<CardDTO> cards = cardConsumer.getCardByClientId(String.valueOf(clientId));

		clientSales.parallelStream().forEach(sale -> {
			SaleDTO item = new SaleDTO();

			item.setTotal(sale.getMontoTotal() + " $ARS");
			item.setFecha(sale.getFechaDeVenta());

			item.setPayment("Efectivo");

			Optional<CardDTO> card = cards.stream().filter(tarjeta -> tarjeta.getId().equals(sale.getIdMetodoDePago()))
					.findFirst();
			if (!card.isEmpty()) {
				item.setPayment("Tarjeta: " + card.get().getCardIssuer() + " " + card.get().getCardNumber());
			}

			List<ProductSales> detalles = productSaleService.getProductSalesByIdSale(sale.getId());
			logger.debug("getSalesByClient-> Para la venta: " + sale.toString()
					+ "\n se recuperaron los siguientes detalles: " + detalles.toString());
			List<ProductReportDTO> productos = new ArrayList<ProductReportDTO>();

			detalles.parallelStream().forEach(detalle -> {
				ProductReportDTO product = new ProductReportDTO();

				ProductDTO productBD = productConsumer.getProductById(detalle.getIdProduct());
				logger.debug("getSalesByClient-> Para el detalle:" + detalle.toString()
						+ "\n se recuperaron el siguientes producto" + productBD.toString());

				product.setDescription(productBD.getName());
				product.setUnitPrice(productBD.getUnitPrice());
				product.setIdProduct(detalle.getIdProduct());
				product.setQuantity(detalle.getQuantity());

				productos.add(product);
			});

			item.setProducts(productos);

			items.add(item);
		});

		report.setAddress(client.getAddress());
		report.setDniClient(client.getDni());
		report.setIdClient(clientId);
		report.setItems(items);
		report.setName(client.getName());
		report.setLastName(client.getLastName());

		return report;
	}

	@Override
	public List<ProductReportDTO> getMostSoldProducts() {
		List<ProductReportDTO> mostSoldProducts=new ArrayList<ProductReportDTO>();
		
		Map<String,Integer> quantityReport=productSaleService.getMostSoldItems();
		quantityReport.forEach((productId,totalQuantity)->{
			ProductDTO item=productConsumer.getProductById(productId);
			ProductReportDTO itemDTO=new ProductReportDTO(item.getId(),item.getName(),totalQuantity,item.getUnitPrice());
			mostSoldProducts.add(itemDTO);
		});
		logger.debug("Se devolvera el orden de los siguientes productos: "+mostSoldProducts.toString());
		
		Collections.sort(mostSoldProducts, Collections.reverseOrder(Comparator.comparing(ProductReportDTO::getQuantity)));
		if(mostSoldProducts.size()<10) {
			return mostSoldProducts;	
		}else {
			return mostSoldProducts.subList(0, 10);
		}
	}

}
