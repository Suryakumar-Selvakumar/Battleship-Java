package player;

import board.Board;

public class Player {
    private final Board board;
    private final String name;

    public Player(String name) {
        this.board = new Board();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }
}
