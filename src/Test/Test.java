package Test;

import java.awt.*;

public class Test {
    public static DisplayMode Max(DisplayMode[] dms) {
        DisplayMode max_resolution = dms[0];
        for (DisplayMode mode: dms
             ) {
            if(mode.getHeight() > max_resolution.getHeight())
                max_resolution = mode;
        }

        return max_resolution;
    }

    public static void main(String[] args) {
        DisplayMode dm;
        GraphicsDevice[] test = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        DisplayMode[] dms = gd.getDisplayModes();


        dm = Max(dms);
        System.out.println(dm.getWidth() + " x " + dm.getHeight());
    }
}
/**
public static boolean isBitSet(int num, int bitnum) {
   int n = 1 << bitnum;
   return n & num > 0;
}

 public static int setBit(int num, int bitnum) {
    int n = 1 << bitnum;
    return num | n
 }

 public static int unsetBit(int num, int bitnum) {
    int n = 1 << bitnum;
    if(!isBitSet(num, bitnum)) return num;
    else return num ^ n;
 }
 4:1 binary:hex
 A93D
 (1010) (1001) (0011) (1101)
 */