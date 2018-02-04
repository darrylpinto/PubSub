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
		try {
			output.writeObject(topic);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

		try {
			this.output.writeUTF("getSubscribedTopics");
			this.output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	//main
	public static void main(String[] args) throws IOException {
		PubSubAgent pubsub = new PubSubAgent();
		pubsub.start();

		new Thread(new Receiver(pubsub.input)).start();

		while(true){
			pubsub.printMenu();
		}
	}

	private void start() throws IOException {

		String host = "192.168.137.38";
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

		String receieved = this.input.readUTF();
		// Status: Logged in or Registered
		System.out.println(receieved);

		//receive arraylist of missed advertisements
		if(!receieved.equalsIgnoreCase("You are Registered:"+username)) {
			try {
				Object obj = this.input.readObject();
				ArrayList<Topic> missedTopics = (ArrayList<Topic>) obj;
				if (missedTopics.size() == 0) {
					System.out.println("You have missed 0 topics");
				} else {
					StringBuilder sb = new StringBuilder("-------------------\n***You have Missed Topics***\n");
					int i =0;
					for (Topic t : missedTopics) {
						sb.append(++i).append(". ").append(t.getName()).append("\n");
					}
					sb.append("-------------------");
					System.out.println(sb);
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}


	private void printMenu() throws IOException {

		System.out.println("1. Subscribe" +
				"\n 2. Unsubscribe" +
				"\n3. List subscribed topics"+
				"\n4. Publish"+
				"\n5. Advertise"+
				"\n6. Read Messages");

		Scanner sc  = new Scanner(System.in);

		System.out.println("Enter choice");
		int choice = sc.nextInt();

		switch (choice){
			case 1:
//
				System.out.println("Press 1 to subscribe by TOPIC\n"+
						"Press 2 to subscribe by KEYWORD\n");
//
				int choice2 = sc.nextInt();

				if(choice2 == 1) {
					System.out.println("\n==== SUBSCRIPTION BY TOPIC ====");
					// first user name
					this.output.writeUTF("getAllTopics");
					this.output.flush();



					synchronized (input) {
						try {
							input.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					this.output.writeUTF("subscribeTopics");
					this.output.flush();

					this.output.writeUTF(this.username);
					this.output.flush();

					System.out.println("Enter topics to subscribe to");
					while (true) {
						System.out.println("Press N/n to exit");
						String topicName = sc.next();
						if (topicName.equalsIgnoreCase("N")){
							this.subscribe(new Topic(0, "3511"));		//Stop code
							break;
						}

						else {
							this.subscribe(new Topic(0, topicName));
						}
					}
				}

					///



//					ArrayList<String> interestedTopics = pubSub.getTopics();
//
//					this.pubSub.output.writeUTF(""+interestedTopics.size());
//					this.pubSub.output.flush();
//
//					this.pubSub.output.writeUTF(this.pubSub.username);
//					this.pubSub.output.flush();
//
//					for (String interestedTopic : interestedTopics) {
//						this.pubSub.subscribe(new Topic(0, interestedTopic));
//					}
//				}
//				else{
//					System.out.println("\n==== SUBSCRIPTION BY KEYWORD ====");
//
//					// KEYWORD SUBSCRIPTION
//				}
//
//				break;

			case 2:
				// UNSUBCRIBE LOGIC
				break;

			case 3:
				this.listSubscribedTopics();
				break;
			// 4
			case 5:
				Random rand = new Random();
				int id = rand.nextInt();
				System.out.println("Name of the topic:");
				String topicName = sc.next();

				Topic newTopic = new Topic(id,topicName);

				this.advertise(newTopic);
				break;

			default:
				System.out.println("Invalid ENTRY! TRY AGAIN");
		}
	}

}