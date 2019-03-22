package GameObjects;

import util.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

    private int baseDamage = 1;
    private float velocity;

    public Bullet(BufferedImage sprite, int damage) {
        this.transform = new Transform();
        this.sprite = sprite;

        this.baseDamage += damage;
        this.velocity = 3.0f;
    }

    public Bullet(Transform transform, BufferedImage sprite, int damage) {
        this.transform = transform;
        this.sprite = sprite;

        this.baseDamage += damage;
        this.velocity = 3.0f;
    }

    @Override
    public void update() {
        this.transform.move(this.velocity);
    }

    @Override
    public void drawGizmos(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        // TODO draw collider box and data members
        g2d.drawString("baseDamage: " + this.baseDamage, this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 60);
        g2d.drawString("velocity: " + this.velocity, this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 60);
    }

}
