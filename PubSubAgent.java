import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PubSubAgent implements Publisher, Subscriber{

	public ObjectInputStream input;
	public ObjectOutputStream output;
	public String username;

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

		try {


			this.output.writeUTF("advertise");
			this.output.flush();

			this.output.writeObject(newTopic);
			this.output.flush();

			System.out.printf(" Topic (%s) sent for advertisement\n", newTopic.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// TODO Auto-generated method stub

	}


	public static void main(String[] args) throws IOException {
		PubSubAgent pubsub = new PubSubAgent();
		pubsub.start();
		new Thread(new Sender(pubsub)).start();
		new Thread(new Receiver(pubsub)).start();

	}

	private void start() throws IOException {

		String host = "localhost";
		Socket socTemp = new Socket(host, 6000);
		DataOutputStream outTemp = new DataOutputStream(socTemp.getOutputStream());
		DataInputStream inputTemp = new DataInputStream(socTemp.getInputStream());
		String port = inputTemp.readUTF();
		System.out.println("Received new port:" + port);

		socTemp.close();

		Socket soc = new Socket(host, Integer.parseInt(port));
		this.output = new ObjectOutputStream(soc.getOutputStream());
		this.input =  new ObjectInputStream(soc.getInputStream());

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter username: ");
		username = sc.next();
		this.output.writeUTF(username);
		this.output.flush();

		// Status: Logged in or Registered
		System.out.println(this.input.readUTF());

		//////////////////////////////////////////////////////////
		// NEED THREADING HERE

	}


//
//	public ArrayList<String> getTopics() throws IOException {
////
//		this.output.writeUTF("getTopics");
//		this.output.flush();
//		ArrayList<String> interestedTopics = new ArrayList<>();
//		try {
//			Object obj = input.readObject();
//			ArrayList<String> topics = (ArrayList<String>) obj;
//			HashSet<String> topicSet = new HashSet<String>(topics);
//
//
//
//			System.out.println("Available Topics:");
//			for(int i=0; i<topics.size(); i++)
//				System.out.println(topics.get(i));
//			System.out.println("Enter all interested topics: ");
//
//			Scanner sc = new Scanner(System.in);
//			while(true){
//				System.out.println("Press N/n to exit");
//				String topicName = sc.next();
//				if(topicName.equalsIgnoreCase("N"))
//					break;
//				else{
//					if(topicSet.contains(topicName))
//						interestedTopics.add(topicName);
//					else{
//						System.out.println("INVALID TOPIC NAME. Check Spelling");
//					}
//				}
//			}
//
//			// check fr spelling
//
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//
//
//		return interestedTopics;
//
//	}
//

}
