package gameobjects;

import util.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 */
public class Powerup extends GameObject {

    public enum Type {

        Health(SpriteCollection.powerHealth.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addHealth(2);
            }
        },

        Speed(SpriteCollection.powerSpeed.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addSpeed(1);
            }
        },

        FireRate(SpriteCollection.powerFireRate.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addFireRate(1);
            }
        },

        Damage(SpriteCollection.powerDamage.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addDamage(1);
            }
        },

        Armor(SpriteCollection.powerArmor.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addArmor(1);
            }
        },

        Ammo(SpriteCollection.powerAmmo.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addAmmo(20);
            }
        },

        Fireball(SpriteCollection.powerFireball.getImage(), SpriteCollection.fireball.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Fireball, this.sprWeapon);
            }
        },

        Boomerang(SpriteCollection.powerBoomerang.getImage(), SpriteCollection.boomerang.getImage()) {
            @Override
            protected void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Boomerang, this.sprWeapon);
            }
        };

        protected BufferedImage sprite;
        protected BufferedImage sprWeapon = null;

        Type(BufferedImage sprite) {
            this.sprite = sprite;
        }

        Type(BufferedImage sprite, BufferedImage sprWeapon) {
            this.sprite = sprite;
            this.sprWeapon = sprWeapon;
        }

        protected abstract void grantBonus(Tank tank);

    }

    private Type type;

    public Powerup() {
        this.transform = new Transform();
        this.type = this.randomPower();
        this.construct(this.type.sprite);
    }

    public Powerup(Powerup.Type type) {
        this.transform = new Transform();
        this.type = type;
        this.construct(this.type.sprite);
    }

    public Powerup(float xPosition, float yPosition, float rotation, Powerup.Type type) {
        this.type = type;
        this.construct(xPosition, yPosition, rotation, this.type.sprite);
    }

    private Powerup.Type[] powerups = Powerup.Type.values();

    private Random random = new Random();

    private final Powerup.Type randomPower() {
        return this.powerups[this.random.nextInt(this.powerups.length)];
    }

    void grantBonus(Tank tank) {
        this.type.grantBonus(tank);
    }

    @Override
    public void update() {

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

}
