package br.unicamp.bookstore.dto;

public class PrecoPrazoEntrada {
	
	private String cep;
	private String pesoProduto;
	private String larguraProduto;
	private String comprimentoProduto;
	private String tipoEntrega;
	
	public PrecoPrazoEntrada(String cep, String peso, String largura, String comprimento, String tipoEntrada) {
		this.cep = cep;
		this.pesoProduto = peso;
		this.larguraProduto = largura;
		this.comprimentoProduto = comprimento;
		this.tipoEntrega = tipoEntrada;
	}
	
	public String getCep() {
		return cep;
	}
	public String getPesoProduto() {
		return pesoProduto;
	}
	public String getLarguraProduto() {
		return larguraProduto;
	}
	public String getComprimentoProduto() {
		return comprimentoProduto;
	}
	public String getTipoEntrega() {
		return tipoEntrega;
	}
	
	

}
