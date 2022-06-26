package util;

public class Time {
    public static float timeStatrted = System.nanoTime();

    public static float getTime() {
        return (float) ((System.nanoTime() - timeStatrted) * 1E-9);
    }
}
