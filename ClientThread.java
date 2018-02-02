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
        this.output = new DataOutputStream(client.getOutputStream());
        this.input = new DataInputStream(client.getInputStream());
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
            System.out.println(user_name);
            try {

                if (EventManager.subscriberTopic.containsKey(user_name)) {
                    output.writeUTF("Logged In:" + user_name);
                    System.out.println(EventManager.subscriberTopic);

                } else {
                    EventManager.subscriberTopic.put(user_name, new ArrayList<Topic>());
                    output.writeUTF("You are Registered:" + user_name);
                    System.out.println(EventManager.subscriberTopic);

                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

    }
}
