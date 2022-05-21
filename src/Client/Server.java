package Client;

import Common.Dot;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    ServerSocket server;
    Socket client;
    ObjectInputStream input;
    Dot dot;

    public Server(Dot d) {
        dot = d;
        try {
            server = new ServerSocket(4488);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        try {
            while (true) {
                client = server.accept();
                input = new ObjectInputStream(client.getInputStream());
                dot.lastPosition = dot.currentPosition;
                Dot serverDot = (Dot) input.readObject();
                dot.currentPosition = serverDot.currentPosition;
                dot.lastPosition = serverDot.lastPosition;
                input.close();
                client.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}