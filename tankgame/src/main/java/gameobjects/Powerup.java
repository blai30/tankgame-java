package gameobjects;

import util.SpriteCollection;
import util.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Powerups with predefined types that spawn from breakable walls at random.
 * These powerups grant tanks various bonuses when collided with.
 */
public class Powerup extends GameObject {

    public enum Type {
        // Increase Health
        Health(SpriteCollection.powerHealth.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addHealth(2);
            }
        },

        // Increase Speed
        Speed(SpriteCollection.powerSpeed.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addSpeed(1);
            }
        },

        // Increase Fire Rate
        FireRate(SpriteCollection.powerFireRate.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addFireRate(1);
            }
        },

        // Increase Damage
        Damage(SpriteCollection.powerDamage.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addDamage(1);
            }
        },

        // Increase Armor
        Armor(SpriteCollection.powerArmor.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addArmor(1);
            }
        },

        // Increase Ammo
        Ammo(SpriteCollection.powerAmmo.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addAmmo(20);
            }
        },

        // Get Fireball weapon
        Fireball(SpriteCollection.powerFireball.getImage(), SpriteCollection.fireball.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Fireball, this.sprPower);
            }
        },

        // Get Boomerang weapon
        Boomerang(SpriteCollection.powerBoomerang.getImage(), SpriteCollection.boomerang.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Boomerang, this.sprPower);
            }
        },

        // Increase stats to max
        // This enum must be last to keep out of the random pool
        Gold(SpriteCollection.powerGold.getImage(), SpriteCollection.tankGold.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addHealth(20);
                tank.addSpeed(10);
                tank.addFireRate(10);
                tank.addDamage(10);
                tank.addArmor(10);
                tank.addAmmo(999);
                tank.setSprite(this.sprPower);
            }
        };

        protected BufferedImage sprite;
        protected BufferedImage sprPower = null;

        // For stat increasing powerups
        Type(BufferedImage sprite) {
            this.sprite = sprite;
        }

        // For weapon powerups and max stats
        Type(BufferedImage sprite, BufferedImage sprPower) {
            this.sprite = sprite;
            this.sprPower = sprPower;
        }

        protected abstract void grantBonus(Tank tank);

    }

    private Type type;

    // Construct random powerup (excluding gold)
    public Powerup() {
        this.transform = new Transform();
        this.type = this.randomPower();
        this.construct(this.type.sprite);
    }

    // Construct specific powerup
    public Powerup(Powerup.Type type) {
        this.transform = new Transform();
        this.type = type;
        this.construct(this.type.sprite);
    }

    // Construct specific powerup at location
    public Powerup(float xPosition, float yPosition, float rotation, Powerup.Type type) {
        this.type = type;
        this.construct(xPosition, yPosition, rotation, this.type.sprite);
    }

    // Random powerups
    private Powerup.Type[] powerups = Powerup.Type.values();
    private Random random = new Random();
    private final Powerup.Type randomPower() {
        // Exclude power max from random pool
        return this.powerups[this.random.nextInt(this.powerups.length - 1)];
    }

    void grantBonus(Tank tank) {
        this.type.grantBonus(tank);
    }

    @Override
    public void update() {
        // Ignored as powerup pickups do not do anything
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Tank collidingTank) {

    }

    @Override
    public void handleCollision(Wall collidingWall) {

    }

    @Override
    public void handleCollision(Weapon collidingWeapon) {

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
