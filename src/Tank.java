

import util.Vector2D;

import java.awt.image.BufferedImage;

public class Tank extends Controllable {

    private final float ROTATION_SPEED = 1.2f;

    private Bullet bullet;
    private BufferedImage sprBullet;

    private float moveSpeed;
    private float fireRate;
    private int damage;
    private int armor;
    private int maxBullets;

    Tank(Vector2D pos, float angle, BufferedImage sprite, BufferedImage sprBullet) {
        // Set properties
        this.position = pos;
        this.angle = angle;
        this.sprite = sprite;

        this.sprBullet = sprBullet;

        // Default stats
        this.moveSpeed = 1.2f;
        this.fireRate = 1.0f;
        this.damage = 1;
        this.armor = 1;
        this.maxBullets = 5;
    }

    private void rotateRight() {
        this.angle += this.ROTATION_SPEED;
    }

    private void rotateLeft() {
        this.angle -= this.ROTATION_SPEED;
    }

    private void moveForwards() {
        float vx = (float) (this.moveSpeed * Math.cos(Math.toRadians(this.angle)));
        float vy = (float) (this.moveSpeed * Math.sin(Math.toRadians(this.angle)));
        this.position.move(vx, vy);
    }

    private void moveBackwards() {
        float vx = (float) -(this.moveSpeed * Math.cos(Math.toRadians(this.angle)));
        float vy = (float) -(this.moveSpeed * Math.sin(Math.toRadians(this.angle)));
        this.position.move(vx, vy);
    }

    private void fire() {
        if (this.maxBullets > 0) {
            this.bullet = new Bullet(this.position, this.angle, this.sprBullet, this.damage);
            Game.add(this.bullet);
            this.maxBullets--;
        }
    }

    @Override
    public void update() {
        // Movement
        if (this.UpPressed) {
            this.moveForwards();
        } else if (this.DownPressed) {
            this.moveBackwards();
        }

        // Rotation
        if (this.LeftPressed) {
            this.rotateLeft();
        } else if (this.RightPressed) {
            this.rotateRight();
        }

        // Weapon
        if (this.ActionPressed) {
            this.fire();
        }
    }

    @Override
    public String toString() {
        return "Position: " + this.position + ", Angle: " + this.angle;
    }

}
