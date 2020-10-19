package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;

public class PhaseSpace {
    private final int spaceNr;
    private final int cellCount;
    /**
     *  Space (Phasenraum)
     *      Phase-List per Phase-Shift (Phase)
     *          Cell (Zelle)
     */
    private final List<List<Cell>> shiftList = new ArrayList<>();

    public PhaseSpace(final Universe universe, final int spaceNr, final int universeSize) {
        this.spaceNr = spaceNr;
        this.cellCount = universeSize / this.spaceNr;
        for (int shiftPos = 0; shiftPos < spaceNr; shiftPos++) {
            final List<Cell> cellList = new ArrayList<>();
            this.shiftList.add(cellList);
            for (int cellPos = 0; cellPos < this.cellCount; cellPos++) {
                cellList.add(new Cell(universe));
            }
            for (int cellPos = 0; cellPos < this.cellCount; cellPos++) {
                final Cell cell = cellList.get(cellPos);
                final Cell[] nextCellArr = new Cell[2];
                nextCellArr[0] = this.getCell(cellList, cellPos - 1);
                nextCellArr[1] = this.getCell(cellList, cellPos + 1);
                cell.init(nextCellArr);
            }
        }
    }

    public Cell getCell(final List<Cell> cellList, final int cellPos) {
        final Cell cell;
        if (cellPos < 0) {
            cell = cellList.get(cellList.size() + cellPos);
        } else {
            cell = cellList.get(cellPos % cellList.size());
        }
        return cell;
    }

    public Cell getCell(final int phaseShiftPos, final int cellPos) {
        final List<Cell> cellList = this.shiftList.get(phaseShiftPos);
        return cellList.get(cellPos);
    }

    public int getSize() {
        return this.cellCount;
    }
}
