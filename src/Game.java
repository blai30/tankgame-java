

import util.Key;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Game extends JPanel {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;

    private static HashMap<Integer, Key> controls1;
    private static HashMap<Integer, Key> controls2;

    private JFrame screen;
    private Tank tank1;
    private Tank tank2;

    private static void setControls() {
        controls1.put(KeyEvent.VK_UP, Key.up);
        controls1.put(KeyEvent.VK_DOWN, Key.down);
        controls1.put(KeyEvent.VK_LEFT, Key.left);
        controls1.put(KeyEvent.VK_RIGHT, Key.right);
        controls1.put(KeyEvent.VK_SLASH, Key.fire);

        controls2.put(KeyEvent.VK_W, Key.up);
        controls2.put(KeyEvent.VK_S, Key.down);
        controls2.put(KeyEvent.VK_A, Key.left);
        controls2.put(KeyEvent.VK_D, Key.right);
        controls2.put(KeyEvent.VK_F, Key.fire);
    }

    private void init() {
        TankController ctrlTank1 = new TankController(tank1, controls1);
        TankController ctrlTank2 = new TankController(tank2, controls2);

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
