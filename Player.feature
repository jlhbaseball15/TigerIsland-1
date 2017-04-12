Feature: Player

  Scenario: When meeple is placed on the board due to a settlement being constructed
    Given That the meeples can be placed
    When the player places meeples on board to create settlement
    Then the Value of remaining meeples is correctly updated

  Scenario: When totoro is placed on the board
    Given That the totoro can be placed
    When the player places totoro on the adjacent hex
    Then the Value of remaining totoro is correctly updated

  Scenario: When tiger is placed on the board
    Given That the tiger can be placed
    When the player places tiger on the adjacent hex
    Then the Value of remaining tiger is correctly updated

  Scenario: When meeples are placed on the board due to a settlement being expanded
    Given that the expansion can happen
    When the player places meeples on board to expand the settlement
    Then the Value of remaining meeples is correctly updated for exapansion

  Scenario: Place a meeple points
    Given That the meeples can be placed
    When the player places meeples on board to create settlement
    Then the player’s score is increased by number of meeple x level

  Scenario: Place a totoro points
    Given That the totoro can be placed
    When the player places totoro on the adjacent hex
    Then the player’s score is increased by 200

  Scenario: Place a tiger points
    Given That the tiger can be placed
    When the player places tiger on the adjacent hex
    Then the player’s score is increased by 75

  Scenario: When meeples are placed on the board due to a settlement being expanded
    Given that the expansion can happen
    When the player places meeples on board to expand the settlement
    Then the players score is increased by the number of meeple played based