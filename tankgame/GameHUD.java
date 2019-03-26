

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameHUD {

    public static final int WIDTH = (int) (GameWindow.SCREEN_HEIGHT * (float) 1 / (float) 3) - 40;
    public static final int HEIGHT = (int) (GameWindow.SCREEN_HEIGHT * (float) 1 / (float) 3) - 40;

    private BufferedImage minimap;

    public GameHUD() {
        this.minimap = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public void update(BufferedImage world) {
        Graphics g = this.minimap.createGraphics();
        g.drawImage(world, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
    }

    public BufferedImage getMinimap() {
        return minimap;
    }

}
