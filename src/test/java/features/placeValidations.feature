Feature: Validating response API's

  @ValidateResponse
  Scenario: Verify response (status code ang body(all 6 acceptance criteria))
    Given Api service
    When User call service  with GET request
    Then The API call got success with status code 200
    And id [int] greater than 0
    And name [String] more than 10 alpha characters
    And last [String] more than 10 alpha characters
    And age [int] must be integer and 0 > age < 120
    And gender [String] must only be "F" or "M"
    And response time less than 500 ms
