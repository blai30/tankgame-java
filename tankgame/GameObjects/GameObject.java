package GameObjects;

import util.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * The base class for all game objects with properties that allow it to exist in the game world.
 */
public abstract class GameObject implements CollisionHandling {

    protected BufferedImage sprite;
    protected Transform transform;
    protected float width;
    protected float height;
    protected Vector2D originOffset;
    protected Rectangle2D.Double collider;

    private boolean destroyed = false;

    protected void construct(float xPosition, float yPosition, float rotation, BufferedImage sprite) {
        this.transform = new Transform(xPosition, yPosition, rotation);
        this.construct(sprite);
    }

    /**
     * Used for projectiles that get instantiated with a location and does not need to construct
     * with a transform.
     * @param sprite
     */
    protected void construct(BufferedImage sprite) {
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
        this.collider = new Rectangle2D.Double(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);
    }

    /**
     * To be called by other game objects. This method will spawn a game object
     * centered at location (ie. the tank's origin)
     * @param spawnObj Game object to be created
     * @param location The location in world coordinates where the game object will be created
     * @param rotation The initial rotation of the game object to be created
     */
    protected void instantiate(GameObject spawnObj, Vector2D location, float rotation) {
        // Offset position by origin to align spawnObj's origin with location before spawning
        float x = location.getX() - spawnObj.originOffset.getX();
        float y = location.getY() - spawnObj.originOffset.getY();
        Vector2D spawnPoint = new Vector2D(x, y);
        spawnObj.transform.setPosition(spawnPoint);
        spawnObj.transform.setRotation(rotation);
        GameObjectCollection.spawn(spawnObj);
    }

    protected void destroy() {
        this.destroyed = true;
    }

    /**
     * Handle collision with solid objects such as walls.
     * @param obj A solid object such as a wall
     */
    protected void solidCollision(GameObject obj) {
        Rectangle2D intersection = this.collider.createIntersection(obj.collider);

        if (intersection.getWidth() >= intersection.getHeight()) {
            // From the top
            if (intersection.getMaxY() >= this.collider.getMaxY()) {
                this.transform.move(0, -(float) intersection.getHeight());
            }
            // From the bottom
            if (intersection.getMaxY() >= obj.collider.getMaxY()) {
                this.transform.move(0, (float) intersection.getHeight());
            }
        }

        if (intersection.getHeight() >= intersection.getWidth()) {
            // From the left
            if (intersection.getMaxX() >= this.collider.getMaxX()) {
                this.transform.move(-(float) intersection.getWidth(), 0);
            }
            // From the right
            if (intersection.getMaxX() >= obj.collider.getMaxX()) {
                this.transform.move((float) intersection.getWidth(), 0);
            }
        }
    }

    public BufferedImage getSprite() {
        return this.sprite;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public Vector2D getOriginOffset() {
        return this.originOffset;
    }

    public Rectangle2D.Double getCollider() {
        return this.collider;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Draws the game object in the game world to g.
     * (ie. the buffer which will be drawn to the screen)
     * @param g Graphics object that is passed in for the game object to draw to
     */
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.transform.getPositionX(), this.transform.getPositionY());
        rotation.rotate(Math.toRadians(this.transform.getRotation()), this.sprite.getWidth() / 2.0, this.sprite.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

    /**
     * Draw the game object's collider to the game world for debugging.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    public void drawCollider(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(this.collider);
    }

    /**
     * Draws general information about the game object in the game world to g.
     * This method is called when drawGizmos is true in GamePanel.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    public void drawTransform(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawString("[" + this.getClass().getSimpleName() + "]", this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 12);
        g2d.drawString("x: " + this.transform.getPositionX(), this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 24);
        g2d.drawString("y: " + this.transform.getPositionY(), this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 36);
        g2d.drawString("angle: " + this.transform.getRotation(), this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 48);
    }

    /**
     * Prints the position and rotation of the game object.
     * @return The position and rotation of the game object as a string
     */
    @Override
    public String toString() {
        return "[" + this.getClass().getSimpleName() + "] " + "Position: " + this.transform.getPosition() + ", Angle: " + this.transform.getRotation();
    }

    /**
     * Constantly called in the update method in GamePanel for every game object.
     */
    public abstract void update();

    /**
     * Draws additional information about the game object in the game world to g.
     * (ie. the aim line for tanks)
     * @param g Graphics object that is passed in for the game object to draw to
     */
    public abstract void drawGizmos(Graphics g);

    /**
     * Draws the game object's variables in the game world to g.
     * This method is called when drawDebug is true in GamePanel.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    public abstract void drawVariables(Graphics g);

}

/**
 * Visitor pattern collision detection for game objects.
 */
interface CollisionHandling {

    void collides(GameObject collidingObj);
    void handleCollision(Tank collidingTank);
    void handleCollision(Wall collidingWall);
    void handleCollision(Weapon collidingWeapon);
    void handleCollision(Powerup collidingPowerup);

}
