package gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Abstract class for weapons with predefined weapon types.
 */
public abstract class Weapon extends GameObject {
    // Possible weapon types that are predefined
    enum Type {
        // Basic bullet
        Bullet {
            @Override
            public Weapon createInstance(BufferedImage sprite, int damage, Tank shooter) {
                return new Bullet(sprite, damage, shooter);
            }
        },

        // Fast projectile that destroys multiple breakable walls
        Fireball {
            @Override
            public Weapon createInstance(BufferedImage sprite, int damage, Tank shooter) {
                return new Fireball(sprite, damage, shooter);
            }
        },

        // Projectile with slow initial velocity that reverses direction and accelerates
        Boomerang {
            @Override
            public Weapon createInstance(BufferedImage sprite, int damage, Tank shooter) {
                return new Boomerang(sprite, damage, shooter);
            }
        };

        public abstract Weapon createInstance(BufferedImage sprite, int damage, Tank shooter);

    }

    protected Tank shooter; // Tank that originally fired the weapon. Prevents damaging itself.

    protected int damage;
    protected float velocity;
    protected int hitPoints;

    /**
     * Initialize weapons with default stats.
     */
    protected abstract void init();

    /**
     * When weapons take damage when they collide and eventually get destroyed.
     * Weapons may get destroyed immediately or after colliding multiple times (fireball).
     */
    public void takeDamage() {
        this.hitPoints--;
        if (this.hitPoints <= 0) {
            this.destroy();
        }
    }

    /**
     * Continuously travel in the direction of the bullet object's rotation with velocity.
     */
    @Override
    public void update() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);
        this.transform.move(this.velocity);
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Tank collidingTank) {
        // Prevents weapon from hitting its own shooter that fires it
        if (collidingTank != this.shooter) {
            collidingTank.takeDamage(this.damage);
            this.takeDamage();
        }
    }

    @Override
    public void handleCollision(Wall collidingWall) {
        if (collidingWall.isBreakable()) {
            collidingWall.takeDamage(this.damage);
        }
        this.takeDamage();
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

    /**
     * Draws additional information about the weapon object to the game world.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    @Override
    public void drawGizmos(Graphics g) {

    }

    /**
     * Draws the game object's variables in the game world to g.
     * This method is called when drawDebug is true in main.java.GamePanel.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    @Override
    public void drawVariables(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawString("damage: " + this.damage, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 60);
        g2d.drawString("velocity: " + this.velocity, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 72);
        g2d.drawString("hitPoints: " + this.hitPoints, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 84);
    }

}
