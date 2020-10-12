package de.schmiereck.qwaves;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    private final List<Tick> tickList = new ArrayList<>();

    private int tickPos = 0;

    public int getTickPos() {
        return tickPos;
    }

    public void addTick(final Tick tick) {
        this.tickList.add(tick);
    }

    public List<Tick> getTickList() {
        return this.tickList;
    }

    public void run() {
        final Tick tick = this.tickList.get(this.tickPos);

        tick.execute();

        if ((this.tickPos + 1) < this.tickList.size()) {
            this.tickPos++;
        } else {
            this.tickPos = 0;
        }
    }
}
