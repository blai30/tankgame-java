package GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 */
public class Powerup extends GameObject {

    public enum Type {

        Health {
            @Override
            public void grantBonus(Tank tank) {
                this.value = 2;
                tank.addHealth(this.value);
            }
        },

        Speed {
            @Override
            public void grantBonus(Tank tank) {
                this.value = 1;
                tank.addSpeed(this.value);
            }
        },

        FireRate {
            @Override
            public void grantBonus(Tank tank) {
                this.value = 1;
                tank.addFireRate(this.value);
            }
        },

        Damage {
            @Override
            public void grantBonus(Tank tank) {
                this.value = 1;
                tank.addDamage(this.value);
            }
        },

        Armor {
            @Override
            public void grantBonus(Tank tank) {
                this.value = 1;
                tank.addArmor(this.value);
            }
        },

        Ammo {
            @Override
            public void grantBonus(Tank tank) {
                this.value = 10;
                tank.addAmmo(this.value);
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

        protected int value;

        public abstract void grantBonus(Tank tank);

    }

    private Type type;

    public Powerup(float xPosition, float yPosition, float rotation, BufferedImage sprite, Powerup.Type type) {
        this.construct(xPosition, yPosition, rotation, sprite);
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    @Override
    public void update() {

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
