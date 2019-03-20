

import util.Vector2D;

import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

    private int damage;
    private float velocity;

    Bullet(Vector2D pos, float direction, BufferedImage sprite, int damage) {
        this.position = pos;
        this.angle = direction;
        this.sprite = sprite;

        this.damage = damage;
        this.velocity = 2.0f;
    }

    @Override
    public void update() {
        float vx = (float) (this.velocity * Math.cos(Math.toRadians(this.angle)));
        float vy = (float) (this.velocity * Math.sin(Math.toRadians(this.angle)));
        this.position.move(vx, vy);
    }

}
