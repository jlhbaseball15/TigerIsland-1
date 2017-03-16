Feature: Map

Scenario: When a tile is Placed
  Given an empty board
  When  a tile is placed
  Then the tile is added on the board
