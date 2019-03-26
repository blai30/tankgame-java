import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

class GameLauncher {

    public static void main(String[] args) {
        GamePanel game = new GamePanel(args[0]);
        new GameWindow(game);
    }

}

public class GameWindow extends JFrame {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;

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
