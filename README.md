# Battleship

This project is a full implementation of the classic two-player strategy game *Battleship*. Players place their fleets
on a 10×10 grid and take turns firing shots at the opponent’s grid, trying to sink all the opponent’s ships first. The
program was developed incrementally, with each stage building toward a complete multiplayer version of the game.

1. **Stage 1 - Create the field:**  
   Print an empty 10×10 game field using the standard conventions (rows A–J, columns 1–10). Allow the player to place a
   single ship by entering its start and end coordinates, validating its length and alignment, and listing the ship’s
   parts.

2. **Stage 2 - Place all ships:**  
   Add all five ships (Aircraft Carrier, Battleship, Submarine, Cruiser, and Destroyer) to the field. Enforce correct
   lengths, prevent overlap, and ensure ships are not placed adjacent to each other. Print the updated field after each
   placement, and display error messages for invalid input.

3. **Stage 3 - Add shooting functionality:**  
   Allow the player to fire a shot at the field by entering coordinates. A hit is marked with `X`, a miss with `M`, and
   messages inform the player whether they hit or missed.

4. **Stage 4 - Implement the fog of war feature:**  
   Hide the positions of ships behind the fog of war (`~`) when displaying the opponent’s grid. After each shot, display
   both the fogged opponent’s grid and the player’s full grid for context.

5. **Stage 5 - Define rules to end the game:**  
   Continue gameplay until all ships are sunk. Print “You sank a ship!” when a ship is destroyed, and end with the
   victory message “You sank the last ship. You won. Congratulations!” once all ships have been sunk.

6. **Stage 6 - Add multiplayer option:**  
   Support two players, each with their own grid and fleet. Players alternate turns, firing at each other’s grids.
   Between turns, the program prompts to press Enter to pass control, ensuring fair play. The game ends when one player
   sinks the entire fleet of the opponent.

## Demo

*Note: Gameplay was cut-in-between to conform to size limit constraints*
<video width="1920" height="1080" align="center" src="https://github.com/user-attachments/assets/0dff6194-b1c0-4073-99f7-8129a6094f75"></video>

## Takeaway

This project significantly bolstered my skills in managing state, validating input, and handling game logic in Java. I
gained practical experience with two-dimensional arrays, object-oriented design, and encapsulation by building out
separate classes for boards, players, and ships. Implementing features like adjacency rules, fog of war, and turn-based
multiplayer strengthened my ability to manage complex rule sets and interactions across objects. By completing this
project, I advanced my ability to design interactive systems with clear progression from setup to endgame, while also
sharpening my focus on user experience and fairness in multiplayer contexts.
