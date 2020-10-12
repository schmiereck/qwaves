package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    private final Universe universe;
    private final List<Tick> tickList = new ArrayList<>();

    private int cellPos = 0;

    public Engine(final Universe universe) {
        this.universe = universe;
    }

    public int getCellPos() {
        return cellPos;
    }

    public void addTick(final Tick tick) {
        this.tickList.add(tick);
    }

    public List<Tick> getTickList() {
        return this.tickList;
    }

    public void run() {
        final Cell cell = this.universe.getCell(0, 0, this.cellPos);

        if (cell.haveFirstTick()) {
            final Tick tick = cell.removeFirstTick();

            tick.execute();
        }

        if ((this.cellPos + 1) < this.universe.getUniverseSize()) {
            this.cellPos++;
        } else {
            this.cellPos = 0;
        }
    }
}
