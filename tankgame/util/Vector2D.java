package util;



public class Vector2D {

    private float x;
    private float y;

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(float xValue, float yValue) {
        this.x = xValue;
        this.y = yValue;
    }

    public Vector2D(Vector2D vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setX(float newX) {
        this.x = newX;
    }

    public void setY(float newY) {
        this.y = newY;
    }

    public void set(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    public void set(Vector2D vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    // Pseudo operator overloading
    public Vector2D add(Vector2D vec) {
        float x = this.x + vec.x;
        float y = this.y + vec.y;
        return new Vector2D(x, y);
    }

    public void move(float scalar) {
        this.x += scalar;
        this.y += scalar;
    }

    public void move(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void move(Vector2D vec) {
        this.x += vec.x;
        this.y += vec.y;
    }

    public boolean equals(Vector2D vec) {
        return (this.x == vec.x) && (this.y == vec.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
