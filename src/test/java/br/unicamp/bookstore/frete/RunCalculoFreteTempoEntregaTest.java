package br.unicamp.bookstore.frete;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "html:target/cucumber" },
        glue = "br.unicamp.bookstore.frete",
        features = "classpath:features/CalculoFreteTempo.feature",
        tags = "@CalculoFrete"
)
public class RunCalculoFreteTempoEntregaTest {
}

