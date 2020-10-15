package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;

import static de.schmiereck.qwaves.Universe.calcDirOffset;

public class Engine {
    private final Universe universe;
    private final List<Wave> waveList = new ArrayList<>();

    public Engine(final Universe universe) {
        this.universe = universe;
    }

    public void addTick(final Wave wave) {
        this.waveList.add(wave);
    }

    public List<Wave> getTickList() {
        return this.waveList;
    }

    public void run() {
         for (int spaceNr = this.universe.getSpaceSize() - 1; spaceNr > 0; spaceNr--) {
             for (int cellPos = 0; (cellPos + 1) < this.universe.getUniverseSize(); cellPos++) {
                for (Cell.Dir dir : Cell.Dir.values()) {
                    final Cell nCell = this.universe.getSpaceCell(spaceNr - 1, cellPos + calcDirOffset(dir), dir);
                    final int finalSpaceNr = spaceNr;
                    final int finalCellPos = cellPos;
                    nCell.getWaveListStream().forEach((wave) -> {
                        final Cell nsCell = this.universe.getSpaceCell(finalSpaceNr, finalCellPos, dir);
                        nsCell.addWave(new Wave(wave.getEvent()));
                    });
                }
            }
        }
     }
}
