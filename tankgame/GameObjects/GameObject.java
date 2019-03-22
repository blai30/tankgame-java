package GameObjects;

import util.Transform;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    protected BufferedImage sprite;
    protected Transform transform;
    // TODO: collider

    // To be called by other game objects
    // This method will spawn a game object at location
    protected void instantiate(GameObject spawnObj, Transform location) {
        spawnObj.transform.setTransform(location);
        GameObjectCollection.spawn(spawnObj);
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.transform.getPositionX(), this.transform.getPositionY());
        rotation.rotate(Math.toRadians(this.transform.getRotation()), this.sprite.getWidth() / 2.0, this.sprite.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getSimpleName() + "] " + "Position: " + this.transform.getPosition() + ", Angle: " + this.transform.getRotation();
    }

    public abstract void update();

    public abstract void drawGizmos(Graphics g);

}
