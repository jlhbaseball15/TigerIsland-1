Feature: GameBoard

Scenario: When a tile is Placed on an empty board
  Given an empty board
  When  a tile is placed
  Then the tile is added on the board

  Scenario: When a tile is placed flat on level one adjacent to an existing tile
    Given The board is not empty
    When The tile is placed
    Then the tile is added to the map