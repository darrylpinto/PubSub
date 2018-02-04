import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;

public class ClientThread implements Runnable {

    private ServerSocket serverSocket;
    private  Socket client;

    private ObjectInputStream objinput;
    private ObjectOutputStream objoutput;

    // ServerSocket is serverSocket on portThread
    public ClientThread(Socket client, ServerSocket serverSocket) throws IOException {

        this.client = client;
        this.serverSocket = serverSocket ;

        this.objinput = new ObjectInputStream(this.client.getInputStream());
        this.objoutput = new ObjectOutputStream(this.client.getOutputStream());

    }

    @Override
    public void run() {

       // synchronized (serverSocket) {
            String user_name = null;
            try {
                user_name = objinput.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("User logged in: " + user_name);

            try
            {
                if (EventManager.subscriberTopics.containsKey(user_name)) {

                    objoutput.writeUTF("Logged In:" + user_name);

                } else {
                    EventManager.subscriberTopics.put(user_name, new ArrayList<>());
                    objoutput.writeUTF("You are Registered:" + user_name);
                }
                objoutput.flush();
                EventManager.subscriberThreadMap.put(user_name, this);

                communicate();
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
       // }  // Synchronised block ends

    }

    private void communicate() throws IOException {

        while (true)
        {

            String input_string = this.objinput.readUTF();


            switch (input_string)
            {
                case "getTopics":
//                    ArrayList<String> arr = new ArrayList<>();
//
//                    for (String key:EventManager.topicSubscriber.keySet()) {
//                        arr.add(key);
//                    }
//                    objoutput.writeObject(arr);
//                    objoutput.flush();
//
//                    try {
//
//                        int no_of_topics = Integer.parseInt(this.objinput.readUTF());
//                        String user_name = this.objinput.readUTF();
//
//                        for (int i = 0; i < no_of_topics; i++) {
//                            Object obj =this.objinput.readObject();
//                            Topic currentTopic = (Topic) obj;
//
//                            ArrayList<String> subscribers = EventManager.topicSubscriber.get(currentTopic.getName());
//                            HashSet<String> subscribersSet = new HashSet<>(subscribers);
//                            if (subscribersSet.contains(user_name)){
//                                System.out.println(user_name + " already subscribed to " + currentTopic.getName() );
//                            }
//                            else{
//                                subscribers.add(user_name);
//                                EventManager.topicSubscriber.put(currentTopic.getName(),subscribers);
//                                System.out.println(user_name + " subscribed to " + currentTopic.getName());
//                            }
//                        }
//
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }

                    break;
                case "advertise":
                    try {
                        System.out.println("*inside advertise");
                        Object obj = this.objinput.readObject();
                        Topic newtopic = (Topic) obj;
                        System.out.println(newtopic);
                        if(EventManager.topicSubscriber.containsKey(newtopic.getName())){
                            System.out.println("Topic already present: "+ newtopic.getName());
                        }
                        else{
                            EventManager.topicSubscriber.put(newtopic.getName(), new ArrayList<>());
                            EventManager.advertiseTopic(newtopic);

                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }


            }
        }



    }

    public void sendAdvertisement(Topic newtopic) {
        try {
            this.objoutput.writeUTF("Topic");
            this.objoutput.flush();

            this.objoutput.writeObject(newtopic);
            this.objoutput.flush();

            System.out.println("Topic Advertised:"+ newtopic.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
