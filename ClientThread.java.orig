import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ClientThread implements Runnable {

    private ServerSocket serverSocket;
    private  Socket client;
    private  DataOutputStream output;
    private  DataInputStream input;

    // ServerSocket is serverSocket on portThread
    public ClientThread(Socket client, ServerSocket serverSocket) throws IOException {
        this.client = client;
        this.serverSocket = serverSocket ;
        this.output = new DataOutputStream(this.client.getOutputStream());
        this.input = new DataInputStream(this.client.getInputStream());
    }

    @Override
    public void run() {

        synchronized (serverSocket) {
            String user_name = null;
            try {
                user_name = input.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //** Add it in a hashmap
<<<<<<< HEAD
            System.out.println(user_name);
            try {

                if (EventManager.subscriberTopic.containsKey(user_name)) {
                    output.writeUTF("Logged In:" + user_name);
                    System.out.println(EventManager.subscriberTopic);

                } else {
                    EventManager.subscriberTopic.put(user_name, new ArrayList<Topic>());
                    output.writeUTF("You are Registered:" + user_name);
                    System.out.println(EventManager.subscriberTopic);

=======
            //System.out.println(user_name);
            try
            {
                if (EventManager.subscriberTopics.containsKey(user_name)) {
                    output.writeUTF("Logged In:" + user_name);
                    output.flush();
                } else {
                    EventManager.subscriberTopics.put(user_name, new ArrayList<Topic>());
                    output.writeUTF("You are Registered:" + user_name);
                    output.flush();
>>>>>>> d781b6cf50671d44f54206010d12f32fc0db6a9f
                }
                commnicate(input,output);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

    }

    private void commnicate(DataInputStream input, DataOutputStream output) throws IOException {

        while (true)
        {
            String input_string = input.readUTF();

            switch (input_string)
            {
                case "get topic":
                    output.writeUTF("topiclist");


            }
        }



    }
}
