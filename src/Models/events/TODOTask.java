package Models.tasks;


import Interfaces.ITODO;

public class TODOTask implements ITODO {

    private String name;
    private String description;
    private boolean isDone;

    public TODOTask(String description, String name, boolean isDone) {
        this.setDescription(description);
        this.setName(name);
        this.setDone(isDone);
    }


    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    protected void setDone(boolean done) {
        isDone = done;
    }
}
