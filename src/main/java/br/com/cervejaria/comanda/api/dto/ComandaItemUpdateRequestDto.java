package br.com.cervejaria.comanda.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ComandaItemUpdateRequestDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Nome do produto")
	private String nomeProduto;
	
	@ApiModelProperty(value = "Valor unitário do produto")
	private BigDecimal valorUnitarioProduto;
	
	@ApiModelProperty(value = "Quantidade de itens")
	private BigDecimal quantidade;
	
	@ApiModelProperty(value = "Valor total do item")
	private BigDecimal valorTotal;

}
