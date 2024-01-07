import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

class AGScheduler extends Scheduler {

    @Override
    public void schedule() {

        List<Process> dieList = new ArrayList<>();
        List<Process> currrentProcess = new ArrayList<>();
        Queue<Process> q = new LinkedList<>();
        int time = 0;
        while (dieList.size() < processes.size()) {
            int currentTime = 0;
            updateCurrentProcess(processes, currrentProcess, dieList, time);
            updateReadyQueue(currrentProcess, q, dieList);

            int nonPreemptTime = (int) Math.ceil(q.peek().getQuantum() / 2.0);
            int preemptTime = q.peek().getQuantum() - nonPreemptTime;

            while (q.peek().getBurstTime() > 0 && nonPreemptTime > 0) {
                time += 1;
                q.peek().setBurstTime(q.peek().getBurstTime() - 1);
                nonPreemptTime--;
            }

            executionOrder.add(q.peek().getProcessName());

            if (check(q, dieList, time, currrentProcess))
                continue;

            updateCurrentProcess(processes, currrentProcess, dieList, time);
            updateReadyQueue(currrentProcess, q, dieList);

            for (int i = 1; i <= preemptTime; i++) {
                time += 1;
                q.peek().setBurstTime(q.peek().getBurstTime() - 1);

                boolean interrupted = false;

                if (q.peek().getBurstTime() == 0) {
                    q.peek().setQuantum(0);
                    q.peek().setTurnaroundTime(time - q.peek().getArrivalTime());
                    dieList.add(q.peek());
                    q.remove();

                    break;
                } else if (i == preemptTime && q.peek().getBurstTime() > 0) {
                    double mean = 0;
                    for (int j = 0; j < currrentProcess.size(); j++) {
                        mean += currrentProcess.get(j).getQuantum();
                    }
                    mean = mean / currrentProcess.size();
                    mean = Math.ceil(mean * 0.1);

                    q.peek().setQuantum(q.peek().getQuantum() + (int) mean);
                    q.add(q.peek());
                    currrentProcess.remove(q.peek());
                    q.remove();

                    break;
                }

                updateCurrentProcess(processes, currrentProcess, dieList, time);
                updateReadyQueue(currrentProcess, q, dieList);

                for (int j = 0; j < currrentProcess.size(); j++) {
                    if (currrentProcess.get(j).getAGFactor() < q.peek().getAGFactor()) {
                        q.peek().setQuantum(q.peek().getQuantum() + (q.peek().getQuantum() - nonPreemptTime - i));
                        q.add(q.peek());
                        q.remove();
                        interrupted = true;
                        break;
                    }
                }
                if (interrupted)
                    break;

                if (check(q, dieList, time, currrentProcess))
                    break;
            }
            check(q, dieList, time, currrentProcess);

        }

    }

    private boolean check(Queue<Process> q, List<Process> dieList, int time, List<Process> currrentProcess) {
        if (!q.isEmpty()) {
            if (q.peek().getBurstTime() <= 0) {
                q.peek().setQuantum(0);
                q.peek().setTurnaroundTime(time - q.peek().getArrivalTime());
                q.peek().setWaitingTime(q.peek().getTurnaroundTime() - q.peek().getOriginalBurstTime());
                dieList.add(q.peek());
                currrentProcess.remove(q.peek());
                q.remove();
                return true;
            }
        }

        return false;
    }

    private void updateCurrentProcess(List<Process> processes, List<Process> currrentProcess, List<Process> dieList,
            int time) {
        for (int i = 0; i < processes.size(); i++) { // to be optimized
            if (!dieList.contains(processes.get(i)) && !currrentProcess.contains(processes.get(i))
                    && time >= processes.get(i).getArrivalTime())
                currrentProcess.add(processes.get(i));
        }
        Collections.sort(currrentProcess, Comparator.comparingInt(Process::getAgFactor));
    }

    private void updateReadyQueue(List<Process> currrentProcess, Queue<Process> q, List<Process> dieList) {
        for (int i = 0; i < currrentProcess.size(); i++) {
            if (!q.contains(currrentProcess.get(i)) && !dieList.contains(currrentProcess.get(i)))
                q.add(currrentProcess.get(i));
        }
    }

    @Override
    public List<Process> getInput() {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Enter the number of processes: ");
            int numProcesses = scanner.nextInt();

            System.out.println("Enter the Round Robin Time Quantum: ");
            int quantumTime = scanner.nextInt();

            List<Process> processes = new ArrayList<>();
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

                int agFactor = getAgFactor(priority, burstTime, arrivalTime);

                Process process = new Process(name, arrivalTime, burstTime, priority);

                process.setQuantum(quantumTime);
                process.setAgFactor(agFactor);
                processes.add(process);
            }
            return processes;
        }

    }

    private int getAgFactor(int priority, int burstTime, int arrivalTime) {
        int factor;
        int randomNum = (int) (Math.random() * 21);

        if (randomNum < 10)
            factor = randomNum + burstTime + arrivalTime;
        else if (randomNum == 10)
            factor = priority + burstTime + arrivalTime;
        else
            factor = 10 + arrivalTime + burstTime;

        return factor;

    }

}