package de.schmiereck.qwaves;

import java.util.List;

public class Main {
    public static void main(final String[] args) {
        final Universe universe = new Universe(16, 1);
        final Engine engine = new Engine(universe);

        final Rule leftRule = new Rule((tick, cell, cellArr) -> {
            final int cellPos = 0;
            final Cell nCell = cellArr[cellPos];
            if (nCell.getTickListSize() < cell.getTickListSize()) {
                nCell.addTick(tick);
            } else {
                cell.addTick(tick);
            }
        });
        final Rule rightRule = new Rule((tick, cell, cellArr) -> {
            final int cellPos = 1;
            final Cell nCell = cellArr[cellPos];
            if (nCell.getTickListSize() < cell.getTickListSize()) {
                nCell.addTick(tick);
            } else {
                cell.addTick(tick);
            }
        });
        final Event event = new Event();
        event.addTicks(engine, leftRule, 8);
        event.addTicks(engine, rightRule, 8);

        universe.addEvent(0, 0, 8, event);

        long runNr = 0;
        while (runNr < 180) {
            System.out.printf("%4d:%4d:", engine.getCellPos(), runNr);
            for (int cellPos = 0; cellPos < universe.getUniverseSize(); cellPos++) {
                final Cell cell = universe.getCell(0, 0, cellPos);
                System.out.printf("| %2d", cell.getTickListSize());
            }
            System.out.println();
            engine.run(true);
            runNr++;
        }
    }
}
