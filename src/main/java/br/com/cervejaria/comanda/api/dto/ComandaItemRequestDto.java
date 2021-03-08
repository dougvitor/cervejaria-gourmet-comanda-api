package br.com.cervejaria.comanda.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ComandaItemRequestDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Identificador único do item da comanda")
	private Long id;
	
	@ApiModelProperty(value = "Nome do Produto")
	@NotBlank(message = "O campo nome do cliente é obrigatório!")
	private String nomeProduto;
	
	@ApiModelProperty(value = "Quantidade de itens")
	@NotBlank(message = "O campo nome do cliente é obrigatório!")
	private BigDecimal quantidade;
	
}
