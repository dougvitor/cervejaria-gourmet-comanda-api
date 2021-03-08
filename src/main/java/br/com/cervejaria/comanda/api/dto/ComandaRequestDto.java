package br.com.cervejaria.comanda.api.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ComandaRequestDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Nome do cliente")
	@NotBlank(message = "O campo nome do cliente é obrigatório!")
	private String nomeCliente;
	
	@ApiModelProperty(value = "Número da mesa")
	@NotBlank(message = "O campo número da mesa é obrigatório!")
	private String numeroMesa;
	
	@ApiModelProperty(value = "Lista de itens")
	private List<ComandaItemRequestDto> itens;
	
	@ApiModelProperty(value = "Flag que sinaliza o fechamento da comanda")
	private boolean fechada = false;

}
