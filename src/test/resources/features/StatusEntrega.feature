Feature: Verificar Status de Entrega do Pedido
  Como um usuario da Bookstore
  Desejo consultar status de entrega de um pedido pelo codigo de rastreamento
  Para que eu possa saber o andamento do pedido

  Scenario: Consultar um pedido com codigo de rastreamento valido
    Given um codigo de rastreamento valido:
      | codigoRastreamento | SQ458226057BR |
    When eu informo o codigo de rastreamento na busca
    Then o resultado deve ser o status do pedido:
      | codigoRastreamento | status |
      | SQ458226057BR | 7 |

  Scenario: Consultar um endereco com CEP nao existente
    Given um codigo de rastreamento nao existente:
      | codigoRastreamento | SQ999999999BR |
    When eu informo o codigo de rastreamento na busca
    Then o retorno deve conter um valor de erro igual a "true"

  Scenario: Consultar um status de pedido com codigo de rastreamento invalido.
    Given um codigo de rastreamento invalido:
      | codigoRastreamento | SQ458226057BRBRBR |
    When eu informo o codigo de rastreamento na busca
    Then uma excecao deve ser lancada com a mensagem de erro:
    """
    O codigo de rastreamento informado e invalido
    """

  Scenario: Servico Rastreamento nao responde
    Given um codigo de rastreamento valido:
      | codigoRastreamento | 13083970 |
    And o servico Rastreamento nao esta respondendo
    When eu informo o codigo de rastreamento na busca
    Then uma excecao deve ser lancada com a mensagem de erro:
    """
    Servico indisponivel
    """
