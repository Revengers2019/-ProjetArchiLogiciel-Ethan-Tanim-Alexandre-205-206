package modele;

import exceptions.EmpruntException;
import exceptions.ReservationException;

public class DVD extends Doc {
    private final boolean adulte;

    public DVD(String id, String titre, boolean adulte) {
        super(id, titre);
        this.adulte = adulte;
    }

    public boolean isAdulte() { return adulte; }

    @Override
    protected void checkReservation(Abonne ab) throws ReservationException {
        if (adulte && ab.getAge() < 16)
            throw new ReservationException("Vous devez avoir au moins 16 ans pour reserver ce DVD. Age : " + ab.getAge());
    }

    @Override
    protected void checkEmprunt(Abonne ab) throws EmpruntException {
        if (adulte && ab.getAge() < 16)
            throw new EmpruntException("Vous devez avoir au moins 16 ans pour emprunter ce DVD. Age : " + ab.getAge());
    }

    @Override
    public String toString() { return "DVD " + super.toString() + (adulte ? " [+16]" : " [Tout public]"); }
}
