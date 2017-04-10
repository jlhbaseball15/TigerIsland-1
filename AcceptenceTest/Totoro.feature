Feature: Totoro

Scenario: When a Totoro is placed legally
Given the settlement has a size 5
When a Totoro is placed
Then the Totoro will be added to the hex

  Scenario: When a Totoro is placed on a volcaneo
    Given the settlement has a size 5
    When a tried Totoro is placed on a volcaneo type
    Then the Totoro is not added to the hex

  Scenario: When a Totoro is placed next to settlement less than 5
    Given the settlement size is less than five
    When a Totoro tried is placed
    Then the Totoro is not added to the settlement

  Scenario: When a Totoro is placed on an occiped
     Given the hex is occupied for a totoro
    When a Totoro is placed on top of it
    Then Totoro is not added to the hex instead

  Scenario: When the player wants to expand a settlement
    Given the settlement has a Totoro
    When it will connect to another settlement that has a Totoro
    Then all adjacent hexes with given Terrain Type are now occupied between the settlements

  Scenario: When the player wants to add a totoro thats right next to anothersettlement that has one
    Given the settlement has a Totoro almost next to one that does not
    When the totoro will connect the two settlements
    Then the totoro is added

  Scenario:the player has played all their totoro pieces
    Given the settlement has all other attributes filled
    When the player wants to put a totoro
    Then the totoro is not added

  Scenario:totoros have to be next to the settlement
    Given the settlement has all other attributes filled but placement
    When the player wants to put a totoro away from the settlement
    Then the totoro is not added to the hex