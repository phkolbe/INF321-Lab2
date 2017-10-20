@CalculoFrete
Feature: Calcular frete e tempo de entrega previsto
  Como Bookstore
  Eu quero saber quanto irei cobrar pelo frete e quanto levara para entregar o produto
  Assim posso informar ao meu cliente

  Scenario: Calcular frete e tempo de entrega via Sedex10 de um produto com valores validos
  	Given Informa ao sistema de Correios o cep, peso, largura e comprimento do produto e tipo de entrega:
  	| cep | 13083970 |
  	|pesoProduto | 5 | 
  	| larguraProduto | 150 | 
  	|comprimentoProduto | 50 | 
  	| tipoEntrega | 40215 |
  	When Eu envio os dados para o servico dos Correios
  	Then Recebo valor do frete e tempo de entrega
  	| valorFrete | prazoEntrega |
    | 13.2 | 7 |
  	And Salvo valor do frete e tempo de entrega na base de dados
  
  
 Scenario: Calcular frete e tempo de entrega de um produto com tipo de entrega inexistente
  	Given Informa tipo de entrega inexistente:
  	| cep | 13083970 |
  	|pesoProduto | 5 | 
  	| larguraProduto | 150 | 
  	|comprimentoProduto | 50 | 
  	| tipoEntrega | 77777 |
  	When Eu envio os dados para o servico dos Correios
  	Then O retorno deve conter um valor de erro igual a "true"
  	
 Scenario: Calcular frete e tempo de entrega de um produto com cep inexistente
  	Given Informa cep inexistente:
  	| cep | 99999999 |
  	|pesoProduto | 5 | 
  	| larguraProduto | 150 | 
  	|comprimentoProduto | 50 | 
  	| tipoEntrega | 40215 |
  	When Eu envio os dados para o servico dos Correios
  	Then O retorno deve conter um valor de erro igual a "true" 
  	
  Scenario: Servico CalcPrecoPrazo dos Correios nao esta respondendo
  	Given Informa ao sistema de Correios o cep, peso, largura e comprimento do produto e tipo de entrega:
  	| cep | 13083970 |
  	|pesoProduto | 5 | 
  	| larguraProduto | 150 | 
  	|comprimentoProduto | 50 | 
  	| tipoEntrega | 40215 |
  	And O servico CalcPrecoPrazo nao responde
  	When Eu envio os dados para o servico dos Correios
  	Then Uma excecao deve ser lancada com a mensagem de erro:  	 	
    """
    Servico indisponivel
    """ 
   
