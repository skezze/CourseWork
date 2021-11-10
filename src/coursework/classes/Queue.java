package coursework.classes;

import java.util.*;

public class Queue {
    private ArrayList<Process> queue;
    private int lastID;

    public int getLastID() {
        return lastID;
    }

    public Process get(int i) {
        return queue.get(i);
    }

    public void setLastID(int lastID) {
        this.lastID = lastID;
    }

    public Queue() {
        this.queue = new ArrayList<>();
        this.lastID = 1;
    }

    public int getSize() {
        return queue.size();
    }

    public void add(Process process) {
        this.queue.add(process);
    }

    public void add() {
        Process process = new Process(this.lastID++);

        this.add(process);
    }

    public void remove(int i){
        this.queue.remove(i);
    }

    public void remove(Process process){
        this.queue.remove(process);
    }

    public void add(final int N) {
        for (int i = 0; i < N; i++) {
            this.add();
        }
    }

    public void addManual(final int N) {
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < N; i++) {
            Process process = new Process();
            System.out.println("Enter the PID");
            process.setId(input.nextInt());
            System.out.println("Set priority");
            process.setPriority(input.nextInt());
            System.out.println("Set time");
            process.setTime(input.nextInt());
            System.out.println("Set memory");
            process.setMemory(input.nextInt());
            this.add(process);
        }
    }

    public synchronized void sortByPriorityAndArrivalTime(State state){
        Process [] sortedQueue = this.queue.toArray(new Process[0]);
        int temp;
        String stemp;
        //Process tempProcess;
        for(int i = 0; i < sortedQueue.length; i++){
            if(sortedQueue[i].getState() != state)
                continue;
            for(int j = 0; j <sortedQueue.length - i - 1; j++){
                if(sortedQueue[j].getArrivalTime() > sortedQueue[j + 1].getArrivalTime()){
                    temp = sortedQueue[j].getArrivalTime();
                    sortedQueue[j].setArrivalTime(sortedQueue[j+1].getArrivalTime());
                    sortedQueue[j + 1].setArrivalTime(temp);

                    temp = sortedQueue[j].getBurstTime();
                    sortedQueue[j].setBurstTime(sortedQueue[j+1].getBurstTime());
                    sortedQueue[j + 1].setBurstTime(temp);

                    temp = sortedQueue[j].getPriority();
                    sortedQueue[j].setPriority(sortedQueue[j+1].getPriority());
                    sortedQueue[j + 1].setPriority(temp);

                    stemp = sortedQueue[j].getName();
                    sortedQueue[j].setName(sortedQueue[j+1].getName());
                    sortedQueue[j + 1].setName(stemp);
                }
                if(sortedQueue[j].getArrivalTime() == sortedQueue[j+1].getArrivalTime()){
                    if(sortedQueue[j].getPriority() > sortedQueue[j+1].getPriority()){
                        temp = sortedQueue[j].getArrivalTime();
                        sortedQueue[j].setArrivalTime(sortedQueue[j+1].getArrivalTime());
                        sortedQueue[j + 1].setArrivalTime(temp);

                        temp = sortedQueue[j].getBurstTime();
                        sortedQueue[j].setBurstTime(sortedQueue[j+1].getBurstTime());
                        sortedQueue[j + 1].setBurstTime(temp);

                        temp = sortedQueue[j].getPriority();
                        sortedQueue[j].setPriority(sortedQueue[j+1].getPriority());
                        sortedQueue[j + 1].setPriority(temp);

                        stemp = sortedQueue[j].getName();
                        sortedQueue[j].setName(sortedQueue[j+1].getName());
                        sortedQueue[j + 1].setName(stemp);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (Process process : queue) {
            result += process;
        }
        return result;
    }
}