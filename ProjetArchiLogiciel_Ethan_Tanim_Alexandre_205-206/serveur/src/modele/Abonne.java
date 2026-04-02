package modele;

import java.time.LocalDate;
import java.time.Period;

public class Abonne {
    private final int numero;
    private final String nom;
    private final LocalDate dateNaissance;
    private LocalDate dateBannissement;

    public Abonne(int numero, String nom, LocalDate dateNaissance) {
        this.numero        = numero;
        this.nom           = nom;
        this.dateNaissance = dateNaissance;
    }

    public int getNumero()              { return numero; }
    public String getNom()              { return nom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public LocalDate getDateBannissement() { return dateBannissement; }

    public int getAge() {
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    public void bannir() {
        this.dateBannissement = LocalDate.now().plusMonths(1);
        System.out.println(nom + " banni jusqu'au " + dateBannissement);
    }

    public boolean estBanni() {
        if (dateBannissement == null) return false;
        if (LocalDate.now().isAfter(dateBannissement)) {
            dateBannissement = null;
            return false;
        }
        return true;
    }

    @Override
    public String toString() { return "Abonne n" + numero + " (" + nom + ")"; }
}
