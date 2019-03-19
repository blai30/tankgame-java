

import util.Key;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Game extends JPanel {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    public static HashMap<Integer, Key> controls1;
    public static HashMap<Integer, Key> controls2;

    private JFrame screen;
    private Tank tank1;
    private Tank tank2;

    private static void setControls() {
        controls1 = new HashMap<>();
    }

    private void init() {
        TankController ctrlTank1 = new TankController(tank1, controls1);

        this.screen.setLayout(new BorderLayout());
        this.screen.add(this);
        this.screen.setSize(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
        this.screen.setResizable(false);
        this.screen.setLocationRelativeTo(null);
        this.screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.screen.setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.init();
    }

}
