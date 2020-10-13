package de.schmiereck.qwaves;

import java.util.List;

public class Wave {
    private final Rule rule;
    private Cell cell;

    public Wave(final Rule rule) {
        this.rule = rule;
    }

    public void setCell(final Cell cell) {
        this.cell = cell;
    }

    public void execute() {
        final Cell[] cellArr = new Cell[2];
        cellArr[0] = cell.getNextCell(Cell.Dir.Left);
        cellArr[1] = cell.getNextCell(Cell.Dir.Right);

        this.rule.execute(this, this.cell, cellArr);
        /*
        for (int cellPos = 0; cellPos < cellArr.length; cellPos++) {
            final List<Tick> tickList = this.cell.getTickList();
            if (cellArr[cellPos].getTickList().size() < tickList.size()) {
                final Tick tick = tickList.remove(0);
                cellArr[cellPos].addTick(tick);
            }
        }
        */
    }
}
