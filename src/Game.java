

import util.Key;
import util.Vector2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Game extends JPanel {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;

    private static boolean running = false;

    private static HashMap<Integer, Key> controls1;
    private static HashMap<Integer, Key> controls2;

    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame screen;
    private Tank tank1;
    private Tank tank2;

    private void setControls() {
        controls1 = new HashMap<>();
        controls2 = new HashMap<>();

        // Player 1
        controls1.put(KeyEvent.VK_UP, Key.up);
        controls1.put(KeyEvent.VK_DOWN, Key.down);
        controls1.put(KeyEvent.VK_LEFT, Key.left);
        controls1.put(KeyEvent.VK_RIGHT, Key.right);
        controls1.put(KeyEvent.VK_SLASH, Key.action);

        // Player 2
        controls2.put(KeyEvent.VK_W, Key.up);
        controls2.put(KeyEvent.VK_S, Key.down);
        controls2.put(KeyEvent.VK_A, Key.left);
        controls2.put(KeyEvent.VK_D, Key.right);
        controls2.put(KeyEvent.VK_F, Key.action);
    }

    private void init() {
        running = true;

        this.screen = new JFrame("Tank Game");
        this.world = new BufferedImage(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage sprTank1 = null;
        BufferedImage sprTank2 = null;

        try {
            System.out.println(System.getProperty("user.dir"));
            sprTank1 = ImageIO.read(Game.class.getResourceAsStream("resources/tank1.png"));
            sprTank2 = ImageIO.read(Game.class.getResourceAsStream("resources/tank2.png"));
        } catch (IOException e) {
            System.out.println("IOException: cannot read image file");
            e.printStackTrace();
        }

        this.tank1 = new Tank(new Vector2D(200, 200), 0f, sprTank1);
        this.tank2 = new Tank(new Vector2D(400, 400), 0f, sprTank2);

        TankController tankController1 = new TankController(tank1, controls1);
        TankController tankController2 = new TankController(tank2, controls2);

        this.screen.addKeyListener(tankController1);
        this.screen.addKeyListener(tankController2);

        this.screen.setLayout(new BorderLayout());
        this.screen.add(this);
        this.screen.setSize(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
        this.screen.setResizable(false);
        this.screen.setLocationRelativeTo(null);
        this.screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.screen.setVisible(true);
    }

    public static void main(String[] args) {
        Thread thread;
        Game game = new Game();
        game.setControls();
        game.init();

        try {
            while (running) {
                game.tank1.update();
                game.tank2.update();
                game.repaint();
                System.out.println("[Tank1] " + game.tank1);
                System.out.println("[Tank2] " + game.tank2);
                System.out.println();
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);

        this.tank1.drawImage(buffer);
        this.tank2.drawImage(buffer);
        g2.drawImage(world,0,0,null);
    }

}
