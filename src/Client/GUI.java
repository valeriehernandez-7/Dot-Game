package Client;

import Common.*;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GUI implements ActionListener, Constantes {

    JFrame ventana;
    Mapa mapa;
    Dot dot;
    Target target;
    Socket client;
    ObjectOutputStream output;

    public GUI() {

        ventana = new JFrame();
        ventana.setTitle("Dot Game - Client");

        mapa = new Mapa(this);
        ventana.add(mapa.panelTablero);

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);

        dot = new Dot();
        target = new Target();

        Server server = new Server(dot);
        Thread hilo = new Thread(server);
        hilo.start();

        run();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        mapa.tablero[target.coords[X]][target.coords[Y]].clearTarget();
        ((Casilla) event.getSource()).setAsTarget();
        target.coords = ((Casilla) event.getSource()).getCoords();

        try {
            client = new Socket("127.0.0.1", 4444);
            output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(target);
            output.flush();
            output.close();
            client.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void moveDot() {
        mapa.tablero[dot.lastPosition[X]][dot.lastPosition[Y]].clearDot();
        mapa.tablero[dot.currentPosition[X]][dot.currentPosition[Y]].setAsDot();
    }

    public void run() {
        while (true) {
            moveDot();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}