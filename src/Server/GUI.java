package Server;

import Common.*;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener, Constantes {

    JFrame ventana;
    JButton next;
    Mapa mapa;
    Dot dot;

    public GUI() {

        ventana = new JFrame();
        ventana.setTitle("Dot Game - Server");

        mapa = new Mapa(this);
        ventana.add(mapa.panelTablero);

        next = new JButton("CONTINUE");
        next.addActionListener(this);
        next.setActionCommand("next");

        ventana.add(next, BorderLayout.SOUTH);

        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);

        dot = new Dot();

        Server server = new Server(dot);
        Thread hilo = new Thread(server);
        hilo.start();

        moveDot();
        run();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public void moveDot() {
        mapa.tablero[dot.lastPosition[X]][dot.lastPosition[Y]].clearDot();
        mapa.tablero[dot.currentPosition[X]][dot.currentPosition[Y]].setAsDot();
    }

    public void run() {
        while (true) {
            dot.move();
            moveDot();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
    }
}