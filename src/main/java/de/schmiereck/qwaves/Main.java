package de.schmiereck.qwaves;

public class Main {
    public static void main(final String[] args) {
        final int spaceSize = 2;
        final Universe universe = new Universe(16, spaceSize);
        final Engine engine = new Engine(universe);

        final Rule leftRule = new Rule((tick, cell, cellArr) -> {
            final int cellPos = 0;
            final Cell nCell = cellArr[cellPos];
            if (nCell.getWaveListSize() < cell.getWaveListSize()) {
                nCell.addWave(tick);
            } else {
                cell.addWave(tick);
            }
        });
        final Rule rightRule = new Rule((tick, cell, cellArr) -> {
            final int cellPos = 1;
            final Cell nCell = cellArr[cellPos];
            if (nCell.getWaveListSize() < cell.getWaveListSize()) {
                nCell.addWave(tick);
            } else {
                cell.addWave(tick);
            }
        });
        final Event event = new Event();
        event.addTicks(engine, leftRule, 8);
        event.addTicks(engine, rightRule, 8);

        universe.addEvent(0, 0, 8, event);

        long runNr = 0;
        while (runNr < 180) {
            for (int spacePos = 0; spacePos < spaceSize; spacePos++) {
                for (int shiftPos = 0; shiftPos < spacePos + 1; shiftPos++) {
                    System.out.printf("%2d/%2d:%4d:%4d:", spacePos, shiftPos, engine.getCellPos(), runNr);
                    for (int p = 0; p < ((shiftPos) * 3); p++) {
                        System.out.print(" ");
                    }
                    for (int cellPos = 0; cellPos < universe.getSpaceSize(spacePos); cellPos++) {
                        final Cell cell = universe.getCell(spacePos, shiftPos, cellPos);
                        System.out.printf("|%" + ((spacePos + 1) * 2 + (spacePos * 1)) + "d", cell.getWaveListSize());
                    }
                    System.out.println("|");
                }
            }
            engine.run(true);
            runNr++;
        }
    }
}
