package GameObjects;

import util.Transform;
import util.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * Tank object to be controlled by a player.
 */
public class Tank extends Player implements SolidObject {

    private final float ROTATION_SPEED = 3.2f;

    private BufferedImage sprBullet;

    private int hitPoints;
    private float moveSpeed;
    private float fireRate;
    private int bonusDamage;
    private int armor;
    private int ammo;

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

        // Default stats
        this.hitPoints = 10;
        this.moveSpeed = 4.2f;
        this.fireRate = 1.0f;
        this.bonusDamage = 0;
        this.armor = 1;
        this.ammo = 5;
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

        // Default stats
        this.hitPoints = 10;
        this.moveSpeed = 4.2f;
        this.fireRate = 1.0f;
        this.bonusDamage = 0;
        this.armor = 1;
        this.ammo = 5;
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
//        if (this.ammo > 0) {
            this.instantiate(new Bullet(this.sprBullet, this.bonusDamage), this.transform.getPosition().add(this.originOffset), this.transform.getRotation());
            this.ammo--;
//        }
    }

    /**
     * Controls tank movement and other key inputs such as fire.
     */
    @Override
    public void update() {
        this.collider.setRect(this.transform.getPositionX(), this.transform.getPositionY(), this.width, this.height);

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
            this.ActionPressed = false;
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

    }

    /**
     * Draws additional information about the tank object to the game world such as aim line.
     * This method is called when drawGizmos is true in GamePanel.
     * @param g Graphics object that is passed in for the game object to draw to
     */
    @Override
    public void drawGizmos(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw aim line
        float toX = (float) (500 * Math.cos(Math.toRadians(this.transform.getRotation())));
        float toY = (float) (500 * Math.sin(Math.toRadians(this.transform.getRotation())));
        g2d.drawLine((int) (this.transform.getPositionX() + this.originOffset.getX()), (int) (this.transform.getPositionY() + this.originOffset.getY()), (int) (this.transform.getPositionX() + this.originOffset.getX() + toX), (int) (this.transform.getPositionY() + this.originOffset.getY() + toY));

//        g2d.drawString("hitPoints: " + this.hitPoints, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 60);
//        g2d.drawString("moveSpeed: " + this.moveSpeed, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 72);
//        g2d.drawString("fireRate: " + this.fireRate, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 84);
//        g2d.drawString("bonusDamage: " + this.bonusDamage, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 96);
//        g2d.drawString("armor: " + this.armor, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 108);
//        g2d.drawString("ammo: " + this.ammo, this.transform.getPositionX(), this.transform.getPositionY() + this.height + 120);
    }

}
