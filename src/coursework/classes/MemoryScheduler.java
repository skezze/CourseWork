package coursework.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MemoryScheduler {
    private static ArrayList<MemoryBlock> memoryBlocks = new ArrayList<>();
    private MemoryBlock biggestBlock;

    public MemoryBlock getBiggestBlock() {
        return biggestBlock;
    }

    public void setBiggestBlock(MemoryBlock biggestBlock) {
        this.biggestBlock = biggestBlock;
    }

    public MemoryBlock fillMemoryBLock(int memorySize) {
        //findFreeBlock(process);
        biggestBlock = Collections.max(memoryBlocks, Comparator.comparing(s -> s.getAvailableMemory()));

        if(memorySize <= biggestBlock.getAvailableMemory()){
            biggestBlock.setAvailableMemory(biggestBlock.getAvailableMemory() - memorySize);
            //process.setState(State.Ready);
            //String key = process.getName();
            //biggestBlock.keys.add(key);
            return biggestBlock;
        }
        return null;
    }

    public void add(MemoryBlock memoryBlock){
        memoryBlocks.add(memoryBlock);
    }

    public MemoryBlock get(int i){
        return memoryBlocks.get(i);
    }

    public int getSize(){
        return memoryBlocks.size();
    }

    public static String print() {
        String result = "[";
        for(MemoryBlock memoryBlock : memoryBlocks){
            result+=memoryBlock+" ";
        }
        return result + "]";
    }



    @Override
    public String toString() {
        String result = "[ ";
        for (int i = 0; i < memoryBlocks.size(); i++){
            result+=memoryBlocks.get(i) + " ";
        }
        return result + "]";
    }
}