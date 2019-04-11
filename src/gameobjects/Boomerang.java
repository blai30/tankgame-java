package gameobjects;

import util.Transform;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Boomerang extends Weapon {

    private float spin;

    /**
     * Constructs a new boomerang object with generic data.
     * @param sprite The image of this bullet passed in by a tank object drawn to the screen
     * @param damage Bonus damage passed in by a tank object added to the base damage
     * @param shooter The tank that originally fired this bullet, prevents it from shooting itself
     */
    public Boomerang(BufferedImage sprite, int damage, Tank shooter) {
        this.transform = new Transform();
        this.construct(sprite);
        this.shooter = shooter;

        Random rand = new Random();
        this.spin = rand.nextInt(360);

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

    /**
     * Continuously travel in the direction of the boomerang object's rotation with velocity.
     * Boomerang slows down until it eventually reverses direction.
     * Increments spin variable that is used to animate the boomerang spinning.
     */
    @Override
    public void update() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);
        // Decrease velocity so that boomerang eventually flies backwards
        this.transform.move(this.velocity);
        this.velocity = Math.max(-16.0f, this.velocity - 0.2f);
        this.spin += 20;
    }

    // Using a separate value for rotation that movement does not depend on to draw spinning boomerang
    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.transform.getPositionX(), this.transform.getPositionY());
        rotation.rotate(Math.toRadians(this.spin), this.sprite.getWidth() / 2.0, this.sprite.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

}
