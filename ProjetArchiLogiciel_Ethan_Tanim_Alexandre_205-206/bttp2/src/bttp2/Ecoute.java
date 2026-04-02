package bttp2;

import java.io.*;
import java.net.Socket;

public class Ecoute {
    private final Socket socket;

    public Ecoute(Socket socket) {
        this.socket = socket;
    }

    public Requete lire() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return Requete.parse(in.readLine());
    }

    public void repondre(Reponse rep) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(rep.format());
    }

    public void fermer() {
        try { socket.close(); } catch (IOException ignored) {}
    }
}
