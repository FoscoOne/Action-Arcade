package library;

import java.util.Random;

public class RandomNumber {

    private double min = 0;
    private double max = 0;

    public RandomNumber(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public RandomNumber(double max) {
        this.max = max;
    }

    public double getRandomDouble() {
        return (min + (new Random()).nextDouble() * (max - min));
    }

    public float getRandomFloat() {
        return (float) (Math.round((min + (new Random()).nextDouble() * (max
                - min))));
    }
    
    public int getRandomInt() {
        return (int) (Math.round((min + (new Random()).nextDouble() * (max
                - min))));
    }
}
