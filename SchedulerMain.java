public class SchedulerMain {
    public static void main(String[] args) {
        SchedulerView view = new SchedulerView();
        SchedulerController controller = new SchedulerController(view);
        controller.start();
    }
}
