package br.com.cervejaria.comanda.api.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@PropertySources({@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true) })
public class CervejariaGourmetComandaConfig {
	
	public static final String CERVEJARIA_API_PRODUTO_SERVERS = "cervejaria.api.produto.servers";
	
	@Resource
	private Environment env;

	private String getValue(String key) {
		return env.getProperty(key);
	}
	
	public String getCervejariaApiProdutoServers() {
		return this.getValue(CERVEJARIA_API_PRODUTO_SERVERS);
	}

}
