package metrics;
import models.Process;
import java.util.List;


public class MetricsCalculator {

    public static int calculateTurnaroundTime(Process p) {

        return p.getFinishTime() - p.getArrival();
    }
    
       public static int calculateWaitingTime(Process p) {

        return calculateTurnaroundTime(p) - p.getBurst();
    }

     public static int calculateResponseTime(Process p) {

        return p.getStartTime() - p.getArrival();
    }

    public static double calculateAverageWaitingTime(List<Process> processes){
        if(processes.isEmpty()){
            return 0;}

        int totalWaitingTime = 0;
        for(Process p :processes){
            totalWaitingTime+=calculateWaitingTime(p);
        }
        return (double) totalWaitingTime/processes.size();
    }

     public static double calculateAverageTurnaroundTime(List<Process> processes) {

        if (processes.isEmpty()) {
            return 0;
        }

        int totalTurnaroundTime = 0;

        for (Process p : processes) {
            totalTurnaroundTime += calculateTurnaroundTime(p);
        }

        return (double) totalTurnaroundTime / processes.size();
    }

    public static double calculateAverageResponseTime(List<Process> processes) {

        if (processes.isEmpty()) {
            return 0;
        }

        int totalResponseTime = 0;

        for (Process p : processes) {
            totalResponseTime += calculateResponseTime(p);
        }

        return (double) totalResponseTime / processes.size();
    }


    public static void printResults(List<Process> processes) {

        System.out.println("\n========== Scheduling Results ==========");

        System.out.printf(
            "%-10s %-10s %-10s %-10s %-10s %-10s%n",
            "Process", "Arrival", "Finish",
            "Waiting", "Turnaround", "Response"
        );

        int totalWaiting = 0;
        int totalTurnaround = 0;
        int totalResponse = 0;

        for (Process p : processes) {

            int waiting = calculateWaitingTime(p);
            int turnaround = calculateTurnaroundTime(p);
            int response = calculateResponseTime(p);

            totalWaiting += waiting;
            totalTurnaround += turnaround;
            totalResponse += response;

            System.out.printf(
                "%-10s %-10d %-10d %-10d %-10d %-10d%n",
                p.getName(),
                p.getArrival(),
                p.getFinishTime(),
                waiting,
                turnaround,
                response
            );
        }

        System.out.println("-----------------------------------------");

        System.out.printf(
            "Average Waiting Time: %.2f%n",
            calculateAverageWaitingTime(processes)
        );

        System.out.printf(
            "Average Turnaround Time: %.2f%n",
            calculateAverageTurnaroundTime(processes)
        );

        System.out.printf(
            "Average Response Time: %.2f%n",
            calculateAverageResponseTime(processes)
        );
    }


}
