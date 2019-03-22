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
        // TODO draw collider box and data members
    }

}
