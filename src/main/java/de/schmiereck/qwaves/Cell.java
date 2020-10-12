package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Cell {
    private Cell[] nextCellArr;
    private final Queue<Tick> tickList = new LinkedList<>();

    public void init(final Cell[] nextCellArr) {
        this.nextCellArr = nextCellArr;
    }

    public void addTick(final Tick tick) {
        this.tickList.add(tick);
        tick.setCell(this);
    }

    public int getTickListSize() {
        return this.tickList.size();
    }

    public Cell getNextCell(final int cellNr) {
        return this.nextCellArr[cellNr];
    }

    public boolean haveFirstTick() {
        return !this.tickList.isEmpty();
    }

    public Tick removeFirstTick() {
        return this.tickList.remove();
    }

    public Tick fetchFirstTick() {
        return this.tickList.peek();
    }
}
