package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Universe {
    /**
     * Phase-Spaces:
     *      4              |. . . .|. . . .|. . . .|. . . .|. . . .|. . . .|. . . .|
     *      4            |. . . .|. . . .|. . . .|. . . .|. . . .|. . . .|. . . .|
     *      4          |. . . .|. . . .|. . . .|. . . .|. . . .|. . . .|. . . .|
     *      4        |. . . .|. . . .|. . . .|. . . .|. . . .|. . . .|. . . .|
     *      3            |. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|
     *      3          |. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|
     *      3        |. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|
     *      2          |. .|. .|. .|. .|. .|. .|. .|. .|. .|. .|. .|. .|. .|. .|
     *      2        |. .|. .|. .|. .|. .|. .|. .|. .|. .|. .|. .|. .|. .|. .|
     *      1        | | | | | | | | | | | | | | |8| | | | | | | | | | | | | |
     *
     *  Universe
     *      Space (Phasenraum)
     *          Phase-List per Phase-Shift (Phase)
     *              Cell (Zelle)
     */
    private final List<PhaseSpace> spaceList = new ArrayList<>();
    private final List<RealityCell> realityCellList = new ArrayList<>();
    private final int universeSize;
    private final int spaceSize;
    private int calcPos = 0;

    public Universe(final int universeSize, final int spaceSize) {
        this.universeSize = universeSize;
        this.spaceSize = spaceSize;
        IntStream.range(0, spaceSize).
                mapToObj(spacePos ->
                    new PhaseSpace(this, spacePos + 1, universeSize)).
                forEach(phaseSpace -> this.spaceList.add(phaseSpace));
        IntStream.range(0, this.universeSize).
                forEach(cellPos ->
                    this.realityCellList.add(new RealityCell(this.spaceSize)));
    }

    public int getUniverseSize() {
        return this.universeSize;
    }

    public int getSpaceSize() {
        return this.spaceSize;
    }

    public int getSpaceSize(final int spacePos) {
        final PhaseSpace phaseSpace = this.spaceList.get(spacePos);
        return phaseSpace.getSize();
    }

    public void addEvent(final int spacePos, final int spaceShiftPos, final int cellPos, final Event event) {
        final Cell cell = this.getCell(spacePos, spaceShiftPos, cellPos);

        event.getTickList().forEach((tick) -> cell.addWave(tick));
    }

    public Cell getCell(final int spacePos, final int phaseShiftPos, final int cellPos) {
        final PhaseSpace phaseSpace = this.spaceList.get(spacePos);
        return phaseSpace.getCell(phaseShiftPos, cellPos);
    }

    /**
     3            |. . .|. . .|. . .|. . .|1 1 1|. . .|. . .|. . .|. . .|. . .|
     3          |. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|
     3        |. . .|. . .|. . .|. . .|1 1 1|. . .|. . .|. . .|. . .|. . .|
     2          |. .|. .|. .|. .|. .|. .|1 1|. .|. .|. .|. .|. .|. .|. .|
     2        |. .|. .|. .|. .|. .|. .|. .|1 1|. .|. .|. .|. .|. .|. .|
     1        |.|.|.|.|.|.|.|.|.|.|.|.|.|.|1|.|.|.|.|.|.|.|.|.|.|.|.|.|
     */
    public Cell getSpaceCell(final int spacePos, final int cellPos, final Cell.Dir dir) {
        final int wrapedCellPos = wrap(cellPos, this.universeSize);
        final int phaseShiftPos = calcPhaseShiftPos(spacePos, wrapedCellPos, dir);
        final int phaseShiftCellPos = (wrapedCellPos - phaseShiftPos)  / (spacePos + 1);
        return this.getCell(spacePos, phaseShiftPos, phaseShiftCellPos);
    }

    /**
     3            |. . .|. . .|. . .|. . .|1 1 1|. . .|. . .|. . .|. . .|. . .|
     3          |. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|
     3        |. . .|. . .|. . .|. . .|1 1 1|. . .|. . .|. . .|. . .|. . .|
     2          |. .|. .|. .|. .|. .|. .|1 1|. .|. .|. .|. .|. .|. .|. .|
     2        |. .|. .|. .|. .|. .|. .|. .|1 1|. .|. .|. .|. .|. .|. .|
     1        |.|.|.|.|.|.|.|.|.|.|.|.|.|.|1|.|.|.|.|.|.|.|.|.|.|.|.|.|
     */
    public Cell getSpaceCell(final int spacePos, final int cellPos, final int phaseShiftPos) {
        final int wrapedCellPos = wrap(cellPos, this.universeSize);
        final int phaseShiftCellPos = (wrapedCellPos - phaseShiftPos)  / (spacePos + 1);
        return this.getCell(spacePos, phaseShiftPos, phaseShiftCellPos);
    }

    public static int calcPhaseShiftPos(final int spacePos, final int cellPos, final Cell.Dir dir) {
        final int phaseShiftPos;
        final int pos = cellPos % (spacePos + 1);
        switch (dir) {
            case Left -> {
                phaseShiftPos = wrap( pos + 1, spacePos + 1);
            }
            case Right -> {
                phaseShiftPos = wrap(pos, spacePos + 1);
            }
            default -> throw new RuntimeException(String.format("Unexcpected direction \"%s\".", dir));
        }
        return phaseShiftPos;
    }

    public static int calcDirOffset(final Cell.Dir dir) {
        final int dirOffset;
        switch (dir) {
            case Left -> {
                dirOffset = -1;
            }
            case Right -> {
                dirOffset = +1;
            }
            default -> throw new RuntimeException(String.format("Unexcpected direction \"%s\".", dir));
        }
        return dirOffset;
    }

    public static int wrap(final int pos, final int range) {
        final int ret;
        if (pos < 0) {
            ret = range + pos;
        } else {
            if (pos >= range) {
                ret = pos - range;
            } else {
                ret = pos;
            }
        }
        return ret;
    }

    public void clearReality() {
        IntStream.range(0, this.spaceSize).
                forEach(spacePos ->
                        this.realityCellList.forEach(realityCell -> {
                            realityCell.setWaveCount(spacePos, 0);
                            realityCell.setBarrier(spacePos, false);
                        }));
    }

    public RealityCell getRealityCell(final int cellPos) {
        return this.realityCellList.get(wrap(cellPos, this.universeSize));
    }

    public void calcReality() {
//      for (int cellPos = 0; cellPos < this.universeSize; cellPos++) {
        IntStream.range(0, this.universeSize).forEach((cellPos) -> {
//          for (int spacePos = 0; spacePos < this.spaceSize; spacePos++) {
            IntStream.range(0, this.spaceSize).forEach((spacePos) -> {
//              for (int phaseShiftPos = 0; phaseShiftPos <= spacePos; phaseShiftPos++) {
                IntStream.rangeClosed(0, spacePos).forEach((phaseShiftPos) -> {
                    final RealityCell realityCell = this.getRealityCell(cellPos);
                    final Cell cell = this.getSpaceCell(spacePos, cellPos, phaseShiftPos);
                    final int particleCount = (int) cell.getWaveListStream().filter((wave) -> wave.getEvent().getEventType() == 1).count();
                    final int barrierCount = (int) cell.getWaveListStream().filter((wave) -> wave.getEvent().getEventType() == 0).count();
                    realityCell.addWaveCount(spacePos, particleCount);
                    if (barrierCount > 0) {
                        realityCell.setBarrier(spacePos, true);
                    }
                });
            });
        });
    }

    public int getCalcPos() {
        return this.calcPos;
    }

    public int getActCalcPos() {
        return this.calcPos % 2;
    }

    public int getNextCalcPos() {
        return (this.calcPos + 1) % 2;
    }

    public void calcNext() {
//      for (int cellPos = 0; cellPos < this.universeSize; cellPos++) {
        IntStream.range(0, this.universeSize).forEach((cellPos) -> {
//          for (int spacePos = 0; spacePos < this.spaceSize; spacePos++) {
            IntStream.range(0, this.spaceSize).forEach((spacePos) -> {
//              for (int phaseShiftPos = 0; phaseShiftPos <= spacePos; phaseShiftPos++) {
                IntStream.rangeClosed(0, spacePos).forEach((phaseShiftPos) -> {
                    final Cell cell = this.getSpaceCell(spacePos, cellPos, phaseShiftPos);
                    cell.clearWaveList();
                });
            });
        });
        this.calcPos++;
    }

    public void addBariere(final Event event, final int cellPos, final Cell.Dir dir) {
        IntStream.range(0, this.spaceSize).forEach((spacePos) -> {
            final Cell cell = this.getSpaceCell(spacePos, cellPos, dir);
            cell.addWave(event.createWave(100));
        });
    }

    public static Cell.Dir calcOtherDir(final Cell.Dir dir) {
        final Cell.Dir retDir;
        switch (dir) {
            case Left -> {
                retDir = Cell.Dir.Right;
            }
            case Right -> {
                retDir = Cell.Dir.Left;
            }
            default -> throw new RuntimeException(String.format("Unexcpected direction \"%s\".", dir));
        }
        return retDir;
    }
}
