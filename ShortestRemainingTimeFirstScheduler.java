import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class ShortestRemainingTimeFirstScheduler extends Scheduler {

    @Override
    public void schedule() {
        Collections.sort(processes, Comparator.comparingInt(Process::getArrivalTime));
        // Create a priority queue to hold the processes, prioritizing the one with the
        // shortest remaining burst time with the aging factor
        PriorityQueue<Process> queue = new PriorityQueue<>(
                Comparator.comparingDouble((Process p) -> p.getBurstTime() - p.getAge() / 10.0)
                        .thenComparingInt(Process::getArrivalTime));

        int currentTime = 0;
        int index = 0;

        while (index < processes.size() || !queue.isEmpty()) {
            // Add processes to the queue based on arrival time
            while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime) {
                queue.add(processes.get(index));
                index++;
            }
            // If the queue is not empty, execute the process with the shortest remaining
            // burst time
            if (!queue.isEmpty()) {
                Process currentProcess = queue.poll();
                currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);
                currentTime++;

                if (currentProcess.getBurstTime() == 0) {
                    currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
                    currentProcess
                            .setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getOriginalBurstTime());
                    executionOrder.add(currentProcess.getProcessName());
                    currentProcess.setAge(0);
                } else {
                    queue.add(currentProcess);
                }
            } else {
                currentTime++;
            }

            // Increase the age of all processes in the queue
            for (Process process : queue) {
                process.setAge(process.getAge() + 1);
            }
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
                this.setNumProcesses(numProcesses);
                Process process = new Process(name, arrivalTime, burstTime, 0);
                processes.add(process);
            }
            return processes;
        }
    }

}