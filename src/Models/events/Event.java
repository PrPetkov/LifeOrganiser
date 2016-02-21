package models.events;

import models.exceptions.IncorrectInputException;

public abstract class Event {

	private String title;
	private String description;

	Event(String title, String description) {

		try {
			if (checkIfInputIsCorrect(title)) {
				throw new IncorrectInputException();
			}

			this.title = title;
			this.description = description;

		} catch (IncorrectInputException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// methods
	private boolean checkIfInputIsCorrect(String title) {
		if (title.trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	
	// getters and setters
	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
