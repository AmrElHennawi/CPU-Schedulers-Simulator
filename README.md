# CPU-Schedulers-Simulator

This project implements a basic CPU scheduler simulation in Java, focusing on four different scheduling algorithms: Non-Preemptive Shortest Job First (SJF), Shortest Remaining Time First (SRTF), Non-Preemptive Priority, and an example of a custom scheduler, AG Scheduler.

## Schedulers Implemented

### 1. Non-Preemptive Shortest Job First (SJF)

In SJF scheduling, the process with the shortest burst time is selected first. This implementation calculates waiting time and turnaround time for each process based on their burst times.

### 2. Shortest Remaining Time First (SRTF)

SRTF is a preemptive version of SJF. The scheduler constantly checks for the process with the shortest remaining burst time. Aging is considered, and processes are prioritized based on a combination of burst time and age.

### 3. Non-Preemptive Priority

This scheduler selects processes based on their priority. The processes are sorted by arrival time, and the process with the highest priority is selected first. Waiting time and turnaround time are calculated accordingly.

### 4. AG Scheduler

The AG Scheduler is a custom scheduler that combines elements of round-robin and Priority scheduling. It introduces an Aging Factor (AGFactor) to prioritize processes. The scheduler uses a combination of non-preemptive and preemptive techniques with a dynamic time quantum.

## Authors
- [Amr Khaled El-Hennawi](https://github.com/AmrElhennawi)
- [Abdelrahman Wael Hanafy](https://github.com/abwael)
- [Alialdeen Muhammad mostafa](https://github.com/Alialdin99)
- [Mazen Mahmoud Adly](https://github.com/mazenmahmoudadly)
- [Yehia Mohamed Yehia Mostafa](https://github.com/Yahya-Ehab)

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.



**Note:** This implementation is for educational purposes and may not be as optimized or feature-rich as dedicated libraries or utilities.

Feel free to explore and experiment with the different scheduling algorithms!
