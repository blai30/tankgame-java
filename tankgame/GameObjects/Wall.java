package GameObjects;

import util.Transform;
import util.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * The base class for various types of walls.
 */
public class Wall extends GameObject implements SolidObject {

    /**
     * Determines if the wall is allowed to be destroyed by other game elements.
     * @return True for breakable and false for unbreakable
     */
    private boolean isBreakable;
    private int hitPoints;

    /**
     * Constructs a soft wall at a coordinate in the game world with default hit points value.
     * @param xPosition The x coordinate of the soft wall in the game world
     * @param yPosition The y coordinate of the soft wall in the game world
     * @param sprite The image of this soft wall drawn to the screen
     */
    public Wall(float xPosition, float yPosition, BufferedImage sprite, boolean isBreakable) {
        this.transform = new Transform(xPosition, yPosition, 0);
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
        this.collider = new Rectangle2D.Double(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);
        this.isBreakable = isBreakable;

        this.init();
    }

    private void init() {
        this.hitPoints = 1;
    }

    private void takeDamage(int damageDealt) {
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
        if (this.isBreakable) {
            this.takeDamage(collidingBullet.dealDamage());
            collidingBullet.destroy();
        }
    }

    @Override
    public void drawGizmos(Graphics g) {

    }

    @Override
    public void drawVariables(Graphics g) {

    }

}
