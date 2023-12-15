import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.PriorityQueue;

class Process {
    private String processName;
    private int arrivalTime;
    private int burstTime;
    private int originalBurstTime;
    private int priorityNum;
    private int waitingTime; // don't put it in the constructor
    private int turnaroundTime; // don't put it in the constructor
   

      public Process(String processName, int arrivalTime, int burstTime, int priorityNum){
            this.processName=processName;
            this.arrivalTime=arrivalTime;
            this.burstTime=burstTime;
            this.originalBurstTime=burstTime;
            this.priorityNum=priorityNum;
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
  
    

}

public abstract class  Scheduler {
    public List<Process> processes = new ArrayList<>();
     List<String> executionOrder = new ArrayList<>();
    private int numProcesses;
    public abstract void schedule();
    public abstract List<Process> getInput();

    // Calculate average waiting time
    static double ClacAvgWaitingTime(List<Process> processes,int numProcesses){
        double totalWaitingTime = 0;
         
       for (Process process : processes) {
          totalWaitingTime += process.getWaitingTime();
      }
      double avgWaitingTime =totalWaitingTime / numProcesses;
      return avgWaitingTime;

  }

  // Calculate average turnaround time
  static double ClacAvgTurnaroundTime(List<Process> processes,int numProcesses){
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

class PriorityScheduler extends Scheduler{

    @Override
    public void schedule() {
          // Sort processes based on arrival time
        Collections.sort(processes, Comparator.comparingInt((Process p) -> p.getArrivalTime()).thenComparingInt(p -> p.getPriorityNum()));

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
    


class SJFScheduler extends Scheduler{

    @Override
    public void schedule() {
        // Sort processes based on burst time then the arrival time
        Collections.sort(processes, Comparator.comparingInt((Process p) -> p.getArrivalTime()).thenComparingInt(p -> p.getBurstTime()));


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
                if((processes.get(j).getArrivalTime() < start) || (processes.get(j).getArrivalTime() >= start && processes.get(j).getArrivalTime() < end)){
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
    public List<Process>getInput() {
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
class AGScheduler extends Scheduler{

    @Override
    public void schedule() {
        
    }

    @Override
    public List<Process>getInput() {
        return processes;
    }
    
}
class ShortestRemainingTimeFirstScheduler extends Scheduler{


    @Override
    public void schedule() {
        Collections.sort(processes, Comparator.comparingInt(Process::getArrivalTime));
        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(Process::getBurstTime).thenComparingInt(Process::getArrivalTime));

        int currentTime = 0;
        int index = 0;

        while (index < processes.size() || !queue.isEmpty()){
            while (index < processes.size() && processes.get(index).getArrivalTime() <= currentTime){
                queue.add(processes.get(index));
                index++;
            }
            if(!queue.isEmpty()){
                Process currentProcess = queue.poll();
                currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);
                currentTime++;

                if (currentProcess.getBurstTime() == 0){
                    currentProcess.setTurnaroundTime(currentTime - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getOriginalBurstTime());
                    executionOrder.add(currentProcess.getProcessName());
                } else {
                    queue.add(currentProcess);
                }
            } else {
                currentTime++;
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