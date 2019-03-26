

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameHUD {

    private int mapWidth;
    private int mapHeight;

    private BufferedImage minimap;

    public GameHUD(BufferedImage world) {
        this.mapWidth = (int) (((GameWindow.SCREEN_HEIGHT / 3) - 40) * ((float) world.getWidth() / (float) world.getHeight()));
        this.mapHeight = (GameWindow.SCREEN_HEIGHT / 3) - 40;
        this.minimap = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_INT_RGB);
    }

    public int getMapWidth() {
        return this.mapWidth;
    }

    public void update(BufferedImage world) {
        Graphics g = this.minimap.createGraphics();
        g.drawImage(world, 0, 0, mapWidth, mapHeight, null);
        g.dispose();
    }

    public BufferedImage getMinimap() {
        return minimap;
    }

}
