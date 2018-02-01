package edu.rit.CSCI652.impl;


import com.sun.imageio.plugins.common.InputStreamAdapter;
import edu.rit.CSCI652.demo.Event;
import edu.rit.CSCI652.demo.Subscriber;
import edu.rit.CSCI652.demo.Topic;

import java.io.*;
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


			Socket socket = serverSock.accept();
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			String user_name = input.readUTF();
			System.out.println(user_name);

			if(subscriberTopic.contains(user_name)){
				out.writeUTF("Logged In:"+ user_name);
			}
			else{
				subscriberTopic.put(user_name,new ArrayList<Topic>());
				out.writeUTF(user_name+" Registered!");
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