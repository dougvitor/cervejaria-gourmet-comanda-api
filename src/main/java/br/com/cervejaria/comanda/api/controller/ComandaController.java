package br.com.cervejaria.comanda.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cervejaria.comanda.api.dto.ComandaItemRequestDto;
import br.com.cervejaria.comanda.api.dto.ComandaRequestDto;
import br.com.cervejaria.comanda.api.dto.ComandaResponseDto;
import br.com.cervejaria.comanda.api.dto.ComandaUpdateRequestDto;
import br.com.cervejaria.comanda.api.mapper.service.ComandaModelMapperService;
import br.com.cervejaria.comanda.api.model.Comanda;
import br.com.cervejaria.comanda.api.model.ComandaItem;
import br.com.cervejaria.comanda.api.service.ComandaService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/comanda")
public class ComandaController {
	
	@Autowired
	private ComandaService service;
	
	@Autowired
	private ComandaModelMapperService mapper;
	
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Serviço para obter comandas por identificador único.", httpMethod = "GET", response = ComandaResponseDto.class)
	public ResponseEntity<ComandaResponseDto> findById(@PathVariable Long id){
		Optional<Comanda> comanda = service.findById(id);
		
		if(comanda.isPresent()) {
			return ResponseEntity.ok(mapper.convertToResponseDto(comanda.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/abertura")
	@ApiOperation(value = "Serviço para efetuar abertura de comandas.", httpMethod = "POST", response = ComandaResponseDto.class)
	public ResponseEntity<ComandaResponseDto> efetuarAbertura(@RequestBody @Valid ComandaRequestDto dto, UriComponentsBuilder uriBuilder){
		Comanda comanda = mapper.convertRequestToEntity(dto);
		Comanda comandaSalvo = service.efetuarAbertura(comanda);
		
		URI uri = uriBuilder
				.path("/comanda/{id}")
				.buildAndExpand(comandaSalvo.getId())
				.toUri();
		
		return ResponseEntity
				.created(uri)
				.body(mapper.convertToResponseDto(comandaSalvo));
	}
	
	@GetMapping("/fechamento/{id}")
	@ApiOperation(value = "Serviço para efetuar fechamento de comandas.", httpMethod = "GET", response = ComandaResponseDto.class)
	public ResponseEntity<ComandaResponseDto> efetuarFechamento(@PathVariable Long id){
		Comanda comandaFechada = service.efetuarFechamento(id);
		return ResponseEntity.ok(mapper.convertToResponseDto(comandaFechada));
	}
	
	@PatchMapping(path = "/addItens/{id}")
	@ApiOperation(value = "Serviço para adicionar novos itens à comandas.", httpMethod = "PATCH", response = ComandaResponseDto.class)
	public ResponseEntity<ComandaResponseDto> addItensComanda(@PathVariable Long id, @RequestBody List<ComandaItemRequestDto> dtos){
		List<ComandaItem> itens =  dtos.stream().map(mapper::convertItemRequestToEntity).collect(Collectors.toList());
		Comanda comandaFechada = service.addItensComanda(id, itens);
		return ResponseEntity.ok(mapper.convertToResponseDto(comandaFechada));
	}
	
	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Serviço para atualizar comandas.", httpMethod = "PUT", response = ComandaResponseDto.class)
	public ResponseEntity<ComandaResponseDto> atualizar(@PathVariable Long id, @RequestBody ComandaUpdateRequestDto dto){
		Comanda comanda = mapper.convertUpdateRequestToEntity(dto);
		Comanda comandaAtualizada = service.atualizar(id, comanda);
		return ResponseEntity.ok(mapper.convertToResponseDto(comandaAtualizada));
	}
	
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Serviço para excluir comandas.", httpMethod = "DELETE")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		Optional<Comanda> produto = service.findById(id);
		
		if(produto.isPresent()) {
			service.excluir(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

}
