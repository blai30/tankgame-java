package gameobjects;

import util.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rubber extends Weapon {

    private float stasis;
    private float bounceCount;

    public Rubber(BufferedImage sprite, int damage, Tank shooter) {
        this.transform = new Transform();
        this.construct(sprite);
        this.shooter = shooter;

        this.damage += damage;
        this.init();
    }

    private void bounce() {
        this.stasis = 0;
        this.transform.rotate(90);
        this.bounceCount++;
    }

    @Override
    protected void init() {
        this.velocity = 9.0f;
        this.hitPoints = 1;

        this.stasis = 0;
        this.bounceCount = 0;
    }

    @Override
    public void update() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);

        this.transform.move(this.velocity);
    }

    @Override
    public void drawGizmos(Graphics g) {

    }

    @Override
    public void drawVariables(Graphics g) {

    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Tank collidingTank) {
        this.stasis++;
        // Prevents weapon from hitting its own shooter that fires it
        if (collidingTank != this.shooter && this.stasis > 5) {
            this.bounce();
            collidingTank.takeDamage(this.damage);
            if (this.bounceCount > 5) {
                this.takeDamage();
            }
        }
    }

    @Override
    public void handleCollision(Wall collidingWall) {
        this.stasis++;
        if (this.stasis > 5) {
            this.bounce();
        }
        if (this.bounceCount > 5) {
            this.takeDamage();
        }
    }

    @Override
    public void handleCollision(Weapon collidingWeapon) {

    }

    @Override
    public void handleCollision(Powerup collidingPowerup) {

    }

}
