# Scoreboard Library

## Overview
The Live Football World Cup Scoreboard library provides functionality to manage and track ongoing football matches.

## Features
The scoreboard supports the following operations:

1. **Start a new game**: Adds a new game to the scoreboard with an initial score of 0 – 0.

2. **Update score**: Updates the score of an ongoing game.

3. **Finish game**: Removes a game currently in progress from the scoreboard.

4. **Get a summary of games in progress**: Retrieves a summary of ongoing games ordered by their total score. Games with the same total score are ordered by the most recently started match.

## Implementation

### Explanation of Data Structure Choice for `GameRepository`

Given that this is a World Cup football scoreboard, the following assumptions are made about operations on this structure:

1. **Frequent Retrieval of All Ongoing Games, Ordered by Total Score and Start Time**:
    - Retrieving a summary of all ongoing games, ordered by total score and start time, is very frequent because viewers often want to know live scores.

2. **Rare Starting, Updating, or Finishing of a Game**:
    - Starting, updating, or finishing a game occurs less frequently than retrieving the live scores. Each of these operations requires retrieval by game ID.
    - Retrieval by game ID is used only for these operations.

3. **Limited Number of Matches**:
    - As this is a World Cup scoreboard, there are around 100 matches only, with only a few matches happening simultaneously.

In summary frequent retrieval of all ongoing games, ordered by total score and start time, is the most frequent operation to optimize for, with much less frequent starting, updating, or finishing of a game. With a limited number of matches efficiency is a primary concern. \
To match these requirements, a `GameRepository` is implemented using a `HashMap` for quick lookups by game ID and a `TreeSet` for maintaining the order of games by total score and start time.

Time complexity of operations:
- **Start a game** - O(log n), since adding to the `TreeSet` is O(log n) and adding to the `HashMap` is O(1).
- **Update a game** - O(log n), since updating the `TreeSet` involves removing and re-adding the game, each operation being O(log n).
- **Finish game** - O(log n), since removing from the `TreeSet` is O(log n) and removing from the `HashMap` is O(1).
- **Get ongoing games summary sorted by total score and start time** - O(n), since iterating over the `TreeSet` is O(n).