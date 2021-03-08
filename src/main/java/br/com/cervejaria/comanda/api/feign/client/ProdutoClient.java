package br.com.cervejaria.comanda.api.feign.client;

import org.springframework.cloud.openfeign.FeignClient;

import br.com.cervejaria.comanda.api.dto.ProdutoDto;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@FeignClient(name = "produtoClient", url = "${cervejaria.api.produto.servers}")
public interface ProdutoClient {
	
	@RequestLine("GET produto-api/produto/filtro-por-nome-produto/{nome}")
	@Headers("Content-Type: application/json")
	public ProdutoDto getProdutoBy(@Param("nome") String nome);

}
