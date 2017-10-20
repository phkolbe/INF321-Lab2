package br.unicamp.bookstore.model;

public enum TipoEntregaEnum {
	PACVAREJO("41106"),
	SEDEXVAREJO("40010"),
	SEDEX10VAREJO("40215");	
	String codigo;
	
	private TipoEntregaEnum(String codigo) {
		this.codigo = codigo;
	}
	
	public String getCodigo() {
		return codigo;
	}
}
