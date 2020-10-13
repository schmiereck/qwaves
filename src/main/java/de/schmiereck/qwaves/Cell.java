package de.schmiereck.qwaves;

import java.util.LinkedList;
import java.util.Queue;

public class Cell {
    public enum Dir {
        Left,
        Right
    };
    private Cell[] nextCellArr;
    private final Queue<Wave> waveList = new LinkedList<>();

    public void init(final Cell[] nextCellArr) {
        this.nextCellArr = nextCellArr;
    }

    public void addWave(final Wave wave) {
        this.waveList.add(wave);
        wave.setCell(this);
    }

    public Cell getNextCell(final Dir dir) {
        return this.nextCellArr[dir.ordinal()];
    }

    public int getWaveListSize() {
        return this.waveList.size();
    }

    public boolean haveFirstWave() {
        return !this.waveList.isEmpty();
    }

    public Wave removeFirstWave() {
        return this.waveList.remove();
    }

    public Wave fetchFirstWave() {
        return this.waveList.peek();
    }
}
