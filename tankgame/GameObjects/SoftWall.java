package GameObjects;

import util.Transform;
import util.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class SoftWall extends Wall {

    private int hitPoints;

    public SoftWall(float xPosition, float yPosition, BufferedImage sprite) {
        this.transform = new Transform(xPosition, yPosition, 0);
        this.sprite = sprite;
        this.originOffset = new Vector2D((float) this.sprite.getWidth() / 2, (float) this.sprite.getHeight() / 2);
        this.collider = new Rectangle2D.Double(this.transform.getPositionX(), this.transform.getPositionY(), this.sprite.getWidth(), this.sprite.getHeight());

        this.hitPoints = 3;
    }

    @Override
    public void update() {

    }

    @Override
    public void drawGizmos(Graphics g) {

    }

    @Override
    public boolean isBreakable() {
        return true;
    }
}
