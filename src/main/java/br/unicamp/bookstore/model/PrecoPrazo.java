package br.unicamp.bookstore.model;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.xml.bind.annotation.XmlElement;

public class PrecoPrazo {
	@XmlElement(name = "Codigo")
	private Integer codigo;

	@XmlElement(name = "Valor")
	private String valor;

	@XmlElement(name = "PrazoEntrega")
	private Integer prazoEntrega;

	@XmlElement(name = "ValorMaoPropria")
	private String valorMaoPropria;

	@XmlElement(name = "ValorAvisoRecebimento")
	private String valorAvisoRecebimento;

	@XmlElement(name = "ValorValorDeclarado")
	private String valorValorDeclarado;

	@XmlElement(name = "EntregaDomiciliar")
	private String entregaDomiciliar;

	@XmlElement(name = "EntregaSabado")
	private String entregaSabado;

	@XmlElement(name = "Erro")
	private String erro;

	@XmlElement(name = "MsgErro")
	private String msgErro;

	public String getMsgErro() {
		return msgErro;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public Double getValorFrete() {
		try {
			return new DecimalFormat("##,##").parse(valor).doubleValue();
		} catch (ParseException e) {
			return null;
		}
	}

	public String getValor() {
		return valor;
	}

	public Integer getPrazoEntrega() {
		return prazoEntrega;
	}

	public String getValorMaoPropria() {
		return valorMaoPropria;
	}

	public String getValorAvisoRecebimento() {
		return valorAvisoRecebimento;
	}

	public String getValorValorDeclarado() {
		return valorValorDeclarado;
	}

	public String getEntregaDomiciliar() {
		return entregaDomiciliar;
	}

	public String getEntregaSabado() {
		return entregaSabado;
	}

	public String getErro() {
		return erro;
	}

	public boolean hasError() {
		return !"0".equals(erro);
	}
}
