package GameObjects;

import util.Transform;
import util.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * A breakable wall.
 */
public class SoftWall extends Wall {

    private int hitPoints;

    /**
     * Constructs a soft wall at a coordinate in the game world with default hit points value.
     * @param xPosition The x coordinate of the soft wall in the game world
     * @param yPosition The y coordinate of the soft wall in the game world
     * @param sprite The image of this soft wall drawn to the screen
     */
    public SoftWall(float xPosition, float yPosition, BufferedImage sprite) {
        this.transform = new Transform(xPosition, yPosition, 0);
        this.sprite = sprite;
        this.originOffset = new Vector2D((float) this.sprite.getWidth() / 2, (float) this.sprite.getHeight() / 2);
        this.collider = new Rectangle2D.Double(this.transform.getPositionX(), this.transform.getPositionY(), this.sprite.getWidth(), this.sprite.getHeight());

        this.hitPoints = 3;
    }

    @Override
    public void update() {

    }

    @Override
    public void drawGizmos(Graphics g) {

    }

    /**
     * Determines if the wall is allowed to be destroyed by other game elements.
     * @return This wall is breakable
     */
    @Override
    public boolean isBreakable() {
        return true;
    }
}
