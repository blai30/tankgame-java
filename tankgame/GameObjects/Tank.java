package GameObjects;

import util.Transform;

import java.awt.image.BufferedImage;

public class Tank extends Player {

    private final float ROTATION_SPEED = 1.4f;

    private BufferedImage sprBullet;

    private int hitPoints;
    private float moveSpeed;
    private float fireRate;
    private int bonusDamage;
    private int armor;
    private int ammo;

    public Tank(Transform transform, BufferedImage sprite, BufferedImage sprBullet) {
        // Set properties
        this.transform = transform;
        this.sprite = sprite;

        this.sprBullet = sprBullet;

        // Default stats
        this.hitPoints = 10;
        this.moveSpeed = 1.4f;
        this.fireRate = 1.0f;
        this.bonusDamage = 0;
        this.armor = 1;
        this.ammo = 5;
    }

    public Tank(float xPosition, float yPosition, float rotation, BufferedImage sprite, BufferedImage sprBullet) {
        // Set properties
        this.transform = new Transform(xPosition, yPosition, rotation);
        this.sprite = sprite;

        this.sprBullet = sprBullet;

        // Default stats
        this.hitPoints = 10;
        this.moveSpeed = 1.4f;
        this.fireRate = 1.0f;
        this.bonusDamage = 0;
        this.armor = 1;
        this.ammo = 5;
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
        if (this.ammo > 0) {
            this.instantiate(new Bullet(this.sprBullet, this.bonusDamage), this.transform);
            this.ammo--;
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
            this.ActionPressed = false;
        }
    }

}
