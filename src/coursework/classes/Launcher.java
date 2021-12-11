package coursework.classes;

public class Launcher implements Runnable {
    public static Scheduler sc;

    @Override
    public void run() {
        sc = new Scheduler();
        sc.Start();
    }

}
