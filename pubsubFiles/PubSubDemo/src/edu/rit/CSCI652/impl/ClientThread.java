package edu.rit.CSCI652.impl;

import edu.rit.CSCI652.demo.Topic;

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
    private static ConcurrentHashMap<String, ArrayList<Topic>> subscriberTopic = new ConcurrentHashMap<>();

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


                if (subscriberTopic.contains(user_name)) {
                    output.writeUTF("Logged In:" + user_name);
                } else {
                    subscriberTopic.put(user_name, new ArrayList<Topic>());
                    output.writeUTF("You are Registered:" + user_name);
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }

    }
}
