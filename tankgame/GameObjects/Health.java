package GameObjects;

import util.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Health extends Powerup {

    public Health(BufferedImage sprite) {
        this.transform = new Transform();
        this.construct(sprite);

        this.type = Powerup.Type.Health;
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
