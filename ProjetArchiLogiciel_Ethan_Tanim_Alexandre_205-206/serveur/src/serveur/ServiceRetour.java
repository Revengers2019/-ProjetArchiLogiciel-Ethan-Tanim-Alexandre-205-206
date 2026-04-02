package serveur;

import bttp2.Ecoute;
import bttp2.Reponse;
import bttp2.Requete;
import exceptions.RetourException;
import modele.Doc;
import modele.Mediatheque;

import java.net.Socket;

public class ServiceRetour implements Runnable {
    private final Socket socket;

    public ServiceRetour(Socket socket) { this.socket = socket; }

    @Override
    public void run() {
        Ecoute ecoute = new Ecoute(socket);
        try {
            Requete req = ecoute.lire();
            if (req == null || req.getParams().length < 1) {
                ecoute.repondre(new Reponse(Reponse.KO, "Syntaxe : RETOUR <idDoc> [DEGRADE]"));
                return;
            }

            String idDoc   = req.getParams()[0];
            boolean degrade = req.getParams().length >= 2
                && req.getParams()[1].equalsIgnoreCase("DEGRADE");

            Mediatheque media = Mediatheque.getInstance();
            Doc doc = media.trouverDoc(idDoc);

            if (doc == null) { ecoute.repondre(new Reponse(Reponse.KO, "Document " + idDoc + " introuvable.")); return; }

            try {
                if (degrade) {
                    doc.retourDegrade();
                    ecoute.repondre(new Reponse(Reponse.OK, "Retour de \"" + doc.getTitre() + "\" enregistre. Abonne banni 1 mois."));
                } else {
                    doc.retour();
                    ecoute.repondre(new Reponse(Reponse.OK, "Retour de \"" + doc.getTitre() + "\" enregistre. Merci !"));
                }
            } catch (RetourException e) {
                ecoute.repondre(new Reponse(Reponse.KO, e.getMessage()));
            }
        } catch (Exception e) {
            System.err.println("[Retour] " + e.getMessage());
        } finally {
            ecoute.fermer();
        }
    }
}
