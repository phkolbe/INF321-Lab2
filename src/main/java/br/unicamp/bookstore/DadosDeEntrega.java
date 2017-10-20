package br.unicamp.bookstore;

import br.unicamp.bookstore.dao.DadosDeEntregaDAO;

public class DadosDeEntrega implements DadosDeEntregaDAO {
	
	private DadosDeEntregaDAO dao;
	
	public DadosDeEntrega(DadosDeEntregaDAO dao) {
		this.dao = dao;
	}
	
	public void saveDadosDeEntrega(Double valorFrete, Integer diasEntrega) {
		dao.saveDadosDeEntrega(valorFrete, diasEntrega);
	}

}
