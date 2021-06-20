package GameEntities;

import javax.swing.*;
import java.awt.*;

public class Bomb{

    private int x, y;
    private Image image;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        image = new ImageIcon("Bomb.gif").getImage();
    }

    public Image getImage() {
        return image;
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
