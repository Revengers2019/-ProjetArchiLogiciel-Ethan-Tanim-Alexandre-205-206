package modele;

public class Livre extends Doc {
    private final int pages;

    public Livre(String id, String titre, int pages) {
        super(id, titre);
        this.pages = pages;
    }

    public int getPages() { return pages; }

    @Override
    public String toString() { return "Livre " + super.toString() + " - " + pages + "p"; }
}
