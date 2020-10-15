package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;

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
    private List<PhaseSpace> spaceList = new ArrayList<>();
    private int universeSize;
    private int spaceSize;

    public Universe(final int universeSize, final int spaceSize) {
        this.universeSize = universeSize;
        this.spaceSize = spaceSize;
        for (int spacePos = 0; spacePos < spaceSize; spacePos++) {
            final PhaseSpace phaseSpace = new PhaseSpace(spacePos + 1, universeSize);
            this.spaceList.add(phaseSpace);
        }
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

    public void addEvent(final int spaceNr, final int spaceShiftNr, final int cellPos, final Event event) {
        final Cell cell = this.getCell(spaceNr, spaceShiftNr, cellPos);

        event.getTickList().forEach((tick) -> cell.addWave(tick));
    }

    public Cell getCell(final int spacePos, final int phaseShiftNr, final int cellPos) {
        final PhaseSpace phaseSpace = this.spaceList.get(spacePos);
        return phaseSpace.getCell(phaseShiftNr, cellPos);
    }

    /**
     3            |. . .|. . .|. . .|. . .|1 1 1|. . .|. . .|. . .|. . .|. . .|
     3          |. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|
     3        |. . .|. . .|. . .|. . .|1 1 1|. . .|. . .|. . .|. . .|. . .|
     2          |. .|. .|. .|. .|. .|. .|1 1|. .|. .|. .|. .|. .|. .|. .|
     2        |. .|. .|. .|. .|. .|. .|. .|1 1|. .|. .|. .|. .|. .|. .|
     1        |.|.|.|.|.|.|.|.|.|.|.|.|.|.|1|.|.|.|.|.|.|.|.|.|.|.|.|.|
     */
    public Cell getSpaceCell(final int spaceNr, final int cellPos, final Cell.Dir dir) {
        final int wrapedCellPos = wrap(cellPos, this.universeSize);
        final int phaseShiftNr = calcPhaseShiftNr(spaceNr, wrapedCellPos, dir);
        final int cellNr = (wrapedCellPos - phaseShiftNr)  / (spaceNr + 1);
        return getCell(spaceNr, phaseShiftNr, cellNr);
    }

    public static int calcPhaseShiftNr(final int spaceNr, final int cellPos, final Cell.Dir dir) {
        final int phaseShiftNr;
        final int pos = cellPos % (spaceNr + 1);
        switch (dir) {
            case Left -> {
                phaseShiftNr = wrap( pos + 1, spaceNr + 1);
            }
            case Right -> {
                phaseShiftNr = wrap(pos, spaceNr + 1);
            }
            default -> throw new RuntimeException(String.format("Unexcpected direction \"%s\".", dir));
        }
        return phaseShiftNr;
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
}
