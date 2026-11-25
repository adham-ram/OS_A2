package output;

import models.Process;
import java.util.*;
import java.util.function.Function;

public class MatrixLogger {

    // "P2 (remaining: 7, Priority: 3)"
    private String fmt(Process p, Function<Process, Integer> priorityOf) {
        return p.getName() + " (remaining: " + p.getRemaining()
                + ", Priority: " + priorityOf.apply(p) + ")";
    }

    private String fmtList(List<Process> list, Function<Process, Integer> priorityOf) {
        if (list.isEmpty())
            return "[Empty]";
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (Process p : list)
            sj.add(fmt(p, priorityOf));
        return sj.toString();
    }

    // One row per time step. running = null when the CPU is idle.
    public void logStep(int time, Process running,
            List<Process> tier1, List<Process> tier2,
            Function<Process, Integer> tier2PriorityOf) {
        String who = (running == null) ? "-" : running.getName();
        System.out.println("T=" + time + " | " + who
                + " | Tier 1: " + fmtList(tier1, Process::getPriority)
                + " | Tier 2: " + fmtList(tier2, tier2PriorityOf));
    }

    // "Context switch saves P1 and loads P2"
    public void logContextSwitch(int time, Process from, Process to) {
        String msg = (from == null)
                ? "Context switch loads " + to.getName()
                : "Context switch saves " + from.getName() + " and loads " + to.getName();
        System.out.println("T=" + time + " | " + msg);
    }

    // "P1: Waited from T=3 to T=11 (8 units). Aging: ⌊8/4⌋ = 2. Effective Priority
    // = 4 − 2 = 2"
    public void logAging(Process p, int enteredAt, int now, int effective) {
        int waited = now - enteredAt;
        int aged = waited / 4;
        String line = p.getName() + ": Waited from T=" + enteredAt + " to T=" + now
                + " (" + waited + " units).";
        if (waited > 0) {
            line += " Aging: ⌊" + waited + "/4⌋ = " + aged
                    + ". Effective Priority = " + p.getPriority() + " − " + aged + " = " + effective;
        } else {
            line += " Effective Priority = " + effective;
        }
        System.out.println(line);
    }
}