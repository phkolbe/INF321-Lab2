package br.unicamp.bookstore.frete;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.dao.DadosDeEntregaDAO;
import br.unicamp.bookstore.dto.PrecoPrazoEntrada;
import br.unicamp.bookstore.model.PrecoPrazo;
import br.unicamp.bookstore.service.CalculoFretePrazoService;

import com.github.tomakehurst.wiremock.WireMockServer;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CalculoFreteTempoEntregaSteps {

	public WireMockServer wireMockServer;

	@Mock
	private Configuracao configuration;

	@InjectMocks
	private CalculoFretePrazoService calculoFretePrazoService;
	
	private DadosDeEntregaDAO dao;

	private PrecoPrazo precoPrazo;

	private String cep;
	private String pesoProduto;
	private String larguraProduto;
	private String comprimentoProduto;
	private String tipoEntrega;
	

	private Throwable throwable;


	@Before
	public void setUp() {
		wireMockServer = new WireMockServer(9876);
		wireMockServer.start();
		MockitoAnnotations.initMocks(this);
		Mockito.when(configuration.getConsultaPrecoPrazoUrl()).thenReturn("http://localhost:9876/ws");
		precoPrazo = null;
		cep = null;
		pesoProduto = null;
		larguraProduto = null;
		comprimentoProduto = null;
		tipoEntrega = null;		
		throwable = null;
		
		this.dao = Mockito.mock(DadosDeEntregaDAO.class);
		
	}

	@After
	public void teardown() {
		wireMockServer.stop();
	}

	@Given("^Informa ao sistema de Correios o cep, peso, largura e comprimento do produto e tipo de entrega:$")
	public void eu_possuo_um_CEP_valido_e_produto_no_carrinho (Map<String, String> map) throws Throwable {
		cep = map.get("cep");
		pesoProduto = map.get("pesoProduto");
		larguraProduto = map.get("larguraProduto");
		comprimentoProduto = map.get("comprimentoProduto");
		tipoEntrega = map.get("tipoEntrega");
		wireMockServer.stubFor(get(
				urlMatching("/ws/"+ cep + "/" + pesoProduto + "/" + larguraProduto + "/" + comprimentoProduto + "/" + tipoEntrega + ".*"))
				.willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-CalcPrecoPrazo.xml")));
		
	}
	
	@Given("^Informa tipo de entrega inexistente:$")
	public void eu_possuo_um_CEP_valido_e_produto_no_carrinho_mas_tipo_entrega_inexistente (Map<String, String> map) throws Throwable {
		cep = map.get("cep");
		pesoProduto = map.get("pesoProduto");
		larguraProduto = map.get("larguraProduto");
		comprimentoProduto = map.get("comprimentoProduto");
		tipoEntrega = map.get("tipoEntrega");
		wireMockServer.stubFor(get(
				urlMatching("/ws/"+ cep + "/" + pesoProduto + "/" + larguraProduto + "/" + comprimentoProduto + "/" + tipoEntrega + ".*"))
				.willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-CalcPrecoPrazo_ERR.xml")));
		
	}
	
	@Given("^Informa cep inexistente:$")
	public void eu_possuo_um_CEP_inexistente (Map<String, String> map) throws Throwable {
		cep = map.get("cep");
		pesoProduto = map.get("pesoProduto");
		larguraProduto = map.get("larguraProduto");
		comprimentoProduto = map.get("comprimentoProduto");
		tipoEntrega = map.get("tipoEntrega");
		wireMockServer.stubFor(get(
				urlMatching("/ws/"+ cep + "/" + pesoProduto + "/" + larguraProduto + "/" + comprimentoProduto + "/" + tipoEntrega + ".*"))
				.willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-CalcPrecoPrazo_ERR.xml")));
		
	}
	
	@And("^O servico CalcPrecoPrazo nao responde$")
	public void o_servico_via_cep_nao_esta_respondendo() throws Throwable {
		wireMockServer.stubFor(get(urlMatching("/ws/.*")).willReturn(aResponse().withStatus(200)
				.withFixedDelay(5000).withBodyFile("resultado-pesquisa-CalcPrecoPrazo-vazio.xml")));
	}
	
	@When("^Eu envio os dados para o servico dos Correios$")
	public void eu_envio_os_dados_para_o_servico_dos_Correios() throws Throwable {
		throwable = catchThrowable(() -> this.precoPrazo = calculoFretePrazoService.consultar(
				new PrecoPrazoEntrada(cep, pesoProduto, larguraProduto, comprimentoProduto, tipoEntrega)));
	}

	@Then("^Recebo valor do frete e tempo de entrega$")
	public void recebo_valor_do_frete_e_tempo_de_entrega(List<Map<String,String>> resultado) throws Throwable {
		assertThat(this.precoPrazo.getValorFrete()).isEqualTo(Double.valueOf(resultado.get(0).get("valorFrete")));
		assertThat(this.precoPrazo.getPrazoEntrega()).isEqualTo(Integer.valueOf(resultado.get(0).get("prazoEntrega")));
		assertThat(throwable).isNull();
	}

	@Then("^Salvo valor do frete e tempo de entrega na base de dados$")
	public void salvo_valor_do_frete_na_base_de_dados() throws Throwable {
		dao.saveDadosDeEntrega(precoPrazo.getValorFrete(), precoPrazo.getPrazoEntrega());
		
		Mockito.verify(dao).saveDadosDeEntrega(
				precoPrazo.getValorFrete(), 
				precoPrazo.getPrazoEntrega());
	}
	
	@Then("^O retorno deve conter um valor de erro igual a \"([^\"]*)\"$")
	public void o_retorno_deve_conter_um_valor_de_erro_igual_a (String erro) throws Throwable {
		assertThat(precoPrazo.getErro()).isEqualTo(erro);
		assertThat(throwable).isNull();
	}
	
	@Then("^Uma excecao deve ser lancada com a mensagem de erro:$")
	public void uma_excecao_deve_ser_lancada_com_a_mensagem_de_erro(String message) throws Throwable {
		assertThat(throwable).hasMessage(message);
	}	
	
	

}