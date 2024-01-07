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

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int n) {
        this.quantum = n;
    }

    public int getAgFactor() {
        return agFactor;
    }

    public void setAgFactor(int n) {
        this.agFactor = n;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

}