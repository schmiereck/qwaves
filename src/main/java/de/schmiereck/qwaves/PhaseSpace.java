package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;

public class PhaseSpace {
    private int spaceNr;
    private int cellSize;
    private List<List<Cell>> shiftList = new ArrayList<>();

    public PhaseSpace(final int spaceNr, final int universeSize) {
        this.cellSize = spaceNr;
        this.cellSize = universeSize / this.cellSize;
        final int cellCount = this.cellSize / spaceNr;
        for (int shiftPos = 0; shiftPos < spaceNr; shiftPos++) {
            final List<Cell> cellList = new ArrayList<Cell>();
            this.shiftList.add(cellList);
            for (int cellPos = 0; cellPos < cellCount; cellPos++) {
                cellList.add(new Cell());
            }
            for (int cellPos = 0; cellPos < cellCount; cellPos++) {
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

    public Cell getCell(final int spaceShiftNr, final int cellPos) {
        final List<Cell> cellList = this.shiftList.get(spaceShiftNr);
        return cellList.get(cellPos);
    }
}
