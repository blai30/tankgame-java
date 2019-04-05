package gameobjects;

import util.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 */
public class Powerup extends GameObject {

    enum Type {

        Health {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addHealth(2);
            }
        },

        Speed {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addSpeed(1);
            }
        },

        FireRate {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addFireRate(1);
            }
        },

        Damage {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addDamage(1);
            }
        },

        Armor {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addArmor(1);
            }
        },

        Ammo {
            @Override
            protected void grantBonus(Tank tank) {
                tank.addAmmo(20);
            }
        },

        Laser {
            @Override
            protected void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Laser, this.sprWeapon);
            }
        },

        Boomerang {
            @Override
            protected void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Boomerang, this.sprWeapon);
            }
        },

        Rubber {
            @Override
            protected void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Rubber, this.sprWeapon);
            }
        };

        protected BufferedImage sprite;
        protected BufferedImage sprWeapon = null;

        protected abstract void grantBonus(Tank tank);

    }

    private Type type;

    Powerup() {
        this.transform = new Transform();
        this.type = this.randomPower();
        this.construct(this.type.sprite);
    }

    Powerup(Powerup.Type type) {
        this.transform = new Transform();
        this.type = type;
        this.construct(this.type.sprite);
    }

    public static void init() {
        Type.Health.sprite = SpriteCollection.powerHealth.getImage();
        Type.Speed.sprite = SpriteCollection.powerSpeed.getImage();
        Type.FireRate.sprite = SpriteCollection.powerFireRate.getImage();
        Type.Damage.sprite = SpriteCollection.powerDamage.getImage();
        Type.Armor.sprite = SpriteCollection.powerArmor.getImage();
        Type.Ammo.sprite = SpriteCollection.powerAmmo.getImage();
        Type.Laser.sprite = SpriteCollection.powerLaser.getImage();
        Type.Boomerang.sprite = SpriteCollection.powerBoomerang.getImage();
        Type.Rubber.sprite = SpriteCollection.powerRubber.getImage();

        Type.Laser.sprWeapon = SpriteCollection.laser.getImage();
        Type.Boomerang.sprWeapon = SpriteCollection.boomerang.getImage();
        Type.Rubber.sprWeapon = SpriteCollection.rubber.getImage();
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
