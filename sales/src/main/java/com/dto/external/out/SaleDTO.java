package com.dto.external.out;

import java.util.Date;
import java.util.List;

public class SaleDTO {
	private Date fecha;
	private String total;
	private String payment;
	private List<ProductReportDTO> products;
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public List<ProductReportDTO> getProducts() {
		return products;
	}
	public void setProducts(List<ProductReportDTO> products) {
		this.products = products;
	}
	public SaleDTO() {
	}
	public SaleDTO(Date fecha, String total, String payment, List<ProductReportDTO> products) {
		this.fecha = fecha;
		this.total = total;
		this.payment = payment;
		this.products = products;
	}
	@Override
	public String toString() {
		return "SaleDTO [fecha=" + fecha + ", total=" + total + ", payment=" + payment + ", products=" + products.toString() + "]\n";
	}
	
	
}
