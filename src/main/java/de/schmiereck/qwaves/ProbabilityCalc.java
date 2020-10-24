package de.schmiereck.qwaves;

public class ProbabilityCalc {
    public int tickPos = 0;
    public int base = 100;
    public int range = 12;
    public int probability = 100;
    public int stepTicks;
    public int remainsPropRange;
    public int remains = 0;

    public ProbabilityCalc(final int probability) {
        this.probability = probability;

    }

    private void initNextRange() {
        // Zähler:	  0 1 2 3 4 5 6 7 8 9 0 1  => Range 12

        // Aktion 1: 0 . . 1 . . 3 . . 4 . .  40% -> alle 3 (12/4=3) (12/9=1)
        // Aktion 2: . 0 . . . 1 . . . 2 . .  30% -> alle 4 (12/3=4)
        // Aktion 3: . . . 0 . . . 1 . . . 2  30% -> alle 4 (12/3=4)
        // x1 = 40% * 12 / 100%                   = 480 / 100%  = 4 Rest 80% / 100%

        // Aktion 1: 0 . 1 . 2 . 3 . 4 . 5 .  50% -> alle 2 (12/6=2)
        // x  = 50% * 12 / 100%         = 600% / 100%  = 6 Rest 0

        // xx%: 1200=100%*12
        final int maxPropRange = this.base * this.range;
        // 30%: 360%=30%*12
        // 40%: 480%=40%*12
        // 50%: 600%=50%*12
        // 60%: 720%=60%*12
        final int propRange = this.probability * this.range;
        // 30%: 3=360%/100%
        // 40%: 4=480%/100% 5=560%/100%
        // 50%: 6=600%/100%
        // 60%: 7=720%/100%
        final int countPerRange = (propRange + this.remains) / this.base;
        // 30%: (4=12/3)
        // 40%: (3=12/4)    2=12/5
        // 50%: (2=12/6)
        // 60%: (1=12/7)
        this.stepTicks = this.range / countPerRange;

        // 30%: 300=3*100
        // 40%: 400=4*100   500=5*100
        // 50%: 600=6*100
        // 60%: 700=7*100
        final int realPropRange = countPerRange * this.base;
        // 30%: 60=360-300;
        // 40%: 80=480-400; -20=480-500
        // 50%: 0=600-600;
        // 60%: 20=720-700;
        this.remainsPropRange = propRange - realPropRange;

        this.remains += this.remainsPropRange;
    }

    public boolean calcTick() {
        final boolean ret;

        final int rangeCount = this.tickPos % this.range;

        if (rangeCount == 0) {
            initNextRange();
        }

        final int stepPos = this.tickPos % this.stepTicks;

        if (stepPos == 0) {
            ret = true;
        } else {
            ret = false;
        }

        this.tickPos++;

        return ret;
    }
}
