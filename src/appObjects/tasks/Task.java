package appObjects.tasks;


import java.util.Date;

public abstract class Task{

    private String taskName;
    private String taskType;
	private boolean isDone;
	private Date date;


	Task(String taskName, String taskType) {
    	setTaskName(taskName);
    	setTaskType(taskType);
    }

	public boolean isDone() {
		return isDone;
	}

	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}

    private void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTaskName() {
		return this.taskName;
	}
	
	private void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	public String getTaskType() {
		return this.taskType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
