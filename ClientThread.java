import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;

public class ClientThread implements Runnable {

    private ServerSocket serverSocket;
    private  Socket client;

    private ObjectInputStream objinput;
    private ObjectOutputStream objoutput;

    public String user_name;

    // ServerSocket is serverSocket on portThread
    public ClientThread(Socket client, ServerSocket serverSocket) throws IOException {

        this.client = client;
        this.serverSocket = serverSocket ;

        this.objinput = new ObjectInputStream(this.client.getInputStream());
        this.objoutput = new ObjectOutputStream(this.client.getOutputStream());
        this.user_name = "";

    }

    @Override
    public void run() {

       // synchronized (serverSocket) {
           // String user_name = null;
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
                    objoutput.flush();

                    objoutput.writeObject(EventManager.offlineTopics.get(user_name));
                    objoutput.flush();

                    EventManager.offlineTopics.put(user_name, new ArrayList<>());

                } else {
                    EventManager.subscriberTopics.put(user_name, new ArrayList<>());
                    objoutput.writeUTF("You are Registered:" + user_name);
                    objoutput.flush();

                    EventManager.offlineTopics.put(user_name, new ArrayList<>());
                }
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
                case "getAllTopics":

                    objoutput.writeUTF("getAllTopics");
                    objoutput.flush();

                    ArrayList<String> arr = new ArrayList<>();

                    for (String key:EventManager.topicSubscriber.keySet()) {
                        arr.add(key);
                    }

                    objoutput.writeObject(arr);
                    objoutput.flush();

                    break;


                case "subscribeTopics":

                    String receivedUserName = objinput.readUTF();

                    while (true)
                    {
                        // receive topics break when topicname == 3511
                        try {
                            Object obj = objinput.readObject();
                            Topic topic = (Topic)obj;

                            if(topic.getName().equals("3511"))
                                break;
                         // adding subscriber to topicsubsriber

                            ArrayList<String> temp =  EventManager.topicSubscriber.get(topic.getName());
                            HashSet<String> isUserPresent = new HashSet<>(temp);

                            if(!isUserPresent.contains(receivedUserName)) {
                                 temp.add(receivedUserName);
                                 EventManager.topicSubscriber.put(topic.getName(), temp);

                                 //adding topic to the subscriber topic
                                 // check if the topic is already present or not
                                 ArrayList<String> topicTemp = EventManager.subscriberTopics.get(receivedUserName);
                                 topicTemp.add(topic.getName());
                                 EventManager.subscriberTopics.put(receivedUserName, topicTemp);

                                System.out.println(receivedUserName+" is subscribed to topic: "+topic.getName());
                                System.out.println(EventManager.subscriberTopics);
                            }


                        }
                        catch (NullPointerException e)
                        {
                            System.out.println("Key not found invalid spelling!!!");
                        }
                        catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }

                    break;

                case "getSubscribedTopics":

                    this.objoutput.writeUTF("getSubscribedTopics");
                    this.objoutput.flush();

                    ArrayList<String> temp = EventManager.subscriberTopics.get(this.user_name);
                    System.out.println("Sent to " + user_name + "->" + temp);

                    this.objoutput.writeObject(temp);
                    this.objoutput.flush();

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

    public void sendAdvertisement(Topic newtopic) throws IOException {

            this.objoutput.writeUTF("Topic");
            this.objoutput.flush();

            this.objoutput.writeObject(newtopic);
            this.objoutput.flush();

            System.out.println("Topic Advertised:"+ newtopic.getName());



    }
}
