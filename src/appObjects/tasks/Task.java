package appObjects.tasks;


import java.time.LocalDateTime;

public abstract class Task{

    private String taskName;
	private boolean isDone;
	private LocalDateTime date;

	Task(String taskName, LocalDateTime dateTime) {
    	this.setTaskName(taskName);
        this.setDate(dateTime);
    }

	public boolean isDone() {
		return isDone;
	}

	protected void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}

    protected void setTaskName(String taskName) {
		if (taskName != null) {
            this.taskName = taskName;
        }
	}
	
	public String getTaskName() {
		return this.taskName;
	}

	public LocalDateTime getDate() {
		return date;
	}

	protected void setDate(LocalDateTime date) {
        if (date != null) {
            this.date = date;
        }
	}
}
