package serveur;

import bttp2.Serveur;

public class AppServeur {
    public static void main(String[] args) {
        System.out.println("=== Mediatheque - Serveur ===");
        new Thread(new Serveur(2000, "Reservation", ServiceReservation::new)).start();
        new Thread(new Serveur(2001, "Emprunt",     ServiceEmprunt::new)).start();
        new Thread(new Serveur(2002, "Retour",      ServiceRetour::new)).start();
        System.out.println("Ports : 2000 (reservation) | 2001 (emprunt) | 2002 (retour)");
    }
}
