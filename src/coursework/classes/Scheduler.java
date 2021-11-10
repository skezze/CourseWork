package coursework.classes;

import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {
    private Queue jobsQueue;
    private Queue readyQueue;
    private Queue waitingQueue;
    private Queue rejectedQueue;

    private MemoryScheduler memoryScheduler;
    private TactGenerator tactGenerator;

    private static CPU cpu;
    private static Device device;

    public Scheduler(final int cpuCoresNumber, int memoryVolume) {
        this.jobsQueue = new Queue();
        this.readyQueue = new Queue();
        this.rejectedQueue = new Queue();
        this.waitingQueue = new Queue();

        this.memoryScheduler = new MemoryScheduler();
        Configuration.memoryVolume = memoryVolume;

        cpu = new CPU(cpuCoresNumber);
        device = new Device();

        tactGenerator = new TactGenerator();
    }

    public void addProcess(int number){
        jobsQueue.add(number);
        for (int i = 0; i < jobsQueue.getSize(); i++) {
            var process = jobsQueue.get(i);
            if(process.getState() == State.New)
                initProcess(process);
        }

        jobsQueue.sortByPriorityAndArrivalTime(State.Ready);
    }

    public void addProcessManual(int number){
        jobsQueue.addManual(number);
        for (int i = 0; i < jobsQueue.getSize(); i++) {
            var process = jobsQueue.get(i);
            if(process.getState() == State.New)
                initProcess(process);
        }

        jobsQueue.sortByPriorityAndArrivalTime(State.Ready);
    }

    public void addProcessRandom(int time){
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            public void run() {
                jobsQueue.add(1);
                jobsQueue.sortByPriorityAndArrivalTime(State.Ready);
            }
        };
        timer.schedule(timerTask, 500);
    }

    public void init() {

        Timer timer = new Timer();
        timer.schedule(tactGenerator, 1000, 1000);
        tactGenerator.run();

        memoryScheduler.add(new MemoryBlock(200));
        memoryScheduler.add(new MemoryBlock(1200));
        memoryScheduler.add(new MemoryBlock(1200));
        memoryScheduler.add(new MemoryBlock(1200));
    }

    public void schedule() {

        do {
            for (int i = 0; i < readyQueue.getSize(); i++) {
                var process = readyQueue.get(i);

                if(process.getState() == State.Ready) {
                    for (Core core : cpu.getCores()) {
                        if (core.isIdle()) {
                            process.setState(State.Running);
                            core.setIdle(false);
                            process.setCore(core);
                            break;
                        }
                    }
                }
                if(process.getState() != State.Running)
                    continue;

                process.setBurstTime(TactGenerator.getTime() - process.getArrivalTime());

                if (process.getBurstTime() >= process.getTime()) {

                    process.setState(State.Terminated);
                    System.out.println("////////////////////////////////////////////");
                    System.out.println(jobsQueue);
                    System.out.println("............................................");
                    System.out.println(cpu);
                    System.out.println("............................................");
                    System.out.println("--------------------------------------------");
                    System.out.println(memoryScheduler);
                    System.out.println("--------------------------------------------");
                    System.out.println("////////////////////////////////////////////");
                }
            }

            for (int i = 0; i < readyQueue.getSize(); i++){
                var process = readyQueue.get(i);
                if(process.getState() == State.Waiting) {
                    waitingQueue.add(process);
                    readyQueue.remove(process);
                }
                else if(process.getState() == State.Terminated)
                    readyQueue.remove(process);
            }

            for (int i = 0; i < waitingQueue.getSize(); i++){
                var process = waitingQueue.get(i);
                if(process.getState() == State.Running) {
                    readyQueue.add(process);
                    waitingQueue.remove(process);
                }
            }
            for (int i = 0; i < jobsQueue.getSize(); i++) {
                var process = jobsQueue.get(i);
                if(process.getState() == State.New)
                {
                    initProcess(process);
                    readyQueue.sortByPriorityAndArrivalTime(State.Ready);
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (true);
    }

    private void initProcess(Process process){
        var block = memoryScheduler.fillMemoryBLock(process.getMemory());
        if(block != null){
            process.setState(State.Ready);
            process.setMemoryBlock(block);
            process.setDevice(device);
            readyQueue.add(process);
        }
    }

    @Override
    public String toString() {
        return "Scheduler {" + "\n" +
                "Jobs queue: [ " + "\n" + jobsQueue + " ]" + " \n" +
                "Rejected queue: [" +  "\n" +rejectedQueue + " ]" + " \n" +
                "CPU:"  + cpu + "\n" +
                "MemoryScheduler: "  + memoryScheduler +
                '}';
    }
}
