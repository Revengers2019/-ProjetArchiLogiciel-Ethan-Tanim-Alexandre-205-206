package modele;

import exceptions.EmpruntException;
import exceptions.ReservationException;
import exceptions.RetourException;

public interface Document {
    String idDoc();
    void reservation(Abonne ab) throws ReservationException;
    void emprunt(Abonne ab) throws EmpruntException;
    void retour() throws RetourException;
}
