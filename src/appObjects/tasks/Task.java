package appObjects.tasks;


public abstract class Task extends User{

    private String taskName;
    private String taskType;
    
    Task(String taskName, String taskType) {
    	setTaskName(taskName);
    	setTaskType(taskType);
    }
    
    private String setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	private void getTaskName() {
		return this.taskName;
	}
	
	private String setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	private void getTaskType() {
		return this.taskType;
	}
    
}
