package br.com.cervejaria.comanda.api.exception;

public class BusinessMessageException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BusinessMessageException() {
		super();
	}
	
	public BusinessMessageException(String mensagem) {
		super(mensagem);
	}
	
}
