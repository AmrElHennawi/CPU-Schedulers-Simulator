import java.util.*;
//import math library

public abstract class Scheduler {
    public List<Process> processes = new ArrayList<>();
    List<String> executionOrder = new ArrayList<>();
    private int numProcesses;

    public abstract void schedule();

    public abstract List<Process> getInput();

    // Calculate average waiting time
    static double ClacAvgWaitingTime(List<Process> processes, int numProcesses) {
        double totalWaitingTime = 0;

        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
        }
        double avgWaitingTime = totalWaitingTime / numProcesses;
        return avgWaitingTime;

    }

    // Calculate average turnaround time
    static double ClacAvgTurnaroundTime(List<Process> processes, int numProcesses) {
        double totalTurnaroundTime = 0;

        for (Process process : processes) {
            totalTurnaroundTime += process.getTurnaroundTime();
        }
        double avgTurnaroundTime = totalTurnaroundTime / numProcesses;
        return avgTurnaroundTime;

    }

    public int getNumProcesses() {
        return numProcesses;
    }

    public void setNumProcesses(int numProcesses) {
        this.numProcesses = numProcesses;
    }
}