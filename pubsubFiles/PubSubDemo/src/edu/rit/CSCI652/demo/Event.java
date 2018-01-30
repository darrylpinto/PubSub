package edu.rit.CSCI652.demo;

import java.util.List;

public class Event {
	private int id;
	private Topic topic;
	private String title;
	private String content;
	
	/* Constructor */
	Event(int id, Topic topic, String title, String content){
		this.id = id;
		this.topic = topic;
		this.title = title;
		this.content = content;
	}
	
	// Method may not be required
	/*
	public void writeContent(String title, String content){
		
		
	}
	*/
}
