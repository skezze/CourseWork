package coursework.classes;

public class Process {
    private final int id;        //after create
    private final String name;    //rand
    private final int priority;  //rand + on work
    private Status status;    //rand + on work
    private final int tickWorks;       //rand
    private final int memory;     //rand
    private final int timeIn;     //after create
    private int bursTime;   //on work

    public Process(int id) {
        this.id = id;
        this.name = Utils.getRandString(Utils.getRandInt(Configuration.minProcName, Configuration.maxProcName));
        this.memory = Utils.getRandInt(Configuration.minMemsize, Configuration.maxMemsize);
        this.priority = Utils.getRandInt(Configuration.maxPriority);
        this.tickWorks = Utils.getRandInt(Configuration.minTickWork, Configuration.maxTickWork);
        this.timeIn = ClockGenerator.getTick();
        this.bursTime = 0;
        this.status = Status.Ready;
    }
    //________Setters________\\

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setBursTime(int bursTime) {
        this.bursTime = bursTime;
    }

    //________Getters________\\


    public int getTickWorks() {
        return tickWorks;
    }

    public int getMemory() {
        return memory;
    }

    public int getBursTime() {
        return bursTime;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Process" +
                "\tid=" + id +
                "\tname=" + name +
                "\tpriority=" + priority +
                "\tstate=" + status +
                "\ttick=" + tickWorks +
                "\tmemory=" + memory +
                "\ttimeIn=" + timeIn +
                "\tbursTime=" + bursTime + '\n';
    }

}
