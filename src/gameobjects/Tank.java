package gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * Tank object to be controlled by a player.
 */
public class Tank extends Player {

    /*  Inherited fields from Player abstract class:
        boolean UpPressed
        boolean DownPressed
        boolean LeftPressed
        boolean RightPressed
        boolean ActionPressed
        int MAX_HP
        int MAX_LIVES
        int currentHP
        int currentLives
    */

    private final float ROTATION_SPEED = 2.5f;

    private BufferedImage originalSprite;   // Used to save original sprite in case tank gets powerGold which alters tank sprite
    private BufferedImage bulletSprite;     // Used to save bullet sprite for when respawning from a different weapon
    private BufferedImage weaponSprite;
    private Weapon projectile;
    private Weapon.Type currentWeapon;

    private int moveSpeed;
    private int fireRate;
    private int damage;
    private int armor;
    private int ammo;

    private float fireCooldown;
    private float fireDelay;

    /**
     * Constructs a tank from values to be constructed into a new Transform object.
     * @param xPosition The x coordinate of the tank in the game world
     * @param yPosition The y coordinate of the tank in the game world
     * @param rotation The rotation of the tank in degrees
     * @param sprite The image of this tank drawn to the screen
     * @param weaponSprite The image of the currentWeapon that this tank object will fire drawn to the screen
     */
    public Tank(float xPosition, float yPosition, float rotation, BufferedImage sprite, BufferedImage weaponSprite) {
        // Set properties
        this.construct(xPosition, yPosition, rotation, sprite);
        this.originalSprite = this.sprite;
        this.weaponSprite = weaponSprite;
        this.bulletSprite = weaponSprite;

        this.init();
    }

    /**
     * Initialize default weapon and stats
     */
    private void init() {
        this.currentWeapon = Weapon.Type.Bullet;
        this.currentHP = this.MAX_HP;
        this.currentLives = this.MAX_LIVES;

        this.statsCollection = new LinkedHashMap<>();
        this.moveSpeed = 4;
        this.fireRate = 1;
        this.damage = 1;
        this.armor = 0;
        this.ammo = 50;

        this.fireDelay = 60f;
        this.fireCooldown = this.fireDelay;
        this.loser = false;
    }

    /**
     * Reset HP, weapon, ammo, and deduct stats by 2.
     */
    private void respawn() {
        this.currentHP = this.MAX_HP;
        this.currentWeapon = Weapon.Type.Bullet;
        this.sprite = this.originalSprite;
        this.weaponSprite = this.bulletSprite;

        this.moveSpeed = Math.max(4, this.moveSpeed - 2);
        this.fireRate = Math.max(1, this.fireRate - 2);
        this.damage = Math.max(1, this.damage - 2);
        this.armor = Math.max(0, this.armor - 2);
        this.ammo = 50;

        // Respawn at new random spawn location
        Random random = new Random();
        this.transform.setPosition(GameObjectCollection.spawnPoints.get(random.nextInt(GameObjectCollection.spawnPoints.size())).getTransform().getPosition());
    }


    // --- MOVEMENT ---
    private void rotateRight() {
        this.transform.rotate(this.ROTATION_SPEED);
    }
    private void rotateLeft() {
        this.transform.rotate(-this.ROTATION_SPEED);
    }
    private void moveForwards() {
        this.transform.move(this.moveSpeed);
    }
    private void moveBackwards() {
        this.transform.move(-this.moveSpeed);
    }
    // --- MOVEMENT ---


    /**
     * Instantiate current weapon and deduct ammo.
     */
    private void fire() {
        if (this.fireCooldown >= this.fireDelay) {
            this.projectile = this.currentWeapon.createInstance(this.weaponSprite, this.damage, this);
            this.instantiate(this.projectile, this.transform.getPosition().add(this.originOffset), this.transform.getRotation());
            this.ammo--;
            this.fireCooldown = 0;
        }
    }

    /**
     * Take (at least 1) damage from bullets and lose life when out of HP.
     * Lose the game when out of lives.
     * @param damageDealt Damage of the weapon
     */
    public void takeDamage(int damageDealt) {
        // Always deal at least 1 damage regardless of armor
        this.currentHP -= Math.max(1, damageDealt - this.armor);
        if (this.currentHP <= 0) {
            this.currentLives--;
            if (this.currentLives <= 0) {
                this.loser = true;
                this.destroy();
            } else {
                this.respawn();
            }
        }
    }


    // --- POWERUPS ---
    public void addHealth(int value) {
        if ((this.currentHP += value) >= this.MAX_HP) {
            this.currentHP = this.MAX_HP;
        }
    }
    public void addSpeed(int value) {
        if ((this.moveSpeed += value) >= 10) {
            this.moveSpeed = 10;
        }
    }
    public void addFireRate(int value) {
        if ((this.fireRate += value) >= 10) {
            this.fireRate = 10;
        }
    }
    public void addDamage(int value) {
        if ((this.damage += value) >= 10) {
            this.damage = 10;
        }
    }
    public void addArmor(int value) {
        if ((this.armor += value) >= 10) {
            this.armor = 10;
        }
    }
    public void addAmmo(int value) {
        if ((this.ammo += value) >= 999) {
            this.ammo = 999;
        }
    }
    public void setWeapon(Weapon.Type newWeapon, BufferedImage sprWeapon) {
        this.currentWeapon = newWeapon;
        this.weaponSprite = sprWeapon;
    }
    public void setSprite(BufferedImage newSprite) {
        // Used by powerup gold, changes tank's sprite to a golden tank
        this.sprite = newSprite;
    }
    // --- POWERUPS ---


    /**
     * Pass the tank's current weapon to GameHUD to draw on screen.
     * @return Type of weapon
     */
    @Override
    public Weapon.Type getWeapon() {
        return this.currentWeapon;
    }

    /**
     * Pass ratio of the tank's weapon cooldown to GameHUD to draw on screen.
     * Used in the form of a meter.
     * @return Time elapsed / total time
     */
    @Override
    public float getCooldownRatio() {
        return this.fireCooldown / this.fireDelay;
    }

    /**
     * Pass the tank's stats to GameHUD to draw on screen.
     * @return Collection of the tank's stats
     */
    @Override
    public LinkedHashMap<String, Integer> getStats() {
        this.statsCollection.put("Health", this.currentHP);
        this.statsCollection.put("Lives", this.currentLives);
        this.statsCollection.put("Speed", this.moveSpeed);
        this.statsCollection.put("Fire Rate", this.fireRate);
        this.statsCollection.put("Damage", this.damage);
        this.statsCollection.put("Armor", this.armor);
        this.statsCollection.put("Ammo", this.ammo);

        return this.statsCollection;
    }

    /**
     * Controls tank movement and other key inputs such as fire.
     */
    @Override
    public void update() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);

        // Cooldown
        if (this.fireCooldown < this.fireDelay) {
            this.fireCooldown += this.fireRate;
        }

        // Movement
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        // Rotation
        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }

        // Weapon
        if (this.ActionPressed && this.ammo > 0) {
            this.fire();
        }
    }

    @Override
    public void collides(GameObject collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Tank collidingTank) {
        this.solidCollision(collidingTank);
    }

    @Override
    public void handleCollision(Wall collidingWall) {
        this.solidCollision(collidingWall);
    }

    @Override
    public void handleCollision(Weapon collidingWeapon) {
        // Damage handled in the weapon class
    }

    @Override
    public void handleCollision(Powerup collidingPowerup) {
        collidingPowerup.grantBonus(this);
        collidingPowerup.destroy();
    }

    /**
     * Draws additional information about the tank object to the game world such as aim line.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    @Override
    public void drawGizmos(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor((this.getHPRatio() > 0.5) ? Color.GREEN : (this.getHPRatio() > 0.25) ? Color.YELLOW : Color.RED);

        // Draw aim line
        float toX = (float) (this.getHPRatio() * 500 * Math.cos(Math.toRadians(this.transform.getRotation())));
        float toY = (float) (this.getHPRatio() * 500 * Math.sin(Math.toRadians(this.transform.getRotation())));
        g2d.drawLine((int) (this.transform.getPositionX() + this.originOffset.getX()), (int) (this.transform.getPositionY() + this.originOffset.getY()), (int) (this.transform.getPositionX() + this.originOffset.getX() + toX), (int) (this.transform.getPositionY() + this.originOffset.getY() + toY));
    }

    /**
     * Draws the game object's variables in the game world to g.
     * This method is called when drawDebug is true in GamePanel.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    @Override
    public void drawVariables(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawString("currentHP: " + this.currentHP, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 60);
        g2d.drawString("moveSpeed: " + this.moveSpeed, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 72);
        g2d.drawString("fireRate: " + this.fireRate, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 84);
        g2d.drawString("damage: " + this.damage, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 96);
        g2d.drawString("armor: " + this.armor, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 108);
        g2d.drawString("ammo: " + this.ammo, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 120);
    }

}
