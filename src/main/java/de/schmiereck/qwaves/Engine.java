package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
            final int spacePos = spaceNr - 1;
            for (int cellPos = 0; cellPos < this.universe.getUniverseSize(); cellPos++) {
                final int finalCellPos = cellPos;
                // Diverge:
                for (final Cell.Dir dir : Cell.Dir.values()) {
                    final Cell nCell = this.universe.getSpaceCell(spacePos, cellPos + calcDirOffset(dir), dir);
                    nCell.getWaveListStream().forEach((wave) -> {
                        final Cell nsCell = this.universe.getSpaceCell(spacePos + 1, finalCellPos, dir);
                        nsCell.addWave(wave.getEvent().createWave());
                        wave.setWaveDiverge(true);
                    });
                }
            }
        }
    }
}
