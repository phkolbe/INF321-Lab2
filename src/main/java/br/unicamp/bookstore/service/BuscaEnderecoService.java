package br.unicamp.bookstore.service;

import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.model.Endereco;

public class BuscaEnderecoService {

  private Configuracao configuracao;

  public Endereco buscar(String cep) throws Exception {
    String url = String.format("%s/%s/xml", configuracao.getBuscarEnderecoUrl(), cep);
    return new RemoteService().getAndParseXml(url, Endereco.class);
  }

}
