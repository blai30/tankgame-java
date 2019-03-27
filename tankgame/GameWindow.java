import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Contains the main method to launch the game.
 */
class GameLauncher {

    public static void main(String[] args) {
        GamePanel game = new GamePanel();
        game.init();
        try {
            game.loadMap(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Program args not given");
            game.loadMap(null);
        }
        new GameWindow(game);
    }

}

/**
 * The game window seen by the user that contains everything.
 */
public class GameWindow extends JFrame {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;

    /**
     * Constructs a game window with the necessary configurations.
     * @param game Game panel that will be contained inside the game window
     */
    GameWindow(GamePanel game) {
        this.setTitle("Tank Game by Brian Lai");

        try {
            System.out.println(System.getProperty("user.dir"));
            Image icon = ImageIO.read(GameWindow.class.getResourceAsStream("resources/icon.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            System.out.println("IOException: cannot read image file");
            e.printStackTrace();
        }

        this.setLayout(new BorderLayout());
        this.add(game, BorderLayout.CENTER);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
