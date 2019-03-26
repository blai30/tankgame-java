

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameHUD {

    public static final int WIDTH = 280;
    public static final int HEIGHT = 280;

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
