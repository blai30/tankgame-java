import gameobjects.*;
import util.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * JPanel object that contains the entire game.
 */
public class GamePanel extends JPanel implements Runnable {

    private GameWindow gameWindow;
    private GameController gameController;
    private Thread thread;

    private boolean running = false;
    private boolean drawDebug = false;  // Draws various debugging info for game objects

    private BufferedReader bufferedReader;
    private BufferedImage background = null;
    private BufferedImage world;
    private Graphics2D buffer;
    private GameHUD gameHUD;

    private String mapFile;
    private HashMap<Integer, BufferedImage> tileMap;    // Tiles for hard walls loaded by bitmask

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
        ResourceCollection.init();
        SpriteCollection.init();
        GameObjectCollection.init();

        this.running = true;
    }

    public void launch() {
        this.gameWindow = new GameWindow(this);
        this.gameController = new GameController(this);
        this.addKeyListener(this.gameController);
    }

    /**
     * Loads data from a file to generate a map based on tiles.
     * "-1" = empty space
     * "S" = SoftWall
     * "H" = HardWall
     * @param mapFile Name of the file to generate map from
     */
    public void loadMap(String mapFile) {
        this.mapFile = mapFile;
        // Loading resources: sprites, tiles, background
        this.background = ResourceCollection.background.getImage();

        // Loading map file
        InputStream defaultMap = null;
        try {
            defaultMap = this.getClass().getResourceAsStream("resources/defaultmap.csv");
            this.bufferedReader = new BufferedReader(new FileReader(mapFile));
        } catch (IOException | NullPointerException e) {
            System.err.println(e + ": Cannot load map file, loading default map");
            this.bufferedReader = new BufferedReader(new InputStreamReader(defaultMap));
        }

        // Parsing map data from file
        ArrayList<ArrayList<String>> mapLayout = new ArrayList<>();
        try {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                if (currentLine.isEmpty()) {
                    continue;
                }
                // Split row into array of strings and add to array list
                mapLayout.add(new ArrayList<>(Arrays.asList(currentLine.split(","))));
            }
        } catch (IOException | NullPointerException e) {
            System.out.println(e + ": Error parsing map data");
            e.printStackTrace();
        }

        // Map dimensions
        int mapWidth = mapLayout.get(0).size();
        int mapHeight = mapLayout.size();

        this.world = new BufferedImage(mapWidth * 32, mapHeight * 32, BufferedImage.TYPE_INT_RGB);
        this.gameHUD = new GameHUD(this.world);

        // Load hard wall tiles
        BufferedImage hardWallTiles = ResourceCollection.tilesHardWall.getImage();
        BufferedImage[][] tiles = new BufferedImage[5][4];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                assert hardWallTiles != null;
                tiles[i][j] = hardWallTiles.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
        this.loadTiles(tiles);

        // Generate entire map
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                switch (mapLayout.get(y).get(x)) {
                    case ("-1"):    // Blank tile; no game object
                        continue;

                    case ("S"):     // Soft wall; breakable wall
                        BufferedImage sprSoftWall = SpriteCollection.softWall.getImage();
                        Wall softWall = new Wall(x * 32, y * 32, 0, sprSoftWall, true);
                        GameObjectCollection.spawn(softWall);
                        break;

                    case ("H"):     // Hard wall; unbreakable wall
                        // Code used to choose tile based on adjacent tiles
                        int code = 0;
                        if (y > 0 && mapLayout.get(y - 1).get(x).equals("H")) {
                            code += 1;  // North
                        }
                        if (y < mapHeight - 1 && mapLayout.get(y + 1).get(x).equals("H")) {
                            code += 4;  // South
                        }
                        if (x > 0 && mapLayout.get(y).get(x - 1).equals("H")) {
                            code += 8;  // West
                        }
                        if (x < mapWidth - 1 && mapLayout.get(y).get(x + 1).equals("H")) {
                            code += 2;  // East
                        }

                        Wall hardWall = new Wall(x * 32, y * 32, 0, this.tileMap.get(code), false);
                        GameObjectCollection.spawn(hardWall);
                        break;

                    case ("R"):
                        Spawn respawnPoint = new Spawn(x * 32, y * 32, 0);
                        GameObjectCollection.spawnPoints.add(respawnPoint);
                        break;

                    case ("PH"):    // Powerup Health
                        Powerup powerHealth = new Powerup(x * 32, y * 32, 0, Powerup.Type.Health);
                        GameObjectCollection.spawn(powerHealth);
                        break;

                    case ("PS"):    // Powerup Speed
                        Powerup powerSpeed = new Powerup(x * 32, y * 32, 0, Powerup.Type.Speed);
                        GameObjectCollection.spawn(powerSpeed);
                        break;

                    case ("PF"):    // Powerup Fire Rate
                        Powerup powerFireRate = new Powerup(x * 32, y * 32, 0, Powerup.Type.FireRate);
                        GameObjectCollection.spawn(powerFireRate);
                        break;

                    case ("PD"):    // Powerup Damage
                        Powerup powerDamage = new Powerup(x * 32, y * 32, 0, Powerup.Type.Damage);
                        GameObjectCollection.spawn(powerDamage);
                        break;

                    case ("PA"):    // Powerup Armor
                        Powerup powerArmor = new Powerup(x * 32, y * 32, 0, Powerup.Type.Armor);
                        GameObjectCollection.spawn(powerArmor);
                        break;

                    case ("PM"):    // Powerup Ammo
                        Powerup powerAmmo = new Powerup(x * 32, y * 32, 0, Powerup.Type.Ammo);
                        GameObjectCollection.spawn(powerAmmo);
                        break;

                    case ("WL"):    // Powerup Fireball
                        Powerup powerLaser = new Powerup(x * 32, y * 32, 0, Powerup.Type.Fireball);
                        GameObjectCollection.spawn(powerLaser);
                        break;

                    case ("WB"):    // Powerup Boomerang
                        Powerup powerBoomerang = new Powerup(x * 32, y * 32, 0, Powerup.Type.Boomerang);
                        GameObjectCollection.spawn(powerBoomerang);
                        break;

                    case ("PG"):    // Powerup Gold
                        Powerup powerGold = new Powerup(x * 32, y * 32, 0, Powerup.Type.Gold);
                        GameObjectCollection.spawn(powerGold);
                        break;

                    case ("1"):     // Player 1 tank
                        BufferedImage sprTank1 = SpriteCollection.tank1.getImage();
                        BufferedImage sprBullet1 = SpriteCollection.bullet1.getImage();
                        Tank tank1 = new Tank(x * 32, y * 32, 90f, sprTank1, sprBullet1);
                        this.camera1 = new Camera(tank1);
                        PlayerController tankController1 = new PlayerController(tank1, this.controls1);
                        this.addKeyListener(tankController1);
                        this.gameHUD.assignPlayer(0, tank1);
                        GameObjectCollection.spawn(tank1);
                        break;

                    case ("2"):     // Player 2 tank
                        BufferedImage sprTank2 = SpriteCollection.tank2.getImage();
                        BufferedImage sprBullet2 = SpriteCollection.bullet2.getImage();
                        Tank tank2 = new Tank(x * 32, y * 32, 270f, sprTank2, sprBullet2);
                        this.camera2 = new Camera(tank2);
                        PlayerController tankController2 = new PlayerController(tank2, this.controls2);
                        this.addKeyListener(tankController2);
                        this.gameHUD.assignPlayer(1, tank2);
                        GameObjectCollection.spawn(tank2);
                        break;

                    default:
                        break;
                }
            }
        }
    }

    /**
     * Called in loadMap to load tile map for hard wall sprites.
     * The correct sprite is chosen for hard wall based on adjacent hard walls.
     * @param tiles Double array of sliced tile map
     */
    private void loadTiles(BufferedImage[][] tiles) {
        this.tileMap = new HashMap<>();
        /*
            [ ][1][ ]
            [8][X][2]
            [ ][4][ ]
            1st bit = north
            2nd bit = east
            3rd bit = south
            4th bit = west
            These bits indicate if there is an adjacent hard wall in that direction
         */
        this.tileMap.put(0b0000, tiles[0][0]);  // 0

        this.tileMap.put(0b0001, tiles[2][0]);  // N
        this.tileMap.put(0b0010, tiles[3][0]);  // E
        this.tileMap.put(0b0100, tiles[1][0]);  // S
        this.tileMap.put(0b1000, tiles[4][0]);  // W

        this.tileMap.put(0b0011, tiles[3][2]);  // N E
        this.tileMap.put(0b1001, tiles[4][2]);  // N W
        this.tileMap.put(0b0110, tiles[1][2]);  // S E
        this.tileMap.put(0b1100, tiles[2][2]);  // S W

        this.tileMap.put(0b1010, tiles[0][3]);  // W E
        this.tileMap.put(0b0101, tiles[0][2]);  // N S

        this.tileMap.put(0b1011, tiles[2][1]);  // N E W
        this.tileMap.put(0b0111, tiles[3][1]);  // N E S
        this.tileMap.put(0b1110, tiles[1][1]);  // S E W
        this.tileMap.put(0b1101, tiles[4][1]);  // S W N

        this.tileMap.put(0b1111, tiles[0][1]);  // N S W E
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
     * When ESC is pressed, close the game
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * When F5 is pressed, reset game object collection, collect garbage, reinitialize game panel, reload map
     */
    public void resetGame() {
        GameObjectCollection.init();
        System.gc();
        this.init();
        this.loadMap(this.mapFile);
    }

    /**
     * Game starts running and keeps running.
     */
    @Override
    public void run() {
        long timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();

        final double NS = 1000000000.0 / 60.0; // Locked ticks per second to 60
        double delta = 0;
        int fps = 0;    // Frames per second
        int ticks = 0;  // Ticks/Updates per second; should be 60 at all times

        // Count FPS, Ticks, and execute updates
        while (this.running) {
            long currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / NS;
            lastTime = currentTime;
            if (delta >= 1) {
                this.update();
                ticks++;
                delta--;
            }
            this.repaint();
            fps++;

            // Update FPS and Ticks counter every second
            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
                System.out.println("FPS: " + fps + ", Ticks: " + ticks);
                gameWindow.setTitle(GameWindow.title + " | " + "FPS: " + fps + ", Ticks: " + ticks);
                fps = 0;
                ticks = 0;
            }
        }
    }

    /**
     * Updates the state of the game by calling the update method from all game objects
     * in the collection, the cameras, the game HUD, and repaint.
     */
    private void update() {
        try {
            for (int i = 0; i < GameObjectCollection.numGameObjects(); ) {
                GameObject obj = GameObjectCollection.getGameObject(i);
                obj.update();
                if (obj.isDestroyed()) {
                    // Destroy and remove game objects that were marked for deletion
                    GameObjectCollection.destroy(obj);
                } else {
                    for (int j = 0; j < GameObjectCollection.numGameObjects(); j++) {
                        GameObject collidingObj = GameObjectCollection.getGameObject(j);
                        // Skip detecting collision on the same object as itself
                        if (obj == collidingObj) {
                            continue;
                        }

                        // Visitor pattern collision handling
                        if (obj.getCollider().intersects(collidingObj.getCollider())) {
                            obj.collides(collidingObj);
                        }
                    }
                    i++;
                }
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
        for (int i = 0; i < GameObjectCollection.numGameObjects(); i++) {
            GameObject obj = GameObjectCollection.getGameObject(i);
            obj.drawGizmos(this.buffer);    // Draw gizmos under its own sprite
            obj.drawImage(this.buffer);

            // Draw debug information
            if (this.drawDebug) {
                this.buffer.setColor(Color.WHITE);
                obj.drawCollider(this.buffer);
                obj.drawTransform(this.buffer);
                obj.drawVariables(this.buffer);
            }
        }

        // Update the cameras and game hud then redraw them on the screen
        this.camera1.redraw(this.world);
        this.camera2.redraw(this.world);
        this.gameHUD.redraw(this.world);
        g2.drawImage(this.camera1.getScreen(), 0, 0, null);
        g2.drawImage(this.camera2.getScreen(), GameWindow.SCREEN_WIDTH / 2, 0, null);
        g2.drawImage(this.gameHUD.getP1info(), 0, GameWindow.SCREEN_HEIGHT - (GameWindow.SCREEN_HEIGHT / 3), null);
        g2.drawImage(this.gameHUD.getP2info(), (GameWindow.SCREEN_WIDTH / 2) + (this.gameHUD.getMinimapWidth() / 2), GameWindow.SCREEN_HEIGHT - (GameWindow.SCREEN_HEIGHT / 3), null);
        g2.drawImage(this.gameHUD.getMinimap(), (GameWindow.SCREEN_WIDTH / 2) - (this.gameHUD.getMinimapWidth() / 2), GameWindow.SCREEN_HEIGHT - (GameWindow.SCREEN_HEIGHT / 3), null);

        g2.dispose();
        this.buffer.dispose();
    }

}

/**
 * Used to control the game
 */
class GameController implements KeyListener {

    private GamePanel gamePanel;

    public GameController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Key events for general game operations such as exit
     * @param e Keyboard key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Close game
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("Escape key pressed: Closing game");
            this.gamePanel.exit();
        }

        // Display controls
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            System.out.println("F1 key pressed: Displaying help");

            String[] columnHeaders = { "", "Player 1", "Player 2" };
            Object[][] controls = {
                    {"Forward", "Up", "W"},
                    {"Backward", "Down", "S"},
                    {"Turn Left", "Left", "A"},
                    {"Turn Right", "Right", "D"},
                    {"Fire", "/", "F"},
                    {"", "", ""},
                    {"Help", "F1", ""},
                    {"Reset", "F5", ""},
                    {"Exit", "ESC", ""} };

            JTable controlsTable = new JTable(controls, columnHeaders);
            JTableHeader tableHeader = controlsTable.getTableHeader();

            // Wrap JTable inside JPanel to display
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(tableHeader, BorderLayout.NORTH);
            panel.add(controlsTable, BorderLayout.CENTER);

            JOptionPane.showMessageDialog(this.gamePanel, panel, "Controls", JOptionPane.PLAIN_MESSAGE);
        }

        // Reset game
        if (e.getKeyCode() == KeyEvent.VK_F5) {
            System.out.println("F5 key pressed: Resetting game");
            this.gamePanel.resetGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
