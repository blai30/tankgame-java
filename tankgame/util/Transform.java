package util;

public class Transform {

    private Vector2D position;
    private float rotation;

    public Transform() {
        this.position = new Vector2D();
        this.rotation = 0f;
    }

    public Transform(Transform transform) {
        this.position = transform.position;
        this.rotation = transform.rotation;
    }

    public Transform(Vector2D position, float rotation) {
        this.position = position;
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

    public float getRotation() {
        return this.rotation;
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
