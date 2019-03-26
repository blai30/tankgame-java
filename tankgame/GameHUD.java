

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameHUD {

    private int minimapWidth;
    private int minimapHeight;

    private BufferedImage minimap;

    public GameHUD(BufferedImage world) {
        this.minimapWidth = (int) (((GameWindow.SCREEN_HEIGHT / 3) - 40) * ((float) world.getWidth() / (float) world.getHeight()));
        this.minimapHeight = (GameWindow.SCREEN_HEIGHT / 3) - 40;
        this.minimap = new BufferedImage(minimapWidth, minimapHeight, BufferedImage.TYPE_INT_RGB);
    }

    public int getMinimapWidth() {
        return this.minimapWidth;
    }

    public int getMinimapHeight() {
        return this.minimapHeight;
    }

    public void update(BufferedImage world) {
        Graphics g = this.minimap.createGraphics();
        g.drawImage(world, 0, 0, minimapWidth, minimapHeight, null);
        g.dispose();
    }

    public BufferedImage getMinimap() {
        return minimap;
    }

}
