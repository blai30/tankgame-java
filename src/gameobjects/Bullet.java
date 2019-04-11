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
     * @param shooter The tank that originally fired this bullet, prevents it from shooting itself
     */
    public Bullet(BufferedImage sprite, int damage, Tank shooter) {
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
        this.velocity = 12.0f;
        this.hitPoints = 1;
    }

}
