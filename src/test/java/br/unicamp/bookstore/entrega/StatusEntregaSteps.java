package br.unicamp.bookstore.entrega;

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
import br.unicamp.bookstore.model.StatusEntrega;
import br.unicamp.bookstore.service.StatusEntregaService;

import com.github.tomakehurst.wiremock.WireMockServer;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StatusEntregaSteps {

	public WireMockServer wireMockServer;

	@Mock
	private Configuracao configuration;

	@InjectMocks
	private StatusEntregaService statusEntregaService;

	private StatusEntrega statusEntrega;

	private String codigoRastreamento;

	private Throwable throwable;


	@Before
	public void setUp() {
		wireMockServer = new WireMockServer(9876);
		wireMockServer.start();
		MockitoAnnotations.initMocks(this);
		Mockito.when(configuration.getStatusEntregaUrl()).thenReturn("http://localhost:9876/ws");
		statusEntrega = null;
		codigoRastreamento = null;
		throwable = null;
	}

	@After
	public void teardown() {
		wireMockServer.stop();
	}

	@Given("^um codigo de rastreamento valido:$")
	public void eu_possuo_um_codigo_rastreamento_valido(Map<String, String> map) throws Throwable {
		codigoRastreamento = map.get("codigoRastreamento");
		wireMockServer.stubFor(get(urlMatching("/ws/"+ codigoRastreamento + ".*")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-StatusEntrega.xml")));
	}

	@Given("^um codigo de rastreamento nao existente:$")
	public void um_codigo_rastreamento_nao_existente(Map<String, String> map) throws Throwable {
		codigoRastreamento = map.get("codigoRastreamento");
		wireMockServer.stubFor(get(urlMatching("/ws/" + codigoRastreamento + ".*")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-StatusEntrega_ERR.xml")));

	}

	@Given("^um codigo de rastreamento invalido:")
	public void um_codigo_rastreamento_invalido(Map<String, String> map) throws Throwable {
		codigoRastreamento = map.get("codigoRastreamento");
		wireMockServer.stubFor(get(urlMatching("/ws/" + codigoRastreamento + ".*"))
				.willReturn(aResponse().withStatus(406).withHeader("Content-Type", "text/xml")
						.withBodyFile("resultado-pesquisa-StatusEntrega_BAD.xml")));
	}

	@When("^eu informo o codigo de rastreamento na busca$")
	public void eu_informo_o_codigo_rastreamento_na_busca_de_status() throws Throwable {
		throwable = catchThrowable(() -> this.statusEntrega = statusEntregaService.buscar(codigoRastreamento));
	}

	@Then("^o resultado deve ser o status do pedido:$")
	public void o_resultado_deve_ser_o_status_do_pedido(List<Map<String,String>> resultado)
			throws Throwable {
		assertThat(this.statusEntrega.getCodigoRastreamento()).isEqualTo(resultado.get(0).get("codigoRastreamento"));
		assertThat(this.statusEntrega.getStatus()).isEqualTo(resultado.get(0).get("status"));
		assertThat(throwable).isNull();
	}

	@Then("^o retorno deve conter um valor de erro igual a \"([^\"]*)\"$")
	public void o_retorno_deve_conter_um_valor_de_erro_igual_a(String erro) throws Throwable {
		assertThat(statusEntrega.getErro()).isEqualTo(erro);
		assertThat(throwable).isNull();
	}

	@And("^o servico Rastreamento nao esta respondendo$")
	public void o_servico_rastreamento_nao_esta_respondendo() throws Throwable {
		wireMockServer.stubFor(get(urlMatching("/ws/.*")).willReturn(aResponse().withStatus(200)
				.withFixedDelay(6000).withBodyFile("resultado-pesquisa-StatusEntrega_out.xml")));
	}

	@Then("^uma excecao deve ser lancada com a mensagem de erro:$")
	public void uma_excecao_deve_ser_lancada_com_a_mensagem_de_erro(String message) throws Throwable {
		assertThat(throwable).hasMessage(message);
	}
}