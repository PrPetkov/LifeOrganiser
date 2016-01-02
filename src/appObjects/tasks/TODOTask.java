package appObjects.tasks;


import Interfaces.ITODO;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TODOTask implements ITODO {

    private String name;
    private String description;
    private boolean isDone;

    public TODOTask(String taskName){

        throw new NotImplementedException();

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {

        this.description = description;

    }

    @Override
    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public void setIsDone(boolean isDone) {

        this.isDone = isDone;

    }
}
