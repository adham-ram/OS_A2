package models;

public class Process {
    
    private String name;
    private int priority;
    private int remaining;
    private int arrival;
    private int burst;

    private int startTime;
    private int waitingSince;
    private int finishTime;

     public Process(String name, int arrival, int burst, int priority) {
        this.name = name;
        this.arrival = arrival;
        this.burst = burst;
        this.remaining = burst;
        this.priority = priority;

        this.startTime = -1;
        this.finishTime = -1;
        this.waitingSince = -1;
    }
    
    public String getName() {
        return name;
    }

    public int getArrival() {
        return arrival;
    }

    public int getBurst() {
        return burst;
    }

    public int getRemaining() {
        return remaining;
    }

    public int getPriority() {
        return priority;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public int getWaitingSince() {
        return waitingSince;
    }


        public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public void setWaitingSince(int waitingSince) {
        this.waitingSince = waitingSince;
    }

    @Override
    public String toString(){
        return "Process{"+
                "name='" + name + '\'' +
                ", arrival=" + arrival +
                ", burst=" + burst +
                ", remaining=" + remaining +
                ", priority=" + priority +
                '}';
    }

}
