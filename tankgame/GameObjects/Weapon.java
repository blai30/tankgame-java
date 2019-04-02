package GameObjects;

import java.awt.image.BufferedImage;

public abstract class Weapon extends GameObject {

    enum Type {

        Bullet(0) {
            @Override
            public Weapon createInstance(BufferedImage sprite, int damage) {
                return new Bullet(sprite, damage);
            }
        },

        Laser(1) {
            @Override
            public Weapon createInstance(BufferedImage sprite, int damage) {
                return null;
            }
        };

        private int id;

        Type(int id) {
            this.id = id;
        }

        public abstract Weapon createInstance(BufferedImage sprite, int damage);

    }

    protected int totalDamage = 1;  // Default base damage
    protected float velocity;

    public int dealDamage() {
        return this.totalDamage;
    }

    protected abstract void init();

}
