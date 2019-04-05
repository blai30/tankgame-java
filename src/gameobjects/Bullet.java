package gameobjects;

import util.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Bullet objects that are fired by tank objects.
 */
public class Bullet extends Weapon {

    /**
     * Constructs a new bullet object with generic data.
     * @param sprite The image of this bullet passed in by a tank object drawn to the screen
     * @param damage Bonus damage passed in by a tank object added to the base damage
     */
    public Bullet(BufferedImage sprite, int damage, Tank shooter) {
        this.transform = new Transform();
        this.construct(sprite);
        this.shooter = shooter;

        this.damage += damage;
        this.init();
    }

    @Override
    protected void init() {
        this.velocity = 12.0f;
        this.hitPoints = 1;
    }

    /**
     * Continuously travel in the direction of the bullet object's rotation with velocity.
     */
    @Override
    public void update() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);
        this.transform.move(this.velocity);
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Tank collidingTank) {
        // Prevents weapon from hitting its own shooter that fires it
        if (collidingTank != this.shooter) {
            collidingTank.takeDamage(this.damage);
            this.takeDamage();
        }
    }

    @Override
    public void handleCollision(Wall collidingWall) {
        if (collidingWall.isBreakable()) {
            collidingWall.takeDamage(this.damage);
        }
        this.takeDamage();
    }

    @Override
    public void handleCollision(Weapon collidingWeapon) {
        // Bullets pass through each other unless they are from the other player
        if (collidingWeapon.shooter != this.shooter) {
            collidingWeapon.takeDamage();
        }
    }

    @Override
    public void handleCollision(Powerup collidingPowerup) {

    }

    /**
     * Draws additional information about the bullet object to the game world.
     * @param g Graphics object that is passed in for the game object to draw to
     */
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

        g2d.drawString("damage: " + this.damage, this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 60);
        g2d.drawString("velocity: " + this.velocity, this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 72);
    }

}
