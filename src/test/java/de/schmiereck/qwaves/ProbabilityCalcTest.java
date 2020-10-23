package de.schmiereck.qwaves;

import static org.junit.jupiter.api.Assertions.*;

public class ProbabilityCalcTest {

    @org.junit.jupiter.api.Test
    void calc100() {
        // arrange
        final ProbabilityCalc probabilityCalc = new ProbabilityCalc(100);

        // assert
        assertTrue(probabilityCalc.calcTick());
        assertTrue(probabilityCalc.calcTick());
        assertTrue(probabilityCalc.calcTick());
        assertTrue(probabilityCalc.calcTick());
    }

    @org.junit.jupiter.api.Test
    void calc50() {
        // arrange
        final ProbabilityCalc probabilityCalc = new ProbabilityCalc(50);

        // assert
        assertTrue(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertTrue(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
    }

    @org.junit.jupiter.api.Test
    void calc20() {
        // arrange
        final ProbabilityCalc probabilityCalc = new ProbabilityCalc(20);

        // assert
        assertTrue(probabilityCalc.calcTick()); // 0
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertTrue(probabilityCalc.calcTick()); // 6
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());    // 11
        assertTrue(probabilityCalc.calcTick());    // 12
    }

    @org.junit.jupiter.api.Test
    void calc10() {
        // arrange
        final ProbabilityCalc probabilityCalc = new ProbabilityCalc(10);

        // assert
        assertTrue(probabilityCalc.calcTick()); // 0
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick()); // 6
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());    // 11
        assertTrue(probabilityCalc.calcTick());    // 12
    }

    @org.junit.jupiter.api.Test
    void calc40() {
        // arrange
        final ProbabilityCalc probabilityCalc = new ProbabilityCalc(40);

        // assert
        assertTrue(probabilityCalc.calcTick()); // 0
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertTrue(probabilityCalc.calcTick()); // 3
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertTrue(probabilityCalc.calcTick()); // 6
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertTrue(probabilityCalc.calcTick()); // 9
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());    // 11
        assertTrue(probabilityCalc.calcTick());    // 12, 0
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertTrue(probabilityCalc.calcTick()); // 15, 3
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertTrue(probabilityCalc.calcTick()); // 18, 6
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());
        assertTrue(probabilityCalc.calcTick()); // 21, 9
        assertFalse(probabilityCalc.calcTick());
        assertFalse(probabilityCalc.calcTick());    // 23, 11
        assertTrue(probabilityCalc.calcTick()); // 24, 0
    }
}
