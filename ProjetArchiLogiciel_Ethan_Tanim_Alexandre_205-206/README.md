# Mediatheque — ProjetArchiLogiciel
Ethan / Tanim / Alexandre — Groupes 205-206

---

## 3 projets

- **bttp2** : bibliothèque réseau (Requete, Reponse, Connexion, Ecoute, Serveur)
- **serveur** : logique métier + 3 services (ports 2000, 2001, 2002)
- **client** : 3 clients (réservation, emprunt, retour)

---

## Compilation

### 1. bttp2 → jar
```
cd bttp2
mkdir out
javac -d out src/bttp2/*.java
jar cf ../libs/bttp2.jar -C out .
```

### 2. Serveur
```
cd serveur
mkdir out
javac -cp ../libs/bttp2.jar -d out -sourcepath src src/exceptions/*.java src/modele/*.java src/serveur/*.java
```

### 3. Client
```
cd client
mkdir out
javac -cp ../libs/bttp2.jar -d out -sourcepath src src/client/*.java
```

---

## Lancement

**Terminal 1 — serveur :**
```
java -cp serveur/out;libs/bttp2.jar serveur.AppServeur
```

**Terminal 2 — clients :**
```
java -cp client/out;libs/bttp2.jar client.ClientReservation
java -cp client/out;libs/bttp2.jar client.ClientEmprunt
java -cp client/out;libs/bttp2.jar client.ClientRetour
```

> Sur Mac/Linux remplacer `;` par `:`

---

## Ports
| Port | Service |
|------|---------|
| 2000 | Réservation |
| 2001 | Emprunt |
| 2002 | Retour |
