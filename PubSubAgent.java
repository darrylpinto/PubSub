import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class PubSubAgent implements Publisher, Subscriber{

	static DataInputStream input;
	static DataOutputStream output;

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

	public void main(String[] args) throws IOException {

		String host = "localhost";
		Socket socTemp = new Socket(host, 6000);
		DataOutputStream outTemp = new DataOutputStream(socTemp.getOutputStream());

		DataInputStream inputTemp = new DataInputStream(socTemp.getInputStream());
		String port = inputTemp.readUTF();
		System.out.println("Received new port:" + port);

		socTemp.close();

		Socket soc = new Socket(host, Integer.parseInt(port));
		this.output = new DataOutputStream(soc.getOutputStream());
		this.input = new DataInputStream(soc.getInputStream());

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter username: ");
		String username = sc.next();
		output.writeUTF(username);
		output.flush();


		System.out.println(input.readUTF());

		//////////////////////////////////////////////////////////
		this.printMenu();
	}


	public void printMenu() throws IOException {

		System.out.println("1. Subscribe");
		System.out.println("2. Unsubscribe");
		System.out.println("3. List subscribed topics");
		System.out.println("4. Publish");
		System.out.println("5. Advertise");

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

			default:
				System.out.println("Invalid ENTRY! TRY AGAIN");
		}
	}

	private Topic getTopics() throws IOException {

		output.writeUTF("getTopics");
		return null;

	}


}
