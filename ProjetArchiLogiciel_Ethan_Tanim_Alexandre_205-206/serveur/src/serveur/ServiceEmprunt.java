package serveur;

import bttp2.Ecoute;
import bttp2.Reponse;
import bttp2.Requete;
import exceptions.EmpruntException;
import modele.Abonne;
import modele.Doc;
import modele.Mediatheque;

import java.net.Socket;

public class ServiceEmprunt implements Runnable {
    private final Socket socket;

    public ServiceEmprunt(Socket socket) { this.socket = socket; }

    @Override
    public void run() {
        Ecoute ecoute = new Ecoute(socket);
        try {
            Requete req = ecoute.lire();
            if (req == null || req.getParams().length < 2) {
                ecoute.repondre(new Reponse(Reponse.KO, "Syntaxe : EMPRUNTE <numAbonne> <idDoc>"));
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

            try {
                doc.emprunt(ab);
                ecoute.repondre(new Reponse(Reponse.OK, "Emprunt confirme : \"" + doc.getTitre() + "\" par " + ab.getNom() + "."));
            } catch (EmpruntException e) {
                ecoute.repondre(new Reponse(Reponse.KO, e.getMessage()));
            }
        } catch (Exception e) {
            System.err.println("[Emprunt] " + e.getMessage());
        } finally {
            ecoute.fermer();
        }
    }
}
