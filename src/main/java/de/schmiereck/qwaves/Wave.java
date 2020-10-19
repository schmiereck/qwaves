package de.schmiereck.qwaves;

import java.util.List;

public class Wave {
    private final Event event;
    private Cell cell;

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

    public boolean isWaveDiverge() {
        return this.waveDiverge;
    }

    public void setWaveDiverge(final boolean waveDiverge) {
        this.waveDiverge = waveDiverge;
    }
}
