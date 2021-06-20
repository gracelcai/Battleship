package mainGame;

import GameEntities.Bomb;
import GameEntities.Ship;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BattleshipView extends JPanel {
    private int width;
    private int height;
    private int squareWidth;
    private int squareHeight;
    private int size;
    private BattleshipModel model;
    private BattleshipController controller;
    private FleetPanel fleetPanel1;
    private FleetPanel fleetPanel2;
    private AttackPanel attackPanel1;
    private AttackPanel attackPanel2;

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    private CardLayout cardLayout;

    public BattleshipView(BattleshipModel d, BattleshipController c) {
        model = d;
        controller = c;
        this.width = model.getWidth();
        this.height = model.getHeight();
        this.size = model.getSize();
        squareWidth = model.getSquareWidth();
        squareHeight = model.getSquareHeight();

        model.setView(this);
        controller.setView(this);


        cardLayout = new CardLayout();
        setLayout(cardLayout);

        this.addMouseListener(controller);
        this.addMouseMotionListener(controller);

        JPanel titleScreen = setUpTitleScreen();
        this.add(titleScreen, "titleScreen");

        fleetPanel1 = new FleetPanel(model.getFirstPlayer());
        this.add(fleetPanel1, "fleetPanel1");

        fleetPanel2 = new FleetPanel(model.getSecondPlayer());
        this.add(fleetPanel2, "fleetPanel2");

        attackPanel1 = new AttackPanel(model.getFirstPlayer(), model.getSecondPlayer());
        this.add(attackPanel1, "attackPanel1");

        attackPanel2 = new AttackPanel(model.getSecondPlayer(), model.getFirstPlayer());
        this.add(attackPanel2, "attackPanel2");

        JPanel endingScreen = setUpEndingScreen();
        this.add(endingScreen, "endingScreen");
    }


    private class FleetPanel extends JPanel {
        private Player player;
        public FleetPanel(Player p) {
            player = p;
            this.setLayout(new BorderLayout());
            JPanel menuPanel = new JPanel();
            menuPanel.setLayout(new GridLayout(2, 1));
            JButton doneButton = new JButton("Done arranging ships. Ready to attack!");
            doneButton.addActionListener(controller);
            JLabel panelLabel = new JLabel(player.getName() + "'s fleet panel");
            panelLabel.setHorizontalAlignment(JLabel.CENTER);

            menuPanel.add(panelLabel);
            menuPanel.add(doneButton);

            this.add(menuPanel, BorderLayout.SOUTH);
        }

        public void paintComponent(Graphics g) {
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    g.setColor(new Color(16,170,230));
                    g.fillRect(j*squareWidth, i*squareHeight, squareWidth, squareHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(j*squareWidth, i*squareHeight, squareWidth, squareHeight);
                }
            }
            if(player != null){
                Ship[] ships = player.getShips();

                for (Ship ship : ships) {
                    if (ship.isVertical()) {
                        g.drawImage(ship.getImage(), ship.getX(), ship.getY(), squareHeight, ship.getLength() * squareWidth, null);
                    } else {
                        g.drawImage(ship.getImage(), ship.getX(), ship.getY(), ship.getLength() * squareWidth, squareHeight, null);

                    }
                }
            }
        }
    }

    private class AttackPanel extends JPanel {
        private Player player;
        private Player opponent;
        public AttackPanel(Player p, Player o) {
            player = p;
            opponent = o;
            this.setLayout(new BorderLayout());
            JPanel menuPanel = new JPanel();
            menuPanel.setLayout(new GridLayout(2, 1));
            JButton doneButton = new JButton("ATTACK!");
            doneButton.addActionListener(controller);
            JLabel panelLabel = new JLabel(player.getName() + "'s attack panel");
            panelLabel.setHorizontalAlignment(JLabel.CENTER);

            menuPanel.add(panelLabel);
            menuPanel.add(doneButton);

            this.add(menuPanel, BorderLayout.SOUTH);
        }

        public void paintComponent(Graphics g) {
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    g.setColor(new Color(16,170,230));
                    g.fillRect(j*squareWidth, i*squareHeight, squareWidth, squareHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(j*squareWidth, i*squareHeight, squareWidth, squareHeight);
                }
            }
            if(opponent != null){
                Ship[] ships = opponent.getShips();
                Bomb[][] bombs = (Bomb[][])opponent.getBoard().getBombs();

                for (Ship ship : ships) {
                    if(ship.isSunk()){
                        if (ship.isVertical()) {
                            g.drawImage(ship.getImage(), ship.getX(), ship.getY(), squareHeight, ship.getLength() * squareWidth, null);
                        } else {
                            g.drawImage(ship.getImage(), ship.getX(), ship.getY(), ship.getLength() * squareWidth, squareHeight, null);

                        }
                    }
                }

                for(Bomb[] row : bombs) {
                    for(Bomb bomb : row) {
                        if(bomb != null){
                            g.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), squareWidth, squareHeight, null);
                        }
                    }
                }
            }
        }

    }

    public JPanel setUpTitleScreen() {
        JPanel title = new JPanel();

        title.setLayout(new BorderLayout());

        JLabel gameText = createJLabelWithImageAndText("BattleshipBackground.png", "Battleship", Color.WHITE, 36);

        title.add(gameText, BorderLayout.CENTER);
        JPanel titleScreenSouth = new JPanel(); // default flowLayout
        JButton startButton = new JButton("Start");
        startButton.addActionListener(controller);

        titleScreenSouth.add(startButton);
        title.add(titleScreenSouth, BorderLayout.SOUTH);

        return title;
    }

    public JPanel setUpEndingScreen() {
        JPanel ending = new JPanel();

        ending.setLayout(new BorderLayout());

        JLabel gameText = createJLabelWithImageAndText("BattleshipBackground.png", "Game Over", Color.WHITE, 36);

        ending.add(gameText, BorderLayout.CENTER);
        JPanel titleScreenSouth = new JPanel(); // default flowLayout

        JLabel endingLabel = new JLabel(model.getCurrentPlayer().getName() + " won. Thanks for playing!");
        titleScreenSouth.add(endingLabel);
        ending.add(titleScreenSouth, BorderLayout.SOUTH);

        return ending;
    }
    public JLabel createJLabelWithImageAndText(String fileName, String text, Color color, int fontSize) {
        // http://stackoverflow.com/questions/20617695/cant-dispay-text-on-jlabel-having-background-image
        // http://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
        JLabel label = null;
        try {
            Image i = ImageIO.read(new File(fileName));
            label = new JLabel(new ImageIcon(i.getScaledInstance(width, height, 0)));
        } catch (IOException e) {
            e.printStackTrace();
            label = new JLabel(text);
        }
        label.setFont(new Font("Impact", Font.BOLD, fontSize));
        label.setForeground(color);
        label.setText(text);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);
        return label;
    }
}
