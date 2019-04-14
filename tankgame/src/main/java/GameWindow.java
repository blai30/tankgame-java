import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Contains the main method to launch the game.
 */
class GameLauncher {

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Tank Game");

        GamePanel game = new GamePanel();
        game.init();
        try {
            // $ java -jar csc413-tankgame-blai30.jar [args]
            game.loadMap(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e + ": Program args not given");
            game.loadMap(null);
        }
        GameWindow.gameWindow = new GameWindow(game);
        game.launch();
        System.gc();
    }

}

/**
 * The game window seen by the user that contains everything.
 */
public class GameWindow extends JFrame {

    // MINIMUM RECOMMENDED SUPPORTED SIZE IS 1280x960 !
    // Please do not use smaller dimensions as the game UI elements may not fit properly
    static final int SCREEN_WIDTH = 1280;
    static final int SCREEN_HEIGHT = 960;
    static final String title = "Tank Game by Brian Lai | Press F1 to view controls";

    static GameWindow gameWindow;

    /**
     * Constructs a game window with the necessary configurations.
     * @param game Game panel that will be contained inside the game window
     */
    GameWindow(JPanel game) {
        this.setTitle(title);

        try {
            System.out.println(System.getProperty("user.dir"));
            Image icon = ImageIO.read(this.getClass().getResource("/resources/icon.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            System.out.println("IOException: cannot read image file");
            e.printStackTrace();
        }

        this.setLayout(new BorderLayout());
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(game, BorderLayout.CENTER);
        this.setVisible(true);
    }

}
