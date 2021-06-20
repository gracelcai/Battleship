package GameEntities;

import javax.swing.*;
import java.awt.*;

public class Ship{
    private int x;
    private int y;

    private int length;
    private int bombs;

    private Image verticalImage;
    private Image horizontalImage;

    private boolean vertical;

    private boolean sunk;

    public Ship(int x, int y, int length, boolean orientation, Image horizontalImage, Image verticalImage) {
        this.x = x;
        this.y = y;
        this.horizontalImage = horizontalImage;
        this.verticalImage = verticalImage;
        this.length = length;
        this.vertical = orientation;
        bombs = 0;
        sunk = false;
    }

    public int getYBound(int squareHeight) {
        if(vertical) {
            return y + length * squareHeight;
        }
        return y + squareHeight;
    }

    public int getXBound(int squareWidth) {
        if(vertical) {
            return x + squareWidth;
        }
        return x + length * squareWidth;
    }
    public Image getImage() {
        if(!isVertical()) {
            return horizontalImage;
        } else {
            return verticalImage;
        }
    }
    public int getLength() {
        return length;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public void hit() {
        bombs++;
        if(bombs >= length) {
            sunk = true;
        }
    }

    public boolean isSunk() {
        return sunk;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
