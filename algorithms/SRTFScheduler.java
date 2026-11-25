package algorithms;

import models.Process;
import java.util.ArrayList;
import java.util.List;

public class SRTFScheduler {

    public static List<Process> schedule(
            List<Process> processes, int contextSwitching) {

        List<Process> completed = new ArrayList<>();

        int time = 0;
        int completedCount = 0;

        Process current = null;
        Process lastProcess = null;

        while (completedCount < processes.size()) {

            // Find the process with the shortest remaining time
            Process shortest = null;

        for (Process p : processes) {
    if (p.getArrival() <= time && p.getRemaining() > 0) {
        if (shortest == null|| p.getRemaining() < shortest.getRemaining()) {
            shortest = p;
                    }
                }
            }

            // No process is ready
            if (shortest == null) {

                time++;
                lastProcess = null;
                current = null;

                continue;
            }

            // Context Switching
            if (lastProcess != null && lastProcess != shortest) {
            System.out.println("Context Switch: " + lastProcess.getName() +
            " -> "+ shortest.getName());

    time += contextSwitching;

     // Recheck after context switching
     shortest = null;

    for (Process p : processes) {

    if (p.getArrival() <= time && p.getRemaining() > 0) {

     if (shortest == null|| p.getRemaining() < shortest.getRemaining()) {
shortest = p;
    }
    }
    }

    if (shortest == null) {

    lastProcess = null;

     continue;
     }
    }

            current = shortest;

            // Record first execution time
            if (current.getStartTime() == -1) {

                current.setStartTime(time);
            }

            // Execute for one time unit
            current.setRemaining(
                    current.getRemaining() - 1
            );

        time++;
        lastProcess = current;

        // Check if process is finished
    if (current.getRemaining() == 0) {

    current.setFinishTime(time);

    completed.add(current);
    completedCount++;
    System.out.println(current.getName()+ " finished at time "+ time);
            }
        }

        return completed;
    }
}