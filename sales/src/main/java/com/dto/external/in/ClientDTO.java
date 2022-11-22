package com.dto.external.in;

public class ClientDTO {
	private Long id;
	private Long dni;
	private String name;
	private String lastName;
	private String address;
	private String city;
	private String country;
	private String mail;
	private String cellphone;
	
	
	public ClientDTO() {
	}
	
	public ClientDTO(Long idCliente, Long dni, String name, String lastName, String address, String city,
			String country, String mail, String cellphone) {
		super();
		this.id = idCliente;
		this.dni = dni;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.country = country;
		this.mail = mail;
		this.cellphone = cellphone;
	}




	public Long getId() {
		return id;
	}


	public void setId(Long idCliente) {
		this.id = idCliente;
	}


	public Long getDni() {
		return dni;
	}


	public void setDni(Long dni) {
		this.dni = dni;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getCellphone() {
		return cellphone;
	}


	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	@Override
	public String toString() {
		return "ClientDto:\n Id cliente: " + id + ",\n Dni: " + dni + ",\n Name: " + name + ", \n Last name: " + lastName
				+ ", \n Address: " + address + ",\n City: " + city + ",\n Country: " + country + ",\n Mail: " + mail + ",\n Cellphone: "
				+ cellphone;
	}
	
}
