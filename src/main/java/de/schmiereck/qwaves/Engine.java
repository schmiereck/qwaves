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

    public void addWave(final Wave wave) {
        this.waveList.add(wave);
    }

    public List<Wave> getWaveList() {
        return this.waveList;
    }

    public void run() {
        for (int spaceNr = this.universe.getSpaceSize(); spaceNr > 0; spaceNr--) {
            final int spacePos = spaceNr - 1;
            for (int cellPos = 0; cellPos < this.universe.getUniverseSize(); cellPos++) {
                final int finalCellPos = cellPos;
                // Stay:
                {
                    final Cell nCell = this.universe.getSpaceCell(spacePos, cellPos, Cell.Dir.Right);
                    nCell.getWaveListStream().forEach((wave) -> {
                        final Event event = wave.getEvent();
                        // Cell contains Barrier?
                        if (event.getEventType() == 0) {
                            nCell.addWave(wave);
                        }
                    });
                }
                // Diverge:
                {
                    if (spacePos < (this.universe.getSpaceSize() - 1)) {
                        for (final Cell.Dir dir : Cell.Dir.values()) {
                            final Cell nCell = this.universe.getSpaceCell(spacePos, cellPos + calcDirOffset(dir), dir);
                            nCell.getWaveListStream().forEach((wave) -> {
                                final Event event = wave.getEvent();
                                // Neighbour is Particle?
                                if (event.getEventType() == 1) {
                                    final Cell nsCell = this.universe.getSpaceCell(spacePos + 1, finalCellPos, dir);
                                    nsCell.addWave(event.createWave());
                                    wave.setWaveDiverge(true);
                                }
                            });
                        }
                    }
                }
            }
        }
    }
}
