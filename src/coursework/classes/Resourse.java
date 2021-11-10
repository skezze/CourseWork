package coursework.classes;

class Resource {
    protected boolean isIdle = true;
    protected int resourceNumber;

    public Resource(int number) {
        this.resourceNumber = number;
    }

    public int getNumber() {
        return resourceNumber;
    }

    public boolean isIdle(){
        return isIdle;
    }

    public void setIdle(boolean idle){
        isIdle = idle;
    }

    @Override
    public String toString() {
        return "resource #" + this.resourceNumber;
    }
}
