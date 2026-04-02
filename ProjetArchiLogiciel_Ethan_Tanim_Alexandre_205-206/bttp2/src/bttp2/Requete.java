package bttp2;

public class Requete {
    private final String methode;
    private final String[] params;

    public Requete(String methode, String... params) {
        this.methode = methode;
        this.params = params;
    }

    public String getMethode() { return methode; }
    public String[] getParams() { return params; }

    public String format() {
        StringBuilder sb = new StringBuilder(methode);
        for (String p : params) sb.append(" ").append(p);
        return sb.toString();
    }

    public static Requete parse(String ligne) {
        if (ligne == null || ligne.isBlank()) return null;
        String[] parts = ligne.trim().split("\\s+");
        String[] params = new String[parts.length - 1];
        System.arraycopy(parts, 1, params, 0, params.length);
        return new Requete(parts[0].toUpperCase(), params);
    }
}
