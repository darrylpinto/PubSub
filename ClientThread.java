import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ClientThread implements Runnable {

    private ServerSocket serverSocket;
    private  Socket client;
   // private  DataOutputStream output;
    //private  DataInputStream input;
    private ObjectInputStream objinput;
    private ObjectOutputStream objoutput;

    // ServerSocket is serverSocket on portThread
    public ClientThread(Socket client, ServerSocket serverSocket) throws IOException {

        this.client = client;
        this.serverSocket = serverSocket ;
        //this.output = new DataOutputStream(this.client.getOutputStream());
       // this.input = new DataInputStream(this.client.getInputStream());
        this.objinput = new ObjectInputStream(this.client.getInputStream());
        this.objoutput = new ObjectOutputStream(this.client.getOutputStream());

    }

    @Override
    public void run() {

        synchronized (serverSocket) {
            String user_name = null;
            try {

                user_name = objinput.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //** Add it in a hashmap
            System.out.println(user_name);

            try
            {
                if (EventManager.subscriberTopics.containsKey(user_name)) {

                    objoutput.writeUTF("Logged In:" + user_name);
                    //System.out.println(EventManager.subscriberTopics);

                } else {
                    EventManager.subscriberTopics.put(user_name, new ArrayList<Topic>());
                    objoutput.writeUTF("You are Registered:" + user_name);
                    objoutput.flush();
                    //System.out.println(EventManager.subscriberTopics);
                }
            //System.out.println(user_name);

                communicate(objinput,objoutput);
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }

    }

    private void communicate(ObjectInputStream input, ObjectOutputStream output) throws IOException {

        while (true)
        {

            String input_string = input.readUTF();


            switch (input_string)
            {
                case "getTopics":
                    //output.writeUTF("topiclist");

                case "advertise":
                    try {
                        System.out.println("*inside advertise");
                        Object obj = objinput.readObject();
                        Topic newtopic = (Topic) obj;
                        System.out.println(newtopic);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }


            }
        }



    }
}
