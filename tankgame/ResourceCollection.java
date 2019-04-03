import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceCollection {

    enum Sprites {
        background,
        tank1,
        tank2,
        bullet1,
        bullet2,
        softWall,
        powerHealth;

        private BufferedImage image = null;

        public BufferedImage getImage() {
            return this.image;
        }
    }

    enum Tiles {
        tilesHardWall;

        private BufferedImage image = null;

        public BufferedImage getImage() {
            return this.image;
        }
    }

    public static void init() {
        try {
            System.out.println(System.getProperty("user.dir"));
            Sprites.background.image = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/bg.jpg"));

            Sprites.tank1.image = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/tank1.png"));
            Sprites.tank2.image = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/tank2.png"));
            Sprites.bullet1.image = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/bullet1.png"));
            Sprites.bullet2.image = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/bullet2.png"));
            Sprites.softWall.image = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/wallS.png"));
            Sprites.powerHealth.image = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/power_health.png"));

            Tiles.tilesHardWall.image = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/wall_tiles.png"));
        } catch (IOException e) {
            System.err.println(e + ": Cannot read image file");
        }
    }

}
