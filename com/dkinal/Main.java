package com.dkinal;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Main {
    public static void main(String[] args) {
        Runnable server = () -> {
            try (ServerSocket socket = new ServerSocket(27013)) {
                System.out.println("Server started. Waiting for clients...");
                while(true) {
                    try {
                        Socket client = socket.accept();
                        if (client != null) {
                            System.out.println("Client connected");
                            Server receiver = new Server(client);
                            Thread thread = new Thread(receiver);
                            thread.start();
                        }
                    } catch (SocketTimeoutException ex) {
                        System.out.println("com.dkinal.Server timeout");
                    }
                }
            } catch (IOException ex) {
                System.out.println("com.dkinal.Server exception " + ex.getMessage());
            }
        };

        new Thread(server).start();
    }
}
