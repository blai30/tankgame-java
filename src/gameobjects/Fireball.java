package gameobjects;

import util.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fireball extends Weapon {

    /**
     * Constructs a new fireball object with generic data.
     * @param sprite The image of this bullet passed in by a tank object drawn to the screen
     * @param damage Bonus damage passed in by a tank object added to the base damage
     * @param shooter The tank that originally fired this bullet, prevents it from shooting itself
     */
    public Fireball(BufferedImage sprite, int damage, Tank shooter) {
        this.transform = new Transform();
        this.construct(sprite);
        this.shooter = shooter;

        this.damage += damage;
        this.init();
    }

    /**
     * Initialize weapons with default stats.
     */
    @Override
    protected void init() {
        this.velocity = 16.0f;
        this.hitPoints = 5;
    }

    @Override
    public void handleCollision(Tank collidingTank) {
        // Prevents weapon from hitting its own shooter that fires it
        if (collidingTank != this.shooter) {
            collidingTank.takeDamage(this.damage);
            this.destroy();
        }
    }

    /**
     * Fireballs can destroy up to 5 soft walls. (Based on hitPoints)
     * @param collidingWall
     */
    @Override
    public void handleCollision(Wall collidingWall) {
        if (collidingWall.isBreakable()) {
            collidingWall.takeDamage(this.damage);
            this.takeDamage();
        } else {
            this.destroy();
        }
    }

}
