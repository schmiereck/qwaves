package de.schmiereck.qwaves;

import java.util.List;

public class Wave {
    private final Event event;
    private Cell cell;

    public Wave(final Event event) {
        this.event = event;
    }

    public void setCell(final Cell cell) {
        this.cell = cell;
    }

    public Event getEvent() {
        return this.event;
    }
}
