import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Darryl Pinto on 2/4/2018.
 */

public class Sender implements Runnable {
    private PubSubAgent pubSub;

    public Sender(PubSubAgent pubsub){
        this.pubSub = pubsub;
    }
    @Override
    public void run() {

        try {
            while(true) {
                printMenu();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
//
//				System.out.println("Press 1 to subscribe by TOPIC\n"+
//						"Press 2 to subscribe by KEYWORD\n");
//
//				int choice2 = sc.nextInt();
//				if(choice2 == 1){
//					System.out.println("\n==== SUBSCRIPTION BY TOPIC ====");
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

            // 3
            // 4
            case 5:
                Random rand = new Random();
                int id = rand.nextInt();
                System.out.println("Name of the topic:");
                String topicName = sc.next();

                Topic newTopic = new Topic(id,topicName);

                pubSub.advertise(newTopic);
                break;

            default:
                System.out.println("Invalid ENTRY! TRY AGAIN");
        }
    }
}

