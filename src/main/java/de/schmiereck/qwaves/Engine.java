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
                    final Cell sourceCell = this.universe.getSpaceCell(spacePos, cellPos, Cell.Dir.Right);
                    sourceCell.getWaveListStream().forEach((sourceWave) -> {
                        final Event sourceEvent = sourceWave.getEvent();
                        // Source-Cell-Wave is a Barrier?
                        if (sourceEvent.getEventType() == 0) {
                            sourceCell.addWave(sourceWave);
                        }
                    });
                }
                // Diverge:
                {
                    if (spacePos < (this.universe.getSpaceSize() - 1)) {
                        for (final Cell.Dir dir : Cell.Dir.values()) {
                            final Cell sourceCell = this.universe.getSpaceCell(spacePos, cellPos + calcDirOffset(dir), dir);
                            sourceCell.getWaveListStream().forEach((sourceWave) -> {
                                final Event sourceEvent = sourceWave.getEvent();
                                // Source-Cell-Wave is a Particle?
                                if (sourceEvent.getEventType() == 1) {
                                    final Cell targetCell = this.universe.getSpaceCell(spacePos + 1, finalCellPos, dir);
                                    final Cell otherWaveTargetCell = this.universe.getSpaceCell(spacePos + 1, finalCellPos, this.universe.calcOtherDir(dir));
                                    // Target-Cell contains a barrier-wave?
                                    if (checkIsBarrier(otherWaveTargetCell)) {
                                        final Cell lastTargetCell = sourceWave.getTargetCell();
                                        // Already diverge 50%?
                                        if (lastTargetCell != null) {
                                            // Diverge the other 50% to same phase.
                                            lastTargetCell.addWave(sourceEvent.createWave());
                                        } else {
                                            // Diverge is impossible.
                                            // Stay in phase.
                                            sourceCell.addWave(sourceWave);
                                        }
                                    } else {
                                        targetCell.addWave(sourceEvent.createWave());
                                        final Cell lastTargetCell = sourceWave.getTargetCell();
                                        // Not diverged to a target?
                                        if (lastTargetCell == null) {
                                            // Source-Wave has no target but is already diverged?
                                            if (sourceWave.getWaveDiverge()) {
                                                // Diverge 100%.
                                                targetCell.addWave(sourceEvent.createWave());
                                            }
                                            sourceWave.setTargetCell(targetCell);
                                        }
                                    }
                                    sourceWave.setWaveDiverge(true);
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    private boolean checkIsBarrier(final Cell cell) {
        return cell.getWaveListStream().anyMatch(wave -> wave.getEvent().getEventType() == 0);
    }
}
