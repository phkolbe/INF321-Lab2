package br.unicamp.bookstore.model;

import javax.xml.bind.annotation.XmlElement;

public class Endereco {

	@XmlElement(name = "cep")
	private String cep;

	@XmlElement(name = "logradouro")
	private String logradouro;

	@XmlElement(name = "bairro")
	private String bairro;

	@XmlElement(name = "localidade")
	private String localidade;

	@XmlElement(name = "uf")
	private String uf;

	@XmlElement(name = "unidade")
	private String unidade;

	@XmlElement(name = "ibge")
	private String ibge;

	@XmlElement(name = "gia")
	private String gia;
	
	@XmlElement(name = "erro")
	private String erro;

	public String getCep() {
		return cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public String getUf() {
		return uf;
	}

	public String getUnidade() {
		return unidade;
	}

	public String getIbge() {
		return ibge;
	}

	public String getGia() {
		return gia;
	}
	
	public String getErro() {
		return erro;
	}

}
