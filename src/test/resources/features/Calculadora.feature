Feature: Calculator
  As a user
  I want to use a basic calculator
  So that I dont need to calculate myself

  Scenario: Sum two numbers
    Given I have a calculator
    When I sum 3 with 3
    Then the result should be 6
    And the result should be saved on the database

  Scenario Outline: Multiply two numbers
    Given I have a calculator
    When I multiply <firstNumber> with <secondNumber>
    Then the result should be <result>
    And the result should be saved on the database

    Examples: 
      | firstNumber | secondNumber | result |
      |           1 |            0 |      0 |
      |           2 |            2 |      4 |
      |           3 |           10 |     30 |

  Scenario: Division by zero
    Given I have a calculator
    When I divide 7 with 0
    Then should show an error with a message:
      """
      / by zero
      """

  Scenario: Divide two numbers
    Given I have a calculator
    When I divide 9 with 3
    Then the result should be 3
    And the result should be saved on the database
