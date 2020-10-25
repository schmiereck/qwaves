package de.schmiereck.qwaves;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        final String expected1 = "1.1.";
        final ProbabilityCalc probabilityCalc = new ProbabilityCalc(50);

        // assert
        assertProbability(expected1, probabilityCalc);
    }

    @org.junit.jupiter.api.Test
    void calc20() {
        // arrange
        //                        012345678901234567890123
        final String expected1 = "1.....1.....1.....1.....";
        final ProbabilityCalc probabilityCalc = new ProbabilityCalc(20);

        // assert
        assertProbability(expected1, probabilityCalc);
    }

    @org.junit.jupiter.api.Test
    void calc10() {
        // arrange
        //                        012345678901234567890123
        final String expected1 = "1...........1...........";
        final ProbabilityCalc probabilityCalc = new ProbabilityCalc(10);

        // assert
        assertProbability(expected1, probabilityCalc);
    }

    @org.junit.jupiter.api.Test
    void calc40() {
        // arrange
        //                        012345678901234567890123
        final String expected1 = "1..1..1..1..1.1.1.1.1.1.";
        final ProbabilityCalc probabilityCalc = new ProbabilityCalc(40);

        // assert
        assertProbability(expected1, probabilityCalc);
    }

    private void assertProbability(final String expected1, final ProbabilityCalc probabilityCalc) {
        final AtomicInteger pos = new AtomicInteger();
        expected1.chars().forEach(c -> {
            final boolean b = probabilityCalc.calcTick();
            if (c == '1') assertTrue(b, String.format("On position \"%s\".", pos)); else assertFalse(b, String.format("On position \"%s\".", pos));
            pos.getAndIncrement();
        });
    }
}
