import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceCollection {

    enum Images {
        background,
        tilesHardWall;

        private BufferedImage image = null;

        public BufferedImage getImage() {
            return this.image;
        }
    }

    public static void init() {
        try {
            System.out.println(System.getProperty("user.dir"));
            Images.background.image = ImageIO.read(ResourceCollection.class.getClassLoader().getResource("resources/bg.jpg"));
            Images.tilesHardWall.image = ImageIO.read(ResourceCollection.class.getClassLoader().getResource("resources/wall_tiles.png"));
        } catch (IOException e) {
            System.err.println(e + ": Cannot read image file");
            e.printStackTrace();
        }
    }

}
