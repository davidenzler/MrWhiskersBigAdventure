package Test;

public class StudCalc {
    public static void main(String[] args) {
        printStud(10, 0, 1.5f, 16.0f);
    }

    public static void printStud(int studNum, float offset, float thickness, float gap) {
        float left = offset + (studNum * gap);
        float right = left + thickness;
        System.out.printf("left: %f , right: %f", left, right);
    }
}
