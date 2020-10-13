package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private final List<Wave> waveList = new ArrayList<>();
    private final Engine engine;

    public Event(final Engine engine) {
        this.engine = engine;
    }

    public Wave createWave() {
        final Wave wave = new Wave(this);
        this.waveList.add(wave);
        this.engine.addTick(wave);
        return wave;
    }

    public List<Wave> getTickList() {
        return this.waveList;
    }
}
