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
        this.universe.clearReality();

        IntStream.range(0, this.universe.getUniverseSize()).forEach(cellPos -> {
            IntStream.range(0, this.universe.getSpaceSize()).forEach(spacePos -> {
                // Stay (Barrier):
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
                    for (final Cell.Dir calcDir : Cell.Dir.values()) {
                        final Cell sourceCell = this.universe.getSpaceCell(spacePos, cellPos + calcDirOffset(calcDir), calcDir);
                        sourceCell.getWaveListStream().forEach((sourceWave) -> {
                            final Event sourceEvent = sourceWave.getEvent();
                            // Source-Cell-Wave is a Particle?
                            if (sourceEvent.getEventType() == 1) {
                                final int calcState = sourceWave.getCalcState();
                                switch (calcState) {
                                    case 0 ->  calcExtend(spacePos, cellPos, sourceCell, sourceWave, calcDir);
                                    case 1 ->  calcMove(spacePos, cellPos, sourceCell, sourceWave, calcDir);
                                    default -> throw new IllegalStateException("Unexpected calcState value: " + calcState);
                                }
                            }
                        });
                    }
                }
            });
        });
        this.universe.calcNext();
        this.universe.calcReality();
    }

    private void calcExtend(final int spacePos, final int cellPos, final Cell sourceCell, final Wave sourceWave, final Cell.Dir calcDir) {
        final Cell targetCell = this.universe.getSpaceCell(spacePos + 1, cellPos, calcDir);
        final Cell otherWaveTargetCell = this.universe.getSpaceCell(spacePos + 1, cellPos, this.universe.calcOtherDir(calcDir));
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
                    sourceCell.addWave(sourceWave.createWave());
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
                    // Source-Wave has no target but is already extend?
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

    private void calcMove(final int spacePos, final int cellPos, final Cell sourceCell, final Wave sourceWave, final Cell.Dir calcDir) {
        final Cell.Dir moveDir = sourceWave.getDir();

        // Wave wants to move?
        if (moveDir != null) {
            final Cell.Dir otherCalcDir = this.universe.calcOtherDir(calcDir);

            // Direction the wave wants to move is calculated?
            if (moveDir == otherCalcDir) {
                final Cell otherWaveTargetCell = this.universe.getSpaceCell(spacePos, cellPos, otherCalcDir);

                // Target-Cell contains a barrier-wave?
                if (checkIsBarrier(otherWaveTargetCell)) {
                    // Move is impossible, revert direction (reflection) and stay in phase.
                    sourceCell.addWave(sourceWave.createWave(Universe.calcOtherDir(moveDir)));
                } else {
                    final Cell targetCell = this.universe.getSpaceCell(spacePos, cellPos, calcDir);
                    // Move 100% to target cell in same phase space.
                    targetCell.addWave(sourceWave.createWave());
                }
            }
        } else {
            // Wave do not want to move, stay in phase.
            sourceCell.addWave(sourceWave.createWave());
        }
    }

    private boolean checkIsBarrier(final Cell cell) {
        return cell.getWaveListStream().anyMatch(wave -> wave.getEvent().getEventType() == 0);
    }
}
