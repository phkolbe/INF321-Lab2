package br.unicamp.bookstore.model;

import javax.xml.bind.annotation.XmlElement;

public class StatusEntrega {
	
	@XmlElement(name = "codigoRastreamento")
	private String codigoRastreamento;

	@XmlElement(name = "tipo")
	private String tipo;

	@XmlElement(name = "status")
	private String status;

	@XmlElement(name = "descricao")
	private String descricao;
	
	@XmlElement(name = "erro")
	private String erro;	

	public String getCodigoRastreamento() {
		return codigoRastreamento;
	}
	
	public String getTipo() {
		return tipo;
	}

	public String getStatus() {
		return status;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getErro() {
		return erro;
	}
	
	public boolean hasError() {
		return !"0".equals(erro);
	}


}
