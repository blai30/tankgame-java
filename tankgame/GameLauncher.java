import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameLauncher {

    public static void main(String[] args) {
        new GameWindow();
    }

}

class GameWindow extends JFrame {

    public GameWindow() {
        this.setTitle("Tank Game by Brian Lai");

        try {
            System.out.println(System.getProperty("user.dir"));
            Image icon = ImageIO.read(GameWindow.class.getResourceAsStream("resources/icon.png"));
            this.setIconImage(icon);
        } catch (IOException e) {
            System.out.println("IOException: cannot read image file");
            e.printStackTrace();
        }

        this.setContentPane(new GamePanel());
        this.setSize(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
