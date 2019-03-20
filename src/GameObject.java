

import util.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    BufferedImage sprite;
    Vector2D position;
    float angle;
    // TODO: collider

    public abstract void update();
    public abstract void drawImage(Graphics g);

}
