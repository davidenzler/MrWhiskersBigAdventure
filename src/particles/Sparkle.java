package particles;

public class Sparkle {
    private ParticleSystem parts;
    private String[] spriteTags;

    public Sparkle(int xpos, int ypos, int xrange, int yrange, int minlife, int maxlife, int numParticles) {
        spriteTags = new String[3];
        for(int i=0; i < 3; i++) {
            spriteTags[i] = String.format("sparkle%d", (i+1));
        }
        int xspeed = 2;
        int yspeed = -8;
        parts = new ParticleSystem(
                numParticles,
                xpos, // lower bound for particle start
                ypos, // lower bound for particle start
                xrange, // max displacement for x values?
                yrange, // max displacement for y values
                minlife, // range for how many steps in each particle lifecycle
                maxlife,
                xspeed, // x & y pixel displacement each step
                yspeed,
                57, // range of values for how long each pixel animation lasts
                71,
                spriteTags);
    }

    public Sparkle(int xpos, int ypos, int xrange, int yrange, int minlife, int maxlife, int numParticles, int diesides) {
        spriteTags = new String[3];
        for(int i=0; i < 3; i++) {
            spriteTags[i] = String.format("sparkle%d", (i+1));
        }
        int xspeed = 1;
        int yspeed = -1;
        parts = new ParticleSystem(
                numParticles,
                xpos, // lower bound for particle start
                ypos, // lower bound for particle start
                xrange, // max displacement for x values?
                yrange, // max displacement for y values
                minlife, // range for how many steps in each particle lifecycle
                maxlife,
                xspeed, // x & y pixel displacement each step
                yspeed,
                45, // range of values for how long each pixel animation lasts
                 57,
                spriteTags,
                diesides);
    }

    private void updateParticleSprites() {
        Particle[] pa = parts.getParticleArray();
        for(int i=0; i<pa.length; i++) {
            int stages = spriteTags.length;
            int life = pa[i].getLifecycle();
            int range = life / stages;
            int age = pa[i].getAge();
            for(int j=0; j < stages; j++) {
                if(age >= (range *j) && age < (range*(j+1))) {
                    pa[i].changeSprite(spriteTags[j]);
                    break;
                }
            }
        }
    }

    public ParticleSystem getParticleSystem() {
        updateParticleSprites();
        return parts;
    }
}
