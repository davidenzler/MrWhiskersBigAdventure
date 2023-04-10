package Data;

import timer.stopWatchX;

import java.util.ArrayList;

public class Animation {
    private ArrayList<Frame> frames;
    private stopWatchX timer;
    private int cursor;
    private boolean isLooping;
    private boolean isFinished;
    private String tag;


    public Animation(int delay, boolean isLooping, String tag) {
        frames = new ArrayList<>();
        timer = new stopWatchX(delay);
        this.isLooping = isLooping;
        cursor = 0;
        isFinished = false;
        this.tag = tag;
    }

    public void addFrame(Frame newFrame) {
        frames.add(newFrame);
    }

    public void addFrame(int x, int y, String spriteTag) {
        frames.add(new Frame(x, y, spriteTag));
    }

    public Frame getCurrentFrame() {
        if(frames.isEmpty()) return null;
        if(timer.isTimeUp()) {
            timer.resetWatch();
            cursor++;
            if(cursor > (frames.size() - 1)) {
                if(isLooping) cursor = 0;
                else {
                    cursor--;
                    isFinished = true;
                }
            }
        }

        return frames.get(cursor);
    }

    public void reset() {
        if(isLooping) cursor = 0;
    }

    public String getTag() {
        return tag;
    }
}
