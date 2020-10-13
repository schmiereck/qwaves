package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private final List<Wave> waveList = new ArrayList<>();

    public Event() {
    }

    public void addTicks(final Engine engine, final Rule rule, final int ticktCount) {
        for (int tickPos = 0; tickPos < ticktCount; tickPos++) {
            final Wave wave = new Wave(rule);
            this.waveList.add(wave);
            engine.addTick(wave);
        }
    }

    public List<Wave> getTickList() {
        return this.waveList;
    }
}
