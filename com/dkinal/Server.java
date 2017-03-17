package com.dkinal;

import java.io.*;
import java.net.Socket;

public class Server implements Runnable {

    private Socket socket;

    Server(Socket s) {
        socket = s;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) {

            String filename = in.readUTF();

            try(FileOutputStream out = new FileOutputStream("/home/dkinal/" + filename)) {
                byte[] buffer = new byte[4096];
                int readSize;

                while ((readSize = in.read(buffer)) != -1) {
                    out.write(buffer, 0, readSize);
                }
            }
            System.out.println("File downloaded: " + filename);
        } catch (IOException ex) {
            System.out.println("Socket Read exception" + ex.getMessage());

        } finally {
            try {
                socket.close();

            } catch (IOException ex) {
                System.out.println("SocketClose exception" + ex.getMessage());
            }
        }
    }
}
