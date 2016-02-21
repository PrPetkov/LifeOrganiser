package models.events;

import java.time.LocalDate;

public class NotificationEvent extends Event {

	private LocalDate forDate;
	
	public NotificationEvent(String name,String description, LocalDate forDate){
		super(name, description);
		this.forDate = forDate;
	}
	
	
	// getters and setters
	public LocalDate getForDate() {
		return forDate;
	}

	public void setForDate(LocalDate forDate) {
		this.forDate = forDate;
	}

}
