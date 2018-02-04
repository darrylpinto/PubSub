import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class EventManager{

	//used for registration
	public static ConcurrentHashMap<String, ArrayList<String>> subscriberTopics = new ConcurrentHashMap<>();

	// used for publish(Events) topics and subscriber
	public static ConcurrentHashMap<String,ArrayList<String>> topicSubscriber = new ConcurrentHashMap<>();

	//used for advertising keyword , topic : possibility of overwriting
	public static ConcurrentHashMap<String,String> keyword_topic = new ConcurrentHashMap<>();

	public static ConcurrentHashMap<String, ClientThread> subscriberThreadMap = new ConcurrentHashMap<>();

    public  static ConcurrentHashMap<String, ArrayList<Topic>> offlineTopics = new ConcurrentHashMap<>();
	private static ServerSocket[] serverSocks = new ServerSocket[10];


	/*
     * Start the repo service
     */
	private void startService() throws IOException {


		ServerSocket serverSock = new ServerSocket(6000);

		//creating new sockets
		for(int i =0 ; i< 10;i++)
		{
			serverSocks[i] = new ServerSocket(6001+i);

			new Thread(new PortThread(serverSocks[i])).start();
		}
		// receive initial connection and assign port numbers
		redirectToNewPort(serverSock);

	}

	/*
     * notify all subscribers of new event
     */
	private void notifySubscribers(Event event) {

	}


	private void redirectToNewPort(ServerSocket serverSock) throws IOException {

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


	public static void advertiseTopic(Topic newtopic) {
		for (String user_name :
				EventManager.subscriberThreadMap.keySet()) {
			ClientThread thread = EventManager.subscriberThreadMap.get(user_name);

            try {
                thread.sendAdvertisement(newtopic);
            } catch (SocketException e) {
                System.out.println(thread.user_name + " is offline. Caching the Topic:"+ newtopic.getName());
                ArrayList<Topic> topicList = new ArrayList<>();

                if(EventManager.offlineTopics.containsKey(user_name)) {
                     topicList = EventManager.offlineTopics.get(user_name);
                }

                topicList.add(newtopic);
                EventManager.offlineTopics.put(user_name,topicList);

            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

		System.out.println(EventManager.topicSubscriber);

	}
}