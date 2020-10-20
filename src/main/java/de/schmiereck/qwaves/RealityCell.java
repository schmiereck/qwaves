package de.schmiereck.qwaves;

/**
 4              |. . . .|. . . .|1 1 1 1|. . . .|. . . .|. . . .|. . . .|		1/8	8*4=32
 4            |. . . .|. . . .|. . . .|1 1 1 1|. . . .|. . . .|. . . .|
 4          |. . . .|. . . .|. . . .|3 3 3 3|. . . .|. . . .|. . . .|
 4        |. . . .|. . . .|. . . .|3 3 3 3|. . . .|. . . .|. . . .|
                                 1 4 7 8 7 4 1
 3            |. . .|. . .|. . .|. . .|1 1 1|. . .|. . .|. . .|. . .|. . .|		1/4	4*3=12
 3          |. . .|. . .|. . .|. . .|2 2 2|. . .|. . .|. . .|. . .|. . .|
 3        |. . .|. . .|. . .|. . .|1 1 1|. . .|. . .|. . .|. . .|. . .|
                                   1 3 4 3 1
 2          |. .|. .|. .|. .|. .|. .|1 1|. .|. .|. .|. .|. .|. .|. .|			1/2	2*2=4
 2        |. .|. .|. .|. .|. .|. .|. .|1 1|. .|. .|. .|. .|. .|. .|
                                     1 2 1
 1        |.|.|.|.|.|.|.|.|.|.|.|.|.|.|1|.|.|.|.|.|.|.|.|.|.|.|.|.|				1/1	1*1=1
 1
 */
public class RealityCell {
    private int[] waveCount;
    private boolean[] barrier;

    RealityCell(final int spaceSize) {
        this.waveCount = new int[spaceSize];
        this.barrier = new boolean[spaceSize];
    }

    public void addWaveCount(final int spacePos, final int waveCount) {
        this.waveCount[spacePos] += waveCount;
    }

    public void setWaveCount(final int spacePos, final int waveCount) {
        this.waveCount[spacePos] = waveCount;
    }

    public int getWaveCount(final int spacePos) {
        return this.waveCount[spacePos];
    }

    public void setBarrier(final int spacePos, final boolean barrier) {
        this.barrier[spacePos] = barrier;
    }

    public boolean getBarrier(final int spacePos) {
        return this.barrier[spacePos];
    }
}
