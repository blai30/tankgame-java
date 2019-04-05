package gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The base class for various types of walls.
 */
public class Wall extends GameObject {

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
    public Wall(float xPosition, float yPosition, float rotation, BufferedImage sprite, boolean isBreakable) {
        this.construct(xPosition, yPosition, rotation, sprite);
        this.isBreakable = isBreakable;

        this.init();
    }

    private void init() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);

        this.hitPoints = 1;
    }

    public void takeDamage(int damageDealt) {
        this.hitPoints -= damageDealt;
        if (this.hitPoints <= 0) {
            double random = Math.random();
            if (random < 0.2) {
                Powerup powerup = new Powerup();
                this.instantiate(powerup, this.transform.getPosition().add(this.originOffset), 0);
            } else if (random < 0.3) {
                Powerup powerup = new Powerup(Powerup.Type.Ammo);
                this.instantiate(powerup, this.transform.getPosition().add(this.originOffset), 0);
            }
            this.destroy();
        }
    }

    public boolean isBreakable() {
        return this.isBreakable;
    }

    @Override
    public void update() {

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
    public void handleCollision(Weapon collidingWeapon) {

    }

    @Override
    public void handleCollision(Powerup collidingPowerup) {

    }

    @Override
    public void drawGizmos(Graphics g) {

    }

    @Override
    public void drawVariables(Graphics g) {

    }

}
