package br.com.cervejaria.comanda.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ComandaResponseDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Identificador único do item da comanda")
	private Long id;
	
	@ApiModelProperty(value = "Nome do cliente")
	private String nomeCliente;
	
	@ApiModelProperty(value = "Número da mesa")
	private String numeroMesa;
	
	@ApiModelProperty(value = "Valor total da comanda")
	private BigDecimal valorTotal;
	
	@ApiModelProperty(value = "Data e hora da abertura da comanda")
	private Date dataAbertura;
	
	@ApiModelProperty(value = "Lista de itens")
	private List<ComandaItemResponseDto> itens;
	
	@ApiModelProperty(value = "Data e hora do fechamento da comanda")
	private Date dataFechamento;
	
	@ApiModelProperty(value = "Flag que sinaliza o fechamento da comanda")
	private boolean fechada = false;

}
