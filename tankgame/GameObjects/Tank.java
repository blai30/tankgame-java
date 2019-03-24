package GameObjects;

import util.Transform;
import util.Vector2D;

import java.awt.*;
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
        this.originOffset = new Vector2D((float) this.sprite.getWidth() / 2, (float) this.sprite.getHeight() / 2);

        this.sprBullet = sprBullet;

        // Default stats
        this.hitPoints = 10;
        this.moveSpeed = 2.2f;
        this.fireRate = 1.0f;
        this.bonusDamage = 0;
        this.armor = 1;
        this.ammo = 5;
    }

    public Tank(float xPosition, float yPosition, float rotation, BufferedImage sprite, BufferedImage sprBullet) {
        // Set properties
        this.transform = new Transform(xPosition, yPosition, rotation);
        this.sprite = sprite;
        this.originOffset = new Vector2D((float) this.sprite.getWidth() / 2, (float) this.sprite.getHeight() / 2);
        this.transform.setOrigin(this.transform.getPositionX() + ((float) this.sprite.getWidth() / 2), this.transform.getPositionY() + ((float) this.sprite.getHeight() / 2));

        this.sprBullet = sprBullet;

        // Default stats
        this.hitPoints = 10;
        this.moveSpeed = 2.2f;
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
//        if (this.ammo > 0) {
            this.instantiate(new Bullet(this.sprBullet, this.bonusDamage), this.transform.getOrigin(), this.transform.getRotation());
            this.ammo--;
//        }
    }

    @Override
    public void update() {
        // Movement
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        // Rotation
        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }

        // Weapon
        if (this.ActionPressed) {
            this.fire();
            this.ActionPressed = false;
        }
    }

    @Override
    public void drawGizmos(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw aim line
        float toX = (float) (500 * Math.cos(Math.toRadians(this.transform.getRotation())));
        float toY = (float) (500 * Math.sin(Math.toRadians(this.transform.getRotation())));
        g2d.drawLine((int) (this.transform.getPositionX() + this.originOffset.getX()), (int) (this.transform.getPositionY() + this.originOffset.getY()), (int) (this.transform.getPositionX() + this.originOffset.getX() + toX), (int) (this.transform.getPositionY() + this.originOffset.getY() + toY));

        g2d.drawString("hitPoints: " + this.hitPoints, this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 60);
        g2d.drawString("moveSpeed: " + this.moveSpeed, this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 72);
        g2d.drawString("fireRate: " + this.fireRate, this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 84);
        g2d.drawString("bonusDamage: " + this.bonusDamage, this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 96);
        g2d.drawString("armor: " + this.armor, this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 108);
        g2d.drawString("ammo: " + this.ammo, this.transform.getPositionX(), this.transform.getPositionY() + this.sprite.getHeight() + 120);
    }

}
