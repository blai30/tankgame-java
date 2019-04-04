package gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

/**
 * Tank object to be controlled by a player.
 */
public class Tank extends Player {

    private final float ROTATION_SPEED = 3.2f;

    private BufferedImage weaponSprite;
    private Weapon projectile;
    private Weapon.Type currentWeapon;

    private int moveSpeed;
    private int fireRate;
    private int bonusDamage;
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
        this.weaponSprite = weaponSprite;

        this.init();
    }

    private void init() {
        // Default weapon and stats
        this.currentWeapon = Weapon.Type.Bullet;
        this.currentHP = 10;
        this.lives = 5;

        this.moveSpeed = 4;
        this.fireRate = 1;
        this.bonusDamage = 0;
        this.armor = 0;
        this.ammo = 5;

        this.fireCooldown = 30f;
        this.fireDelay = 30f;
    }

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

    private void fire() {
        if (this.fireCooldown >= this.fireDelay) {
            this.projectile = this.currentWeapon.createInstance(this.weaponSprite, this.bonusDamage);
            this.instantiate(this.projectile, this.transform.getPosition().add(this.originOffset), this.transform.getRotation());
            this.ammo--;
            this.fireCooldown = 0;
        }
    }

    private void respawn() {
        this.lives--;
        this.currentHP = 10;
        // TODO: respawn at new location
    }

    private void takeDamage(int damageDealt) {
        // Always deal at least 1 damage regardless of armor
        this.currentHP -= Math.max(1, damageDealt - this.armor);
        if (this.currentHP <= 0) {
            this.respawn();
        }
    }


    // --- POWERUPS ---
    public void addHealth(int value) {
        this.currentHP += value;
        if (this.currentHP > 10) {
            this.currentHP = 10;
        }
    }

    public void addSpeed(int value) {
        this.moveSpeed += value;
        if (this.moveSpeed > 10) {
            this.moveSpeed = 10;
        }
    }

    public void addFireRate(int value) {
        this.fireRate += value;
        if (this.fireRate > 5) {
            this.fireRate = 5;
        }
    }

    public void addDamage(int value) {
        this.bonusDamage += value;
        if (this.bonusDamage > 10) {
            this.bonusDamage = 10;
        }
    }

    public void addArmor(int value) {
        this.armor += value;
        if (this.armor > 10) {
            this.armor = 10;
        }
    }

    public void addAmmo(int value) {
        this.ammo += value;
        if (this.ammo > 999) {
            this.ammo = 999;
        }
    }

    public void setWeapon(Weapon.Type newWeapon) {
        this.currentWeapon = newWeapon;
    }
    // --- POWERUPS ---


    @Override
    public Weapon.Type getWeapon() {
        return this.currentWeapon;
    }

    @Override
    public LinkedHashMap<String, Integer> getStats() {
        LinkedHashMap<String, Integer> statsCollection = new LinkedHashMap<>();

        statsCollection.put("Health", this.currentHP);
        statsCollection.put("Lives", this.lives);
        statsCollection.put("Speed", this.moveSpeed);
        statsCollection.put("Fire Rate", this.fireRate);
        statsCollection.put("Damage", this.bonusDamage);
        statsCollection.put("Armor", this.armor);
        statsCollection.put("Ammo", this.ammo);

        return statsCollection;
    }

    /**
     * Controls tank movement and other key inputs such as fire.
     */
    @Override
    public void update() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);

        if (this.fireCooldown <= this.fireDelay) {
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
        if (this.ActionPressed) {
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
        // Prevent currentWeapon from dealing damage to the tank that fires it
        if (this.projectile != collidingWeapon) {
            this.takeDamage(collidingWeapon.dealDamage());
            collidingWeapon.destroy();
        }
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
        g2d.setColor((this.currentHP > 5) ? Color.GREEN : (this.currentHP > 2) ? Color.YELLOW : Color.RED);

        // Draw aim line
        float toX = (float) (this.currentHP * 50 * Math.cos(Math.toRadians(this.transform.getRotation())));
        float toY = (float) (this.currentHP * 50 * Math.sin(Math.toRadians(this.transform.getRotation())));
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
        g2d.drawString("bonusDamage: " + this.bonusDamage, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 96);
        g2d.drawString("armor: " + this.armor, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 108);
        g2d.drawString("ammo: " + this.ammo, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 120);
    }

}
