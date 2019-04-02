package GameObjects;

import util.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 */
public abstract class Powerup extends GameObject {

    enum Type {

        Health {
            @Override
            public Powerup createInstance(BufferedImage sprite) {
                return new Health(sprite);
            }

            @Override
            public void grantBonus(Tank tank) {
                this.value = 2;
                tank.addHealth(this.value);
            }
        },

        Speed {
            @Override
            public Powerup createInstance(BufferedImage sprite) {
                return null;
            }

            @Override
            public void grantBonus(Tank tank) {
                this.value = 1;
                tank.addSpeed(this.value);
            }
        },

        FireRate {
            @Override
            public Powerup createInstance(BufferedImage sprite) {
                return null;
            }

            @Override
            public void grantBonus(Tank tank) {
                this.value = 1;
                tank.addFireRate(this.value);
            }
        },

        Damage {
            @Override
            public Powerup createInstance(BufferedImage sprite) {
                return null;
            }

            @Override
            public void grantBonus(Tank tank) {
                this.value = 1;
                tank.addDamage(this.value);
            }
        },

        Armor {
            @Override
            public Powerup createInstance(BufferedImage sprite) {
                return null;
            }

            @Override
            public void grantBonus(Tank tank) {
                this.value = 1;
                tank.addArmor(this.value);
            }
        },

        Ammo {
            @Override
            public Powerup createInstance(BufferedImage sprite) {
                return null;
            }

            @Override
            public void grantBonus(Tank tank) {
                this.value = 10;
                tank.addAmmo(this.value);
            }
        },

        Laser {
            @Override
            public Powerup createInstance(BufferedImage sprite) {
                return null;
            }

            @Override
            public void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Laser);
            }
        },

        Boomerang {
            @Override
            public Powerup createInstance(BufferedImage sprite) {
                return null;
            }

            @Override
            public void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Boomerang);
            }
        },

        Rubber {
            @Override
            public Powerup createInstance(BufferedImage sprite) {
                return null;
            }

            @Override
            public void grantBonus(Tank tank) {
                tank.setWeapon(Weapon.Type.Rubber);
            }
        };

        protected int value;

        public abstract Powerup createInstance(BufferedImage sprite);

        public abstract void grantBonus(Tank tank);

    }

    protected Type type;

    public Type getType() {
        return this.type;
    }

}
