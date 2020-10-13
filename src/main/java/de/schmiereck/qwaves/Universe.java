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

    public Universe(final int universeSize, final int spaceSize) {
        this.universeSize = universeSize;
        for (int spacePos = 0; spacePos < spaceSize; spacePos++) {
            final PhaseSpace phaseSpace = new PhaseSpace(spacePos + 1, universeSize);
            this.spaceList.add(phaseSpace);
        }
    }

    public int getUniverseSize() {
        return this.universeSize;
    }

    public void addEvent(final int spaceNr, final int spaceShiftNr, final int cellPos, final Event event) {
        final Cell cell = this.getCell(spaceNr, spaceShiftNr, cellPos);

        event.getTickList().forEach((tick) -> cell.addWave(tick));
    }

    public Cell getCell(final int spaceNr, final int phaseShiftNr, final int cellPos) {
        final PhaseSpace phaseSpace = this.spaceList.get(spaceNr);
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
        final int cellNr = cellPos / spaceNr;
        final int phaseShiftNr = calcPhaseShiftNr(spaceNr, cellPos, dir);
        return getCell(spaceNr, phaseShiftNr, cellNr);
    }

    public static int calcPhaseShiftNr(final int spaceNr, final int cellPos, final Cell.Dir dir) {
        final int phaseShiftNr;
        switch (dir) {
            case Left -> {
                phaseShiftNr = wrap(cellPos % spaceNr - 1, spaceNr);
            }
            case Right -> {
                phaseShiftNr = wrap(cellPos % spaceNr + 1, spaceNr);
            }
            default -> throw new RuntimeException(String.format("Unexcpected direction \"%s\".", dir));
        }
        return phaseShiftNr;
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
