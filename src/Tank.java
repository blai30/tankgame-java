

import util.Vector2D;

import java.awt.image.BufferedImage;

public class Tank {

    private Vector2D position;
    private int angle;

    private final int ROTATIONSPEED = 2;

    private BufferedImage sprite;

    public Tank() {
        this.position = new Vector2D();
    }

    public Tank(Vector2D pos, int angle, BufferedImage sprite) {
        this.position = pos;
        this.angle = angle;
        this.sprite = sprite;
    }

    public void update() {

    }

}
