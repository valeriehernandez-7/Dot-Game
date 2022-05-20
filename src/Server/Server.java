package Server;

import Common.Dot;
import Common.Target;

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
            server = new ServerSocket(4444);
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public void run() {
        try {
            while (true) {
                client = server.accept();
                input = new ObjectInputStream(client.getInputStream());
                dot.target = (Target) input.readObject();
                input.close();
                client.close();
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}