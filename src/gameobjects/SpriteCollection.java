package gameobjects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Collection of sprites for the game objects to load from
 */
public enum SpriteCollection {
    // Solid objects
    tank1,
    tank2,
    tankGold,
    softWall,
    // Powerup pickups
    powerHealth,
    powerSpeed,
    powerFireRate,
    powerDamage,
    powerArmor,
    powerAmmo,
    powerFireball,
    powerBoomerang,
    powerGold,
    // Weapons
    bullet1,
    bullet2,
    fireball,
    boomerang;

    private BufferedImage image = null;

    public BufferedImage getImage() {
        return this.image;
    }

    // Load sprites from disk
    public static void init() {
        try {
            SpriteCollection.tank1.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/tank1.png"));
            SpriteCollection.tank2.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/tank2.png"));
            SpriteCollection.tankGold.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/tank3.png"));
            SpriteCollection.softWall.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/wallS.png"));

            SpriteCollection.powerHealth.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_health.png"));
            SpriteCollection.powerSpeed.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_speed.png"));
            SpriteCollection.powerFireRate.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_firerate.png"));
            SpriteCollection.powerDamage.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_damage.png"));
            SpriteCollection.powerArmor.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_armor.png"));
            SpriteCollection.powerAmmo.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_ammo.png"));
            SpriteCollection.powerFireball.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_fireball.png"));
            SpriteCollection.powerBoomerang.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_boomerang.png"));
            SpriteCollection.powerGold.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/power_gold.png"));

            SpriteCollection.bullet1.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/bullet1.png"));
            SpriteCollection.bullet2.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/bullet2.png"));
            SpriteCollection.fireball.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/weapon_fireball.png"));
            SpriteCollection.boomerang.image = ImageIO.read(SpriteCollection.class.getClassLoader().getResource("resources/weapon_boomerang.png"));
        } catch (IOException e) {
            System.err.println(e + ": Cannot read image file");
            e.printStackTrace();
        }
    }

}
