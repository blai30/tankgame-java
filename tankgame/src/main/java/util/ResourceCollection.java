package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Collection of main.resources for the game to load from
 */
public enum ResourceCollection {

    background,
    tilesHardWall;

    private BufferedImage image = null;

    public BufferedImage getImage() {
        return this.image;
    }

    // Load main.resources from disk
    public static void init() {
        try {
            background.image = ImageIO.read(ResourceCollection.class.getClassLoader().getResource("resources/bg.jpg"));
            tilesHardWall.image = ImageIO.read(ResourceCollection.class.getClassLoader().getResource("resources/wall_tiles.png"));
        } catch (IOException e) {
            System.err.println(e + ": Cannot read image file");
            e.printStackTrace();
        }
    }

}
