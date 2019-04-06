package gameobjects;

import util.Transform;

public class Spawn {

    private Transform transform;

    public Spawn() {
        this.transform = new Transform();
    }

    public Spawn(float xPosition, float yPosition, float rotation) {
        this.transform = new Transform(xPosition, yPosition, rotation);
    }

    public Transform getTransform() {
        return this.transform;
    }

}
