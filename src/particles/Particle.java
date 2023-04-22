package particles;

import Data.Frame;
import timer.stopWatchX;

import static Main.Main.ScreenX;
import static Main.Main.ScreenY;

public class Particle {
    private int x,y;
    private String particleSpriteTag;
    private int lifecycle;
    private int age;
    private int xMove, yMove;
    private int xOffset, yOffset; // used to center range around point. allows range of [-n, n]
    private stopWatchX timer;
    private int rootX, rootY;
    private boolean isReset;

    public Particle(int minX, int maxX, int minY, int maxY, String particleSpriteTag,
                    int minLife, int maxLife, int xMove, int yMove, int mindDelay, int maxDelay) {
        this.particleSpriteTag = particleSpriteTag;
        this.x = getRandomInt(minX, maxX);
        this.y = getRandomInt(minY, maxY);
        lifecycle = getRandomInt(minLife, maxLife);
        this.xMove = xMove;
        this.yMove = yMove;
        int delay = getRandomInt(mindDelay, maxDelay);
        timer = new stopWatchX(delay);
        rootX = x;
        rootY = y;
    }

    public boolean hasBeenReset() {
        return isReset;
    }
    public void changeX(int newX) {
        x = newX;
    }
    public void changeY(int newY) {
        y = newY;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getLifecycle() {
        return lifecycle;
    }
    public  int getAge() {
        return age;
    }
    public void changeSprite(String newSpriteTag) {
        particleSpriteTag = newSpriteTag;
    }

    public boolean isParticleDead() {
        if(age >= lifecycle)
            return true;
        if(y > ScreenY || x > ScreenX)
            return true;
        return false;
    }
    public void simulateAge() {
        age++;
        x += xMove;
        y += yMove;
        if(isParticleDead()) {
            x = rootX;
            y = rootY;
            age = 0;
            isReset = true;
        }
    }
    public void simulateAge(int dieSides) {
        age++;
        int sign = 1;
        int diceroll = rollDie(dieSides);
        if(diceroll % 2 == 0)
            sign *= -1;
        x += xMove * sign;
        y += yMove;
        if(isParticleDead()) {
            x = rootX;
            y = rootY;
            age = 0;
            isReset = true;
        }
    }

    public Frame getCurrentFrame() {
        if(timer.isTimeUp()) {
            age++;
            x += xMove;
            y += yMove;
            if(isParticleDead()) {
                x = rootX;
                y = rootY;
                age = 0;
                isReset = true;
            }
            timer.resetWatch();
        }
        return new Frame(x, y, particleSpriteTag);
    }

    public Frame getCurrentFrame(int dieSides) {
        int sign = 1;
        int diceroll = rollDie(dieSides);
        if(diceroll % 2 == 0)
            sign *= -1;

        if(timer.isTimeUp()) {
            age++;
            x += xMove * sign;
            y += yMove;
            if(isParticleDead()) {
                x = rootX;
                y = rootY;
                age = 0;
                isReset = true;
            }
            timer.resetWatch();
        }
        return new Frame(x, y, particleSpriteTag);
    }

    public static  int getRandomInt(int first, int last) {
        int diff = last - first;
        double num = Math.random() * diff;
        int intNum = (int)num;
        return first + intNum;
    }

    public static int rollDie(int dieSides) {
        double result = Math.random() * dieSides;
        int intResult = (int) result;
        intResult++; // prevents returning '0' for die roll

        return intResult;
    }
}
