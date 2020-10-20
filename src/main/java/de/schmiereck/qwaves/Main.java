package de.schmiereck.qwaves;

public class Main {
    public static void main(final String[] args) {
        final int spaceSize = 4;
        final Universe universe = new Universe(1*2*3*4, spaceSize);
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
        final Event particleEvent = new Event(engine, 1);
        final Wave wave = particleEvent.createWave();
        universe.addEvent(0, 0, 8, particleEvent);

        // For Testing:
        // Add a sharp Barrier...
        final Event barrierEvent = new Event(engine, 0);
        // ...to the right...
        universe.addBariere(barrierEvent, 10, Cell.Dir.Right);
        // and to the left.
        universe.addBariere(barrierEvent, 6, Cell.Dir.Left);

        universe.calcNext();
        universe.calcReality();

        final boolean showSpaceCells = true;
        final boolean showReality = true;
        long runNr = 0;
        while (runNr < 4) {
            System.out.println("----------------------------------------------------------------------------------------------------");
            if (showSpaceCells) {
                for (int spacePos = spaceSize - 1; spacePos >= 0; spacePos--) {
                    for (int shiftPos = spacePos; shiftPos >= 0; shiftPos--) {
                        System.out.printf("%4d:%2d/%2d:", runNr, spacePos + 1, shiftPos);
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
            }
            if (showReality) {
                for (int spacePos = spaceSize - 1; spacePos >= 0; spacePos--) {
                    final int spaceNr = spacePos + 1;
                    int spaceWaveCount = 0;
                    System.out.printf("%4d:%3d:  ", runNr, spaceNr);
                    for (int cellPos = 0; cellPos < universe.getUniverseSize(); cellPos++) {
                        final RealityCell realityCell = universe.getRealityCell(cellPos);
                        if (realityCell.getBarrier(spacePos)) {
                            System.out.printf("|##");
                        } else {
                            final int waveCount = realityCell.getWaveCount(spacePos);
                            System.out.printf("|%2d", waveCount);
                            spaceWaveCount += waveCount;
                        }
                    }
                    final int spaceCellProbability = calcSumSum(spacePos);
                    System.out.printf("| 1/%d %d*%d=%d (%d)\n", spaceCellProbability, spaceCellProbability, spaceNr, spaceCellProbability * spaceNr, spaceWaveCount);
                }
            }
            universe.clearReality();
            engine.run();
            universe.calcNext();
            universe.calcReality();
            runNr++;
        }
    }

    private static int calcSumSum(final int n) {
        int ret = 1;
        for (int pos = 0; pos < n; pos++) {
            ret += (ret);
        }
        return ret;
    }
}
