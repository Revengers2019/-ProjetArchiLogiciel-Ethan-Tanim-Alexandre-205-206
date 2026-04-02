package client;

import bttp2.Connexion;
import bttp2.Reponse;
import bttp2.Requete;

import java.util.Scanner;

public class ClientRetour {
    public static void main(String[] args) throws Exception {
        String hote = args.length >= 1 ? args[0] : "localhost";
        int port    = args.length >= 2 ? Integer.parseInt(args[1]) : 2002;
        Scanner sc  = new Scanner(System.in);

        System.out.println("=== Retour Mediatheque ===");
        System.out.print("ID document   : ");
        String id  = sc.nextLine().trim();
        System.out.print("Degrade ? (o/n) : ");
        String deg = sc.nextLine().trim();
        boolean degrade = deg.equalsIgnoreCase("o");

        Connexion co  = new Connexion(hote, port);
        Reponse   rep = co.envoyer(new Requete("RETOUR", degrade ? new String[]{id, "DEGRADE"} : new String[]{id}));
        System.out.println("Reponse : " + rep.format());
        sc.close();
    }
}
