package Data;

public class Vector2D {
    int x;
    int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D vector) {
        this.x = vector.getX();
        this.y = vector.getY();
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2D coordinates) {
        this.x = coordinates.getX();
        this.y = coordinates.getY();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return new Integer(x);
    }

    public int getY() {
        return new Integer(y);
    }

    /**
     * updates this.class to vector sum of this.class + addend
     * @param addend
     */
    public void add(Vector2D addend) {
        this.x += + addend.getX();
        this.y += addend.getY();
    }

    /**
     * updates this.class to vector subtractino of this.cass - subtrahend
     * @param subtrahend
     */
    public void sub(Vector2D subtrahend) {
        this.x -= subtrahend.getX();
        this.y -= subtrahend.getY();
    }

    /**
     * Scalar multiplication of this.class
     * @param multiplier
     */
    public void multiply(int multiplier) {
        this.x *= multiplier;
        this.y *= multiplier;
    }

    public void division(int divisor) {
        this.x /= divisor;
        this.y /= divisor;
    }
}
