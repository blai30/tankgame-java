package GameObjects;

import util.Transform;
import util.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * An unbreakable wall.
 */
public class HardWall extends Wall {

    /**
     * Constructs a hard wall at a coordinate in the game world
     * @param xPosition The x coordinate of the hard wall in the game world
     * @param yPosition The y coordinate of the hard wall in the game world
     * @param sprite The image of this hard wall drawn to the screen
     */
    public HardWall(float xPosition, float yPosition, BufferedImage sprite) {
        this.transform = new Transform(xPosition, yPosition, 0);
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
        this.collider = new Rectangle2D.Double(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);
    }

    /**
     * Determines if the wall is allowed to be destroyed by other game elements.
     * @return This wall is unbreakable
     */
    @Override
    public boolean isBreakable() {
        return false;
    }

    @Override
    public void takeDamage(int damageDealt) {

    }

    @Override
    public void update() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);
    }

    @Override
    public void colliding(GameObject collidingObj) {
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

}
