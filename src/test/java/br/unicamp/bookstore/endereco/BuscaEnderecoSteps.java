package br.unicamp.bookstore.endereco;

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
import br.unicamp.bookstore.model.Endereco;
import br.unicamp.bookstore.service.BuscaEnderecoService;

import com.github.tomakehurst.wiremock.WireMockServer;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class BuscaEnderecoSteps {

	public WireMockServer wireMockServer;

	@Mock
	private Configuracao configuration;

	@InjectMocks
	private BuscaEnderecoService buscaEnderecoService;

	private Endereco endereco;

	private String cep;

	private Throwable throwable;


	@Before
	public void setUp() {
		wireMockServer = new WireMockServer(9876);
		wireMockServer.start();
		MockitoAnnotations.initMocks(this);
		Mockito.when(configuration.getBuscarEnderecoUrl()).thenReturn("http://localhost:9876/ws");
		endereco = null;
		cep = null;
		throwable = null;
	}

	@After
	public void teardown() {
		wireMockServer.stop();
	}

	@Dado("^um CEP valido:$")
	public void eu_possuo_um_CEP_valido(Map<String, String> map) throws Throwable {
		cep = map.get("cep");
		wireMockServer.stubFor(get(urlMatching("/ws/"+ cep + ".*")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-BuscaEndereco.xml")));
	}

	@Dado("^um CEP nao existente:$")
	public void um_CEP_nao_existente(Map<String, String> map) throws Throwable {
		cep = map.get("cep");
		wireMockServer.stubFor(get(urlMatching("/ws/" + cep + ".*")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-BuscaEndereco_ERR.xml")));

	}

	@Dado("^um CEP invalido:")
	public void um_CEP_invalido(Map<String, String> map) throws Throwable {
		cep = map.get("cep");
		wireMockServer.stubFor(get(urlMatching("/ws/" + cep + ".*"))
				.willReturn(aResponse().withStatus(400).withHeader("Content-Type", "text/xml")
						.withBodyFile("resultado-pesquisa-BuscaEndereco_BAD.xml")));
	}

	@Quando("^eu informo o CEP na busca de endereco$")
	public void eu_informo_o_CEP_na_busca_de_endereco() throws Throwable {
		throwable = catchThrowable(() -> this.endereco = buscaEnderecoService.buscar(cep));
	}

	@Entao("^o resultado deve ser o endereco:$")
	public void o_resultado_deve_ser_o_endereco(List<Map<String,String>> resultado)
			throws Throwable {
		assertThat(this.endereco.getLogradouro()).isEqualTo(resultado.get(0).get("Logradouro"));
		assertThat(this.endereco.getLocalidade()).isEqualTo(resultado.get(0).get("Cidade"));
		assertThat(throwable).isNull();
	}

	@Entao("^o retorno deve conter um valor de erro igual a \"([^\"]*)\"$")
	public void o_retorno_deve_conter_um_valor_de_erro_igual_a(String erro) throws Throwable {
		assertThat(endereco.getErro()).isEqualTo(erro);
		assertThat(throwable).isNull();
	}

	@E("^o servico ViaCep nao esta respondendo$")
	public void o_servico_via_cep_nao_esta_respondendo() throws Throwable {
		wireMockServer.stubFor(get(urlMatching("/ws/.*")).willReturn(aResponse().withStatus(200)
				.withFixedDelay(6000).withBodyFile("resultado-pesquisa-BuscaEndereco_out.xml")));
	}

	@Entao("^uma excecao deve ser lancada com a mensagem de erro:$")
	public void uma_excecao_deve_ser_lancada_com_a_mensagem_de_erro(String message) throws Throwable {
		assertThat(throwable).hasMessage(message);
	}
}