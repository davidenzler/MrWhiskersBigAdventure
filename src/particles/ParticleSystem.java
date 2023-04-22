package particles;

import Data.Frame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleSystem {
    private Particle[] particles;
    private int x,y;
    private int xrange, yRange;
    private int maxLife;
    private String[] spriteTags;
    private int dieSides = 1;


    public ParticleSystem(int numParticles, int x, int y, int xrange, int yRange, int minLife,
                          int maxLife, int xMove, int yMove, int minDelay, int maxDelay, String[] spriteTags) {
        this.xrange = xrange;
        this. yRange = yRange;
        this.x = x;
        this.y = y;
        this.maxLife = maxLife;
        particles = new Particle[numParticles];
        this.spriteTags = spriteTags;
        initParticles(xMove, yMove, minDelay, maxDelay, minLife);
    }

    public ParticleSystem(int numParticles, int x, int y, int xrange, int yRange, int minLife,
                          int maxLife, int xMove, int yMove, int minDelay, int maxDelay, String[] spriteTags,
                           int dieSides) {
        this.xrange = xrange;
        this. yRange = yRange;
        this.x = x;
        this.y = y;
        this.maxLife = maxLife;
        particles = new Particle[numParticles];
        this.spriteTags = spriteTags;
        initParticles(xMove, yMove, minDelay, maxDelay, minLife);
        this.dieSides = dieSides;
    }

    private void initParticles(int xMove, int yMove, int mindDelay, int maxDelay, int _minLife) {
        for(int i=0; i<particles.length; i++) {
            int n = spriteTags.length;
            int index = Particle.getRandomInt(0, n-1);
            particles[i] = new Particle(x, x+xrange, y, y+yRange, spriteTags[index],
                    _minLife, maxLife, xMove, yMove, mindDelay, maxDelay);
        }
        // artificially age until through one life cycle
        boolean isDone = false;
        while(!isDone) {
            isDone = true;
            for(int i=0; i< particles.length; i++) {
                particles[i].simulateAge(dieSides);
                if(!particles[i].hasBeenReset())
                    isDone = false;
            }
        }
    }

    public Particle[] getParticleArray() {
        return particles;
    }
    public Iterator<Data.Frame> getParticles() {
        List<Frame> parts = new ArrayList<>();
        for(int i=0; i< particles.length; i++) {
            Frame temp = particles[i].getCurrentFrame(dieSides);
            parts.add(temp);
        }
        return parts.iterator();
    }
}
