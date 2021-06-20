package mainGame;

import GameEntities.Bomb;
import GameEntities.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BattleshipController implements MouseListener, MouseMotionListener, ActionListener {


    private BattleshipView view;
    private BattleshipModel model;
    private Ship currentShip;
    private Bomb currentBomb;
    private int xOffset;
    private int yOffset;
    private int prevX, prevY;
    private String instructions = "Battleship Instructions: \nThe goal of Battleship is to sink all of your opponents ships. " +
            "\nYou each start by arranging your ships on the board by dragging them around the board and right clicking to switch the orientation. " +
            "\nThen, you take turns dropping bombs on each other's board by clicking a space to select a spot to attack and then clicking the \"ATTACK!\" button. " +
            "\n Whoever sinks all their opponent's ships first wins! ";
    private String setupInstruction = "Arrange your ships by dragging them around the board. Right click to switch the orientation.";
    private String attackInstruction = "Click on a space on the board to select a spot to attack. Then click on the \"ATTACK!\" button.";
    private String hitMessage = "BOOM! You hit a ship!";
    private String sunkMessage = "You sunk a ship!";
    private String missMessage = "Aww, you missed.";

    public BattleshipController(BattleshipModel model) {
        this.model = model;
        model.setController(this);
        currentShip = null;
        currentBomb = null;
    }
    public void setView(BattleshipView view) {
        this.view = view;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int x, y, xPos, yPos;
        if(e.isMetaDown()) {
            if(!model.isShipsSet()) {
                currentShip = model.getCurrentPlayer().getBoard().shipAt(e.getX(),e.getY());
                if(currentShip != null) {
                    currentShip.setVertical(!currentShip.isVertical());
                    if(model.getCurrentPlayer().getBoard().overlapping(currentShip)) {
                        currentShip.setVertical(!currentShip.isVertical());
                    }
                }
            }
        }
        xPos = e.getX()/model.getSquareWidth();
        yPos = e.getY()/ model.getSquareHeight();
        if(xPos < 10 && yPos < 10) {
            if (model.isShipsSet() && model.getCurrentOpponent().getBoard().getBombs()[xPos][yPos] == null){
            x = e.getX() / model.getSquareWidth() * model.getSquareWidth();
            y = e.getY() / model.getSquareHeight() * model.getSquareHeight();
            xPos = x / model.getSquareWidth();
            yPos = y / model.getSquareHeight();

            if (currentBomb == null) {
                currentBomb = new Bomb(x, y);
                model.getCurrentOpponent().getBoard().getBombs()[xPos][yPos] = currentBomb;
            } else {
                model.getCurrentOpponent().getBoard().getBombs()[currentBomb.getX() / model.getSquareWidth()][currentBomb.getY() / model.getSquareHeight()] = null;
                currentBomb.setX(x);
                currentBomb.setY(y);
                model.getCurrentOpponent().getBoard().getBombs()[xPos][yPos] = currentBomb;
            }

        }
        }
        view.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!model.isShipsSet()) {
            currentShip = model.getCurrentPlayer().getBoard().shipAt(e.getX(), e.getY());
        }
        if(currentShip != null) {
            xOffset = e.getX() - currentShip.getX();
            yOffset = e.getY() - currentShip.getY();
            prevX = currentShip.getX();
            prevY = currentShip.getY();

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int xPos = (e.getX() - xOffset) / model.getSquareWidth();
        int yPos = (e.getY() - yOffset) / model.getSquareHeight();

        if(xPos < 0) {
            xPos = 0;
        }

        if(yPos < 0) {
            yPos = 0;
        }
        if(xPos >= model.getSize()) {
            xPos = model.getSize() - 1;
        }
        if(yPos >= model.getSize()) {
            yPos = model.getSize() - 1;
        }
        if(currentShip != null) {
            currentShip.setX(xPos * model.getSquareWidth());
            currentShip.setY(yPos * model.getSquareHeight());
            if(model.getCurrentPlayer().getBoard().overlapping(currentShip)) {
                currentShip.setX(prevX);
                currentShip.setY(prevY);
            }

        }
        view.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        CardLayout cardLayout = view.getCardLayout();
        if(command.equals("Start")) {
            cardLayout.show(view, "fleetPanel1");
            JOptionPane.showMessageDialog(view, "Player 1's turn! " + setupInstruction);
        }
        if(command.equals("Done arranging ships. Ready to attack!")) {
            if(model.isFirstPlayerTurn()) {
                cardLayout.show(view, "fleetPanel2");
                model.setFirstPlayerTurn(false);
                view.repaint();
                JOptionPane.showMessageDialog(view, "Player 2's turn! " + setupInstruction);

            } else {
                //show attack panel
                cardLayout.show(view, "attackPanel1");
                model.setFirstPlayerTurn(true);
                model.setShipsSet(true);
                view.repaint();
                JOptionPane.showMessageDialog(view, "Player 1's turn! " + attackInstruction);

            }
        }
        if(command.equals("ATTACK!")) {
            if(currentBomb != null) {
                currentShip = model.getCurrentOpponent().getBoard().attack(currentBomb.getX(), currentBomb.getY());
                currentBomb = null;
                if(currentShip != null) {
                    if(currentShip.isSunk()) {
                        JOptionPane.showMessageDialog(view, sunkMessage);

                    } else {
                        JOptionPane.showMessageDialog(view, hitMessage);
                    }
                } else {
                    JOptionPane.showMessageDialog(view, missMessage);
                }
                if(model.won()) {
                    cardLayout.show(view, "endingScreen");
                    return;
                }
                model.getCurrentPlayer().setFirstAttack(false);
                model.setFirstPlayerTurn(!model.isFirstPlayerTurn());
                if(model.isFirstPlayerTurn()) {
                    cardLayout.show(view, "attackPanel1");
                } else {
                    cardLayout.show(view, "attackPanel2");
                }
                if(model.getCurrentPlayer().isFirstAttack()) {
                    JOptionPane.showMessageDialog(view, model.getCurrentPlayer().getName() + "'s turn! " + attackInstruction);
                }
            }
        }
        if(command.equals("Instructions")) {
            JOptionPane.showMessageDialog(view, instructions);
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // stop at edges
        int x, y, xBound, yBound;

        if(currentShip != null) {

            x = e.getX() - xOffset;
            y = e.getY() - yOffset;

            if(currentShip.isVertical()) {
                xBound = model.getWidth() - model.getSquareWidth();
                yBound = model.getHeight() - model.getSquareHeight() * currentShip.getLength();
            } else {
                xBound = model.getWidth() - model.getSquareWidth() * currentShip.getLength();
                yBound = model.getHeight() - model.getSquareHeight();
            }
            if(x < 0) {
                x = 0;
            }

            if(y < 0) {
                y = 0;
            }
            if(x > xBound) {
                x = xBound;
            }
            if(y > yBound) {
                y = yBound;
            }
            currentShip.setX(x);
            currentShip.setY(y);
            view.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
