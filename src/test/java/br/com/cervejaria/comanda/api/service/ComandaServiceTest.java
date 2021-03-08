package br.com.cervejaria.comanda.api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.cervejaria.comanda.api.dto.ProdutoDto;
import br.com.cervejaria.comanda.api.feign.client.ProdutoClient;
import br.com.cervejaria.comanda.api.model.Comanda;
import br.com.cervejaria.comanda.api.model.ComandaItem;
import br.com.cervejaria.comanda.api.repository.ComandaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ComandaServiceTest {
	
	private ComandaService service;
	
	private ComandaRepository repository;
	
	private ProdutoClient produtoClient;
	
	@BeforeEach
	public void initService() {
		repository = mock(ComandaRepository.class);
		produtoClient = mock(ProdutoClient.class);
		service = new ComandaService(repository, produtoClient);
	}
	
	@Test
	public void findById() {
		Optional<Comanda> comandaMock = comandaAbertura();
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(comandaMock);
		Optional<Comanda> result = service.findById(1L);
		assertThat(result.isPresent());
		assertThat(result.get().getNomeCliente()).isEqualTo("José");
		assertThat(result.get().getNumeroMesa()).isEqualTo("01");
	}
	
	@Test
	public void efetuarAbertura() {
		Optional<Comanda> comandaMock = comandaAbertura();
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(comandaMock);
		Mockito.when(produtoClient.getProdutoBy(Mockito.anyString())).thenReturn(produto());
		Mockito.when(repository.save(Mockito.any())).thenReturn(comandaMock.get());
		Comanda result = service.efetuarAbertura(new Comanda());
		assertThat(result.getId()).isNotNull();
		assertTrue(result.getDataAbertura() != null);
		assertTrue(!result.isFechada());
	}
	
	@Test
	public void efetuarFechamento() {
		Optional<Comanda> comandaMock = comandaAbertura();
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(comandaMock);
		Mockito.when(repository.save(Mockito.any())).thenReturn(comandaFechamento());
		Comanda result = service.efetuarFechamento(1L);
		assertThat(result.getItens()).isNotEmpty();
		assertTrue(result.getDataFechamento() != null);
		assertTrue(result.isFechada());
	}
	
	@Test
	public void addItensComanda() {
		Optional<Comanda> comandaMock = comandaAbertura();
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(comandaMock);
		Mockito.when(produtoClient.getProdutoBy(Mockito.anyString())).thenReturn(produto());
		Mockito.when(repository.save(Mockito.any())).thenReturn(comandaFechamento());
		Comanda result = service.addItensComanda(1L, comandaItens());
		assertThat(result.getItens().size()).isEqualTo(1);
	}

	private Optional<Comanda> comandaAbertura() {
		return Optional.ofNullable(new Comanda(1L, new Date(), "José", "01", null, new ArrayList<>(), false, null));
	}
	
	private Comanda comandaFechamento() {
		return new Comanda(1L, new Date(), "José", "01", BigDecimal.ZERO, comandaItensFechamento(), true, new Date());
	}
	
	private List<ComandaItem> comandaItensFechamento(){
		return Arrays.asList(new ComandaItem(1L, null, "Refrigerante", new BigDecimal("3.50"), new BigDecimal(2), new BigDecimal(7)));
	}
	
	private List<ComandaItem> comandaItens(){
		return Arrays.asList(new ComandaItem(null, null, "Refrigerante", new BigDecimal("3.50"), new BigDecimal(2), new BigDecimal(7)));
	}
	
	private ProdutoDto produto() {
		return new ProdutoDto("Refrigerante", new BigDecimal("3.50"));
	}
}
