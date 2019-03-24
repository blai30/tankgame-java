package util;

public class Transform {

    private Vector2D position;
    private Vector2D origin;
    private float rotation;

    public Transform() {
        this.position = new Vector2D();
        this.origin = new Vector2D();
        this.rotation = 0f;
    }

    public Transform(Transform transform) {
        this.position = transform.getPosition();
        this.origin = transform.getOrigin();
        this.rotation = transform.rotation;
    }

    public Transform(Vector2D position, Vector2D origin, float rotation) {
        this.position = position;
        this.origin = origin;
        this.rotation = rotation;
    }

    public Transform(float xPosition, float yPosition, float originX, float originY, float rotation) {
        this.position = new Vector2D(xPosition, yPosition);
        this.origin = new Vector2D(originX, originY);
        this.rotation = rotation;
    }

    public Vector2D getPosition() {
        return this.position;
    }

    public float getPositionX() {
        return this.position.getX();
    }

    public float getPositionY() {
        return this.position.getY();
    }

    public Vector2D getOrigin() {
        return this.origin;
    }

    public float getOriginX() {
        return this.origin.getX();
    }

    public float getOriginY() {
        return this.origin.getY();
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setTransform(Transform location) {
        this.position.set(location.getPositionX(), location.getPositionY());
        this.rotation = location.getRotation();
    }

    // Negative scalar for backwards movement
    public void move(float scalar) {
        float vx = (float) (scalar * Math.cos(Math.toRadians(this.rotation)));
        float vy = (float) (scalar * Math.sin(Math.toRadians(this.rotation)));
        this.position.move(vx, vy);
    }

    // Negative scalar for left rotation
    public void rotate(float scalar) {
        if (this.rotation >= 360.0f) {
            this.rotation -= 360.0f;
        } else if (this.rotation <= 0.0f) {
            this.rotation += 360.0f;
        }
        this.rotation += scalar;
    }

}
