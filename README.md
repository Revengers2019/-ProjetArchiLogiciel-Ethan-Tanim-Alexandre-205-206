#  Médiathèque — Réservation / Emprunt / Retour

> **Ethan / Tanim / Alexandre — Groupes 205-206**  
> R4.01 Architecture Logicielle — BUT2 FI — 2025/2026

---

##  Architecture

| Projet | Rôle |
|--------|------|
| `bttp2.jar` | Bibliothèque réseau — importée par serveur et client |
| `serveur` | Logique métier + 3 services (ports 2000, 2001, 2002) |
| `client` | 3 clients (réservation, emprunt, retour) |

```
ProjetArchiLogiciel_Ethan_Tanim_Alexandre_205-206/
│
├── bttp2/
│   └── src/bttp2/
│       ├── Requete.java
│       ├── Reponse.java
│       ├── Connexion.java
│       ├── Ecoute.java
│       └── Serveur.java
│
├── serveur/
│   └── src/
│       ├── exceptions/
│       │   ├── ReservationException.java
│       │   ├── EmpruntException.java
│       │   └── RetourException.java
│       ├── modele/
│       │   ├── Document.java
│       │   ├── Doc.java
│       │   ├── Livre.java
│       │   ├── DVD.java
│       │   ├── Abonne.java
│       │   └── Mediatheque.java
│       └── serveur/
│           ├── ServiceReservation.java
│           ├── ServiceEmprunt.java
│           ├── ServiceRetour.java
│           └── AppServeur.java
│
├── client/
│   └── src/client/
│       ├── ClientReservation.java
│       ├── ClientEmprunt.java
│       └── ClientRetour.java
│
├── libs/
│   └── bttp2.jar
├── tests.ps1
└── README.md
```

---

##  Compilation

### 1. bttp2 → jar
```powershell
cd bttp2
mkdir out
javac -d out src/bttp2/*.java
jar cf ../libs/bttp2.jar -C out .
cd ..
```

### 2. Serveur
```powershell
cd serveur
mkdir out
javac -cp ../libs/bttp2.jar -d out -sourcepath src src/exceptions/*.java src/modele/*.java src/serveur/*.java
cd ..
```

### 3. Client
```powershell
cd client
mkdir out
javac -cp ../libs/bttp2.jar -d out -sourcepath src src/client/*.java
cd ..
```

---

##  Lancement

**Terminal 1 — serveur (ne pas fermer) :**
```powershell
java -cp "serveur/out;libs/bttp2.jar" serveur.AppServeur
```

**Terminal 2 — clients :**
```powershell
java -cp "client/out;libs/bttp2.jar" client.ClientReservation
java -cp "client/out;libs/bttp2.jar" client.ClientEmprunt
java -cp "client/out;libs/bttp2.jar" client.ClientRetour
```

---

##  Tests automatiques

```powershell
.\tests.ps1
```

---

## 🔌 Ports

| Port | Service |
|------|---------|
| `2000` | Réservation |
| `2001` | Emprunt |
| `2002` | Retour |
