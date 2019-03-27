

import GameObjects.*;
import util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * JPanel object that contains the entire game.
 */
public class GamePanel extends JPanel implements Runnable {

    private Thread thread;
    private boolean running = false;
    private boolean drawGizmos = false;

    private BufferedReader bufferedReader;
    private BufferedImage background = null;
    private BufferedImage world;
    private Graphics2D buffer;
    private GameHUD gameHUD;

    private HashMap<Integer, Key> controls1;
    private HashMap<Integer, Key> controls2;

    private Camera camera1;
    private Camera camera2;

    /**
     * Constructor used to initialize the JPanel of the game for the JFrame.
     */
    public GamePanel() {
        this.setFocusable(true);
        this.requestFocus();
    }

    /**
     * Initializer that sets the game into a running state and enables the update method.
     */
    public void init() {
        this.setControls();
        GameObjectCollection.init();

        this.running = true;
    }

    /**
     * Loads data from a file to generate a map based on tiles.
     * '.' = empty space
     * 'S' = SoftWall
     * 'H' = HardWall
     * @param mapFile Name of the file to generate map from
     */
    public void loadMap(String mapFile) {
        // Loading sprites
        BufferedImage sprTank1 = null;
        BufferedImage sprTank2 = null;
        BufferedImage sprBullet1 = null;
        BufferedImage sprBullet2 = null;
        BufferedImage sprSoftWall = null;
        BufferedImage sprHardWall = null;
        try {
            System.out.println(System.getProperty("user.dir"));
            this.background = ImageIO.read(GamePanel.class.getResourceAsStream("resources/bg.jpg"));
            sprTank1 = ImageIO.read(GamePanel.class.getResourceAsStream("resources/tank1.png"));
            sprTank2 = ImageIO.read(GamePanel.class.getResourceAsStream("resources/tank2.png"));
            sprBullet1 = ImageIO.read(GamePanel.class.getResourceAsStream("resources/bullet1.png"));
            sprBullet2 = ImageIO.read(GamePanel.class.getResourceAsStream("resources/bullet2.png"));
            sprSoftWall = ImageIO.read(GamePanel.class.getResourceAsStream("resources/wall1.png"));
            sprHardWall = ImageIO.read(GamePanel.class.getResourceAsStream("resources/wall2.png"));
        } catch (IOException e) {
            System.out.println("IOException: cannot read image file");
            e.printStackTrace();
        }

        // No map file was provided, generate empty 1024x1024 map with two player tanks
        if (mapFile == null) {
            this.world = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_RGB);
            this.gameHUD = new GameHUD(this.world);

            Tank tank1 = new Tank(128, 128, 90f, sprTank1, sprBullet1);
            Tank tank2 = new Tank(846, 846, 270f, sprTank2, sprBullet2);
            this.camera1 = new Camera(tank1);
            this.camera2 = new Camera(tank2);
            PlayerController tankController1 = new PlayerController(tank1, this.controls1);
            PlayerController tankController2 = new PlayerController(tank2, this.controls2);
            this.addKeyListener(tankController1);
            this.addKeyListener(tankController2);
            GameObjectCollection.spawn(tank1);
            GameObjectCollection.spawn(tank2);
        } else {
            // Loading map data from file
            ArrayList<ArrayList<Integer>> mapLayout = new ArrayList<>();
            try {
                this.bufferedReader = new BufferedReader(new FileReader(mapFile));

                String currentLine;
                while ((currentLine = bufferedReader.readLine()) != null) {
                    if (currentLine.isEmpty()) {
                        continue;
                    }

                    ArrayList<Integer> row = new ArrayList<>();
                    byte[] tiles = currentLine.getBytes();
                    for (byte tile : tiles) {
                        if (tile != ' ') {
                            row.add((int) tile);
                        }
                    }
                    mapLayout.add(row);
                }
            } catch (IOException | NullPointerException e) {
                System.out.println("Error loading map data");
                e.printStackTrace();
            }

            int mapWidth = mapLayout.get(0).size();
            int mapHeight = mapLayout.size();

            this.world = new BufferedImage(mapWidth * 32, mapHeight * 32, BufferedImage.TYPE_INT_RGB);
            this.gameHUD = new GameHUD(this.world);

            // Generate entire map
            for (int y = 0; y < mapHeight; y++) {
                for (int x = 0; x < mapWidth; x++) {
                    switch (mapLayout.get(y).get(x)) {
                        case ((int) '.'):   // Blank tile; no game object
                            continue;
                        case ((int) 'S'):   // Soft wall; breakable wall
                            SoftWall softWall = new SoftWall(x * 32, y * 32, sprSoftWall);
                            GameObjectCollection.spawn(softWall);
                            break;
                        case ((int) 'H'):   // Hard wall; unbreakable wall
                            HardWall hardWall = new HardWall(x * 32, y * 32, sprHardWall);
                            GameObjectCollection.spawn(hardWall);
                            break;
                        case ((int) '1'):   // Player 1 tank
                            Tank tank1 = new Tank(x * 32, y * 32, 90f, sprTank1, sprBullet1);
                            this.camera1 = new Camera(tank1);
                            PlayerController tankController1 = new PlayerController(tank1, this.controls1);
                            this.addKeyListener(tankController1);
                            GameObjectCollection.spawn(tank1);
                            break;
                        case ((int) '2'):   // Player 2 tank
                            Tank tank2 = new Tank(x * 32, y * 32, 270f, sprTank2, sprBullet2);
                            this.camera2 = new Camera(tank2);
                            PlayerController tankController2 = new PlayerController(tank2, this.controls2);
                            this.addKeyListener(tankController2);
                            GameObjectCollection.spawn(tank2);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    /**
     * Initialize key bindings for player 1 and player 2.
     * TODO: load controls from file
     */
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

    /**
     * Game starts running and keeps running.
     */
    @Override
    public void run() {
        long timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();

        final double NS = 1000000000.0 / 120.0; // Locked UPS to 120
        double delta = 0;
        int fps = 0;    // Frames per second
        int ups = 0;    // Updates per second; should be 120 at all times

        // Count FPS, UPS, and execute updates
        while (this.running) {
            long currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / NS;
            lastTime = currentTime;
            if (delta >= 1) {
                this.update();
                ups++;
                delta--;
            }
            this.repaint();
            fps++;

            // Update FPS and UPS counter every second
            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
                System.out.println("FPS: " + fps + ", UPS: " + ups);
                GameLauncher.window.setTitle(GameWindow.title + " | " + "FPS: " + fps + ", UPS: " + ups);
                fps = 0;
                ups = 0;
            }
        }
    }

    /**
     * Updates the state of the game by calling the update method from all game objects
     * in the collection, the cameras, the game HUD, and repaint.
     */
    private void update() {
        try {
            for (GameObject obj : GameObjectCollection.getGameObjects()) {
                obj.update();
                obj.checkCollision();
            }
            Thread.sleep(1000 / 144);
        } catch (InterruptedException ignored) {

        }
    }

    /**
     * Constantly redraws the screen according to the game state.
     * @param g What is shown on the screen
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        this.buffer = this.world.createGraphics();
        this.buffer.clearRect(0, 0, this.world.getWidth(), this.world.getHeight());
        super.paintComponent(g2);

        // Set window background to black
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw background
        for (int i = 0; i < this.world.getWidth(); i += this.background.getWidth()) {
            for (int j = 0; j < this.world.getHeight(); j += this.background.getHeight()) {
                this.buffer.drawImage(this.background, i, j, null);
            }
        }

        // Draw GameObjects
        for (GameObject obj : GameObjectCollection.getGameObjects()) {
            obj.drawImage(this.buffer);
            if (this.drawGizmos) {
                obj.drawTransform(this.buffer);
                obj.drawGizmos(this.buffer);
            }
        }

        // Update the cameras and minimap then redraw them on the screen
        this.camera1.redraw(this.world);
        this.camera2.redraw(this.world);
        this.gameHUD.redraw(this.world);
        g2.drawImage(this.camera1.getScreen(), 0, 0, null);
        g2.drawImage(this.camera2.getScreen(), GameWindow.SCREEN_WIDTH / 2, 0, null);
        g2.drawImage(this.gameHUD.getMinimap(), (GameWindow.SCREEN_WIDTH / 2) - (gameHUD.getMinimapWidth() / 2), GameWindow.SCREEN_HEIGHT - (GameWindow.SCREEN_HEIGHT / 3), null);

        g2.dispose();
        this.buffer.dispose();
    }

}
