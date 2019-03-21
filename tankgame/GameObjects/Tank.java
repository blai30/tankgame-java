package GameObjects;

import util.Transform;

import java.awt.image.BufferedImage;

public class Tank extends Player {

    private final float ROTATION_SPEED = 1.2f;

    private BufferedImage sprBullet;

    private float moveSpeed;
    private float fireRate;
    private int damage;
    private int armor;
    private int maxBullets;

    public Tank(Transform transform, BufferedImage sprite, BufferedImage sprBullet) {
        // Set properties
        this.transform = transform;
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
        this.transform.rotate(this.ROTATION_SPEED);
    }

    private void rotateLeft() {
        this.transform.rotate(-this.ROTATION_SPEED);
    }

    private void moveForwards() {
        this.transform.move(this.moveSpeed);
    }

    private void moveBackwards() {
        this.transform.move(-this.moveSpeed);
    }

    private void fire() {
        if (this.maxBullets > 0) {
            Bullet bullet = new Bullet(new Transform(this.transform), this.sprBullet, this.damage);
//            Game.add(bullet);
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
        return "Position: " + this.transform.getPosition() + ", Angle: " + this.transform.getRotation();
    }

}
