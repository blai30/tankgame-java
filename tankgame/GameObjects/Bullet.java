package GameObjects;

import util.Transform;
import util.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Bullet objects that are fired by tank objects.
 */
public class Bullet extends GameObject {

    private int baseDamage = 1;
    private float velocity;

    /**
     * Constructs a new bullet object with generic data.
     * @param sprite The image of this bullet passed in by a tank object drawn to the screen
     * @param damage Bonus damage passed in by a tank object added to the base damage
     */
    public Bullet(BufferedImage sprite, int damage) {
        this.transform = new Transform();
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
        this.collider = new Rectangle2D.Double(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);

        this.baseDamage += damage;
        this.velocity = 8.0f;
    }

    /**
     * Constructs a new bullet by passing in a Transform object that the bullet will now own.
     * @param transform The bullet will take control of this Transform
     * @param sprite The image of this bullet passed in by a tank object drawn to the screen
     * @param damage Bonus damage passed in by a tank object added to the base damage
     */
    public Bullet(Transform transform, BufferedImage sprite, int damage) {
        this.transform = transform;
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
        this.collider = new Rectangle2D.Double(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);

        this.baseDamage += damage;
        this.velocity = 8.0f;
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
    public void checkCollision() {

    }

    @Override
    public void visit(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Bullet collidingObj) {

    }

    @Override
    public void handleCollision(Wall collidingObj) {
        this.destroy();
    }

    @Override
    public void handleCollision(Tank collidingObj) {

    }

    /**
     * Draws additional information about the bullet object to the game world.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    @Override
    public void drawGizmos(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // TODO draw collider box and data members
        g2d.drawString("baseDamage: " + this.baseDamage, this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 60);
        g2d.drawString("velocity: " + this.velocity, this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 72);
    }

}
