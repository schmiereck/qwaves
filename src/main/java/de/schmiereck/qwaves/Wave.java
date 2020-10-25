package de.schmiereck.qwaves;

import java.util.List;

public class Wave {
    private final Event event;
    private Cell cell;
    private Cell targetCell;
    private Cell.Dir dir = null;
    private final ProbabilityCalc placePulseProbCalc = new ProbabilityCalc(12, 100, 50);

    private boolean extendCalculated = false;

    public Wave(final Event event) {
        this.event = event;
    }

    public void setCell(final Cell cell) {
        this.cell = cell;
    }

    public Event getEvent() {
        return this.event;
    }

    public boolean getExtendCalculated() {
        return this.extendCalculated;
    }

    public void setExtendCalculated(final boolean extendCalculated) {
        this.extendCalculated = extendCalculated;
    }

    public void setTargetCell(final Cell targetCell) {
        this.targetCell = targetCell;
    }

    public Cell getTargetCell() {
        return this.targetCell;
    }

    public int getCalcState() {
        final int calcState;
        if (this.placePulseProbCalc.getExecute()) {
            calcState = 0;
        } else {
            calcState = 1;
        }
        return calcState;
    }

    public Wave createWave() {
        final Wave wave = createWave(this.dir, this.placePulseProbCalc);
        this.event.addWave(wave);
        return wave;
    }

    public Wave createWave(final Cell.Dir dir) {
        final Wave wave = createWave(dir, this.placePulseProbCalc);
        this.event.addWave(wave);
        return wave;
    }

    public Wave createWave(final Cell.Dir dir, final ProbabilityCalc placePulseProbCalc) {
        final Wave wave = new Wave(this.event);
        wave.setDir(dir);
        wave.setPlacePulseProbCalc(placePulseProbCalc);
        return wave;
    }

    private void setPlacePulseProbCalc(final ProbabilityCalc placePulseProbCalc) {
        this.placePulseProbCalc.copy(placePulseProbCalc);
    }

    public void calcPlacePulseProbCalc() {
        this.placePulseProbCalc.calcTick();
    }

    public Cell.Dir getDir() {
        return this.dir;
    }

    public void setDir(final Cell.Dir dir) {
        this.dir = dir;
    }
}
