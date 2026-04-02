package client;

import bttp2.Connexion;
import bttp2.Reponse;
import bttp2.Requete;

import java.util.Scanner;

public class ClientEmprunt {
    public static void main(String[] args) throws Exception {
        String hote = args.length >= 1 ? args[0] : "localhost";
        int port    = args.length >= 2 ? Integer.parseInt(args[1]) : 2001;
        Scanner sc  = new Scanner(System.in);

        System.out.println("=== Emprunt Mediatheque ===");
        System.out.print("Numero abonne : ");
        String num = sc.nextLine().trim();
        System.out.print("ID document   : ");
        String id  = sc.nextLine().trim();

        Connexion co  = new Connexion(hote, port);
        Reponse   rep = co.envoyer(new Requete("EMPRUNTE", num, id));
        System.out.println("Reponse : " + rep.format());
        sc.close();
    }
}
