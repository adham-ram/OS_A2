package algorithms;
import models.Process;
import java.util.*;

public class Tier2Queue {
    private final List<Process> queue = new ArrayList<>();
    private final Map<Process, Integer> enteredAt = new HashMap<>();

    public void add(Process p, int time) {
        queue.add(p);
        enteredAt.put(p, time);
    }

    public boolean isEmpty() { return queue.isEmpty(); }

    public int waited(Process p, int now) { return now - enteredAt.get(p); }

    public int effectivePriority(Process p, int now) {
        return p.getPriority() - waited(p, now) / 4;
    }

    public Process pickNext(int now) {
        Process best = null;
        for (Process p : queue) {
            if (best == null || effectivePriority(p, now) < effectivePriority(best, now))
                best = p;
        }
        queue.remove(best);
        enteredAt.remove(best);
        return best;
    }
}