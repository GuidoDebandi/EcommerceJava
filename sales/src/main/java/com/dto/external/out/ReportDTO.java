package com.dto.external.out;

import java.util.List;

public class ReportDTO {
	private Long idClient;
	private String name;
	private String lastName;
	private String address;
	private Long dniClient;
	List<SaleDTO> items;
	
	public Long getIdClient() {
		return idClient;
	}
	public void setIdClient(Long idClient) {
		this.idClient = idClient;
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
	public Long getDniClient() {
		return dniClient;
	}
	public void setDniClient(Long dniClient) {
		this.dniClient = dniClient;
	}
	public List<SaleDTO> getItems() {
		return items;
	}
	public void setItems(List<SaleDTO> items) {
		this.items = items;
	}
	public ReportDTO() {
	}
	public ReportDTO(Long idClient, String name, String lastName, String address, Long dniClient, List<SaleDTO> items) {
		this.idClient = idClient;
		this.name = name;
		this.lastName = lastName;
		this.address = address;
		this.dniClient = dniClient;
		this.items = items;
	}
	@Override
	public String toString() {
		return "ReportDTO [idClient=" + idClient + ", name=" + name + ", lastName=" + lastName + ", address=" + address
				+ ", dniClient=" + dniClient + ", items=" + items.toString() + "]\n";
	}
	
	
}
