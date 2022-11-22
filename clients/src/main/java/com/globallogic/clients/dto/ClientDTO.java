package com.globallogic.clients.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor


public class ClientDTO {
	
	@NotNull
	@NotBlank(message="ID is required")
	@Min(value = 0L, message = "The value must be positive")
	private Long id;
	@NotNull
	private Long dni;
	private String name;
	private String lastName;
	private String address;
	private String city;
	private String country;
	private String mail;
	private String cellphone;
	
	
}
