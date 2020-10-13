package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    private final Universe universe;
    private final List<Wave> waveList = new ArrayList<>();

    private int cellPos = 0;

    public Engine(final Universe universe) {
        this.universe = universe;
    }

    public int getCellPos() {
        return cellPos;
    }

    public void addTick(final Wave wave) {
        this.waveList.add(wave);
    }

    public List<Wave> getTickList() {
        return this.waveList;
    }

    public void run(final boolean runAllCells) {
        do {
            final int spaceNr = 0;
            final int phaseShiftNr = 0;
            final Cell cell = this.universe.getCell(spaceNr, phaseShiftNr, this.cellPos);

            final Cell[] cellArr = new Cell[2];
            cellArr[Cell.Dir.Left.ordinal()] = cell.getNextCell(Cell.Dir.Left);
            cellArr[Cell.Dir.Right.ordinal()] = cell.getNextCell(Cell.Dir.Right);

            for (Cell.Dir dir : Cell.Dir.values()) {
                final Cell nCell = cellArr[dir.ordinal()];
                if (cell.getWaveListSize() < nCell.getWaveListSize()) {
                    final Cell nsCell = this.universe.getSpaceCell(spaceNr + 1, this.cellPos, dir);

                    if (nsCell.getWaveListSize() == 0) {
                        final Wave wave = nCell.fetchFirstWave();
                        nsCell.addWave(new Wave(wave.getEvent()));
                    }
                }
            }
            if ((this.cellPos + 1) < this.universe.getUniverseSize()) {
                this.cellPos++;
            } else {
                this.cellPos = 0;
                if (runAllCells) {
                    break;
                }
            }
        } while (runAllCells);
    }
}
