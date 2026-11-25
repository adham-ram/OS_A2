package input;
import models.Process;
import java.util.List;

public class SchedulerInput {
    private List<Process> processes;
    private int quantum;// q -> in RR,preemptive 
    private int contextSwitching;

    public SchedulerInput(List<Process> processes,int quantum,int contextSwitching){
        this.processes = processes;
        this.quantum = quantum;
        this.contextSwitching = contextSwitching;
    }

    public List<Process> getProcesses() {
        return processes;
    }

    public int getQuantum() {
        return quantum;
    }

    public int getContextSwitching() {
        return contextSwitching;
    }
}
