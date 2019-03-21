package GameObjects;

import util.Transform;

import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

    private int damage;
    private float velocity;

    public Bullet(Transform transform, BufferedImage sprite, int damage) {
        this.transform = new Transform(transform);
        this.sprite = sprite;

        this.damage = damage;
        this.velocity = 2.0f;
    }

    @Override
    public void update() {
        this.transform.move(this.velocity);
    }

}
