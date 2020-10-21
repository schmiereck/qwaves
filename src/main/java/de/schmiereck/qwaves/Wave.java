package de.schmiereck.qwaves;

import java.util.List;

public class Wave {
    private final Event event;
    private Cell cell;
    private Cell targetCell;
    private int calcState = 0;

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
        final Wave wave = new Wave(this.event);
        this.event.addWave(wave);
        return wave;
    }
}
