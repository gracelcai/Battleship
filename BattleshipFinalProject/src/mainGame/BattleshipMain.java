package mainGame;

import javax.swing.*;

public class BattleshipMain {
    static final int GAMEWIDTH = 640;
    static final int GAMEHEIGHT = 640;

    public static void main(String[] args) {
        JFrame window = new JFrame("Battleship");

        BattleshipModel model = new BattleshipModel(10, GAMEWIDTH, GAMEHEIGHT);
        BattleshipController controller = new BattleshipController(model);
        BattleshipView view = new BattleshipView(model, controller);


        JMenuBar menuBar = new JMenuBar();
        JMenu infoMenu = new JMenu("Info");
        JMenuItem instructions = new JMenuItem("Instructions");
        instructions.addActionListener(controller);
        infoMenu.add(instructions);
        menuBar.add(infoMenu);


        window.setContentPane(view);
        window.setJMenuBar(menuBar);
        window.setSize(GAMEWIDTH,GAMEHEIGHT+100);
        window.setLocation( 100,100);
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setResizable(false);
        window.setVisible(true);
    }


}
