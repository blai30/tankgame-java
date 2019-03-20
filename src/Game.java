

import util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Game extends JPanel {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;

    private static boolean running = false;

    private BufferedImage world;
    private Graphics2D buffer;
    private JFrame window;

    private Tank tank1;
    private Tank tank2;
    private static HashMap<Integer, Key> controls1;
    private static HashMap<Integer, Key> controls2;

    public static ArrayList<GameObject> gameObjects;

    private void setControls() {
        controls1 = new HashMap<>();
        controls2 = new HashMap<>();

        // Set Player 1 controls
        controls1.put(KeyEvent.VK_UP, Key.up);
        controls1.put(KeyEvent.VK_DOWN, Key.down);
        controls1.put(KeyEvent.VK_LEFT, Key.left);
        controls1.put(KeyEvent.VK_RIGHT, Key.right);
        controls1.put(KeyEvent.VK_SLASH, Key.action);

        // Set Player 2 controls
        controls2.put(KeyEvent.VK_W, Key.up);
        controls2.put(KeyEvent.VK_S, Key.down);
        controls2.put(KeyEvent.VK_A, Key.left);
        controls2.put(KeyEvent.VK_D, Key.right);
        controls2.put(KeyEvent.VK_F, Key.action);
    }

    private void init() {
        running = true;
        gameObjects = new ArrayList<>();

        // Creating game window
        this.window = new JFrame("Tank Game");
        this.world = new BufferedImage(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);

        BufferedImage sprTank1 = null;
        BufferedImage sprTank2 = null;
        BufferedImage sprBullet1 = null;
        BufferedImage sprBullet2 = null;
        Image icon = null;

        // Loading sprites
        try {
            System.out.println(System.getProperty("user.dir"));
            sprTank1 = ImageIO.read(Game.class.getResourceAsStream("resources/tank1.png"));
            sprTank2 = ImageIO.read(Game.class.getResourceAsStream("resources/tank2.png"));
            sprBullet1 = ImageIO.read(Game.class.getResourceAsStream("resources/bullet1.png"));
            sprBullet2 = ImageIO.read(Game.class.getResourceAsStream("resources/bullet2.png"));
            icon = ImageIO.read(Game.class.getResourceAsStream("resources/icon.png"));
        } catch (IOException e) {
            System.out.println("IOException: cannot read image file");
            e.printStackTrace();
        }

        // Instantiating tanks
        this.tank1 = new Tank(new Vector2D(200, 200), 0f, sprTank1, sprBullet1);
        this.tank2 = new Tank(new Vector2D(400, 400), 0f, sprTank2, sprBullet2);
        TankController tankController1 = new TankController(tank1, controls1);
        TankController tankController2 = new TankController(tank2, controls2);
        this.window.addKeyListener(tankController1);
        this.window.addKeyListener(tankController2);

        // Setting JFrame properties
        this.window.setIconImage(icon);
        this.window.setLayout(new BorderLayout());
        this.window.add(this);
        this.window.setSize(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
        this.window.setResizable(false);
        this.window.setLocationRelativeTo(null);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setVisible(true);
    }

    public static void add(GameObject newObj) {
        gameObjects.add(newObj);
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

        this.tank1.drawSprite(buffer);
        this.tank2.drawSprite(buffer);
        for (GameObject obj : gameObjects) {
            obj.drawSprite(buffer);
        }
        g2.drawImage(world,0,0,null);
        buffer.clearRect(0, 0, getWidth(), getHeight());
    }

}
