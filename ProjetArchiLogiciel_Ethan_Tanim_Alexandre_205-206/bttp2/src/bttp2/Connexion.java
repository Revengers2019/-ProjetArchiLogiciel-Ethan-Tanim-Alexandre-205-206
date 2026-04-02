package bttp2;

import java.io.*;
import java.net.Socket;

public class Connexion {
    private final String hote;
    private final int port;

    public Connexion(String hote, int port) {
        this.hote = hote;
        this.port = port;
    }

    public Reponse envoyer(Requete req) throws IOException {
        try (Socket socket = new Socket(hote, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println(req.format());
            return Reponse.parse(in.readLine());
        }
    }
}
