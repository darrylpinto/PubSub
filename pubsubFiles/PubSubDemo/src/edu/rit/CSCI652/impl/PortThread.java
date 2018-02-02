package edu.rit.CSCI652.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PortThread implements Runnable {

    ServerSocket mysocket;

    public PortThread(ServerSocket mysocket)
    {
        this.mysocket = mysocket;

    }

    @Override
    public void run() {

        Listen();

    }

    public void Listen()
    {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        while (true) {
            try {
                Socket newClient = mysocket.accept();
                threadPool.execute(new ClientThread(newClient,mysocket));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
