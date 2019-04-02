package GameObjects;

import java.awt.image.BufferedImage;

/**
 *
 */
public abstract class Powerup extends GameObject {

    public Powerup(float xPosition, float yPosition, float rotation, BufferedImage sprite) {
        this.construct(xPosition, yPosition, rotation, sprite);
    }

}
