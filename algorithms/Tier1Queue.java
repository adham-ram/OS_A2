package algorithms;

import models.Process;
import java.util.*;

public class Tier1Queue {
    private final Deque<Process> queue = new ArrayDeque<>();
    private final int quantum;

    public Tier1Queue(int quantum) {
        this.quantum = quantum;
    }

    public int getQuantum() {
        return quantum;
    }

    public void add(Process p) {
        queue.addLast(p);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public Process poll() {
        return queue.pollFirst();
    }

    public List<Process> snapshot() {
        return new ArrayList<>(queue);
    }

    // how long p runs in this slice
    public int sliceFor(Process p) {
        return Math.min(quantum, p.getRemaining());
    }

    // call AFTER the slice has been subtracted from remaining
    public boolean shouldDemote(Process p) {
        return p.getRemaining() > 0;
    }
}