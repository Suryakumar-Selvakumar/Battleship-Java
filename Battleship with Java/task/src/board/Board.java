package board;

import ship.Ship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.stream.IntStream;

public class Board {
    static HashMap<Character, Integer> letterToRowIndex = new HashMap<>();
    static HashMap<Integer, Character> rowIndexToLetter = new HashMap<>();

    static {
        letterToRowIndex.put('A', 0);
        letterToRowIndex.put('B', 1);
        letterToRowIndex.put('C', 2);
        letterToRowIndex.put('D', 3);
        letterToRowIndex.put('E', 4);
        letterToRowIndex.put('F', 5);
        letterToRowIndex.put('G', 6);
        letterToRowIndex.put('H', 7);
        letterToRowIndex.put('I', 8);
        letterToRowIndex.put('J', 9);
        rowIndexToLetter.put(0, 'A');
        rowIndexToLetter.put(1, 'B');
        rowIndexToLetter.put(2, 'C');
        rowIndexToLetter.put(3, 'D');
        rowIndexToLetter.put(4, 'E');
        rowIndexToLetter.put(5, 'F');
        rowIndexToLetter.put(6, 'G');
        rowIndexToLetter.put(7, 'H');
        rowIndexToLetter.put(8, 'I');
        rowIndexToLetter.put(9, 'J');
    }

    public Object[][] field;
    public HashMap<String, Ship> ships;
    public ArrayList<String> coordsCloseToShip;
    public boolean allShipsHaveSunk;

    public Board() {
        this.field = new Object[][]{
                {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
                {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
                {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
                {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
                {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
                {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
                {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
                {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
                {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'},
                {'~', '~', '~', '~', '~', '~', '~', '~', '~', '~'}
        };
        this.ships = new HashMap<>();
        this.coordsCloseToShip = new ArrayList<>();
        this.allShipsHaveSunk = false;
    }

    public void printField(boolean hidden) {
        StringBuilder fieldStr = new StringBuilder();
        for (int i = -1; i < field.length; i++) {
            for (int j = -1; j < field[0].length; j++) {
                if (i == -1) {
                    fieldStr.append(j == -1 ? " " : " %d".formatted(j + 1));
                    continue;
                }

                if (j == -1) {
                    fieldStr.append("%c".formatted(rowIndexToLetter.get(i)));
                } else {
                    if (field[i][j] instanceof String) {
//                        Get ship state and print appropriate character
                        Ship ship = ships.get((String) field[i][j]);
                        LinkedHashMap<String, Boolean> parts = ship.getParts();
                        String coordinate = "" + rowIndexToLetter.get(i) + (j + 1);
                        if (parts.get(coordinate)) {
                            fieldStr.append(" X");
                        } else {
                            fieldStr.append(hidden ? " ~" : " O");
                        }
                    } else {
                        fieldStr.append(" %c".formatted((char) field[i][j]));
                    }
                }
            }
            fieldStr.append("\n");
        }
        if (hidden) {
            System.out.print(fieldStr);
        } else {
            System.out.println(fieldStr);

        }
    }

    public String placeShip(String coordinates, String name, int cells) {
        String[] coords = coordinates.split(" ");
        String shipID = "";

        if (validateCoordinates(coords, name, cells)) {
            String ID = UUID.randomUUID().toString();
            String[] parts = processParts(coords, ID);
            int shipLength = parts.length;
            Ship ship = new Ship(name, shipLength, parts, ID);
            ships.put(ship.getID(), ship);
            shipID = ship.getID();
        }

        return shipID;
    }

    String[] setStartEnd(String[] coords) {
        String start = coords[0];
        String end = coords[1];

        if (coords[0].charAt(0) == coords[1].charAt(0)) {
            if (coords[0].length() == 3 && coords[1].length() == 2) {
                start = coords[1];
                end = coords[0];
            }

            if (coords[0].length() == 2 && coords[1].length() == 2) {
                if (coords[0].charAt(1) > coords[1].charAt(1)) {
                    start = coords[1];
                    end = coords[0];
                }
            }
        } else {
            if (coords[0].charAt(0) > coords[1].charAt(0)) {
                start = coords[1];
                end = coords[0];
            }
        }

        return new String[]{start, end};
    }

    boolean validateCoordinates(String[] coords, String name, int cells) {
        String[] startAndEnd = setStartEnd(coords);
        String start = startAndEnd[0];
        String end = startAndEnd[1];
        int startCol = Integer.parseInt(new StringBuilder(start).substring(1));
        int endCol = Integer.parseInt(new StringBuilder(end).substring(1));

//        Ship Length check
        int length = 0;
        if (start.charAt(0) == end.charAt(0)) { // Horizontal length check
            for (int i = startCol; i <= endCol; i++) {
                length++;
            }
        } else { // Vertical length check
            for (int j = letterToRowIndex.get(start.charAt(0)); j <= letterToRowIndex.get(end.charAt(0)); j++) {
                length++;
            }
        }

        if (length != cells) {
            System.out.printf("Error! Wrong length of %s! Try again:\n\n", name);
            return false;
        }

//        Out of Bounds check
        if (start.charAt(0) > 'J' ||
                end.charAt(0) > 'J' ||
                startCol > 10 ||
                endCol > 10 ||
                startCol <= 0 ||
                endCol <= 0) {
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        }

//        Horizontal check
        if (start.charAt(0) == end.charAt(0) &&
                startCol == endCol) {
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        }

//        Vertical check
        if (start.charAt(0) != end.charAt(0) &&
                startCol != endCol) {
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        }

//        Horizontal vacancy check if same row
        if (start.charAt(0) == end.charAt(0)) {
            int coordsRow = letterToRowIndex.get(start.charAt(0));
            for (int i = startCol - 1; i < endCol; i++) {
                if (field[coordsRow][i] instanceof String) {
                    System.out.println("Error: A ship has already been placed here! Try again:\n");
                    return false;
                }
            }
        } else {
            // Vertical vacancy check if same column
            int coordsCol = startCol - 1;
            for (int j = letterToRowIndex.get(start.charAt(0)); j <= letterToRowIndex.get(end.charAt(0)); j++) {
                if (field[j][coordsCol] instanceof String) {
                    System.out.println("Error: A ship has already been placed here! Try again:\n");
                    return false;
                }
            }
        }

//        Coordinates not close to existing ships check
        if (start.charAt(0) == end.charAt(0)) { // Horizontal close coordinates check
            for (int i = startCol; i <= endCol; i++) {
                String currCoord = "" + start.charAt(0) + i;
                if (coordsCloseToShip.contains(currCoord)) {
                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                    return false;
                }
            }
        } else { // Vertical close coordinates check
            for (int j = letterToRowIndex.get(start.charAt(0)); j <= letterToRowIndex.get(end.charAt(0)); j++) {
                String currCoord = "" + rowIndexToLetter.get(j) + startCol;
                if (coordsCloseToShip.contains(currCoord)) {
                    System.out.println("Error! You placed it too close to another one. Try again:\n");
                    return false;
                }
            }
        }

        return true;
    }

    String[] processParts(String[] coords, String ID) {
        /*
         * returns the ship's parts
         * populates the field with new ship's ID
         * adds coordinates close to the new ship into coordsCloseToShips ArrayList
         * */
        String[] startAndEnd = setStartEnd(coords);
        String start = startAndEnd[0];
        String end = startAndEnd[1];
        int startCol = Integer.parseInt(new StringBuilder(start).substring(1));
        int endCol = Integer.parseInt(new StringBuilder(end).substring(1));
        StringBuilder parts = new StringBuilder();

//        Process horizontal parts
        if (start.charAt(0) == end.charAt(0)) {
            for (int i = startCol; i <= endCol; i++) {
                parts.append("%s%d ".formatted(start.charAt(0), i));

                field[letterToRowIndex.get(start.charAt(0))][i - 1] = ID;

                if (i == startCol && i - 1 >= 1) {
                    coordsCloseToShip.add("" + start.charAt(0) + (i - 1));
                }
                addCloseCoords(i, start.charAt(0));
                if (i == endCol && i + 1 <= 10) {
                    coordsCloseToShip.add("" + end.charAt(0) + (i + 1));
                }
            }
        } else { // Process vertical parts
            for (int j = letterToRowIndex.get(start.charAt(0)); j <= letterToRowIndex.get(end.charAt(0)); j++) {
                parts.append("%s%d ".formatted(rowIndexToLetter.get(j), startCol));

                field[j][startCol - 1] = ID;

                if (j == letterToRowIndex.get(start.charAt(0)) && j - 1 >= 0) {
                    coordsCloseToShip.add("" + rowIndexToLetter.get(j - 1) + startCol);
                }
                addCloseCoords(startCol, rowIndexToLetter.get(j));
                if (j == letterToRowIndex.get(end.charAt(0)) && j + 1 <= 9) {
                    coordsCloseToShip.add("" + rowIndexToLetter.get(j + 1) + endCol);
                }
            }
        }

        if (start.equals(coords[0])) {
            return parts.toString().split(" ");
        } else {
            String[] partsArr = parts.toString().split(" ");
            return IntStream.range(0, partsArr.length)
                    .mapToObj(i -> partsArr[partsArr.length - 1 - i])
                    .toArray(String[]::new);
        }
    }

    void addCloseCoords(int coordCol, char coordRow) {
        int previousRow = letterToRowIndex.get(coordRow) - 1;
        int nextRow = letterToRowIndex.get(coordRow) + 1;
        int prevCol = coordCol - 1;
        int nextCol = coordCol + 1;

        if (previousRow >= 0) {
            if (prevCol >= 1) {
                coordsCloseToShip.add("" + rowIndexToLetter.get(previousRow) + prevCol);
            }

            if (nextCol <= 10) {
                coordsCloseToShip.add("" + rowIndexToLetter.get(previousRow) + nextCol);
            }
        }

        if (nextRow >= 9) {
            if (prevCol >= 1) {
                coordsCloseToShip.add("" + rowIndexToLetter.get(nextRow) + prevCol);
            }

            if (nextCol <= 10) {
                coordsCloseToShip.add("" + rowIndexToLetter.get(nextRow) + nextCol);
            }
        }
    }

    public boolean registerShot(String shotCoord) {
        /*
         * Checks for valid coordinate and returns false if invalid
         * Updates a ship's corresponding part as hit if the shot hit
         * Updates the field's coordinate as miss if the shot missed
         * returns true if a shot successfully landed on the field
         * */
        char row = shotCoord.charAt(0);
        int col = Integer.parseInt(new StringBuilder(shotCoord).substring(1));

        if (letterToRowIndex.get(row) == null ||
                col < 1 || col > 10
        ) {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            return false;
        }

//        Update the ship's part's hit status to true if the shot hit a ship
        if (field[letterToRowIndex.get(row)][col - 1] instanceof String shipID) {
            Ship ship = ships.get(shipID);
            ship.getParts().put(shotCoord, true);

            if (haveAllShipsSunk()) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                allShipsHaveSunk = true;
            } else {
                if (ship.isSunk()) {
                    System.out.print("You sank a ship!");
                } else {
                    System.out.print("You hit a ship!");
                }
            }
        } else {
            // Update field's coordinate to 'M' if the shot missed
            field[letterToRowIndex.get(row)][col - 1] = 'M';
            System.out.print("You missed!");
        }

        return true;
    }

    public boolean haveAllShipsSunk() {
        int sunkShipsCount = 0;
        for (Ship ship : ships.values()) {
            if (ship.isSunk()) {
                sunkShipsCount++;
            }
        }

        return sunkShipsCount == ships.size();
    }
}
