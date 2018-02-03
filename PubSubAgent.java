import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class PubSubAgent implements Publisher, Subscriber{

	private ObjectInputStream input;
	private ObjectOutputStream output;

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

			System.out.printf(" Topic (%s) sent for advertisement", newTopic.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		
	}


	public static void main(String[] args) throws IOException {
		PubSubAgent pubsub = new PubSubAgent();
		pubsub.start();

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
		String username = sc.next();
//		DataOutputStream out1 = new DataOutputStream(this.output);
//		DataInputStream in1 = new DataInputStream(this.input);
		this.output.writeUTF(username);
		this.output.flush();

		// Status: Logged in or Registered
		System.out.println(this.input.readUTF());

		//////////////////////////////////////////////////////////
		// NEED THREADING HERE
		printMenu();
	}


	private void printMenu() throws IOException {

		System.out.println("1. Subscribe");
		System.out.println("2. Unsubscribe");
		System.out.println("3. List subscribed topics");
		System.out.println("4. Publish");
		System.out.println("5. Advertise");
		System.out.println("6. Read Messages");

		Scanner sc  = new Scanner(System.in);

		System.out.println("Enter choice");
		int choice = sc.nextInt();

		switch (choice){
			case 1:

				System.out.println("Press 1 to subscribe by TOPIC\n"+
						"Press 2 to subscribe by KEYWORD\n");

				int choice2 = sc.nextInt();
				if(choice2 == 1){
					System.out.println("SUBSCRIPTION BY TOPIC");
					Topic topic = getTopics();
					this.subscribe(topic);
				}
				else{
					System.out.println("SUBSCRIPTION BY KEYWORD");

					// KEYWORD SUBSCRIPTION
				}

				break;

			case 2:
			// UNSUBCRIBE LOGIC
				break;

			// 3
			// 4
			case 5:
//				System.out.println("Name of the topic:");
//				String topicName = sc.next();
//
				Topic newTopic = new Topic(1234,"Sports");

				this.advertise(newTopic);
				break;

			default:
				System.out.println("Invalid ENTRY! TRY AGAIN");
		}
	}

	private Topic getTopics() throws IOException {

//		DataOutputStream out1 = new DataOutputStream(this.output);
//		DataInputStream in1 = new DataInputStream(this.input);
//		out1.writeUTF("getTopics");
//		out1.flush();



		return null;

	}


}
