package de.schmiereck.qwaves;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Cell {
    public enum Dir {
        Left,
        Right
    };
    private Cell[] nextCellArr;
    private final Queue<Wave>[] waveList;
    private final Universe universe;
    
    public Cell(final Universe universe) {
        this.universe = universe;
        this.waveList = IntStream.rangeClosed(0, 1).mapToObj(pos -> new LinkedList<Wave>()).toArray(LinkedList[]::new);
    }

    public void init(final Cell[] nextCellArr) {
        this.nextCellArr = nextCellArr;
    }

    public void addWave(final Wave wave) {
        this.waveList[this.universe.getNextCalcPos()].add(wave);
        wave.setCell(this);
    }

    public Cell getNextCell(final Dir dir) {
        return this.nextCellArr[dir.ordinal()];
    }

    public void clearWaveList() {
        this.waveList[this.universe.getActCalcPos()].clear();
    }

    public int getWaveListSize() {
        return this.waveList[this.universe.getActCalcPos()].size();
    }

    public Stream<Wave> getWaveListStream() {
        return this.waveList[this.universe.getActCalcPos()].stream();
    }

    public boolean haveFirstWave() {
        return !this.waveList[this.universe.getActCalcPos()].isEmpty();
    }

    public Wave removeFirstWave() {
        return this.waveList[this.universe.getActCalcPos()].remove();
    }

    public Wave fetchFirstWave() {
        return this.waveList[this.universe.getActCalcPos()].peek();
    }
}
