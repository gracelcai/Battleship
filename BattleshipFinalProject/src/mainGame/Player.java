package mainGame;

import GameEntities.*;

import javax.swing.*;

public class Player {
    private Board board;
    private String name;
    private Ship[] ships;

    private boolean firstAttack;

    public Player(int width, int height, int size, String name) {
        // create ship array and add ships manually to fleetBoard (after making graphics)
        int squareWidth = width / size;
        this.name = name;

        ships = new Ship[5];
        ships[0] = new Ship(0, 0, 2, true, new ImageIcon("Destroyer.gif").getImage(),new ImageIcon("VerticalDestroyer.gif").getImage());
        ships[1] = new Ship(squareWidth, 0, 3, true, new ImageIcon("Submarine.gif").getImage(), new ImageIcon("VerticalSubmarine.gif").getImage());
        ships[2] = new Ship(squareWidth*2, 0, 3, true, new ImageIcon("Cruiser.gif").getImage(), new ImageIcon("VerticalCruiser.gif").getImage());
        ships[3] = new Ship(squareWidth*3, 0, 4, true, new ImageIcon("Battleship.gif").getImage(), new ImageIcon("VerticalBattleship.gif").getImage());
        ships[4] = new Ship(squareWidth*4, 0, 5, true, new ImageIcon("Carrier.gif").getImage(), new ImageIcon("VerticalCarrier.gif").getImage());

        board = new Board(width, height, size, ships);
        firstAttack = true;
    }
    public Ship[] getShips() {
        return ships;
    }

    public Board getBoard() {
        return board;
    }

    public String getName() {
        return name;
    }
    public boolean isFirstAttack() {
        return firstAttack;
    }

    public void setFirstAttack(boolean firstAttack) {
        this.firstAttack = firstAttack;
    }
}
