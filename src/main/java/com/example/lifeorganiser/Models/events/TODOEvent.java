package com.example.lifeorganiser.src.Models.events;

public class TODOEvent extends Event {

	public enum Type {ShortTerm, MidTerm, LongTerm}

	public TODOEvent(String name,String description){
		super(name,description);
	}

}
