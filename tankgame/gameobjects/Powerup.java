package gameobjects;

import util.Transform;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 *
 */
public class Powerup extends GameObject {

    private enum Type {

        Health {
            @Override
            protected void setSprite() {
                this.sprite = null;
                try {
                    this.sprite = ImageIO.read(Powerup.class.getClassLoader().getResource("resources/power_health.png"));
                } catch (IOException e) {
                    System.err.println(e + ": Cannot read image file");
                }
            }
            @Override
            protected void grantBonus(Tank tank) {
                tank.addHealth(2);
            }
        },

        Speed {
            @Override
            protected void setSprite() {
                this.sprite = null;
                try {
                    this.sprite = ImageIO.read(Powerup.class.getClassLoader().getResource("resources/power_speed.png"));
                } catch (IOException e) {
                    System.err.println(e + ": Cannot read image file");
                }
            }
            @Override
            protected void grantBonus(Tank tank) {
                tank.addSpeed(1);
            }
        },

        FireRate {
            @Override
            protected void setSprite() {
                this.sprite = null;
                try {
                    this.sprite = ImageIO.read(Powerup.class.getClassLoader().getResource("resources/power_firerate.png"));
                } catch (IOException e) {
                    System.err.println(e + ": Cannot read image file");
                }
            }
            @Override
            protected void grantBonus(Tank tank) {
                tank.addFireRate(1);
            }
        },

        Damage {
            @Override
            protected void setSprite() {
                this.sprite = null;
                try {
                    this.sprite = ImageIO.read(Powerup.class.getClassLoader().getResource("resources/power_damage.png"));
                } catch (IOException e) {
                    System.err.println(e + ": Cannot read image file");
                }
            }
            @Override
            protected void grantBonus(Tank tank) {
                tank.addDamage(1);
            }
        },

        Armor {
            @Override
            protected void setSprite() {
                this.sprite = null;
                try {
                    this.sprite = ImageIO.read(Powerup.class.getClassLoader().getResource("resources/power_armor.png"));
                } catch (IOException e) {
                    System.err.println(e + ": Cannot read image file");
                }
            }
            @Override
            protected void grantBonus(Tank tank) {
                tank.addArmor(1);
            }
        },

        Ammo {
            @Override
            protected void setSprite() {
                this.sprite = null;
                try {
                    this.sprite = ImageIO.read(Powerup.class.getClassLoader().getResource("resources/power_ammo.png"));
                } catch (IOException e) {
                    System.err.println(e + ": Cannot read image file");
                }
            }
            @Override
            protected void grantBonus(Tank tank) {
                tank.addAmmo(10);
            }
        },

        Laser {
            @Override
            protected void setSprite() {
                this.sprite = null;
                try {
                    this.sprite = ImageIO.read(Powerup.class.getClassLoader().getResource("resources/power_laser.png"));
                } catch (IOException e) {
                    System.err.println(e + ": Cannot read image file");
                }
            }
            @Override
            protected void grantBonus(Tank tank) {
                BufferedImage sprWeapon = null;
                try {
                    sprWeapon = ImageIO.read(Powerup.class.getClassLoader().getResource("resources/weapon_laser.png"));
                } catch (IOException e) {
                    System.err.println(e + ": Cannot read image file");
                }
                tank.setWeapon(Weapon.Type.Laser, sprWeapon);
            }
        },

        Boomerang {
            @Override
            protected void setSprite() {
                this.sprite = null;
                try {
                    this.sprite = ImageIO.read(Powerup.class.getClassLoader().getResource("resources/power_boomerang.png"));
                } catch (IOException e) {
                    System.err.println(e + ": Cannot read image file");
                }
            }
            @Override
            protected void grantBonus(Tank tank) {
                BufferedImage sprWeapon = null;
                try {
                    sprWeapon = ImageIO.read(Powerup.class.getClassLoader().getResource("resources/weapon_boomerang_anim.png"));
                } catch (IOException e) {
                    System.err.println(e + ": Cannot read image file");
                }
                tank.setWeapon(Weapon.Type.Boomerang, sprWeapon);
            }
        },

        Rubber {
            @Override
            protected void setSprite() {
                this.sprite = null;
                try {
                    this.sprite = ImageIO.read(Powerup.class.getClassLoader().getResource("resources/power_rubber.png"));
                } catch (IOException e) {
                    System.err.println(e + ": Cannot read image file");
                }
            }
            @Override
            protected void grantBonus(Tank tank) {
                BufferedImage sprWeapon = null;
                try {
                    sprWeapon = ImageIO.read(Powerup.class.getClassLoader().getResource("resources/weapon_rubber.png"));
                } catch (IOException e) {
                    System.err.println(e + ": Cannot read image file");
                }
                tank.setWeapon(Weapon.Type.Rubber, sprWeapon);
            }
        };

        protected BufferedImage sprite;

        protected abstract void setSprite();

        protected abstract void grantBonus(Tank tank);

    }

    private Type type;

    Powerup() {
        this.transform = new Transform();
        this.type = this.randomPower();
        this.type.setSprite();
        this.construct(this.type.sprite);
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
