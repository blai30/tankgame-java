

import util.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tank {

    private Vector2D position;
    private float vx;
    private float vy;
    private float angle;

    private float moveSpeed;
    private float fireRate;
    private int damage;
    private int armor;
    private int maxBullets;

    private final float ROTATIONSPEED = 2;

    private BufferedImage sprite;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean LeftPressed;
    private boolean RightPressed;
    private boolean ActionPressed;

    // Avoid using this constructor
    public Tank() {
        this.position = new Vector2D();
        this.angle = 0;

        this.moveSpeed = 1.0f;
        this.fireRate = 1.0f;
        this.damage = 1;
        this.armor = 1;
        this.maxBullets = 5;
    }

    public Tank(Vector2D pos, float angle, BufferedImage sprite) {
        this.position = pos;
        this.angle = angle;
        this.sprite = sprite;

        this.moveSpeed = 1.0f;
        this.fireRate = 1.0f;
        this.damage = 1;
        this.armor = 1;
        this.maxBullets = 5;
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleActionPressed() {
        this.ActionPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleActionPressed() {
        this.ActionPressed = false;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void moveForwards() {
        vx = (float) Math.round(this.moveSpeed * Math.cos(Math.toRadians(this.angle)));
        vy = (float) Math.round(this.moveSpeed * Math.sin(Math.toRadians(this.angle)));
        position.add(vx, vy);
    }

    private void moveBackwards() {
        vx = (float) Math.round(this.moveSpeed * Math.cos(Math.toRadians(this.angle)));
        vy = (float) Math.round(this.moveSpeed * Math.sin(Math.toRadians(this.angle)));
        position.add(-vx, -vy);
    }

    private void fire() {
        if (this.maxBullets > 0) {
            // TODO: shoot a bullet with fireRate and damage
            this.maxBullets--;
        }
    }

    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        } else if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        } else if (this.RightPressed) {
            this.rotateRight();
        }

        if (this.ActionPressed) {
            this.fire();
        }
    }

    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        rotation.rotate(Math.toRadians(angle), this.sprite.getWidth() / 2.0, this.sprite.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

    @Override
    public String toString() {
        return "Position: " + position + ", Angle: " + angle;
    }

}
