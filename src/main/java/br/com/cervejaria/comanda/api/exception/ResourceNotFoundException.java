package br.com.cervejaria.comanda.api.exception;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super();
	}
	
	public ResourceNotFoundException(String mensagem) {
		super(mensagem);
	}
	
}
