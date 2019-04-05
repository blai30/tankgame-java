package gameobjects;

import util.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Boomerang extends Weapon {

    private float timer;
    private boolean reverse;
    private BufferedImage[] animation;
    private int counter;

    public Boomerang(BufferedImage sprite, int damage, Tank shooter) {
        this.animation = new BufferedImage[20];
        for (int i = 0; i < 20; i++) {
            this.animation[i] = sprite.getSubimage(i * 32, 0, 32, 32);
        }
        this.sprite = this.animation[0];

        this.transform = new Transform();
        this.construct(this.sprite);
        this.shooter = shooter;

        this.damage += damage;
        this.init();
    }

    @Override
    protected void init() {
        this.velocity = 10f;
        this.hitPoints = 1;

        this.timer = 0;
        this.reverse = false;
        this.counter = 0;
    }

    public void reverse() {
        this.transform.rotate(180);
    }

    @Override
    public void update() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);

        // Turn boomerang around after certain time passed
        if (this.timer >= 40 && !this.reverse) {
            this.reverse = true;
            this.reverse();
        }
        this.transform.move(this.velocity);

        // Cycle through sprite animation
        if (++this.counter >= 20) {
            this.counter = 0;
        }
        this.sprite = this.animation[this.counter];

        this.timer += 1;
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Tank collidingTank) {
        // Prevents weapon from hitting its own shooter that fires it
        if (collidingTank != this.shooter) {
            collidingTank.takeDamage(this.dealDamage());
            this.takeDamage();
        }

        // Boomerang takes damage upon returning to shooter
        if (this.reverse) {
            this.takeDamage();
        }
    }

    @Override
    public void handleCollision(Wall collidingWall) {
    }

    @Override
    public void handleCollision(Weapon collidingWeapon) {
        // Bullets pass through each other unless they are from the other player
        if (collidingWeapon.shooter != this.shooter) {
            collidingWeapon.takeDamage();
        }
    }

    @Override
    public void handleCollision(Powerup collidingPowerup) {

    }

    @Override
    public void drawGizmos(Graphics g) {

    }

    @Override
    public void drawVariables(Graphics g) {

    }

}
