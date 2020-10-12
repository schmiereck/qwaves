package de.schmiereck.qwaves;

import java.util.List;

public class Main {
    public static void main(final String[] args) {
        final Engine engine = new Engine();
        final Universe universe = new Universe(16, 1);

        final Rule leftRule = new Rule((cell, cellArr) -> {
            final int cellPos = 0;
            final List<Tick> tickList = cell.getTickList();
            if (cellArr[cellPos].getTickList().size() < tickList.size()) {
                final Tick tick = tickList.remove(0);
                cellArr[cellPos].addTick(tick);
            }
        });
        final Rule rightRule = new Rule((cell, cellArr) -> {
            final int cellPos = 1;
            final Cell nCell = cellArr[cellPos];
            final List<Tick> tickList = cell.getTickList();
            if (nCell.getTickList().size() < tickList.size()) {
                final Tick tick = tickList.remove(0);
                nCell.addTick(tick);
            }
        });
        final Event event = new Event();
        //event.addTicks(engine, leftRule, 10);
        event.addTicks(engine, rightRule, 6);

        universe.addEvent(0, 0, 0, event);

        long runNr = 0;
        while (runNr < 18) {
            System.out.printf("%4d:", engine.getTickPos());
            for (int cellPos = 0; cellPos < universe.getUniverseSize(); cellPos++) {
                final Cell cell = universe.getCell(0, 0, cellPos);
                final List<Tick> tickList = cell.getTickList();
                System.out.printf("| %2d", tickList.size());
            }
            System.out.println();
            engine.run();
            runNr++;
        }
    }
}
