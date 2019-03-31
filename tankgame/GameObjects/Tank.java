package GameObjects;

import util.Transform;
import util.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;

/**
 * Tank object to be controlled by a player.
 */
public class Tank extends Player implements SolidObject {

    private final float ROTATION_SPEED = 3.2f;

    private BufferedImage sprBullet;
    private Bullet bullet;

    private int currentHP;
    private int lives;
    private float moveSpeed;
    private float fireRate;
    private int bonusDamage;
    private int armor;
    private int ammo;

    private float fireCooldown;
    private float fireDelay;

    /**
     * Constructs a tank by passing in a Transform object that the tank will now own.
     * @param transform The tank will take control of this Transform
     * @param sprite The image of this tank drawn to the screen
     * @param sprBullet The image of the bullet that this tank object will fire drawn to the screen
     */
    public Tank(Transform transform, BufferedImage sprite, BufferedImage sprBullet) {
        // Set properties
        this.transform = transform;
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
        this.collider = new Rectangle2D.Double(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);
        this.sprBullet = sprBullet;

        this.init();
    }

    /**
     * Constructs a tank from values to be constructed into a new Transform object.
     * @param xPosition The x coordinate of the tank in the game world
     * @param yPosition The y coordinate of the tank in the game world
     * @param rotation The rotation of the tank in degrees
     * @param sprite The image of this tank drawn to the screen
     * @param sprBullet The image of the bullet that this tank object will fire drawn to the screen
     */
    public Tank(float xPosition, float yPosition, float rotation, BufferedImage sprite, BufferedImage sprBullet) {
        // Set properties
        this.transform = new Transform(xPosition, yPosition, rotation);
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
        this.originOffset = new Vector2D(this.width / 2, this.height / 2);
        this.collider = new Rectangle2D.Double(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);
        this.sprBullet = sprBullet;

        this.init();
    }

    private void init() {
        // Default stats
        this.currentHP = 10;
        this.lives = 5;
        this.moveSpeed = 4.2f;
        this.fireRate = 1.0f;
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
            this.bullet = new Bullet(this.sprBullet, this.bonusDamage);
            this.instantiate(this.bullet, this.transform.getPosition().add(this.originOffset), this.transform.getRotation());
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

    @Override
    public int getHP() {
        return this.currentHP;
    }

    @Override
    public LinkedHashMap<String, Number> getStats() {
        LinkedHashMap<String, Number> statsCollection = new LinkedHashMap<>();

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
    public void handleCollision(Bullet collidingBullet) {
        // Prevent bullet from dealing damage to the tank that fires it
        if (this.bullet != collidingBullet) {
            this.takeDamage(collidingBullet.dealDamage());
            collidingBullet.destroy();
        }
    }

    /**
     * Draws additional information about the tank object to the game world such as aim line.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    @Override
    public void drawGizmos(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);

        // Draw aim line
        float toX = (float) (500 * Math.cos(Math.toRadians(this.transform.getRotation())));
        float toY = (float) (500 * Math.sin(Math.toRadians(this.transform.getRotation())));
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
