package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private final List<Wave> waveList = new ArrayList<>();
    private final Engine engine;
    private final int eventType;

    public Event(final Engine engine, final int eventType) {
        this.engine = engine;
        this.eventType = eventType;
    }

    public Wave createWave() {
        final Wave wave = new Wave(this);
        this.waveList.add(wave);
        this.engine.addWave(wave);
        return wave;
    }

    public List<Wave> getTickList() {
        return this.waveList;
    }

    public int getEventType() {
        return this.eventType;
    }
}
