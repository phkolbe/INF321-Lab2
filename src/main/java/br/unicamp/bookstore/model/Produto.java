package br.unicamp.bookstore.model;

public class Produto {
	private double peso;	
	private double largura;	
	private double altura;
	private double comprimento;
	
	public Produto(double peso, double largura, double altura, double comprimento) {
		this.peso = peso;
		this.largura = largura;
		this.altura = altura;
		this.comprimento = comprimento;
	}

	public double getPeso() {
		return peso;
	}

	public double getLargura() {
		return largura;
	}

	public double getAltura() {
		return altura;
	}

	public double getComprimento() {
		return comprimento;
	}

	public String toQueryString() {
		return String.format("&nVlPeso=%s&nVlComprimento=%s&nVlAltura=%s&nVlLargura=%s", peso, comprimento, altura, largura);
	}

}
