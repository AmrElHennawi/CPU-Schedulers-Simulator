public class SchedulerController {
    private SchedulerView view;
    private Scheduler scheduler;

    public SchedulerController(SchedulerView view) {
        this.view = view;
    }

    public void start() {
        int schedulerChoice = view.getSchedulerChoice();

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

        scheduler.processes = scheduler.getInput();
        scheduler.schedule();
        view.display(scheduler);
    }
}
