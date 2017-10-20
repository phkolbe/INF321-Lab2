package br.unicamp.exemplo;

import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.Assert.assertEquals;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;

import br.unicamp.exemplo.Calculadora;
import br.unicamp.exemplo.dao.CalculadoraDAO;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CalculadoraSteps {
	private CalculadoraDAO calcDAO;
	
	private Calculadora calculadora;
	
	private Throwable throwable;

	@Before
	public void setUp() {
		calcDAO = Mockito.mock(CalculadoraDAO.class);
	}

	@Given("^I have a calculator$")
	public void i_have_a_calculator() throws Throwable {
		calculadora = new Calculadora(calcDAO);
	}

	@When("^I sum (\\d+) with (\\d+)$")
	public void i_sum_with(int arg1, int arg2) throws Throwable {
		throwable = catchThrowable(() -> calculadora.add(arg1, arg2));
	}

	@When("^I multiply (\\d+) with (\\d+)$")
	public void i_mult_with(int arg1, int arg2) throws Throwable {
		throwable = catchThrowable(() -> calculadora.multiply(arg1, arg2));
	}

	@When("^I divide (\\d+) with (\\d+)$")
	public void i_div_with(int arg1, int arg2) throws Throwable {
		throwable = catchThrowable(() -> calculadora.divide(arg1, arg2));
	}

	@Then("^the result should be (\\d+)$")
	public void the_result_should_be(int result) throws Throwable {
		assertEquals(result, calculadora.getResult());
	}

	@Then("^should show an error with a message:$")
	public void should_show_an_error(String message) {
		Assertions.assertThat(throwable).isNotNull().hasMessage(message);
	}
	
	@And("^the result should be saved on the database$")
	public void theResultShouldBeSavedOnTheDatabase() throws Throwable {
		Mockito.verify(calcDAO, Mockito.times(1)).saveResult(calculadora.getResult());;
	}

}