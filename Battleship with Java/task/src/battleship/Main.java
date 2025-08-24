package battleship;

import player.Player;
import ship.Type;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        int playersWhoHavePlacedShips = 0;
        Player activePlayer = player2;

        while (playersWhoHavePlacedShips < 2) {
            activePlayer = activePlayer.equals(player1) ? player2 : player1;
            System.out.printf("%s, place your ships on the game field\n\n", activePlayer.getName());
            activePlayer.getBoard().printField(false);

            for (Type type : Type.values()) {
                System.out.printf("Enter the coordinates of the %s (%d cells):\n\n", type.getName(), type.getLength());

                boolean shipNotPlaced = true;
                while (shipNotPlaced) {
                    String coordinates = sc.nextLine();
                    System.out.println();
                    String placedShipID = activePlayer.getBoard().placeShip(coordinates, type.getName(), type.getLength());

                    if (!placedShipID.isEmpty()) {
                        activePlayer.getBoard().printField(false);
                        shipNotPlaced = false;
                    }
                }
            }

            System.out.print("Press Enter and pass the move to another player");
            sc.nextLine();
            System.out.println("...");
            playersWhoHavePlacedShips++;
        }

//        After 2 iterations of the above loop, activePlayer is player2.
//        inActivePlayer is always opposite of activePlayer
        Player inActivePlayer = player1;

        while (!activePlayer.getBoard().allShipsHaveSunk && !inActivePlayer.getBoard().allShipsHaveSunk) {
            activePlayer = activePlayer.equals(player1) ? player2 : player1;
            inActivePlayer = activePlayer.equals(player1) ? player2 : player1; // opposite of activePlayer

            inActivePlayer.getBoard().printField(true);
            System.out.println("---------------------");
            activePlayer.getBoard().printField(false);

            System.out.printf("%s, it's your turn:\n\n", activePlayer.getName());

            while (true) {
                String shotCoord = sc.nextLine().trim();
                System.out.println();
                if (inActivePlayer.getBoard().registerShot(shotCoord)) {
                    break;
                }
            }

            if (!activePlayer.getBoard().allShipsHaveSunk && !inActivePlayer.getBoard().allShipsHaveSunk) {
                System.out.print("\nPress Enter and pass the move to another player");
                sc.nextLine();
                System.out.println("...\n");
            }
        }

        sc.close();
    }
}
