package com.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.controller.SalesServiceController;
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

@SpringBootTest
public class SalesServiceControllerTest {
	@InjectMocks
	SalesServiceController controller;
	
	@Mock
	SalesServiceImpl service;
	@Mock
	CardRestConsumer cardConsumer;
	@Mock
	ClientRestConsumer clientConsumer;
	@Mock
	ProductRestConsumer productConsumer;
	@Mock
	ProductSalesServiceImpl productService;

	Collection<Sales> allSales = new ArrayList<Sales>();

	
	List<CardDTO> cards = new ArrayList<CardDTO>();
	CardDTO card=new CardDTO(1L,"Master Card",null,"5323458712364589","2");
	ClientDTO client = new ClientDTO(2L,42403450L,"Homero","Simpson","Siempreviva 123","Springfield","USA","soyhomeroelmalo01@gmail.com","1240987346");
	
	ProductDTO dto1=new ProductDTO("1","chocolate",10,null,125.5);
	ProductDTO dto2=new ProductDTO("2","leche",10,null,24.4);
	ProductDTO dto3=new ProductDTO("3","azucar",10,null,10.5);
	List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
	ProductDTO detal1=new ProductDTO("1",null,null,1,null);
	ProductDTO detal2=new ProductDTO("2",null,null,2,null);
	ProductDTO detal3=new ProductDTO("3",null,null,3,null);

	@Test
	public void getSalesTestOk() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(service.getSales()).thenReturn(allSales);

		ResponseEntity<Object> responseEntity = controller.getSales();

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void createSalesTestOk() throws InvalidOperationDatabaseException{
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(service.getSales()).thenReturn(allSales);

//		ResponseEntity<Object> responseEntity = controller.createSale(null);

	}

	@Test
	public void updateSalesTestOk() throws InvalidOperationDatabaseException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(service.getSales()).thenReturn(allSales);

//		ResponseEntity<Object> responseEntity = controller.updateSale(null);
	}

	@Test
	public void deleteSalesTestOk() throws InvalidOperationDatabaseException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(service.getSales()).thenReturn(allSales);

		ResponseEntity<Object> responseEntity = controller.deleteSale(null);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void deleteSalesTestNoOk() throws InvalidOperationDatabaseException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		doThrow(DataIntegrityViolationException.class).when(service).deleteSale(999L);
		
		
		assertThrows(InvalidOperationDatabaseException.class,()->controller.deleteSale(999L));
	}

	@Test
	public void getSalesByIdTestOk() throws InvalidOperationDatabaseException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(service.getSales()).thenReturn(allSales);

		ResponseEntity<Object> responseEntity = controller.getSales(null);

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void getSalesByMontoTotalTestOk() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(service.getSales()).thenReturn(allSales);

		ResponseEntity<Object> responseEntity = controller.getSalesByMontoTotal(null);

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void saveSalewithProductsOkTest() throws InvalidPaymentMethodException, LackOfStockException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		cards.add(card);
		Long clientId = 2L;
		String metodoPago = "Tarjeta";
		String nroTarjeta = "5323458712364589";
			
		productDTOList.add(detal1);
		productDTOList.add(detal2);
		productDTOList.add(detal3);
		
		when(clientConsumer.getCLientById(clientId)).thenReturn(client);
		
		when(productConsumer.getProductById("1")).thenReturn(dto1);
		when(productConsumer.getProductById("2")).thenReturn(dto2);
		when(productConsumer.getProductById("3")).thenReturn(dto3);
		
		when(cardConsumer.validarTarjetaCliente(String.valueOf(clientId), nroTarjeta)).thenReturn(new CardDTO());
		when(cardConsumer.getCardByClientId(String.valueOf(clientId))).thenReturn(cards);
		
		when(service.createSale(any(Sales.class))).thenReturn(new Sales(1L,null,null,null,null));
		
		when(productConsumer.modifyStock(any(List.class))).thenReturn("");
		when(productService.postProductSale(any(List.class))).thenReturn(productDTOList);
		
		
		ResponseEntity<Object> responseEntity = controller.saveSaleWithProducts(clientId, metodoPago, productDTOList, nroTarjeta);
		
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
		@Test
		public void saveSalewithProductsNoOkTest() throws InvalidPaymentMethodException, LackOfStockException {
			MockHttpServletRequest request = new MockHttpServletRequest();
			RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
			
			cards.add(card);
			Long clientId = 2L;
			String metodoPago = "asdasdasd";
			String nroTarjeta = "5323458712364589";
			
			productDTOList.add(detal1);
			productDTOList.add(detal2);
			productDTOList.add(detal3);
			
			when(clientConsumer.getCLientById(clientId)).thenReturn(client);
			
			when(productConsumer.getProductById("1")).thenReturn(dto1);
			when(productConsumer.getProductById("2")).thenReturn(dto2);
			when(productConsumer.getProductById("3")).thenReturn(dto3);
			
			when(cardConsumer.validarTarjetaCliente(String.valueOf(clientId), nroTarjeta)).thenReturn(new CardDTO());
			when(cardConsumer.getCardByClientId(String.valueOf(clientId))).thenReturn(cards);
			
			when(service.createSale(any(Sales.class))).thenReturn(new Sales(1L,null,null,null,null));
			
			when(productConsumer.modifyStock(any(List.class))).thenReturn("");
			when(productService.postProductSale(any(List.class))).thenReturn(productDTOList);
			
			
			assertThrows(InvalidPaymentMethodException.class,()->controller.saveSaleWithProducts(clientId, metodoPago, productDTOList, nroTarjeta));
		
	}
		@Test
		public void reportProductsOkTest() {
			MockHttpServletRequest request = new MockHttpServletRequest();
			RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		}
	
}
