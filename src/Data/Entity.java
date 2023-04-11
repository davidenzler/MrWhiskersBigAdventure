package Data;

import Data.Vector2D;

public class Entity {
    private Vector2D position;
    public int width = 64;
    public int height = 64;
    private int speed;
    private Direction direction;


    enum Direction {
        LEFT,
        RIGHT,
        IDLE
    }

    public Entity() {
        speed = 10;
        position = new Vector2D(0,0);
        direction = Direction.RIGHT;
    }

    public Entity(int speed) {
        this.speed   = speed;
        position = new Vector2D(0,0);
        direction = Direction.RIGHT;
    }
    public Entity(int speed, int x, int y) {
        this.speed   = speed;
        position = new Vector2D(x,y);
        direction = Direction.RIGHT;
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Vector2D getPosition() {
        return new Vector2D(position.getX(), position.getY());
    }

    public int getSpeed() {
        return speed;
    }

    public void setPosition(Vector2D position) {
        this.position.set(position.getX(), position.getY());
    }

    public void setPosition(int x, int y) {
        this.position.set(x, y);
    }

    public void changePosition(Vector2D delta) {
        if(delta.getX() < 0) {
            direction = Direction.LEFT;
        } else if(delta.getX() > 0) {
            direction = Direction.RIGHT;
        } else if (delta.getY() == 0){
            direction = Direction.IDLE;
        }

        position.add(delta);
    }

    public Direction getDirection() {
        return direction;
    }
}
