import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.PriorityQueue;
//import math library
import java.lang.Math;

class Process {
    private String processName;
    private int arrivalTime;
    private int burstTime;
    private int originalBurstTime;
    private int priorityNum;
    private int quantum = 0;
    private int agFactor = 0;
    private int age;
    private int waitingTime; // don't put it in the constructor
    private int turnaroundTime; // don't put it in the constructor

    public Process(String processName, int arrivalTime, int burstTime, int priorityNum) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.originalBurstTime = burstTime;
        this.priorityNum = priorityNum;
        this.age = 0;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setPriorityNum(int priorityNum) {
        this.priorityNum = priorityNum;
    }

    public int getPriorityNum() {
        return priorityNum;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getOriginalBurstTime() {
        return originalBurstTime;
    }

    public void setAGFactor(int calculateAGFactor) {
    }

    public boolean isPreemptive() {
        return false;
    }

    public int getAGFactor() {
        return 0;
    }
    public int getQuantum(){
        return quantum;
    }
    public void setQuantum(int n)
    {
        this.quantum = n;
    }
    public int getAgFactor(){
        return agFactor;
    }
    public void setAgFactor(int n)
    {
        this.agFactor = n;
    }
    public void setAge(int age){
        this.age = age;
    }
    public int getAge(){
        return age;
    }

}

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

class PriorityScheduler extends Scheduler {

    @Override
    public void schedule() {
        // Sort processes based on arrival time
        Collections.sort(processes,
                Comparator.comparingInt((Process p) -> p.getArrivalTime()).thenComparingInt(p -> p.getPriorityNum()));

        int end = processes.get(0).getArrivalTime();

        //this loop should sort the processes to the final order they will be executed on
        //current
        for(int i = 0; i < processes.size()-1;i++){
            int start;
            if(processes.get(i).getArrivalTime() <= end){
                start = end;
            }
            else{
                start = processes.get(i).getArrivalTime();
            }

            end = start + processes.get(i).getBurstTime();
            int minIndex = i+1;

            //search next
            for(int j = i+1; j < processes.size(); j++){
                if((processes.get(j).getArrivalTime() < start) ||
                        (processes.get(j).getArrivalTime() >= start && processes.get(j).getArrivalTime() < end)){
                    if(processes.get(j).getPriorityNum() < processes.get(minIndex).getPriorityNum()){
                        minIndex = j;
                    }
                }
            }

            Collections.swap(processes,i+1,minIndex);
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

class SJFScheduler extends Scheduler {

    @Override
    public void schedule() {
        // Sort processes based on burst time then the arrival time
        Collections.sort(processes,
                Comparator.comparingInt((Process p) -> p.getArrivalTime()).thenComparingInt(p -> p.getBurstTime()));


        int end = processes.get(0).getArrivalTime();

        //this loop should sort the processes to the final order they will be executed on
        //current
        for(int i = 0; i < processes.size()-1;i++){
            int start;
            if(processes.get(i).getArrivalTime() <= end){
                start = end;
            }
            else{
                start = processes.get(i).getArrivalTime();
            }

            end = start + processes.get(i).getBurstTime();
            int minIndex = i+1;

            //search next
            for(int j = i+1; j < processes.size(); j++){
                if((processes.get(j).getArrivalTime() < start) ||
                        (processes.get(j).getArrivalTime() >= start && processes.get(j).getArrivalTime() < end)){
                    if(processes.get(j).getBurstTime() < processes.get(minIndex).getBurstTime()){
                        minIndex = j;
                    }
                }
            }

            Collections.swap(processes,i+1,minIndex);
        }

        int currentTime = 0;

        // Calculate waiting and turnaround time for each process
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
                this.setNumProcesses(numProcesses);
                Process process = new Process(name, arrivalTime, burstTime, 0);
                processes.add(process);
            }
            return processes;
        }
    }

}

//class AGScheduler extends Scheduler {
//
//    @Override
//    public void schedule() {
//        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getAGFactor));
//        List<Process> dieList = new ArrayList<>();
//
//        int currentTime = 0;
//        int quantumTime = 4; // Initial quantum time for each process
//
//        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
//            // Add processes to the ready queue based on arrival time
//            while (!processes.isEmpty() && processes.get(0).getArrivalTime() <= currentTime) {
//                Process process = processes.remove(0);
//                process.setAGFactor(calculateAGFactor(process));
//                readyQueue.add(process);
//            }
//
//            if (!readyQueue.isEmpty()) {
//                Process runningProcess = readyQueue.poll();
//
//                int remainingQuantumTime = quantumTime;
//                if (runningProcess.isPreemptive()) {
//                    // Non-preemptive AG till ceil(50%) of Quantum time
//                    remainingQuantumTime = (int) Math.ceil(0.5 * quantumTime);
//                }
//
//                while (remainingQuantumTime > 0 && runningProcess.getBurstTime() > 0) {
//                    runningProcess.setBurstTime(runningProcess.getBurstTime() - 1);
//                    currentTime++;
//                    remainingQuantumTime--;
//
//                    if (runningProcess.isPreemptive() && remainingQuantumTime == 0) {
//                        readyQueue.add(runningProcess); // Add to the end of the queue
//                        quantumTime += (int) Math.ceil(0.1 * quantumTime); // Increase Quantum time
//                    }
//                }
//
//                if (runningProcess.getBurstTime() > 0) {
//                    // Process didn't finish its job
//                    readyQueue.add(runningProcess);
//                    quantumTime += (int) Math.ceil(0.1 * quantumTime); // Increase Quantum time
//                } else {
//                    // Process finished its job
//                    dieList.add(runningProcess);
//                }
//            } else {
//                // No process in the ready queue, just increment time
//                currentTime++;
//            }
//        }
//
//        executionOrder.addAll(dieList.stream().map(Process::getProcessName).collect(Collectors.toList()));
//    }
//
//    // Calculate AG-Factor based on the specified conditions
//    private int calculateAGFactor(Process process) {
//        int randomFunction = getRandomFunction();
//        int priorityOrTenOrRandom = Math.max(process.getPriorityNum(), Math.max(10, randomFunction));
//        return priorityOrTenOrRandom + process.getArrivalTime() + process.getBurstTime();
//    }
//
//    // Generate random number between 0 and 20
//    private int getRandomFunction() {
//        return (int) (Math.random() * 21);
//    }
//
//    @Override
//    public List<Process> getInput() {
//        return processes;
//        // Input code remains unchanged
//    }
//}

class AGScheduler extends Scheduler {

    @Override
    public  void schedule(){
        List<Process> processes = getInput();
        processes = getInput();
        
    }
    @Override
    public  List<Process> getInput(){
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Enter the number of processes: ");
            int numProcesses = scanner.nextInt();
            List<Process> processes = new ArrayList<>();

            System.out.println("Enter the Round Robin Time Quantum: ");
            int quantumTime = scanner.nextInt();
            for(int i = 0; i < numProcesses ;i++)
            {
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

                int agFactor = SetAgFactor(priority,burstTime,arrivalTime);

                Process process = new Process(name, arrivalTime, burstTime, priority);

                process.setQuantum(quantumTime);
                process.setAGFactor(agFactor);
                processes.add(process);
            }
        }
        return processes;
    }

    private int SetAgFactor(int priority, int burstTime, int arrivalTime)
    {
        int factor;
        int randomNum = ((int)Math.random() * 21);;
        if(randomNum < 10)
            factor = randomNum + burstTime + arrivalTime;
        else if(randomNum == 10)
            factor = priority + burstTime + arrivalTime;
        else
            factor = 10 + arrivalTime + burstTime;

        return factor;

    }

}

class ShortestRemainingTimeFirstScheduler extends Scheduler{


    @Override
    public void schedule() {
        Collections.sort(processes, Comparator.comparingInt(Process::getArrivalTime));
        // Create a priority queue to hold the processes, prioritizing the one with the shortest remaining burst time with the aging factor
        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingDouble((Process p) -> p.getBurstTime() - p.getAge() / 10.0).thenComparingInt(Process::getArrivalTime));

        int currentTime = 0;
        int index = 0;

        while (index < processes.size() || !queue.isEmpty()){
            // Add processes to the queue based on arrival time
            while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime){
                queue.add(processes.get(index));
                index++;
            }
            // If the queue is not empty, execute the process with the shortest remaining burst time
            if(!queue.isEmpty()){
                Process currentProcess = queue.poll();
                currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);
                currentTime++;

                if (currentProcess.getBurstTime() == 0){
                    currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getOriginalBurstTime());
                    executionOrder.add(currentProcess.getProcessName());
                    currentProcess.setAge(0);
                } else {
                    queue.add(currentProcess);
                }
            } else {
                currentTime++;
            }

            // Increase the age of all processes in the queue
            for(Process process : queue){
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