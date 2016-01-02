package states;


import appObjects.User;
import appObjects.tasks.Task;

public abstract class TaskState extends UserState {

    private Task task;

    public TaskState(User user, Task task) {
        super(user);

        this.setTask(task);

    }

    protected Task getTask() {
        return this.task;
    }

    protected void setTask(Task task) {
        this.task = task;
    }

    @Override
    public abstract void draw();
}
