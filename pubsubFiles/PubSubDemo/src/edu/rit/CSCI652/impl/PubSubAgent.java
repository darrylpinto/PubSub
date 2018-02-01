package edu.rit.CSCI652.impl;

import edu.rit.CSCI652.demo.Event;
import edu.rit.CSCI652.demo.Publisher;
import edu.rit.CSCI652.demo.Subscriber;
import edu.rit.CSCI652.demo.Topic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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

	public static void main(String[] args) throws IOException {

		String host = "192.168.137.38";
		Socket socTemp = new Socket(host, 6000);
		DataOutputStream outTemp = new DataOutputStream(socTemp.getOutputStream());

		DataInputStream inputTemp = new DataInputStream(socTemp.getInputStream());
		String port = inputTemp.readUTF();
		System.out.println("Received new port:" + port);

		//socTemp.close();

		Socket soc = new Socket(host, Integer.parseInt(port));
		DataOutputStream output = new DataOutputStream(soc.getOutputStream());
		DataInputStream input = new DataInputStream(soc.getInputStream());

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter username: ");
		String username = sc.next();
		output.writeUTF(username);


		System.out.println(input.readUTF());
	}


	
}
