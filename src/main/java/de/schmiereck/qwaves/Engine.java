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
        IntStream.range(0, this.universe.getUniverseSize()).forEach(cellPos -> {
            IntStream.range(0, this.universe.getSpaceSize()).forEach(spacePos -> {
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
                                        if (sourceWave.getDivergeCalculated()) {
                                            // Already diverge 50% in other direction?
                                            if (lastTargetCell != null) {
                                                // Diverge 50% to this target cell.
                                                lastTargetCell.addWave(sourceEvent.createWave());
                                                // And follow with the other 50% and diverge to same phase.
                                                lastTargetCell.addWave(sourceEvent.createWave());
                                            } else {
                                                // Diverge is impossible.
                                                // Stay in phase.
                                                sourceWave.setDivergeCalculated(false);
                                                sourceCell.addWave(sourceWave);
                                            }
                                        } else {
                                            // Found no target, but Diverge calculation is done.
                                            sourceWave.setDivergeCalculated(true);
                                        }
                                    } else {
                                        final Cell lastTargetCell = sourceWave.getTargetCell();
                                        if (sourceWave.getDivergeCalculated()) {
                                            // Diverged to a target yet?
                                            if (lastTargetCell != null) {
                                                // Diverge 50% to target cell.
                                                targetCell.addWave(sourceEvent.createWave());
                                                // Have last Target, than do this 50% to target.
                                                lastTargetCell.addWave(sourceEvent.createWave());
                                            } else {
                                                // Source-Wave has no target but is already diverged?
                                                // I want to diverge 50% to this target cell.
                                                // Diverge 50% to target cell.
                                                targetCell.addWave(sourceEvent.createWave());
                                                // Have no last Target, than do this 50% also.
                                                targetCell.addWave(sourceEvent.createWave());
                                            }
                                        } else {
                                            // Not diverged to a target yet.
                                            sourceWave.setTargetCell(targetCell);
                                            sourceWave.setDivergeCalculated(true);
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            });
        });
    }

    private boolean checkIsBarrier(final Cell cell) {
        return cell.getWaveListStream().anyMatch(wave -> wave.getEvent().getEventType() == 0);
    }
}
