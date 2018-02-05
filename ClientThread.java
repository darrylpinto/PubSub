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

                    objoutput.reset();
                    objoutput.writeObject(EventManager.offlineTopics.get(user_name));
                    objoutput.flush();

                    objoutput.reset();
                    objoutput.writeObject(EventManager.offlineEvents.get(user_name));
                    objoutput.flush();

                    EventManager.offlineTopics.put(user_name, new ArrayList<>());
                    EventManager.offlineEvents.put(user_name, new ArrayList<>());


                } else {
                    EventManager.subscriberTopics.put(user_name, new ArrayList<>());
                    objoutput.writeUTF("You are Registered:" + user_name);
                    objoutput.flush();

                    EventManager.offlineTopics.put(user_name, new ArrayList<>());
                    EventManager.offlineEvents.put(user_name, new ArrayList<>());

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

                    objoutput.reset();
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
                    break;


                case "unsubscribeTopic":

                    String userNametoUnsubscribe = objinput.readUTF();

                    while (true)
                    {
                        // receive topic name = 3512 to break the loop
                        try {

                            // receive topic name = 3512 to break the loop

                            Object obj =objinput.readObject();
                            Topic topic = (Topic)obj;

                            if(topic.getName().equals("3512"))
                                break;

                            //removed from the subscrbed topic list of user
                           // System.out.println("SUBSCRIBER TOPIC (before): "+ EventManager.subscriberTopics);
                            ArrayList<String> temparr = EventManager.subscriberTopics.get(userNametoUnsubscribe);
                            temparr.remove(topic.getName());
                            EventManager.subscriberTopics.put(userNametoUnsubscribe, temparr);
                           // System.out.println("SUBSCRIBER TOPIC(after): "+ EventManager.subscriberTopics);

                            // removed from the list of subscriber for that specific topic
                          //  System.out.println("TOPIC SUBSCRIBER (before): "+ EventManager.topicSubscriber);
                            temparr = EventManager.topicSubscriber.get(topic.getName());
                            temparr.remove(userNametoUnsubscribe);
                            EventManager.topicSubscriber.put(topic.getName(), temparr);
                         //   System.out.println("TOPIC SUBSCRIBER (after): "+ EventManager.topicSubscriber);

                            System.out.println(userNametoUnsubscribe+ " has unsubsribed from the topic: "+topic.getName());


                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        //error to be caught when topic name is not present
                        catch(NullPointerException e)
                        {
                            System.out.println("Error : Invalid spelling !");
                        }

                    }


                    break;

                case "unsubscribeAll":
                     String usernameAll = objinput.readUTF();

                     //removed all topics from subsriber topic
                     ArrayList<String> temptopics = EventManager.subscriberTopics.get(usernameAll);
                     EventManager.subscriberTopics.put(usernameAll,new ArrayList<>());

                     // removed one by one from topic subscriber
                    for (String s:temptopics) {
                      ArrayList<String> temptopiclist =  EventManager.topicSubscriber.get(s);
                      temptopiclist.remove(usernameAll);
                      EventManager.topicSubscriber.put(s,temptopiclist);

                      System.out.println(usernameAll+ " has unsubsribed from the topic: "+s);
                    }

                    break;


                case "Event":
                    try {
                        Object obj = objinput.readObject();
                        Event event = (Event)obj;
                        EventManager.notifySubscribers(event);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    break;



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

    public void sendEvent(Event event) throws IOException {
        this.objoutput.writeUTF("Event");
        this.objoutput.flush();

        this.objoutput.writeObject(event);
        this.objoutput.flush();

        System.out.println("Event Published:"+ event.getTitle());
    }
}
