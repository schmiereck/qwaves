package de.schmiereck.qwaves;

import static org.junit.jupiter.api.Assertions.*;

class UniverseTest {

    /**
     3            |. . .|. . .|. . .|. . .|1 1 1|. . .|. . .|. . .|. . .|. . .|
     3          |. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|. . .|
     3        |. . .|. . .|. . .|. . .|1 1 1|. . .|. . .|. . .|. . .|. . .|
     2          |. .|. .|. .|. .|. .|. .|1 1|. .|. .|. .|. .|. .|. .|. .|
     2        |. .|. .|. .|. .|. .|. .|. .|1 1|. .|. .|. .|. .|. .|. .|
     1        |.|.|.|.|.|.|.|.|.|.|.|.|.|.|1|.|.|.|.|.|.|.|.|.|.|.|.|.|
               0
     */
    @org.junit.jupiter.api.Test
    void calcPhaseShiftNr() {
        // assert
        assertEquals(0, Universe.calcPhaseShiftPos(0, 0, Cell.Dir.Right));
        assertEquals(0, Universe.calcPhaseShiftPos(0, 0, Cell.Dir.Left));
        assertEquals(0, Universe.calcPhaseShiftPos(0, 1, Cell.Dir.Right));
        assertEquals(0, Universe.calcPhaseShiftPos(0, 1, Cell.Dir.Left));
        assertEquals(0, Universe.calcPhaseShiftPos(0, 2, Cell.Dir.Right));
        assertEquals(0, Universe.calcPhaseShiftPos(0, 2, Cell.Dir.Left));

        assertEquals(0, Universe.calcPhaseShiftPos(1, 0, Cell.Dir.Right));
        assertEquals(1, Universe.calcPhaseShiftPos(1, 0, Cell.Dir.Left));
        assertEquals(1, Universe.calcPhaseShiftPos(1, 1, Cell.Dir.Right));
        assertEquals(0, Universe.calcPhaseShiftPos(1, 1, Cell.Dir.Left));
        assertEquals(0, Universe.calcPhaseShiftPos(1, 2, Cell.Dir.Right));
        assertEquals(1, Universe.calcPhaseShiftPos(1, 2, Cell.Dir.Left));
        assertEquals(1, Universe.calcPhaseShiftPos(1, 3, Cell.Dir.Right));
        assertEquals(0, Universe.calcPhaseShiftPos(1, 4, Cell.Dir.Right));
        assertEquals(1, Universe.calcPhaseShiftPos(1, 4, Cell.Dir.Left));
        assertEquals(1, Universe.calcPhaseShiftPos(1, 5, Cell.Dir.Right));
        assertEquals(0, Universe.calcPhaseShiftPos(1, 5, Cell.Dir.Left));

        assertEquals(0, Universe.calcPhaseShiftPos(2, 0, Cell.Dir.Right));
        assertEquals(1, Universe.calcPhaseShiftPos(2, 0, Cell.Dir.Left));
        assertEquals(1, Universe.calcPhaseShiftPos(2, 1, Cell.Dir.Right));
        assertEquals(2, Universe.calcPhaseShiftPos(2, 1, Cell.Dir.Left));
        assertEquals(2, Universe.calcPhaseShiftPos(2, 2, Cell.Dir.Right));
        assertEquals(0, Universe.calcPhaseShiftPos(2, 2, Cell.Dir.Left));
        assertEquals(0, Universe.calcPhaseShiftPos(2, 3, Cell.Dir.Right));
        assertEquals(1, Universe.calcPhaseShiftPos(2, 3, Cell.Dir.Left));
        assertEquals(1, Universe.calcPhaseShiftPos(2, 4, Cell.Dir.Right));
        assertEquals(2, Universe.calcPhaseShiftPos(2, 4, Cell.Dir.Left));
        assertEquals(2, Universe.calcPhaseShiftPos(2, 5, Cell.Dir.Right));
        assertEquals(0, Universe.calcPhaseShiftPos(2, 5, Cell.Dir.Left));
   }

    @org.junit.jupiter.api.Test
    void wrap() {
        // assert
        assertEquals(1, Universe.wrap(-2, 3));
        assertEquals(2, Universe.wrap(-1, 3));
        assertEquals(0, Universe.wrap(0, 3));
        assertEquals(1, Universe.wrap(1, 3));
        assertEquals(2, Universe.wrap(2, 3));
        assertEquals(0, Universe.wrap(3, 3));
        assertEquals(1, Universe.wrap(4, 3));
    }
}