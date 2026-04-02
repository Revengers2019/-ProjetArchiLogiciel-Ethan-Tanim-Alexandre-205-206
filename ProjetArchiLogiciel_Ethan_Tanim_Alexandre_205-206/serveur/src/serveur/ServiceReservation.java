package serveur;

import bttp2.Ecoute;
import bttp2.Reponse;
import bttp2.Requete;
import exceptions.ReservationException;
import modele.Abonne;
import modele.Doc;
import modele.Mediatheque;

import java.net.Socket;
import java.time.format.DateTimeFormatter;

public class ServiceReservation implements Runnable {
    private final Socket socket;

    public ServiceReservation(Socket socket) { this.socket = socket; }

    @Override
    public void run() {
        Ecoute ecoute = new Ecoute(socket);
        try {
            Requete req = ecoute.lire();
            if (req == null || req.getParams().length < 2) {
                ecoute.repondre(new Reponse(Reponse.KO, "Syntaxe : RESERVE <numAbonne> <idDoc>"));
                return;
            }

            int numAb;
            try { numAb = Integer.parseInt(req.getParams()[0]); }
            catch (NumberFormatException e) {
                ecoute.repondre(new Reponse(Reponse.KO, "Numero abonne invalide."));
                return;
            }

            String idDoc = req.getParams()[1];
            Mediatheque media = Mediatheque.getInstance();
            Abonne ab  = media.trouverAbonne(numAb);
            Doc    doc = media.trouverDoc(idDoc);

            if (ab  == null) { ecoute.repondre(new Reponse(Reponse.KO, "Abonne n" + numAb + " introuvable.")); return; }
            if (doc == null) { ecoute.repondre(new Reponse(Reponse.KO, "Document " + idDoc + " introuvable.")); return; }

            if (doc.getEtat() == Doc.Etat.RESERVE) {
                long sec = doc.secondesRestantes();
                if (sec > 0 && sec <= 60) {
                    ecoute.repondre(new Reponse(Reponse.INFO, "Patientez " + sec + "s, tentative en cours..."));
                    try { Thread.sleep(sec * 1000 + 500); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
                }
            }

            try {
                doc.reservation(ab);
                ecoute.repondre(new Reponse(Reponse.OK,
                    "Reservation confirmee pour \"" + doc.getTitre() + "\" jusqu'a "
                    + doc.getFinReservation().format(DateTimeFormatter.ofPattern("HH'h'mm")) + "."));
            } catch (ReservationException e) {
                ecoute.repondre(new Reponse(Reponse.KO, e.getMessage()));
            }
        } catch (Exception e) {
            System.err.println("[Reservation] " + e.getMessage());
        } finally {
            ecoute.fermer();
        }
    }
}
