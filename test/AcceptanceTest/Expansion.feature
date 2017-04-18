Feature: Expansion

  Scenario: On creation of a settlement
    Given when a player finishes their turn
    When a new settlement is formed or its size recalculated
    Then all Meeples in the settlement will belong to the same player

  Scenario: On the end of the turn
    Given the player successfully build or expand in the turn
    When the turn is over
    Then all adjacent settlements will be count as one settlement with size same as the number of the hex it contains

  Scenario: On expansion of a settlement
    Given the player has the meeples for an expansion
    When the player expands
    Then all adjacent hexes with given Terrain Type are now occupied

  Scenario: On expansion of a settlement
    Given the player does not have the meeples for an expansion
    When the player tries expands
    Then all adjacent hexes with given Terrain Type are not occupied

  Scenario: On expansion of a settlement
    Given the player wants to expand on a terrain not adjacent to their settlement
    When the player wants expands
    Then adjacent hexes with given Terrain Type are not occupied

  Scenario: Cannot Expand Onto Volcanoes
    Given the player wants to expand on a volcanoe terrain
    When the player wants expands into volcaneos
    Then adjacent hexes with given Terrain Type volcaneo are not occupied