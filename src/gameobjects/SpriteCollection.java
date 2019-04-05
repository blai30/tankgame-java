package gameobjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum SpriteCollection {

    tank1,
    tank2,
    bullet1,
    bullet2,
    softWall,

    powerHealth,
    powerSpeed,
    powerFireRate,
    powerDamage,
    powerArmor,
    powerAmmo,
    powerFireball,
    powerBoomerang,
    powerRubber,

    fireball,
    boomerang,
    rubber;

    private BufferedImage image = null;

    public BufferedImage getImage() {
        return this.image;
    }

    public static void init() {
        try {
            SpriteCollection.tank1.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/tank1.png"));
            SpriteCollection.tank2.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/tank2.png"));
            SpriteCollection.bullet1.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/bullet1.png"));
            SpriteCollection.bullet2.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/bullet2.png"));
            SpriteCollection.softWall.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/wallS.png"));

            SpriteCollection.powerHealth.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_health.png"));
            SpriteCollection.powerSpeed.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_speed.png"));
            SpriteCollection.powerFireRate.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_firerate.png"));
            SpriteCollection.powerDamage.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_damage.png"));
            SpriteCollection.powerArmor.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_armor.png"));
            SpriteCollection.powerAmmo.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_ammo.png"));
            SpriteCollection.powerFireball.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_laser.png"));
            SpriteCollection.powerBoomerang.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_boomerang.png"));
            SpriteCollection.powerRubber.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_rubber.png"));

            SpriteCollection.fireball.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("weapon_fireball.png"));
            SpriteCollection.boomerang.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/weapon_boomerang_anim.png"));
            SpriteCollection.rubber.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/weapon_rubber.png"));
        } catch (IOException e) {
            System.err.println(e + ": Cannot read image file");
            e.printStackTrace();
        }
    }

}
