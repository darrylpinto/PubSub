import java.io.IOException;

/**
 * Created by Darryl Pinto on 2/4/2018.
 */
public class Receiver implements Runnable{
    PubSubAgent pubSub;

    public Receiver(PubSubAgent pubSub){
        this.pubSub = pubSub;
    }

    //Have to declare a new
    @Override
    public void run() {

        while(true) {
            try {
                String choice = this.pubSub.input.readUTF();

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
        Object obj = this.pubSub.input.readObject();
        Topic topic = (Topic)obj;
        System.out.println("===============================================");
        System.out.println("**New Advertisement Received :"+topic.getName());
        System.out.println("===============================================");

    }

    private void receiveEvents(){

        //implement receiving event notifications here

    }
}
