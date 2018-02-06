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
		try {
			output.writeUTF(keyword);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void unsubscribe(Topic topic) {
		// TODO Auto-generated method stub
		try {

			this.output.writeObject(topic);
			this.output.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void unsubscribe() {

		// TODO Auto-generated method stub
		try {
			this.output.writeUTF("unsubscribeAll");
			this.output.flush();

			this.output.writeUTF(this.username);
			this.output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}


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
		try{
			this.output.writeUTF("Event");
			this.output.flush();

			this.output.writeObject(event);
			this.output.flush();
			System.out.println(this.username + " published Event: " + event.getTitle());
		}
		catch(IOException e){
			e.printStackTrace();
		}
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

		String received = this.input.readUTF();
		// Status: Logged in or Registered
		System.out.println(received);

		if(!received.equalsIgnoreCase("You are Registered:"+username)) {
			try {
				//receive arraylist of missed topics
				Object objtopic = this.input.readObject();
				ArrayList<Topic> missedTopics = (ArrayList<Topic>) objtopic;

				if (missedTopics.size() == 0) {
					System.out.println("You have missed 0 topics");
				} else {
					StringBuilder sb = new StringBuilder("===============================================" +
							"\n***You have Missed Topics***\n");
					int i =0;
					for (Topic t : missedTopics) {
						sb.append(++i).append(". ").append(t.getName()).append("\n");
					}
					sb.append("===============================================");
					System.out.println(sb);
				}


				Object objevent = this.input.readObject();
				ArrayList<Event> missedEvents = (ArrayList<Event>) objevent;


				if (missedEvents.size() == 0) {
					System.out.println("You have missed 0 Events");
				} else {
					StringBuilder sb = new StringBuilder("===============================================" +
							"\n***You have Missed Events***\n");
					int i = 0;
					for (Event e : missedEvents) {
						sb.append(++i).append(". ").append("Topic Name: ").append(e.getTopic().getName())
								.append(" Title: ").append(e.getTitle()).append(" Content: ")
								.append(e.getContent()).append("\n");
					}
					sb.append("===============================================");
					System.out.println(sb);
				}

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}


	private void printMenu() throws IOException {

		System.out.println("1. Subscribe" +
				"\n2. Unsubscribe" +
				"\n3. List subscribed topics"+
				"\n4. Publish"+
				"\n5. Advertise"+
				"\n6. Read Messages");

		Scanner sc  = new Scanner(System.in);
		Random rand = new Random();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Enter choice");
		int choice = sc.nextInt();

		switch (choice){
			case 1:

				System.out.println("Press 1 to subscribe by TOPIC\n"+
						"Press 2 to subscribe by KEYWORD\n");

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

					System.out.println("Enter topics to subscribe to:");
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
				else if(choice2 ==2){
					// KEYWORD SUBSCRIPTION
					System.out.println("\n==== SUBSCRIPTION BY KEYWORD ====");

					this.output.writeUTF("getAllKeywords");
					this.output.flush();

					synchronized (input) {
						try {
							input.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					this.output.writeUTF("subscribeKeywords");
					this.output.flush();


					System.out.println("Enter keywords to subscribe to:");
					while (true) {
						System.out.println("Press N/n to exit");
						String keyWordName = sc.next();
						if (keyWordName.equalsIgnoreCase("N")){
							this.subscribe("3513");		//Stop code
							break;
						}

						else {
							this.subscribe(keyWordName);
						}
					}
				}
				break;

			case 2:
				// UNSUBCRIBE LOGIC
				System.out.println("Press 1 to Unsubscribe by TOPIC\n"+
						"Press 2 to Unsubscribe from all topics\n");

				int choice3 = sc.nextInt();

				if(choice3 == 1){
					System.out.println("Enter the names of the topics you wish to unsubscribe to");

					this.output.writeUTF("unsubscribeTopic");
					this.output.flush();

					this.output.writeUTF(this.username);
					this.output.flush();

					while(true){
						System.out.println("Press N/n to exit");
						String unsubscribed = sc.next();

						if(unsubscribed.equalsIgnoreCase("N")){
							Topic stop = new Topic(0,"3512");
							unsubscribe(stop);
							break;
						}
						else{
							unsubscribe(new Topic(0,unsubscribed));
						}

					}

				}
				else if(choice3 == 2){
					this.unsubscribe();
				}
				break;

			case 3:
				this.listSubscribedTopics();
				break;

			case 4:
				System.out.println("Publish Event");
				int idEvent = rand.nextInt();
				System.out.println("Name of the TOPIC:");
				String eventTopicName = sc.next();
				Topic eventTopic = new Topic(0, eventTopicName);

				System.out.print("Name of the topic Title: ");

				String eventTitle = br.readLine();
				System.out.print("Enter Content: ");
				String eventContent = br.readLine();
				Event newevent = new Event(idEvent, eventTopic, eventTitle, eventContent);
				this.publish(newevent);
				break;

			case 5:
				Topic newTopic;
				int idTopic = rand.nextInt();
				System.out.println("Name of the topic:");
				String topicName = sc.next();
				System.out.println("Do you have keywords?(Y/y)");
				String _keyword = sc.next();
				if(_keyword.equalsIgnoreCase("y")){
					System.out.println("Enter Keywords:");
					ArrayList<String> keywordList = new ArrayList<>();
					while(true){
						System.out.print("Press N/n to exit");
						_keyword = sc.next();
						if(_keyword.equalsIgnoreCase("n"))
							break;
						keywordList.add(_keyword);
					}
					newTopic = new Topic(idTopic, topicName, keywordList);

				}
				else {
					newTopic = new Topic(idTopic, topicName);
				}
				this.advertise(newTopic);
				break;

			default:
				System.out.println("Invalid ENTRY! TRY AGAIN");
		}
	}

}