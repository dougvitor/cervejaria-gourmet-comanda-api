package br.com.cervejaria.comanda.api.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cervejaria.comanda.api.dto.ProdutoDto;
import br.com.cervejaria.comanda.api.exception.ResourceNotFoundException;
import br.com.cervejaria.comanda.api.feign.client.ProdutoClient;
import br.com.cervejaria.comanda.api.model.Comanda;
import br.com.cervejaria.comanda.api.model.ComandaItem;
import br.com.cervejaria.comanda.api.repository.ComandaRepository;
import feign.FeignException;
import feign.FeignException.FeignClientException;

@Service
public class ComandaService {

	private ComandaRepository repository;
	
	private ProdutoClient produtoClient;
	
	@Autowired
	public ComandaService(ComandaRepository repository, ProdutoClient produtoClient) {
		this.repository = repository;
		this.produtoClient = produtoClient;
	}
	
	public Optional<Comanda> findById(Long id) {
		return repository.findById(id);
	}
	
	@Transactional
	public Comanda efetuarAbertura(Comanda comanda) {
		comanda.setDataAbertura(new Date());
		obterInformacoesProdutosParaListaDeItens(comanda);
		return repository.save(comanda);
	}
	
	@Transactional
	public Comanda efetuarFechamento(Long id) {
		
		Optional<Comanda> comandaASerFechada = findById(id);
		
		if(!comandaASerFechada.isPresent()) {
			throw new ResourceNotFoundException("Não existe comanda cadastrada com o id informado!");
		}
		
		Comanda comanda = comandaASerFechada.get(); 
		comanda.setDataFechamento(new Date());
		comanda.setFechada(true);
		return repository.save(comanda);
	}
	
	@Transactional
	public Comanda addItensComanda(Long id, List<ComandaItem> itens) {
		
		Optional<Comanda> comandaASerFechada = findById(id);
		
		if(!comandaASerFechada.isPresent()) {
			throw new ResourceNotFoundException("Não existe comanda cadastrada com o id informado!");
		}
		
		Comanda comanda = comandaASerFechada.get(); 
		atualizarInformacoesDeListaDeItens(comanda, itens);
		return repository.save(comanda);
	}
	
	@Transactional
	public Comanda atualizar(Long id, Comanda comanda) {
		Optional<Comanda> comandaASerAtualizada = findById(id);
		
		if(!comandaASerAtualizada.isPresent()) {
			throw new ResourceNotFoundException("Não existe comanda cadastrada com o id informado!");
		}
		
		Comanda comandaAtualizada = comandaASerAtualizada.get();
		comandaAtualizada.setId(id);
		comandaAtualizada.setDataAbertura(comanda.getDataAbertura());
		comandaAtualizada.setDataFechamento(comanda.getDataFechamento());
		comandaAtualizada.setNomeCliente(comanda.getNomeCliente());
		comandaAtualizada.setNumeroMesa(comanda.getNumeroMesa());
		comandaAtualizada.setFechada(comanda.isFechada());
		comandaAtualizada.getItens().clear();
		comandaAtualizada.getItens().addAll(comanda.getItens());
		comandaAtualizada.getItens().forEach(item -> {
			item.setComanda(comandaAtualizada);
			item.setValorTotal(item.getQuantidade().multiply(item.getValorUnitarioProduto()));
		});
		comandaAtualizada.setValorTotal(comandaAtualizada.getItens().stream().map(item -> item.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add));
		return repository.save(comandaAtualizada);
	}
	
	@Transactional
	public void excluir(final Long id) {
		repository.deleteById(id);
	}
	
	private void obterInformacoesProdutosParaListaDeItens(Comanda comanda) throws FeignClientException {
		BigDecimal valorTotalComanda = BigDecimal.ZERO;
		
		for(ComandaItem item : comanda.getItens()) {
			try {
				ProdutoDto produto = produtoClient.getProdutoBy(item.getNomeProduto());
				item.setNomeProduto(produto.getNome());
				item.setValorUnitarioProduto(produto.getValor());
				item.setValorTotal(item.getQuantidade().multiply(item.getValorUnitarioProduto()));
				
				valorTotalComanda = valorTotalComanda.add(item.getValorTotal());
				item.setComanda(comanda);
				
			}catch(FeignClientException e) {
				
				if(e instanceof FeignException.NotFound) {
					throw new ResourceNotFoundException(String.format("O produto %s não foi encontrado!", item.getNomeProduto()));
				}
				
				throw e;
			}
		}
		
		comanda.setValorTotal(valorTotalComanda);
	}
	
	private void atualizarInformacoesDeListaDeItens(Comanda comanda, List<ComandaItem> itens) throws FeignClientException {
		BigDecimal valorTotalComanda = BigDecimal.ZERO;
		
		for(ComandaItem item : itens) {
			try {
				ProdutoDto produto = produtoClient.getProdutoBy(item.getNomeProduto());
				item.setNomeProduto(produto.getNome());
				item.setValorUnitarioProduto(produto.getValor());
				item.setValorTotal(item.getQuantidade().multiply(item.getValorUnitarioProduto()));
				valorTotalComanda = valorTotalComanda.add(item.getValorTotal());
				
				if(item.getId() == null) {
					item.setComanda(comanda);
					comanda.getItens().add(item);
				}else {
					comanda.getItens().forEach(i -> {
						if(i.getId() == item.getId()) {
							i.setNomeProduto(item.getNomeProduto());
							i.setQuantidade(item.getQuantidade());
							i.setValorUnitarioProduto(item.getValorUnitarioProduto());
							i.setValorTotal(item.getValorTotal());
						}
					});
				}
			}catch(FeignClientException e) {
				
				if(e instanceof FeignException.NotFound) {
					throw new ResourceNotFoundException(String.format("O produto %s não foi encontrado!", item.getNomeProduto()));
				}
				
				throw e;
			}
		}
		comanda.setValorTotal(valorTotalComanda);
	}

}
