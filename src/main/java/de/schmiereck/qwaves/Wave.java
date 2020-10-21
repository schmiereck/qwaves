package de.schmiereck.qwaves;

import java.util.List;

public class Wave {
    private final Event event;
    private Cell cell;
    private Cell targetCell;
    private int calcState = 0;
    private Cell.Dir dir = null;

    private boolean extendCalculated = false;

    public Wave(final Event event) {
        this.event = event;
    }

    public void setCell(final Cell cell) {
        this.cell = cell;
    }

    public Event getEvent() {
        return this.event;
    }

    public boolean getExtendCalculated() {
        return this.extendCalculated;
    }

    public void setExtendCalculated(final boolean extendCalculated) {
        this.extendCalculated = extendCalculated;
    }

    public void setTargetCell(final Cell targetCell) {
        this.targetCell = targetCell;
    }

    public Cell getTargetCell() {
        return this.targetCell;
    }

    public int getCalcState() {
        return this.calcState;
    }

    public void setCalcState(final int calcState) {
        this.calcState = calcState;
    }

    public Wave createWave() {
        final Wave wave = createWave(this.dir, this.calcState);
        this.event.addWave(wave);
        return wave;
    }

    public Wave createWave(final Cell.Dir dir) {
        final Wave wave = createWave(dir, this.calcState);
        this.event.addWave(wave);
        return wave;
    }

    public Wave createWave(final Cell.Dir dir, final int calcState) {
        final Wave wave = new Wave(this.event);
        wave.setDir(dir);
        wave.setCalcState(calcState);
        return wave;
    }

    public Cell.Dir getDir() {
        return this.dir;
    }

    public void setDir(final Cell.Dir dir) {
        this.dir = dir;
    }
}
