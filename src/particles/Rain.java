package particles;

public class Rain {
    private ParticleSystem parts;
    private String[] spriteTags;

    public Rain(int xpos, int ypos, int xrange, int yrange, int minlife, int maxlife, int numParticles) {
        spriteTags = new String[5];
        for(int i=0; i < 5; i++) {
            spriteTags[i] = String.format("raindrop%d", (i+1));
        }
        int xspeed = 6;
        int yspeed = 16;
        parts = new ParticleSystem(numParticles, xpos, ypos, xrange, yrange, minlife, maxlife, xspeed,
                yspeed, 16, 18, spriteTags);
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
