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

    /**
     * Initialize walls with default stats
     */
    private void init() {
        this.hitPoints = 1;
    }

    /**
     * Breakable walls take damage and have a chance to randomly drop powerups.
     * @param damageDealt Damage of the weapon
     */
    public void takeDamage(int damageDealt) {
        this.hitPoints -= damageDealt;

        // Chance to randomly drop powerups upon destroy
        if (this.hitPoints <= 0) {
            double random = Math.random();
            // Random powerup at 20% chance
            if (random < 0.2) {
                Powerup powerup = new Powerup();
                this.instantiate(powerup, this.transform.getPosition().add(this.originOffset), 0);
            } else if (random < 0.25) {
                // Ammo at 25% chance if random powerup did not pass
                Powerup powerup = new Powerup(Powerup.Type.Ammo);
                this.instantiate(powerup, this.transform.getPosition().add(this.originOffset), 0);
            }
            this.destroy();
        }
    }

    /**
     * Used to determine if the wall is breakable (soft wall) or unbreakable (hard wall).
     * @return Breakable?
     */
    public boolean isBreakable() {
        return this.isBreakable;
    }

    @Override
    public void update() {
        // Ignored as walls do not do anything
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

    /**
     * Draws the game object's variables in the game world to g.
     * This method is called when drawDebug is true in GamePanel.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    @Override
    public void drawVariables(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawString("hitPoints: " + this.hitPoints, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 60);
        g2d.drawString("isBreakable: " + this.isBreakable, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 72);
    }

}
