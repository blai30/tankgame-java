

import util.Vector2D;

import java.awt.image.BufferedImage;

public class Tank {

    private Vector2D position;
    private int angle;
    private BufferedImage sprite;

    private final int ROTATIONSPEED = 4;

    public Tank() {
        this.position = new Vector2D();
    }

    public Tank(Vector2D pos, int angle, BufferedImage sprite) {
        this.position = pos;
    }

    public void update() {

    }

}
