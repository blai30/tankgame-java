package GameObjects;

import util.Transform;
import util.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class GameObject {

    protected BufferedImage sprite;
    protected Transform transform;
    // TODO: collider

    public void instantiate(GameObject spawnObj, Transform location) {
        spawnObj.transform.setTransform(location);
        add(spawnObj);
    }

    public void drawSprite(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.transform.getPositionX(), this.transform.getPositionY());
        rotation.rotate(Math.toRadians(this.transform.getRotation()), this.sprite.getWidth() / 2.0, this.sprite.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getSimpleName() + "] " + "Position: " + this.transform.getPosition() + ", Angle: " + this.transform.getRotation();
    }

    public abstract void update();



    private static ArrayList<GameObject> gameObjects;

    public static void init() {
        gameObjects = new ArrayList<>();
    }

    private static void add(GameObject instantiatedObj) {
        gameObjects.add(instantiatedObj);
    }

    public static void spawn(GameObject obj) {
        gameObjects.add(obj);
    }

    public static ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public static int numGameObjects() {
        return gameObjects.size();
    }

    public static GameObject getGameObject(int index) {
        return gameObjects.get(index);
    }

}
