import java.io.IOException;
import java.io.ObjectInputStream;

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

                if (choice.equals("Topic"))
                    this.receiveAdvertisements();
                else if (choice.equals("Event"))
                    this.receiveEvents();
                else
                    System.out.println("Error");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }
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
