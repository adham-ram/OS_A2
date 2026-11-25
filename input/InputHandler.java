package input;
import models.Process;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    //take int s7e7
    private static int readInt(String message, int minValue) {
        int value;

    while (true) {
        System.out.print(message);
        if (scanner.hasNextInt()) {
        value = scanner.nextInt();

        if (value >= minValue) {
            return value;}

        System.out.println("Invalid input! Minimum value is " + minValue );
        } else {
    System.out.println("Please enter a valid integer.");
        scanner.next();
            }
        }
    }

        // take process name 
      private static String readName(String message) {

        System.out.print(message);
        String name;
        do {
            name = scanner.next().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
            }
        } while (name.isEmpty());

        return name;
    }

    public static SchedulerInput readInput() {

        System.out.println("========== CPU Scheduling Simulator ==========");

        // Number of processes
        int n = readInt("Enter number of processes: ", 1);

        List<Process> processes = new ArrayList<>();

        // Read each process
        for (int i = 0; i < n; i++) {

            System.out.println("\nProcess " + (i + 1));
            String name = readName("Enter process name: ");
            int arrival = readInt("Enter arrival time: ", 0);
            int burst = readInt("Enter burst time: ", 1);
            int priority = readInt("Enter priority: ", 1);

        Process p = new Process(name,arrival,burst,priority);
        processes.add(p);
        }

        // Read Quantum and Context Switching
        int quantum = readInt("\nEnter Quantum (Q): ", 1);
        int cs = readInt("Enter Context Switching (CS): ", 0);
        System.out.println("\nInput completed successfully!");

        return new SchedulerInput(processes, quantum, cs);
    }

}
