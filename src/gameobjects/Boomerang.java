package gameobjects;

import util.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Boomerang extends Weapon {

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
        this.velocity = 12.0f;
        this.hitPoints = 1;

        this.counter = 0;
    }

    @Override
    public void update() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);

        // Decrease velocity so that boomerang eventually flies backwards
        this.transform.move(this.velocity);
        this.velocity = Math.max(-16.0f, this.velocity - 0.2f);

        // Cycle through sprite animation
        if (++this.counter >= 20) {
            this.counter = 0;
        }
        this.sprite = this.animation[this.counter];
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
