import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class ResourceCollection {

    public enum Sprites {
        background,
        tank1,
        tank2,
        bullet1,
        bullet2,
        softWall,
        powerHealth,
        tilesHardWall;
    }

    private static HashMap<Sprites, BufferedImage> sprites;

    public static void init() {
        BufferedImage background = null;

        BufferedImage sprTank1 = null;
        BufferedImage sprTank2 = null;
        BufferedImage sprBullet1 = null;
        BufferedImage sprBullet2 = null;
        BufferedImage sprSoftWall = null;
        BufferedImage sprPowerHealth = null;

        BufferedImage tilesHardWall = null;

        try {
            System.out.println(System.getProperty("user.dir"));
            background = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/bg.jpg"));

            sprTank1 = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/tank1.png"));
            sprTank2 = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/tank2.png"));
            sprBullet1 = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/bullet1.png"));
            sprBullet2 = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/bullet2.png"));
            sprSoftWall = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/wallS.png"));
            sprPowerHealth = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/power_health.png"));

            tilesHardWall = ImageIO.read(ResourceCollection.class.getResourceAsStream("resources/wall_tiles.png"));
        } catch (IOException e) {
            System.err.println(e + ": Cannot read image file");
        }

        sprites = new HashMap<>();

        sprites.put(Sprites.background, background);

        sprites.put(Sprites.tank1, sprTank1);
        sprites.put(Sprites.tank2, sprTank2);
        sprites.put(Sprites.bullet1, sprBullet1);
        sprites.put(Sprites.bullet2, sprBullet2);
        sprites.put(Sprites.softWall, sprSoftWall);
        sprites.put(Sprites.powerHealth, sprPowerHealth);

        sprites.put(Sprites.tilesHardWall, tilesHardWall);
    }

    public static BufferedImage getSprite(Sprites sprite) {
        return sprites.get(sprite);
    }

}
