Feature: Tiger

  Scenario: When a Tiger is placed legally
    Given the hex level is greater than two
    When a Tiger is placed
    Then the Tiger will be added to the hex

  Scenario: a Tiger is placed on a volcaneo
    Given the hex is level three or higher
    When a tried Tiger is placed on a volcaneo type
    Then the Tiger is not added to the hex

  Scenario: a Tiger is placed on a hex level less than 3
    Given the hex level is less than three
    When a Tiger tried is placed
    Then the Tiger is not added to the HEX

  Scenario: a Tiger is placed on an occiped
    Given the hex is occupied for a tiger
    When a Tiger is placed on top of it
    Then Tiger is not added to the hex instead

  Scenario:  the player wants to add a tiger thats has one
    Given the settlement has a Tiger
    When the player wants to add another tiger
    Then the tiger is not added to the hex given

  Scenario:the player has played all their tiger pieces
    Given the settlement has all other attributes for tiger filled
    When the player wants to put a tiger
    Then the tiger is not added

  Scenario:tigers have to be next to the settlement
    Given the hex level is greater than two
    When the player wants to put a tiger away from the settlement
    Then the tiger is not added to the hex