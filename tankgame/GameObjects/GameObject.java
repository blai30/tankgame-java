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
     * Draws general information about the game object in the game world to g.
     * This method is called when drawGizmos is true in GamePanel.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    public void drawTransform(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawRect((int) this.transform.getPositionX(), (int) this.transform.getPositionY(), this.sprite.getWidth(), this.sprite.getHeight());
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
     * This method is called when drawGizmos is true in GamePanel.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    public abstract void drawGizmos(Graphics g);

}

/**
 * Visitor pattern.
 */
interface CollisionHandling {

    void colliding(GameObject collidingObj);
    void handleCollision(Tank collidingTank);
    void handleCollision(Wall collidingWall);
    void handleCollision(Bullet collidingBullet);

}
