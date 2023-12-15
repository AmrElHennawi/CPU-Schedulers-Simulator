import java.util.Scanner;

public class SchedulerView {
    private Scanner scanner;

    public SchedulerView() {
        this.scanner = new Scanner(System.in);
    }

    public int getSchedulerChoice() {
        System.out.println("Select a scheduler:");
        System.out.println("1. Non-Preemptive Shortest Job First");
        System.out.println("2. Shortest Remaining Time First");
        System.out.println("3. Non-Preemptive Priority");
        System.out.println("4. AG");
        System.out.print("Enter your choice: ");
        return scanner.nextInt();
    }

    public void display(Scheduler scheduler) {
        double avgWaitingTime = Scheduler.ClacAvgWaitingTime(scheduler.processes, scheduler.getNumProcesses());
        double avgTurnaroundTime = Scheduler.ClacAvgTurnaroundTime(scheduler.processes, scheduler.getNumProcesses());

        System.out.println("\nProcesses execution order: " + String.join(" -> ", scheduler.executionOrder));

        System.out.println("\nProcesses     Arrival Time     Burst Time      Priority      Waiting Time     Turn Around Time");

        for (Process process : scheduler.processes) {
            System.out.println(process.getProcessName() + "             " +process.getArrivalTime()+"             " + process.getOriginalBurstTime() + "              " + process.getPriorityNum()
                        + "               " + process.getWaitingTime() + "               " + process.getTurnaroundTime());
        }
        System.out.println("\nWaiting Time for each process:");
        for (Process process : scheduler.processes) {
            System.out.println(process.getProcessName() + ": " + process.getWaitingTime());
        }
        System.out.println("\nTurnaround Time for each process:");
        for (Process process : scheduler.processes) {
            System.out.println(process.getProcessName() + ": " + process.getTurnaroundTime());
        }
        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime); 
    }
}


