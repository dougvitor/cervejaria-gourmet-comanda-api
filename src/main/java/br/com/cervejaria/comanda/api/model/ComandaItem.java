package br.com.cervejaria.comanda.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComandaItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_comanda")
	private Comanda comanda;
	
	@Column(length = 100, nullable = false)
	private String nomeProduto;
	
	@Column(precision = 13, scale = 2, nullable = false)
	private BigDecimal valorUnitarioProduto;
	
	@Column(precision = 12, scale = 2, nullable = false)
	private BigDecimal quantidade;
	
	@Column(precision = 17, scale = 2, nullable = false)
	private BigDecimal valorTotal;

}
