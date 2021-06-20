package GameEntities;

public class Board {
    private Bomb[][] bombs;

    private int size;
    private int width;
    private int height;
    private int squareWidth;
    private int squareHeight;
    private Ship[] ships;

    public Board(int width, int height, int size, Ship[] ships) {
        // add ships manually
        this.width = width;
        this.height = height;
        this.size = size;
        if(width == 0) {
            width = 640;
        }
        if(height == 0) {
            height = 640;
        }
        squareWidth = width / size;
        squareHeight = height / size;
        bombs = new Bomb[size][size];
        this.ships = ships;
    }
    public Ship attack(int x, int y) { // change to coordinates
        Ship ship = shipAt(x,y);
        if(ship != null) {
            ship.hit();
            return ship;
        } else {
            return null;
        }
    }
    public Ship shipAt(int x, int y) {
        int xOffset;
        int yOffset;
        Ship returnShip = null;
        for (Ship ship : ships) {
            xOffset = x - ship.getX();
            yOffset = y - ship.getY();
            if(ship.isVertical()) {
                if(xOffset >= 0 && xOffset < getSquareWidth() && yOffset >=0 && yOffset < getSquareHeight() * ship.getLength()) {
                    returnShip = ship;
                }
            } else {
                if(xOffset >= 0 && xOffset < getSquareWidth() * ship.getLength() && yOffset >=0 && yOffset < getSquareHeight()) {
                    returnShip = ship;
                }
            }

        }
        return returnShip;
    }


    public boolean overlapping(Ship currentShip) {
        int count = 0;
        int x, y, xBound, yBound;

        x = currentShip.getX();
        y = currentShip.getY();
        xBound = currentShip.getXBound(squareWidth);
        yBound = currentShip.getYBound(squareHeight);

        for (Ship ship : ships) {
            if(((ship.getX() >= x && ship.getXBound(squareWidth) <= xBound) || (ship.getX() <= x && ship.getXBound(squareWidth) >= xBound)) && ((ship.getY() >= y && ship.getYBound(squareHeight) <= yBound) || (ship.getY() <= y && ship.getYBound(squareHeight) >= yBound))) {
                count++;
            } else if(((ship.getX() <= x && ship.getXBound(squareWidth) >= xBound) || (ship.getX() >= x && ship.getXBound(squareWidth) <= xBound)) && ((ship.getY() <= y && ship.getYBound(squareHeight) >= yBound) || (ship.getY() >= y && ship.getYBound(squareHeight) <= yBound))) {
                count++;
            } else if ((x >= ship.getX() && x < ship.getXBound(squareWidth)) && (y >= ship.getY() && y < ship.getYBound(squareHeight))) {
                count++;
            } else if ((xBound >= ship.getX() && xBound < ship.getXBound(squareWidth)) && (yBound >= ship.getY() && yBound < ship.getYBound(squareHeight))) {
                count++;
            }
        }
        return count > 1;
    }

    public int getSquareWidth() {
        return squareWidth;
    }

    public int getSquareHeight() {
        return squareHeight;
    }
    public Bomb[][] getBombs() {
        return bombs;
    }

}
