package edu.rit.CSCI652.impl;

import edu.rit.CSCI652.demo.Event;
import edu.rit.CSCI652.demo.Publisher;
import edu.rit.CSCI652.demo.Subscriber;
import edu.rit.CSCI652.demo.Topic;

import java.util.Scanner;

public class PubSubAgent implements Publisher, Subscriber{

	@Override
	public void subscribe(Topic topic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subscribe(String keyword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsubscribe(Topic topic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsubscribe() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listSubscribedTopics() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publish(Event event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void advertise(Topic newTopic) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Are you a new user? (Y/N)");
		String newUser =  sc.next();
		System.out.println("Enter username: ");
		String username = sc.next();

		if(newUser.equalsIgnoreCase("Y")){

			// NEW USER TO BE ADDED BY EVENT Manager

			// Send username to event manager and add it
		}
		else{

			// Existing user. Login. If incorrect username then end with message
		}

	}


	
}
