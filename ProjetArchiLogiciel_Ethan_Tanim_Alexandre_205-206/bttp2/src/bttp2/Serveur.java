package bttp2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Function;

public class Serveur implements Runnable {
    private final int port;
    private final String nom;
    private final Function<Socket, Runnable> factory;

    public Serveur(int port, String nom, Function<Socket, Runnable> factory) {
        this.port    = port;
        this.nom     = nom;
        this.factory = factory;
    }

    @Override
    public void run() {
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("[" + nom + "] port " + port);
            while (!Thread.currentThread().isInterrupted()) {
                Socket client = ss.accept();
                new Thread(factory.apply(client)).start();
            }
        } catch (IOException e) {
            System.err.println("[" + nom + "] " + e.getMessage());
        }
    }
}
