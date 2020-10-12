package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private Cell[] nextCellArr;
    private final List<Tick> tickList = new ArrayList<>();

    public void init(final Cell[] nextCellArr) {
        this.nextCellArr = nextCellArr;
    }

    public void addTick(final Tick tick) {
        this.tickList.add(tick);
        tick.setCell(this);
    }

    public List<Tick> getTickList() {
        return this.tickList;
    }

    public Cell getNextCell(final int cellNr) {
        return this.nextCellArr[cellNr];
    }
}
