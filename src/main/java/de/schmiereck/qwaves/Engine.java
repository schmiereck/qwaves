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
                if (spacePos < (this.universe.getSpaceSize() - 1)) {
                    for (final Cell.Dir dir : Cell.Dir.values()) {
                        final Cell sourceCell = this.universe.getSpaceCell(spacePos, cellPos + calcDirOffset(dir), dir);
                        sourceCell.getWaveListStream().forEach((sourceWave) -> {
                            final Event sourceEvent = sourceWave.getEvent();
                            // Source-Cell-Wave is a Particle?
                            if (sourceEvent.getEventType() == 1) {
                                final Cell targetCell = this.universe.getSpaceCell(spacePos + 1, finalCellPos, dir);
                                final Cell otherWaveTargetCell = this.universe.getSpaceCell(spacePos + 1, finalCellPos, this.universe.calcOtherDir(dir));
                                final int calcState = sourceWave.getCalcState();
                                switch (calcState) {
                                    case 0 -> { // Extend:
                                        // Target-Cell contains a barrier-wave?
                                        if (checkIsBarrier(otherWaveTargetCell)) {
                                            final Cell lastTargetCell = sourceWave.getTargetCell();
                                            if (sourceWave.getExtendCalculated()) {
                                                // Already extend 50% in other direction?
                                                if (lastTargetCell != null) {
                                                    // Extend 50% to this target cell.
                                                    lastTargetCell.addWave(sourceWave.createWave());
                                                    // And follow with the other 50% and extend to same phase.
                                                    lastTargetCell.addWave(sourceWave.createWave());
                                                } else {
                                                    // Extend is impossible.
                                                    // Stay in phase.
                                                    sourceWave.setExtendCalculated(false);
                                                    sourceCell.addWave(sourceWave);
                                                }
                                            } else {
                                                // Found no target, but Extend calculation is done.
                                                sourceWave.setExtendCalculated(true);
                                            }
                                        } else {
                                            final Cell lastTargetCell = sourceWave.getTargetCell();
                                            if (sourceWave.getExtendCalculated()) {
                                                // Extended to a target yet?
                                                if (lastTargetCell != null) {
                                                    // Extend 50% to target cell.
                                                    targetCell.addWave(sourceWave.createWave());
                                                    // Have last Target, than do this 50% to target.
                                                    lastTargetCell.addWave(sourceWave.createWave());
                                                } else {
                                                    // Source-Wave has no target but is already extendd?
                                                    // I want to extend 50% to this target cell.
                                                    // Extend 50% to target cell.
                                                    targetCell.addWave(sourceWave.createWave());
                                                    // Have no last Target, than do this 50% also.
                                                    targetCell.addWave(sourceWave.createWave());
                                                }
                                            } else {
                                                // Not extended to a target yet.
                                                sourceWave.setTargetCell(targetCell);
                                                sourceWave.setExtendCalculated(true);
                                            }
                                        }
                                    }
                                    case 1 -> { // Move:

                                    }
                                    default -> throw new IllegalStateException("Unexpected calcState value: " + calcState);
                                }
                            }
                        });
                    }
                }
            });
        });
    }

    private boolean checkIsBarrier(final Cell cell) {
        return cell.getWaveListStream().anyMatch(wave -> wave.getEvent().getEventType() == 0);
    }
}
