package GameObjects;

import util.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 */
public class Powerup extends GameObject {

    public enum Type {

        Health {
            @Override
            public void grantBonus(Tank tank) {
                tank.addHealth(2);
            }
        },

        Speed {
            @Override
            public void grantBonus(Tank tank) {
                tank.addSpeed(1);
            }
        },

        FireRate {
            @Override
            public void grantBonus(Tank tank) {
                tank.addFireRate(1);
            }
        },

        Damage {
            @Override
            public void grantBonus(Tank tank) {
                tank.addDamage(1);
            }
        },

        Armor {
            @Override
            public void grantBonus(Tank tank) {
                tank.addArmor(1);
            }
        },

        Ammo {
            @Override
            public void grantBonus(Tank tank) {
                tank.addAmmo(10);
            }
        },

        Laser {
            @Override
            public void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Laser);
            }
        },

        Boomerang {
            @Override
            public void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Boomerang);
            }
        },

        Rubber {
            @Override
            public void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Rubber);
            }
        };

        public abstract void grantBonus(Tank tank);

    }

    protected Type type;

    public Powerup(BufferedImage sprite) {
        this.transform = new Transform();
        this.construct(sprite);
        this.type = this.random();
    }

    public Type getType() {
        return this.type;
    }

    private Powerup.Type[] powerups = Powerup.Type.values();

    private Random random = new Random();

    public final Powerup.Type random() {
        return powerups[random.nextInt(powerups.length)];
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
