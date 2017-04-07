Feature: GameBoard

Scenario: When a tile is Placed on an empty board
  Given an empty board
  When  a tile is placed
  Then the tile is added on the board

Scenario: When a tile is placed flat on level one adjacent to an existing tile
  Given The board is not empty
  When The tile is placed
  Then the tile is added to the map

Scenario: When a tile is placed flat on level one not adjacent to an existing tile
  Given The board is not empty
  When The tile is placed away from other tiles
  Then the tile is not added to the map

Scenario: Place a Tile onto the Map
  Given The board is not empty
  When the tile is plaed in a nonempty spot
  Then the tile is not added to the map due to the Tile overhanging

Scenario: Placing a tile on top of and destroying a settlement
  Given The tile is placed on two or three tiles at level two or higher
  And The two non-volcanic hexes are occupies by the same size two settlement
  When The tile is placed in that location
  Then the placement is rejected

Scenario: Placing a tile on top of a settlement that wont be destroyed and eliminating the meeple's
  Given The tile is placed on two or three tiles at level two or higher
  And At least one of the covered hexes is occupied, but will not be destroyed
  When The tile is placed on that spot
  Then The tile is added to the board