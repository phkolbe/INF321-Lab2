package br.unicamp.exemplo;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "html:target/cucumber" },
        glue = "br.unicamp.exemplo",
        features = "classpath:features/Calculadora.feature"
//        name = "nome do cenario"
)
public class RunCalculadoraTest {
}

