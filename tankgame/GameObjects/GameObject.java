package GameObjects;

import util.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    protected BufferedImage sprite;
    protected Transform transform;
    protected Vector2D originOffset;
    protected Rectangle2D.Double collider;

    // To be called by other game objects
    // This method will spawn a game object centered at location (ie. tank's origin)
    protected void instantiate(GameObject spawnObj, Vector2D location, float rotation) {
        // Offset position by origin to align spawnObj's origin with location before spawning
        float x = location.getX() - spawnObj.originOffset.getX();
        float y = location.getY() - spawnObj.originOffset.getY();
        Vector2D spawnPoint = new Vector2D(x, y);
        spawnObj.transform.setPosition(spawnPoint);
        spawnObj.transform.setRotation(rotation);
        GameObjectCollection.spawn(spawnObj);
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

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.transform.getPositionX(), this.transform.getPositionY());
        rotation.rotate(Math.toRadians(this.transform.getRotation()), this.sprite.getWidth() / 2.0, this.sprite.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

    // General GameObject information used in drawGizmos
    public void drawTransform(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawRect((int) this.transform.getPositionX(), (int) this.transform.getPositionY(), this.sprite.getWidth(), this.sprite.getHeight());
        g2d.drawString("[" + this.getClass().getSimpleName() + "]", this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 12);
        g2d.drawString("x: " + this.transform.getPositionX(), this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 24);
        g2d.drawString("y: " + this.transform.getPositionY(), this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 36);
        g2d.drawString("angle: " + this.transform.getRotation(), this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 48);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getSimpleName() + "] " + "Position: " + this.transform.getPosition() + ", Angle: " + this.transform.getRotation();
    }

    public abstract void update();

    public abstract void drawGizmos(Graphics g);

}
