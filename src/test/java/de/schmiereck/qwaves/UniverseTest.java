package de.schmiereck.qwaves;

import static org.junit.jupiter.api.Assertions.*;

class UniverseTest {

    @org.junit.jupiter.api.Test
    void calcPhaseShiftNr() {
        // assert
        assertEquals(0, Universe.calcPhaseShiftNr(0, 0, Cell.Dir.Right));
        assertEquals(0, Universe.calcPhaseShiftNr(0, 1, Cell.Dir.Right));
        assertEquals(0, Universe.calcPhaseShiftNr(0, 2, Cell.Dir.Right));
        assertEquals(0, Universe.calcPhaseShiftNr(0, 0, Cell.Dir.Left));
        assertEquals(0, Universe.calcPhaseShiftNr(0, 1, Cell.Dir.Left));
        assertEquals(0, Universe.calcPhaseShiftNr(0, 2, Cell.Dir.Left));

        assertEquals(0, Universe.calcPhaseShiftNr(1, 0, Cell.Dir.Right));
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