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
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
        this.collider = new Rectangle2D.Double(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);

        this.hitPoints = 1;
    }

    /**
     * Determines if the wall is allowed to be destroyed by other game elements.
     * @return This wall is breakable
     */
    @Override
    public boolean isBreakable() {
        return true;
    }

    @Override
    public void takeDamage(int damageDealt) {
        this.hitPoints -= damageDealt;
        if (this.hitPoints <= 0) {
            this.destroy();
        }
    }

    @Override
    public void update() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Tank collidingTank) {

    }

    @Override
    public void handleCollision(Wall collidingWall) {

    }

    @Override
    public void handleCollision(Bullet collidingBullet) {

    }

    @Override
    public void drawGizmos(Graphics g) {

    }

    @Override
    public void drawVariables(Graphics g) {

    }

}
