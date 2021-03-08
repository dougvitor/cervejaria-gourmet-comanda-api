package br.com.cervejaria.comanda.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProdutoDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	private BigDecimal valor;
	
}
