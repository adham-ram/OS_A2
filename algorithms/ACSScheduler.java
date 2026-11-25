package algorithms;

import models.Process;
import output.MatrixLogger;
import java.util.*;

public class ACSScheduler {
    private final Tier1Queue tier1;
    private final Tier2Queue tier2 = new Tier2Queue();
    private final MatrixLogger log = new MatrixLogger();
    private final List<Process> pending;
    private final int cs;
    private int time = 0, nextArrival = 0;
    private boolean rowLogged = false;

    public ACSScheduler(List<Process> processes, int quantum, int cs) {
        this.tier1 = new Tier1Queue(quantum);
        this.cs = cs;
        this.pending = new ArrayList<>(processes);
        pending.sort(Comparator.comparingInt(Process::getArrival)); // stable: input order breaks ties
    }

    private void admit() {
        while (nextArrival < pending.size() && pending.get(nextArrival).getArrival() <= time)
            tier1.add(pending.get(nextArrival++));
    }

    private Map<Process, Integer> effMap(List<Process> t2) {
        Map<Process, Integer> eff = new HashMap<>();
        for (Process q : t2)
            eff.put(q, tier2.effectivePriority(q, time));
        return eff;
    }

    // advances the clock, printing one row per time unit
    private void advance(int units, Process running) {
        for (int i = 0; i < units; i++) {
            if (rowLogged)
                rowLogged = false;
            else {
                List<Process> t2 = tier2.snapshot();
                log.logStep(time, running, tier1.snapshot(), t2, effMap(t2)::get);
            }
            time++;
            admit();
        }
    }

    public void run() {
        int done = 0, n = pending.size();
        Process last = null;
        boolean lastFinished = false;
        admit();

        while (done < n) {
            // snapshot BEFORE picking, so the dispatch row matches the PDF
            List<Process> t1 = tier1.snapshot();
            List<Process> t2 = tier2.snapshot();
            Map<Process, Integer> eff = effMap(t2);

            Process p;
            if (!tier1.isEmpty()) {
                p = tier1.poll();
            } else if (!tier2.isEmpty()) {
                if (t2.size() > 1)
                    for (Process q : t2)
                        log.logAging(q, time - tier2.waited(q, time), time, eff.get(q));
                p = tier2.pickNext(time);
            } else { // CPU idle
                log.logStep(time, null, t1, t2, eff::get);
                time++;
                admit();
                last = null;
                continue;
            }

            boolean fromTier1 = t1.contains(p);
            boolean switching = last != null && last != p;
            int csUnits = switching ? cs : 0;

            if (switching && cs > 0)
                log.logContextSwitch(time, lastFinished ? null : last, p);
            log.logStep(time, csUnits > 0 ? null : p, t1, t2, eff::get);
            rowLogged = true;

            advance(csUnits, null);
            if (p.getStartTime() < 0)
                p.setStartTime(time); // first time on CPU, after CS

            int slice = fromTier1 ? tier1.sliceFor(p) : p.getRemaining();
            advance(slice, p);
            p.setRemaining(p.getRemaining() - slice);

            if (p.getRemaining() == 0) {
                p.setFinishTime(time);
                done++;
                lastFinished = true;
            } else if (tier1.shouldDemote(p)) {
                tier2.add(p, time);
                lastFinished = false; // demotion
            }
            last = p;
        }
    }
}