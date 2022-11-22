package com.globallogic.clients.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.globallogic.clients.controller.ClientServiceController;
import com.globallogic.clients.dto.ClientDTO;
import com.globallogic.clients.error.exception.NonExistentDniException;
import com.globallogic.clients.error.exception.NonExistentIdException;


@SpringBootTest
public class ClientRepositoryTest {
	
		
	@Autowired
	ClientRepository repository;
	
	private static Logger logger = Logger.getLogger(ClientServiceController.class);
	
	
	@Test
	public void getAllClientTest() {
		List<ClientDTO> clients = repository.getAllclients();
		assertEquals(10,clients.size());
	}
	@Test
	public void findByIdTest() throws NonExistentIdException {
		ClientDTO dto1=repository.findById(5L);
		assertEquals("Garcia Marquez", dto1.getLastName());
	}
	
	
	@Test
	public void findByDniTest() throws NonExistentDniException {
		ClientDTO dto1=repository.findByDni(10523698L);
		assertEquals("Storni", dto1.getLastName());
	}
	
	@Test
	public void nonExistentIdExceptionErrorTest() throws NonExistentIdException{
		assertThrows(NonExistentIdException.class,()->repository.findById(11L));
	}
	
	@Test
	public void nonExistentDniExceptionErrorTest() throws NonExistentDniException{
		assertThrows(NonExistentDniException.class,()->repository.findByDni(123456789789L));
	}
	@Test
	public void nonExistenDniExceptionErrorMesaggeTest() {

		long wrongDni=12353235L;
		String expectedMessage="El dni: " + wrongDni + " no se ha encontrado o es inexistente";
		try {
			ClientDTO dto1 = repository.findByDni(wrongDni);
			logger.debug("No se lanzo la exception esperada en nonExistenDniExceptionErrorMesaggeTest");
			logger.debug("Se obtuvo el dto"+dto1.toString());
		} catch (NonExistentDniException e) {
			assertEquals(expectedMessage, e.getMessage(),"El mensaje de NonExistentDniException no fue igual al esperado");
		}
	}
}