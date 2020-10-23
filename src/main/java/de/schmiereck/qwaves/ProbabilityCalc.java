package de.schmiereck.qwaves;

public class ProbabilityCalc {
    public int tickPos = 0;
    public int base = 100;
    public int range = 12;
    public int probability = 100;

    public ProbabilityCalc(final int probability) {
        this.probability = probability;
    }

    public boolean calcTick() {
        final boolean ret;
        // x  = 50% * 12 / 100%         = 600% / 100%  = 6 Rest 0
        final int x = this.probability * this.range;
        final int countPerRange = x / this.base;
        final int remains = x % this.base;
        // (12/6=2)
        final int stepTicks = this.range / countPerRange;

        final int stepPos = this.tickPos % stepTicks;
        final int rangeCount = this.tickPos % stepTicks;

        if (stepPos == 0) {
            ret = true;
        } else {
            ret = false;
        }
        this.tickPos++;
        return ret;
    }
}
