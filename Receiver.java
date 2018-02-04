import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Darryl Pinto on 2/4/2018.
 */
public class Receiver implements Runnable {
    private ObjectInputStream input;

    public Receiver(ObjectInputStream input) {
        this.input = input;
    }

    //Have to declare a new
    @Override
    public void run() {

        while (true) {
            try {
                String choice = this.input.readUTF();

                switch (choice) {
                    case "Topic":
                        this.receiveAdvertisements();
                        break;
                    case "Event":
                        this.receiveEvents();
                        break;
                    case "getAllTopics":
                        this.receiveAllTopics();
                        break;
                    case "getSubscribedTopics":
                        this.receiveSubscribedTopics();
                        break;
                    default:
                        System.out.println("Error");
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void receiveSubscribedTopics() {

        try {
            Object obj = input.readObject();
            ArrayList<String> subscribedTopics = (ArrayList<String>)obj;
            StringBuilder sb = new StringBuilder("Subscribed  Topics:\n");

            for(String topicNames: subscribedTopics){
                sb.append(topicNames).append("\n");
            }

            System.out.println(sb);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void receiveAllTopics() {

        synchronized (input)
        {
            //receive arraylistt of strings
            try {
                Object obj = input.readObject();
                ArrayList<String> allTopics = (ArrayList<String>)obj;

                StringBuilder topics_string = new StringBuilder("======================All Topics=========================\n");
                int i =0;
                for (String topic: allTopics) {
                    topics_string.append("").append(++i).append(". ").append(topic).append("\n");
                }
                topics_string.append("===============================================\n");
                System.out.println(topics_string);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


            input.notify();
        }

    }

    private void receiveAdvertisements() throws IOException, ClassNotFoundException {

        //Implement receiving advertisements here
        Object obj = this.input.readObject();
        Topic topic = (Topic) obj;
        System.out.println("===============================================\n" +
                "**New Advertisement Received :" + topic.getName() +
                "\n===============================================");

    }

    private void receiveEvents() {

        //implement receiving event notifications here

    }
}
