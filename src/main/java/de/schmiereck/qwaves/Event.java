package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private final List<Tick> tickList = new ArrayList<>();

    public Event() {
    }

    public void addTicks(final Engine engine, final Rule rule, final int ticktCount) {
        for (int tickPos = 0; tickPos < ticktCount; tickPos++) {
            final Tick tick = new Tick(rule);
            this.tickList.add(tick);
            engine.addTick(tick);
        }
    }

    public List<Tick> getTickList() {
        return this.tickList;
    }
}
