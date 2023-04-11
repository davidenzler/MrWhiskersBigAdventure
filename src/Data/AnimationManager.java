package Data;

import Data.Animation;
import Data.Frame;

import java.util.HashMap;

public class AnimationManager {
    private HashMap<String, Animation> animations;

    public AnimationManager() {
        animations = new HashMap<>();
    }

    public void addAnimation(Animation animation) {
        animations.put(animation.getTag(), animation);
    }

    public Frame getCurrentFrame(String tag) {
        Animation selectedAnimation = animations.get(tag);
        if(selectedAnimation != null) {
            return selectedAnimation.getCurrentFrame();
        } else {
            return null;
        }
    }

    public Animation getAnimation(String tag) {
        Animation selectedAnimation = animations.get(tag);
        if(selectedAnimation != null) {
            return selectedAnimation;
        } else {
            return null;
        }
    }
}
