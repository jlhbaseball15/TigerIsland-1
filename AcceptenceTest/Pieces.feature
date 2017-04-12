Feature: Pieces

Scenario:
  Given the Terrain Type is not a volcano and the hex is unoccupied
  When a meeple is placed
  Then the meeple will be added to the hex

  Scenario: When a meeple is placed
    Given the Terrain Type is a volcano
    When A meeple is placed
    Then the meeple is not added to the hex

  Scenario: When a meeple is placed
    Given the hex is occupied
    When a Meeple is placed
    Then the Meeple is not added to the hex

  Scenario: When a meeple is placed on Tile with level greater than one
    Given the hex is not level one and the Terrain Type is not a volcano
    When the meeples isare placed
    Then the point is added to the settlement

  Scenario: Meeple placed on an empty spot in the board
    Given there is an empty spot
    When A Meeple is placed
    Then The Meeple Is Not Added

Scenario: Cannot Build a new settlement on an occpied Hex
  Given There is an occupided hex on the map
  When A meeple is placed there
  Then The Meeple is not added there

Scenario: Cannot Build A new settlement on a nonlevel one hex
  Given There is an unoccupied nonlevel one hex on the map
  When A settlement is built there
  Then the settlement in not created

Scenario: Player does not have enough villagers
  Given The player does have not any remaining meeple
 When The player tries to place a meeple
  Then The player cannot add a meeple