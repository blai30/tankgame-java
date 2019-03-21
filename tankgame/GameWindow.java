

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameWindow extends JFrame {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;

    private JPanel gameView;

    public GameWindow() {
        this.setTitle("Tank Game");

        Image icon = null;
        try {
            System.out.println(System.getProperty("user.dir"));
            icon = ImageIO.read(GameWindow.class.getResourceAsStream("resources/icon.png"));
        } catch (IOException e) {
            System.out.println("IOException: cannot read image file");
            e.printStackTrace();
        }
        this.setIconImage(icon);

        this.setLayout(new BorderLayout());
        this.gameView = new JPanel(this.getLayout());
        this.add(this.gameView);

        this.setSize(GameWindow.SCREEN_WIDTH, GameWindow.SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
        Game game = new Game(gameWindow);

        while (game.isRunning()) {
            game.update();
        }
    }

}
