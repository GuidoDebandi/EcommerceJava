package com.globallogic.clients.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.globallogic.clients.dto.ClientDTO;
import com.globallogic.clients.error.exception.NonExistentDniException;
import com.globallogic.clients.error.exception.NonExistentIdException;
import com.globallogic.clients.service.impl.ClientServiceImpl;

@SpringBootTest
public class ClientServiceControllerTest {
	@InjectMocks
	ClientServiceController controller;
	
	@Mock
	ClientServiceImpl service;
	
	List<ClientDTO> allClients=new ArrayList<ClientDTO>();
	ClientDTO dto1=new ClientDTO(1L,45412546L,"Cristiano","Ronaldo","first st 12","Lisboa","Portugal","Cr7@gmail.com",null);
	ClientDTO dto2=new ClientDTO(1L,45412546L,"Lionel","Messi",null,null,null,null,null);
	ClientDTO dto3=new ClientDTO(1L,45412546L,"Micheal","Jackson",null,null,null,null,null);
	
	
	
	@Test
	public void getAllClientsTestOk() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        when(service.getClientsAll()).thenReturn(allClients);
         
        
        ResponseEntity<Object> responseEntity = controller.getClients();
         
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
	@Test
	public void getClientByIdTestOk() throws NonExistentIdException {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        when(service.getClientById(1L)).thenReturn(dto1);
         
        
        ResponseEntity<Object> responseEntity = controller.getClientById(1L);
         
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		
	}
	@Test
	public void getClientByDniTestOk() throws NonExistentDniException {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        when(service.getClientByDni(45412546L)).thenReturn(dto1);
         
        ResponseEntity<Object> responseEntity = controller.getClientByDni(45412546L);
         
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		
	}
	
	@Test
	public void getClientByIdTestNoOk() throws NonExistentIdException {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        
       when(service.getClientById(12L)).thenThrow(NonExistentIdException.class);
                 
       assertThrows(NonExistentIdException.class, ()->controller.getClientById(12L));
		
	}
	@Test
	public void getClientByDniTestNoOk() throws NonExistentDniException {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
         
        when(service.getClientByDni(123123L)).thenThrow(NonExistentDniException.class);
                  
        assertThrows(NonExistentDniException.class, ()->controller.getClientByDni(123123L));
		
	}
}
