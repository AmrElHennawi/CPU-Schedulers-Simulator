import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Process {
    private String processName;
    private int arrivalTime;
    private int burstTime;
    private int priorityNum;
    private int waitingTime;
    private int turnaroundTime;
    private int quantumTime;

      public Process(String processName, int arrivalTime, int burstTime, int priorityNum){
            this.processName=processName;
            this.arrivalTime=arrivalTime;
            this.burstTime=burstTime;
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
    public void setQuantumTime(int quantumTime) {
        this.quantumTime = quantumTime;
    }
    public int getQuantumTime() {
        return quantumTime;
    }
    

}


public abstract class  Scheduler {
    List readyQueue = new ArrayList<>();
    public abstract void schedule(List readyQueue);
}

class PriorityScheduler extends Scheduler{

    @Override
    public void schedule(List readyQueue) {
    
    }
    }
    


class SJFScheduler extends Scheduler{

    @Override
    public void schedule(List readyQueue) {
        
    }
    
}
class AGScheduler extends Scheduler{

    @Override
    public void schedule(List readyQueue) {
        
    }
    
}
class ShortestRemainingTimeFirstScheduler extends Scheduler{

    @Override
    public void schedule(List readyQueue) {
       
    }
    
}