package coursework.classes;

import java.util.ArrayList;

public class Queue {
    private final ArrayList<Process> queue;
    private int PID;

    public Queue() {
        this.queue = new ArrayList<>();
        this.PID = 1;
    }

    public void Add(final int PCount) {
        for (int i = 0; i < PCount; i++) {
            Process p = new Process(this.PID);
            this.PID++;
            addProcess(p);
        }
    }

    public void addProcess(Process p) {
        if (p.getStatus() == Status.Waiting)
            p.setBursTime(p.getBursTime() + (int) (p.getBursTime() / Configuration.ZombieDiv));

        if (MemScheduler.fillMB(p)) {
            this.queue.add(p);
        }

    }

    public void Remove(Process process) {
        queue.remove(process);
        MemScheduler.releaseMB(process);
    }

    public ArrayList<Process> getQueue() {
        return queue;
    }


    public Process getNextProcess() {
        if (queue.size() != 0) {
            Process _tmp = queue.get(0);
            this.Remove(_tmp);
            _tmp.setStatus(Status.Running);
            return _tmp;
        }
        return null;
    }
}
