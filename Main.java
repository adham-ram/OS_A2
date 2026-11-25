import algorithms.SRTFScheduler;
import metrics.MetricsCalculator;
import models.Process;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Create test processes
        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 0, 8, 1));
        processes.add(new Process("P2", 1, 4, 2));
        processes.add(new Process("P3", 2, 2, 3));
        processes.add(new Process("P4", 3, 1, 4));

        // Context Switching time
        int contextSwitching = 1;

        System.out.println("========== SRTF TEST ==========");

        System.out.println("\nProcesses:");

        for (Process p : processes) {
            System.out.println(
                p.getName()
                + " | Arrival = " + p.getArrival()
                + " | Burst = " + p.getBurst()
                + " | Priority = " + p.getPriority()
            );
        }

        System.out.println(
            "\nContext Switching = " + contextSwitching
        );

        // Run SRTF
        List<Process> completed =
                SRTFScheduler.schedule(processes, contextSwitching);

        // Print Metrics
        MetricsCalculator.printResults(completed);
    }
}