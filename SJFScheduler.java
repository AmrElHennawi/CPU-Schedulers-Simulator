import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class SJFScheduler extends Scheduler {

    private int contextSwitch;

    @Override
    public void schedule() {
        // Sort processes based on burst time then the arrival time
        Collections.sort(processes,
                Comparator.comparingInt((Process p) -> p.getArrivalTime()).thenComparingInt(p -> p.getBurstTime()));

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
                    if (processes.get(j).getBurstTime() < processes.get(minIndex).getBurstTime()) {
                        minIndex = j;
                    }
                }
            }

            Collections.swap(processes, i + 1, minIndex);
        }

        int currentTime = 0;

        // Calculate waiting and turnaround time for each process
        for (Process process : processes) {
            if (process.getArrivalTime() > currentTime) {
                currentTime = process.getArrivalTime();
            }

            process.setWaitingTime(currentTime - process.getArrivalTime());
            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());

            currentTime += process.getBurstTime() + contextSwitch;
            executionOrder.add(process.getProcessName());
        }

    }

    @Override
    public List<Process> getInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of processes: ");
            int numProcesses = scanner.nextInt();
            System.out.print("Enter the Context Switch Value: ");
            int contextSwitchInput = scanner.nextInt();
            contextSwitch = contextSwitchInput;

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
                this.setNumProcesses(numProcesses);
                Process process = new Process(name, arrivalTime, burstTime, 0);
                processes.add(process);
            }
            return processes;
        }
    }

}