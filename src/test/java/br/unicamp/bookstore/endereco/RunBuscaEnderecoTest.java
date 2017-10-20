package br.unicamp.bookstore.endereco;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "html:target/cucumber" },
        glue = "br.unicamp.bookstore.endereco",
        features = "classpath:features/BuscaEndereco.feature"
)
public class RunBuscaEnderecoTest {
}

