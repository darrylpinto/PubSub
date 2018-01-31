package edu.rit.CSCI652.impl;


import com.sun.imageio.plugins.common.InputStreamAdapter;
import edu.rit.CSCI652.demo.Event;
import edu.rit.CSCI652.demo.Subscriber;
import edu.rit.CSCI652.demo.Topic;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class EventManager{

	private static ConcurrentHashMap<String, ArrayList<Topic>> subscriberTopic = new ConcurrentHashMap<>();
	/*
	 * Start the repo service
	 */
	private void startService() throws IOException {

		ServerSocket serverSock = new ServerSocket(6000);
		receive(serverSock);

	}

	/*
	 * notify all subscribers of new event 
	 */
	private void notifySubscribers(Event event) {
		
	}

	private void receive(ServerSocket serverSock) throws IOException {
		while (true) {

			String user_name = "";
			Socket socket = serverSock.accept();
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			System.out.println(input.readLine());
			System.out.println("recieved");
			System.out.println(user_name);

			if(subscriberTopic.contains(user_name)){
				out.write("Logged In");
			}
			else{
				subscriberTopic.put(user_name,new ArrayList<Topic>());
				out.write("You are Registered:"+ user_name);
			}
		}
	}
	
	/*
	 * add new topic when received advertisement of new topic
	 */
	private void addTopic(Topic topic){
		
	}
	
	/*
	 * add subscriber to the internal list
	 */
	private void addSubscriber(){
		
	}
	
	/*
	 * remove subscriber from the list
	 */
	private void removeSubscriber(){
		
	}
	
	/*
	 * show the list of subscriber for a specified topic
	 */
	private void showSubscribers(Topic topic){
		
	}
	
	
	public static void main(String[] args) throws IOException {

		new EventManager().startService();
	}


}
