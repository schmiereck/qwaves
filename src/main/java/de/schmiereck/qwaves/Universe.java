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

        event.getTickList().forEach((tick) -> cell.addTick(tick));
    }

    public Cell getCell(final int spaceNr, final int spaceShiftNr, final int cellPos) {
        final PhaseSpace phaseSpace = this.spaceList.get(spaceNr);
        return phaseSpace.getCell(spaceShiftNr, cellPos);
    }
}
