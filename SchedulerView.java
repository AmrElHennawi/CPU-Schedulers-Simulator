import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class SchedulerView {
    public static void main(String[] args) {
        try (
        Scanner scanner = new Scanner(System.in)) {
           
            System.out.print("Enter the number of processes: ");
            int numProcesses = scanner.nextInt();
            List<Process> readyQueue = new ArrayList<>();
            for (int i = 0; i < numProcesses; i++) {
              
                System.out.println("Process name");
                String processName = scanner.nextLine();
                System.out.print("Arrival Time: ");
                int arrivalTime = scanner.nextInt();
                System.out.print("Burst Time: ");
                int burstTime = scanner.nextInt();
                System.out.print("Priority: ");
                int priority = scanner.nextInt();

                
                Process process = new Process(processName, arrivalTime, burstTime, priority);
                readyQueue.add(process);
            }

            
            System.out.println("Select a scheduler:");
            System.out.println("1. Non-Preemptive Shortest Job First");
            System.out.println("2. Shortest Remaining Time First");
            System.out.println("3. Non-Preemptive Priority");
            System.out.println("4. AG");
            System.out.print("Enter your choice: ");
            int schedulerChoice = scanner.nextInt();

            Scheduler scheduler;
            switch (schedulerChoice) {
                case 1:
                    scheduler = new SJFScheduler();
                    break;
                case 2:
                    scheduler = new ShortestRemainingTimeFirstScheduler();
                    break;
                case 3:
                    scheduler = new PriorityScheduler();
                    break;
                case 4:
                    scheduler = new AGScheduler();
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

          
            scheduler.schedule(readyQueue);
        }
    }
}

