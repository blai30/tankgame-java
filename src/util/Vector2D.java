package util;



public class Vector2D {

    private float x;
    private float y;

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(int xValue, int yValue) {
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

    public boolean equals(Vector2D vec) {
        return (this.x == vec.x) && (this.y == vec.y);
    }

    public void set(float newX, float newY) {
        this.x = newX;
        this.y = newY;
    }

    public void set(Vector2D vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public void add(float scalar) {
        this.x += scalar;
        this.y += scalar;
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void add(Vector2D vec) {
        this.x += vec.x;
        this.y += vec.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
