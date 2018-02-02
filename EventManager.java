import com.sun.imageio.plugins.common.InputStreamAdapter;
import edu.rit.CSCI652.demo.Event;
import edu.rit.CSCI652.demo.Subscriber;
import edu.rit.CSCI652.demo.Topic;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class EventManager{


	private static ServerSocket[] serversocks = new ServerSocket[10];
	/*
     * Start the repo service
     */
	private void startService() throws IOException {

		ServerSocket serverSock = new ServerSocket(6000);

		//creating new sockets
		for(int i =0 ; i< 10;i++)
		{
			serversocks[i] = new ServerSocket(6001+i);
			new Thread(new PortThread(serversocks[i])).start();
		}
		// receive initial connection and assign port numbers
		receive(serverSock);

	}

	/*
     * notify all subscribers of new event
     */
	private void notifySubscribers(Event event) {

	}


	private void receive(ServerSocket serverSock) throws IOException {
		Random rand = new Random();

		while (true) {


			Socket socket = serverSock.accept();

			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			int random_integer = rand.nextInt(10)+6001;

			System.out.println("1 connection received, redirecting to port:"+ random_integer);
			String portNum = "" + random_integer;
			out.writeUTF(portNum);


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