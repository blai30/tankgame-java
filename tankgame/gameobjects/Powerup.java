package gameobjects;

import util.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 */
public class Powerup extends GameObject {

    private enum Type {

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
                tank.addAmmo(10);
            }
        },

        Laser {
            @Override
            protected void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Laser);
            }
        },

        Boomerang {
            @Override
            protected void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Boomerang);
            }
        },

        Rubber {
            @Override
            protected void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Rubber);
            }
        };

        protected abstract void grantBonus(Tank tank);

    }

    private Type type;

    Powerup(BufferedImage sprite) {
        this.transform = new Transform();
        this.construct(sprite);
        this.type = this.randomPower();
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
