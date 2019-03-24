

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

    private Thread thread;
    private boolean running = false;
    private boolean drawGizmos = true;

    private BufferedImage background = null;
    private BufferedImage world;
    private Graphics2D buffer;

    private HashMap<Integer, Key> controls1;
    private HashMap<Integer, Key> controls2;

    private Camera camera1;
    private Camera camera2;

    public GamePanel() {
        this.setFocusable(true);
        this.requestFocus();

        // Background is loaded in here because loading in init gives NullPointerException
        try {
            background = ImageIO.read(GamePanel.class.getResourceAsStream("resources/bg.jpg"));
        } catch (IOException e) {
            System.out.println("IOException: cannot read image file");
            e.printStackTrace();
        }
    }

    private void setControls() {
        this.controls1 = new HashMap<>();
        this.controls2 = new HashMap<>();

        // Set GameObjects.Player 1 controls
        this.controls1.put(KeyEvent.VK_UP, Key.up);
        this.controls1.put(KeyEvent.VK_DOWN, Key.down);
        this.controls1.put(KeyEvent.VK_LEFT, Key.left);
        this.controls1.put(KeyEvent.VK_RIGHT, Key.right);
        this.controls1.put(KeyEvent.VK_SLASH, Key.action);

        // Set GameObjects.Player 2 controls
        this.controls2.put(KeyEvent.VK_W, Key.up);
        this.controls2.put(KeyEvent.VK_S, Key.down);
        this.controls2.put(KeyEvent.VK_A, Key.left);
        this.controls2.put(KeyEvent.VK_D, Key.right);
        this.controls2.put(KeyEvent.VK_F, Key.action);
    }

    public void addNotify() {
        super.addNotify();

        if (this.thread == null) {
            this.thread = new Thread(this, "GameThread");
            this.thread.start();
        }
    }

    private void init() {
        this.setControls();
        this.world = new BufferedImage(2048, 2048, BufferedImage.TYPE_INT_RGB);
        GameObjectCollection.init();

        BufferedImage sprTank1 = null;
        BufferedImage sprTank2 = null;
        BufferedImage sprBullet1 = null;
        BufferedImage sprBullet2 = null;

        // Loading sprites
        try {
            System.out.println(System.getProperty("user.dir"));
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
        Tank tank2 = new Tank(800, 800, 0f, sprTank2, sprBullet2);
        this.camera1 = new Camera(tank1);
        this.camera2 = new Camera(tank2);
        PlayerController tankController1 = new PlayerController(tank1, this.controls1);
        PlayerController tankController2 = new PlayerController(tank2, this.controls2);
        this.addKeyListener(tankController1);
        this.addKeyListener(tankController2);
        GameObjectCollection.spawn(tank1);
        GameObjectCollection.spawn(tank2);

        this.running = true;
    }

    @Override
    public void run() {
        this.init();

        while (this.running) {
            this.update();
        }
    }

    private void update() {
        try {
            for (int i = 0; i < GameObjectCollection.numGameObjects(); i++) {
                GameObjectCollection.getGameObject(i).update();
                System.out.println(GameObjectCollection.getGameObject(i));
            }
            System.out.println();
            this.camera1.update(this.world);
            this.camera2.update(this.world);
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
        for (int i = 0; i < this.world.getWidth(); i += this.background.getWidth()) {
            for (int j = 0; j < this.world.getHeight(); j += this.background.getHeight()) {
                this.buffer.drawImage(this.background, i, j, null);
            }
        }

        // Draw GameObjects
        for (int i = 0; i < GameObjectCollection.numGameObjects(); i++) {
            GameObjectCollection.getGameObject(i).drawImage(this.buffer);
            if (this.drawGizmos) {
                GameObjectCollection.getGameObject(i).drawTransform(this.buffer);
                GameObjectCollection.getGameObject(i).drawGizmos(this.buffer);
            }
        }

        g2.drawImage(this.camera1.getScreen(), 0, 0, null);
        g2.drawImage(this.camera2.getScreen(), 800, 0, null);
//        g2.drawImage(this.world, 0, 0, null);
        g2.dispose();
        this.buffer.clearRect(0, 0, getWidth(), getHeight());
    }

}
