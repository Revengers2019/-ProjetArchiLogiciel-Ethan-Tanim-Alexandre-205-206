package modele;

import exceptions.EmpruntException;
import exceptions.ReservationException;
import exceptions.RetourException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Doc implements Document {

    public enum Etat { DISPONIBLE, RESERVE, EMPRUNTE }

    private final String id;
    private final String titre;
    private Etat etat = Etat.DISPONIBLE;
    private Abonne abonne;
    private LocalDateTime finReservation;
    private LocalDateTime dateEmprunt;

    protected Doc(String id, String titre) {
        this.id    = id;
        this.titre = titre;
    }

    @Override
    public String idDoc() { return id; }
    public String getTitre() { return titre; }

    public Etat getEtat() {
        if (etat == Etat.RESERVE && LocalDateTime.now().isAfter(finReservation)) {
            etat            = Etat.DISPONIBLE;
            abonne          = null;
            finReservation  = null;
            System.out.println("[AUTO] Reservation expiree : " + id);
        }
        return etat;
    }

    public Abonne getAbonne()              { return abonne; }
    public LocalDateTime getFinReservation() { return finReservation; }

    public long secondesRestantes() {
        if (etat != Etat.RESERVE || finReservation == null) return 0;
        return Math.max(0, java.time.Duration.between(LocalDateTime.now(), finReservation).getSeconds());
    }

    @Override
    public synchronized void reservation(Abonne ab) throws ReservationException {
        if (ab.estBanni())
            throw new ReservationException("Vous etes banni jusqu'au " + ab.getDateBannissement() + ".");
        Etat e = getEtat();
        if (e == Etat.EMPRUNTE)
            throw new ReservationException("Le document \"" + titre + "\" est deja emprunte.");
        if (e == Etat.RESERVE)
            throw new ReservationException("Le document \"" + titre + "\" est reserve jusqu'a "
                + finReservation.format(DateTimeFormatter.ofPattern("HH'h'mm")) + ".");
        checkReservation(ab);
        etat           = Etat.RESERVE;
        abonne         = ab;
        finReservation = LocalDateTime.now().plusHours(2);
        System.out.println("[OK] Reservation " + titre + " -> " + ab);
    }

    @Override
    public synchronized void emprunt(Abonne ab) throws EmpruntException {
        if (ab.estBanni())
            throw new EmpruntException("Vous etes banni jusqu'au " + ab.getDateBannissement() + ".");
        Etat e = getEtat();
        if (e == Etat.EMPRUNTE)
            throw new EmpruntException("Le document \"" + titre + "\" est deja emprunte.");
        if (e == Etat.RESERVE && !abonne.equals(ab))
            throw new EmpruntException("Le document \"" + titre + "\" est reserve pour un autre abonne jusqu'a "
                + finReservation.format(DateTimeFormatter.ofPattern("HH'h'mm")) + ".");
        checkEmprunt(ab);
        etat          = Etat.EMPRUNTE;
        abonne        = ab;
        finReservation = null;
        dateEmprunt   = LocalDateTime.now();
        System.out.println("[OK] Emprunt " + titre + " -> " + ab);
    }

    @Override
    public synchronized void retour() throws RetourException {
        if (etat == Etat.DISPONIBLE)
            throw new RetourException("Le document \"" + titre + "\" est deja disponible.");
        if (etat == Etat.EMPRUNTE && dateEmprunt != null) {
            long jours = java.time.Duration.between(dateEmprunt, LocalDateTime.now()).toDays();
            if (jours > 14 && abonne != null) {
                System.out.println("[GERONIMO] Retard " + jours + "j -> " + abonne);
                abonne.bannir();
            }
        }
        etat          = Etat.DISPONIBLE;
        abonne        = null;
        finReservation = null;
        dateEmprunt   = null;
        System.out.println("[OK] Retour " + titre);
    }

    public synchronized void retourDegrade() throws RetourException {
        if (etat == Etat.DISPONIBLE)
            throw new RetourException("Le document \"" + titre + "\" est deja disponible.");
        if (abonne != null) {
            System.out.println("[GERONIMO] Degradation -> " + abonne);
            abonne.bannir();
        }
        retour();
    }

    protected void checkReservation(Abonne ab) throws ReservationException {}
    protected void checkEmprunt(Abonne ab) throws EmpruntException {}

    @Override
    public String toString() { return "[" + id + "] " + titre + " (" + getEtat() + ")"; }
}
