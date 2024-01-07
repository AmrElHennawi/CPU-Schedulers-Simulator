import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class PriorityScheduler extends Scheduler {

    @Override
    public void schedule() {
        // Sort processes based on arrival time
        Collections.sort(processes,
                Comparator.comparingInt((Process p) -> p.getArrivalTime()).thenComparingInt(p -> p.getPriorityNum()));

        int end = processes.get(0).getArrivalTime();

        // this loop should sort the processes to the final order they will be executed
        // on
        // current
        for (int i = 0; i < processes.size() - 1; i++) {
            int start;
            if (processes.get(i).getArrivalTime() <= end) {
                start = end;
            } else {
                start = processes.get(i).getArrivalTime();
            }

            end = start + processes.get(i).getBurstTime();
            int minIndex = i + 1;

            // search next
            for (int j = i + 1; j < processes.size(); j++) {
                if ((processes.get(j).getArrivalTime() < start) ||
                        (processes.get(j).getArrivalTime() >= start && processes.get(j).getArrivalTime() < end)) {
                    if (processes.get(j).getPriorityNum() < processes.get(minIndex).getPriorityNum()) {
                        minIndex = j;
                    }
                }
            }

            Collections.swap(processes, i + 1, minIndex);
        }

        int currentTime = 0;

        // Calculate waiting time and turnaround time for each process
        for (Process process : processes) {
            if (process.getArrivalTime() > currentTime) {
                currentTime = process.getArrivalTime();
            }

            process.setWaitingTime(currentTime - process.getArrivalTime());
            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());

            currentTime += process.getBurstTime();
            executionOrder.add(process.getProcessName());
        }

    }

    @Override
    public List<Process> getInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of processes: ");
            int numProcesses = scanner.nextInt();

            List<Process> processes = new ArrayList<>();

            // Input parameters for each process
            for (int i = 0; i < numProcesses; i++) {
                System.out.println("Process " + (i + 1) + ":");
                System.out.print("Name: ");
                String name = scanner.next();
                System.out.print("Arrival Time: ");
                int arrivalTime = scanner.nextInt();
                System.out.print("Burst Time: ");
                int burstTime = scanner.nextInt();
                System.out.print("Priority Number: ");
                int priority = scanner.nextInt();
                this.setNumProcesses(numProcesses);
                Process process = new Process(name, arrivalTime, burstTime, priority);
                processes.add(process);
            }
            return processes;
        }
    }
}
