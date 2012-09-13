package library;

public class Clock {

    private int interval;
    private long lastTick;

    public Clock(int interval) {
        this.interval = interval;
        this.lastTick = System.currentTimeMillis() + interval;
    }

    public boolean isNextTick() {
        if ((System.currentTimeMillis() - interval) > lastTick) {
            lastTick = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }
    
    public void increaseInterval(int increase) {
        interval += increase;
    }
    
    public void notifyNextTick() {
        lastTick = 0;
    }
}
