package util;



public class Vector2D {

    private int x;
    private int y;

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

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public boolean equals(Vector2D vec) {
        return (this.x == vec.x) && (this.y == vec.y);
    }

    public void set(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public void set(Vector2D vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public void add(int scalar) {
        this.x += scalar;
        this.y += scalar;
    }

    public void subtract(int scalar) {
        this.x -= scalar;
        this.y -= scalar;
    }

    public void multiply(int scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public void divide(int scalar) {
        this.x /= scalar;
        this.y /= scalar;
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void add(Vector2D vec) {
        this.x += vec.x;
        this.y += vec.y;
    }

    public void subtract(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void subtract(Vector2D vec) {
        this.x -= vec.x;
        this.y -= vec.y;
    }

    public void multiply(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void multiply(Vector2D vec) {
        this.x *= vec.x;
        this.y *= vec.y;
    }

    public void divide(int x, int y) {
        this.x /= x;
        this.y /= y;
    }

    public void divide(Vector2D vec) {
        this.x /= vec.x;
        this.y /= vec.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
