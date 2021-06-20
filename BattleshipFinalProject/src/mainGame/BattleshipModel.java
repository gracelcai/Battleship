package mainGame;

import GameEntities.Ship;

public class BattleshipModel {
    private BattleshipView view;
    private BattleshipController controller;

    private Player firstPlayer;
    private Player secondPlayer;

    private boolean firstPlayerTurn;
    private boolean shipsSet;

    private int size;
    private int width;
    private int height;

    private int squareWidth;
    private int squareHeight;

    public BattleshipModel(int s, int w, int h) {
        size = s;
        width = w;
        height = h;
        squareWidth = width / size;
        squareHeight = height / size;

        firstPlayerTurn = true;
        shipsSet = false;

    }

    public boolean won() {
        boolean allSunk = true;
        for(Ship ship : getCurrentOpponent().getShips()) {
            if(!ship.isSunk()) {
                allSunk = false;
                break;
            }
        }
        return allSunk;
    }
    public int getSize() {
        return size;
    }


    public void setView(BattleshipView view) {
        this.view = view;
        firstPlayer = new Player(width, height, size, "Player 1");
        secondPlayer = new Player(width, height, size, "Player 2");
    }

    public void setController(BattleshipController controller) {
        this.controller = controller;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public int getSquareWidth() {
        return squareWidth;
    }

    public int getSquareHeight() {
        return squareHeight;
    }
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public Player getCurrentPlayer() {
        if(isFirstPlayerTurn()) {
            return firstPlayer;
        }
        return secondPlayer;
    }

    public Player getCurrentOpponent() {
        if(isFirstPlayerTurn()) {
            return secondPlayer;
        }
        return firstPlayer;
    }

    public boolean isShipsSet() {
        return shipsSet;
    }

    public void setShipsSet(boolean shipsSet) {
        this.shipsSet = shipsSet;
    }

    public boolean isFirstPlayerTurn() {
        return firstPlayerTurn;
    }

    public void setFirstPlayerTurn(boolean firstPlayerTurn) {
        this.firstPlayerTurn = firstPlayerTurn;
    }
}
