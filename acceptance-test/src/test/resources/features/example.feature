@Product
Feature: User would like to get products
  Background:
    Given the following products exists in the library
      | code | description                 |
      | 1    | Twinkle twinkle little star |
      | 2    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get all products
    When user requests for all products
    Then the user gets the following products
      | code | description                 |
      | 1    | Twinkle twinkle little star |
      | 2    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get products by code
    When user requests for products by code "1"
    Then the user gets the following products
      | code | description                 |
      | 1    | Twinkle twinkle little star |

  Scenario: User should get an appropriate NOT FOUND message while trying get products by an invalid code
    When user requests for products by id "10000" that does not exists
    Then the user gets an exception "Product with code 10000 does not exist"