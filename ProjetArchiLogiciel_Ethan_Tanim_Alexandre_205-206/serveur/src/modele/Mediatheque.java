package modele;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Mediatheque {

    private static Mediatheque instance;
    private final List<Abonne> abonnes  = new ArrayList<>();
    private final List<Doc>    documents = new ArrayList<>();

    private Mediatheque() {
        abonnes.add(new Abonne(1, "Dupont Alice",  LocalDate.of(1990, 3, 15)));
        abonnes.add(new Abonne(2, "Martin Bob",    LocalDate.of(2010, 7, 22)));
        abonnes.add(new Abonne(3, "Durand Claire", LocalDate.of(1985, 11, 5)));
        abonnes.add(new Abonne(4, "Bernard David", LocalDate.of(2000, 1, 30)));
        abonnes.add(new Abonne(5, "Petit Emma",    LocalDate.of(1995, 6, 18)));

        documents.add(new Livre("L001", "Le Seigneur des Anneaux", 1200));
        documents.add(new Livre("L002", "Harry Potter", 320));
        documents.add(new Livre("L003", "Dune", 896));
        documents.add(new Livre("L004", "1984", 380));
        documents.add(new DVD("D001", "Inception",   false));
        documents.add(new DVD("D002", "Pulp Fiction", true));
        documents.add(new DVD("D003", "Le Roi Lion",  false));
        documents.add(new DVD("D004", "Alien",        true));
        documents.add(new DVD("D005", "Avatar",       false));
    }

    public static synchronized Mediatheque getInstance() {
        if (instance == null) instance = new Mediatheque();
        return instance;
    }

    public Abonne trouverAbonne(int numero) {
        return abonnes.stream().filter(a -> a.getNumero() == numero).findFirst().orElse(null);
    }

    public Doc trouverDoc(String id) {
        return documents.stream().filter(d -> d.idDoc().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public List<Abonne> getAbonnes()   { return abonnes; }
    public List<Doc>    getDocuments() { return documents; }
}
