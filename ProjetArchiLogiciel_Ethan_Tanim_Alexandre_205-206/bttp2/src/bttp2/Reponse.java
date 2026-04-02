package bttp2;

public class Reponse {
    public static final String OK  = "OK";
    public static final String KO  = "KO";
    public static final String INFO = "INFO";

    private final String statut;
    private final String message;

    public Reponse(String statut, String message) {
        this.statut  = statut;
        this.message = message;
    }

    public String getStatut()  { return statut; }
    public String getMessage() { return message; }
    public boolean isOk()      { return OK.equals(statut); }

    public String format() { return statut + " " + message; }

    public static Reponse parse(String ligne) {
        if (ligne == null || ligne.isBlank()) return new Reponse(KO, "Reponse vide");
        int idx = ligne.indexOf(' ');
        if (idx < 0) return new Reponse(ligne.trim(), "");
        return new Reponse(ligne.substring(0, idx), ligne.substring(idx + 1));
    }
}
