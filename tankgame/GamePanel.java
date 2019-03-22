

import GameObjects.GameObjectCollection;
import GameObjects.Tank;
import util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class GamePanel extends JPanel implements Runnable {

    public static final int SCREEN_WIDTH = 1600;
    public static final int SCREEN_HEIGHT = 900;

    private Thread thread;
    private boolean running = false;

    private BufferedImage background;
    private BufferedImage world;
    private Graphics2D buffer;

    private static HashMap<Integer, Key> controls1;
    private static HashMap<Integer, Key> controls2;

    public GamePanel() {
        this.setFocusable(true);
        this.requestFocus();
    }

    private void setControls() {
        controls1 = new HashMap<>();
        controls2 = new HashMap<>();

        // Set GameObjects.Player 1 controls
        controls1.put(KeyEvent.VK_UP, Key.up);
        controls1.put(KeyEvent.VK_DOWN, Key.down);
        controls1.put(KeyEvent.VK_LEFT, Key.left);
        controls1.put(KeyEvent.VK_RIGHT, Key.right);
        controls1.put(KeyEvent.VK_SLASH, Key.action);

        // Set GameObjects.Player 2 controls
        controls2.put(KeyEvent.VK_W, Key.up);
        controls2.put(KeyEvent.VK_S, Key.down);
        controls2.put(KeyEvent.VK_A, Key.left);
        controls2.put(KeyEvent.VK_D, Key.right);
        controls2.put(KeyEvent.VK_F, Key.action);
    }

    public void addNotify() {
        super.addNotify();

        if (this.thread == null) {
            this.thread = new Thread(this, "GameThread");
            this.thread.start();
        }
    }

    private void init() {
        this.running = true;
        this.setControls();
        this.world = new BufferedImage(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        GameObjectCollection.init();

        BufferedImage sprTank1 = null;
        BufferedImage sprTank2 = null;
        BufferedImage sprBullet1 = null;
        BufferedImage sprBullet2 = null;

        // Loading sprites
        try {
            System.out.println(System.getProperty("user.dir"));
            background = ImageIO.read(GamePanel.class.getResourceAsStream("resources/bg.jpg"));
            sprTank1 = ImageIO.read(GamePanel.class.getResourceAsStream("resources/tank1.png"));
            sprTank2 = ImageIO.read(GamePanel.class.getResourceAsStream("resources/tank2.png"));
            sprBullet1 = ImageIO.read(GamePanel.class.getResourceAsStream("resources/bullet1.png"));
            sprBullet2 = ImageIO.read(GamePanel.class.getResourceAsStream("resources/bullet2.png"));
        } catch (IOException e) {
            System.out.println("IOException: cannot read image file");
            e.printStackTrace();
        }

        // Instantiating tanks
        Tank tank1 = new Tank(200, 200, 0f, sprTank1, sprBullet1);
        Tank tank2 = new Tank(400, 400, 0f, sprTank2, sprBullet2);
        PlayerController tankController1 = new PlayerController(tank1, controls1);
        PlayerController tankController2 = new PlayerController(tank2, controls2);
        this.addKeyListener(tankController1);
        this.addKeyListener(tankController2);
        GameObjectCollection.spawn(tank1);
        GameObjectCollection.spawn(tank2);
    }

    @Override
    public void run() {
        this.init();

        while (this.running) {
            this.update();
        }
    }

    private synchronized void update() {
        try {
            for (int i = 0; i < GameObjectCollection.numGameObjects(); i++) {
                GameObjectCollection.getGameObject(i).update();
                System.out.println(GameObjectCollection.getGameObject(i));
            }
            System.out.println();
            this.repaint();
            Thread.sleep(1000 / 144);
        } catch (InterruptedException ignored) {

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        this.buffer = this.world.createGraphics();
        super.paintComponent(g2);

        // Draw background
        for (int i = 0; i < SCREEN_WIDTH; i += this.background.getTileWidth()) {
            for (int j = 0; j < SCREEN_HEIGHT; j += this.background.getTileHeight()) {
                this.buffer.drawImage(this.background, i, j, null);
            }
        }

        // Draw GameObjects
        for (int i = 0; i < GameObjectCollection.numGameObjects(); i++) {
            GameObjectCollection.getGameObject(i).drawImage(this.buffer);
        }

        g2.drawImage(this.world,0,0,null);
        this.buffer.clearRect(0, 0, getWidth(), getHeight());
    }

}
