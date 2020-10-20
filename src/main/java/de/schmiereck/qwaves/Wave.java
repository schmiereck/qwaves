package de.schmiereck.qwaves;

import java.util.List;

public class Wave {
    private final Event event;
    private Cell cell;
    private Cell targetCell;

    private boolean waveDiverge = false;

    public Wave(final Event event) {
        this.event = event;
    }

    public void setCell(final Cell cell) {
        this.cell = cell;
    }

    public Event getEvent() {
        return this.event;
    }

    public boolean getWaveDiverge() {
        return this.waveDiverge;
    }

    public void setWaveDiverge(final boolean waveDiverge) {
        this.waveDiverge = waveDiverge;
    }

    public void setTargetCell(final Cell targetCell) {
        this.targetCell = targetCell;
    }

    public Cell getTargetCell() {
        return this.targetCell;
    }
}
