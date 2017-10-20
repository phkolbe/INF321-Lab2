package br.unicamp.bookstore;

// TODO refatorar para ler de um arquivo de configuracao externo (properties ou yml)
public class Configuracao {

	public String getStatusEntregaUrl() {
		return "http://websro.correios.com.br/sro_bin/sroii_xml.eventos";
	}

	public String getBuscarEnderecoUrl() {
		return "http://viacep.com.br/ws";
	}
	
	public String getConsultaPrecoPrazoUrl() {
		return "http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx";
	}
	
}
