package br.unicamp.bookstore.service;

import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.dto.PrecoPrazoEntrada;
import br.unicamp.bookstore.model.PrecoPrazo;

public class CalculoFretePrazoService {
	
	  private Configuracao configuracao;

	  public PrecoPrazo consultar(PrecoPrazoEntrada entrada) throws Exception {
	    String url = String.format("%s/%s/%s/%s/%s/%s/xml", configuracao.getConsultaPrecoPrazoUrl(), 
	    		entrada.getCep(),
	    		entrada.getPesoProduto(),
	    		entrada.getLarguraProduto(),
	    		entrada.getComprimentoProduto(),
	    		entrada.getTipoEntrega());
	    return new RemoteService().getAndParseXml(url, PrecoPrazo.class);
	  }

}
