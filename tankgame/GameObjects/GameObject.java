package GameObjects;

import util.Transform;
import util.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    BufferedImage sprite;
    Transform transform;
    // TODO: collider

    public void drawSprite(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.transform.getPositionX(), this.transform.getPositionY());
        rotation.rotate(Math.toRadians(this.transform.getRotation()), this.sprite.getWidth() / 2.0, this.sprite.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

    public abstract void update();

}
