package br.unicamp.exemplo;

import br.unicamp.exemplo.dao.CalculadoraDAO;

public class Calculadora {
	private int result;
	private CalculadoraDAO calculadoraDAO;

    public Calculadora(CalculadoraDAO calculadoraDAO){
    	this.calculadoraDAO = calculadoraDAO;
	}

	public void add(int arg1, int arg2) {
        result = arg1 + arg2;
        calculadoraDAO.saveResult(result);
    }
    
    public void multiply(int arg1, int arg2) {
        result = arg1 * arg2;
        calculadoraDAO.saveResult(result);
    }

	public void divide(int arg1, int arg2) {
		result = arg1 / arg2;
		calculadoraDAO.saveResult(result);
	}
	
	public int getResult() {
		return this.result;
	}

}
