package gameobjects;

import java.awt.image.BufferedImage;

public abstract class Weapon extends GameObject {

    enum Type {

        Bullet {
            @Override
            public Weapon createInstance(BufferedImage sprite, int damage, Tank shooter) {
                return new Bullet(sprite, damage, shooter);
            }
        },

        Fireball {
            @Override
            public Weapon createInstance(BufferedImage sprite, int damage, Tank shooter) {
                return new Fireball(sprite, damage, shooter);
            }
        },

        Boomerang {
            @Override
            public Weapon createInstance(BufferedImage sprite, int damage, Tank shooter) {
                return new Boomerang(sprite, damage, shooter);
            }
        };

        public abstract Weapon createInstance(BufferedImage sprite, int damage, Tank shooter);

    }

    protected Tank shooter;

    protected int damage;
    protected float velocity;
    protected int hitPoints;

    public void takeDamage() {
        this.hitPoints--;
        if (this.hitPoints <= 0) {
            this.destroy();
        }
    }

    protected abstract void init();

}
