package br.com.cervejaria.comanda.api.mapper.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cervejaria.comanda.api.dto.ComandaItemRequestDto;
import br.com.cervejaria.comanda.api.dto.ComandaRequestDto;
import br.com.cervejaria.comanda.api.dto.ComandaResponseDto;
import br.com.cervejaria.comanda.api.dto.ComandaUpdateRequestDto;
import br.com.cervejaria.comanda.api.model.Comanda;
import br.com.cervejaria.comanda.api.model.ComandaItem;

@Service
public class ComandaModelMapperService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ComandaRequestDto convertToRequestDto(Comanda model) {
		return modelMapper.map(model, ComandaRequestDto.class);
	}
	
	public Comanda convertRequestToEntity(ComandaRequestDto dto) {
		return modelMapper.map(dto, Comanda.class);
	}
	
	public ComandaResponseDto convertToResponseDto(Comanda model) {
		return modelMapper.map(model, ComandaResponseDto.class);
	}
	
	public Comanda convertResponseToEntity(ComandaResponseDto dto) {
		return modelMapper.map(dto, Comanda.class);
	}
	
	public ComandaItemRequestDto convertToItemRequestDto(ComandaItem model) {
		return modelMapper.map(model, ComandaItemRequestDto.class);
	}
	
	public ComandaItem convertItemRequestToEntity(ComandaItemRequestDto dto) {
		return modelMapper.map(dto, ComandaItem.class);
	}
	
	public Comanda convertUpdateRequestToEntity(ComandaUpdateRequestDto dto) {
		return modelMapper.map(dto, Comanda.class);
	}

}
