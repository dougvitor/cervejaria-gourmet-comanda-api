package br.com.cervejaria.comanda.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comanda implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAbertura;
	
	@Column(length = 100, nullable = false)
	private String nomeCliente;
	
	@Column(length = 3, nullable = false)
	private String numeroMesa;
	
	@Column(precision = 17, scale = 2, nullable = false)
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "comanda")
	private List<ComandaItem> itens = new ArrayList<>();
	
	private boolean fechada = false;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFechamento;

}
