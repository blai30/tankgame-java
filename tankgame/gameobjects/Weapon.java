package gameobjects;

import java.awt.image.BufferedImage;

public abstract class Weapon extends GameObject {

    enum Type {

        Bullet {
            @Override
            public Weapon createInstance(BufferedImage sprite, int damage) {
                return new Bullet(sprite, damage);
            }
        },

        Laser {
            @Override
            public Weapon createInstance(BufferedImage sprite, int damage) {
                return null;
            }
        },

        Boomerang {
            @Override
            public Weapon createInstance(BufferedImage sprite, int damage) {
                return null;
            }
        },

        Rubber {
            @Override
            public Weapon createInstance(BufferedImage sprite, int damage) {
                return null;
            }
        };

        public abstract Weapon createInstance(BufferedImage sprite, int damage);

    }

    protected int totalDamage = 1;  // Default base damage
    protected float velocity;

    public int dealDamage() {
        return this.totalDamage;
    }

    protected abstract void init();

}
