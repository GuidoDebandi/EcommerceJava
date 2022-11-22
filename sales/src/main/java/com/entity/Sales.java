package com.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SALES")
public class Sales {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SALE")
	private Long id;
	
	@Column(name = "MONTO_TOTAL")
	private Double montoTotal;
	
	@Column(name = "TIME_STAMP")
	private Date fechaDeVenta;
	
	@Column(name = "ID_CLIENT")
	private Long idCliente;
	 
	@Column(name = "ID_MEDIO_PAGO")
	private Long idMetodoDePago;
	


}
