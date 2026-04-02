# ============================================================
# Tests automatiques Mediatheque
# ProjetArchiLogiciel - Ethan / Tanim / Alexandre 205-206
# ============================================================

$CP = "client/out;libs/bttp2.jar"
$OK = 0
$KO = 0

function Test-Client {
    param($num, $TestNom, $Attendu, $Commande, $Inputs)
    $result = $Inputs | java -cp $CP $Commande 2>&1
    $reponse = $result | Select-String "Reponse :" | ForEach-Object { $_.ToString().Trim() }
    if ($reponse -match $Attendu) {
        Write-Host "[OK] T$num - $TestNom" -ForegroundColor Green
        Write-Host "     $reponse" -ForegroundColor DarkGray
        $script:OK++
    } else {
        Write-Host "[ECHEC] T$num - $TestNom" -ForegroundColor Red
        Write-Host "     Attendu  : $Attendu" -ForegroundColor Yellow
        Write-Host "     Recu     : $reponse" -ForegroundColor Red
        $script:KO++
    }
}

Write-Host ""
Write-Host "============================================================" -ForegroundColor Cyan
Write-Host "   TESTS AUTOMATIQUES - Mediatheque" -ForegroundColor Cyan
Write-Host "============================================================" -ForegroundColor Cyan
Write-Host ""

# T1 - Reservation normale
Test-Client 1 "Reservation normale" "OK" "client.ClientReservation" @("1", "D001")

# T2 - Reservation doc deja reserve
Test-Client 2 "Reservation doc deja reserve" "KO" "client.ClientReservation" @("3", "D001")

# T3 - Emprunt apres reservation (meme abonne)
Test-Client 3 "Emprunt apres reservation" "OK" "client.ClientEmprunt" @("1", "D001")

# T4 - Reservation doc deja emprunte
Test-Client 4 "Reservation doc deja emprunte" "KO" "client.ClientReservation" @("3", "D001")

# T5a - Reservation L001 par abonne 3
Test-Client 5 "Reservation L001 abonne 3" "OK" "client.ClientReservation" @("3", "L001")

# T5b - Emprunt L001 par abonne 4 (pas le bon)
Test-Client 6 "Emprunt reserve pour un autre" "KO" "client.ClientEmprunt" @("4", "L001")

# T6a - DVD adulte mineur reservation
Test-Client 7 "DVD adulte - mineur reservation" "KO" "client.ClientReservation" @("2", "D002")

# T6b - DVD adulte mineur emprunt
Test-Client 8 "DVD adulte - mineur emprunt" "KO" "client.ClientEmprunt" @("2", "D002")

# T7 - Retour normal
Test-Client 9 "Retour normal" "OK" "client.ClientRetour" @("D001", "n")

# T8 - Retour doc deja disponible
Test-Client 10 "Retour doc deja disponible" "KO" "client.ClientRetour" @("D001", "n")

# T9a - Reservation D003 par abonne 3
Test-Client 11 "Geronimo - Reservation D003" "OK" "client.ClientReservation" @("3", "D003")

# T9b - Emprunt D003 par abonne 3
Test-Client 12 "Geronimo - Emprunt D003" "OK" "client.ClientEmprunt" @("3", "D003")

# T9c - Retour degrade -> bannissement
Test-Client 13 "Geronimo - Retour degrade -> banni" "OK" "client.ClientRetour" @("D003", "o")

# T10 - Reservation en etant banni
Test-Client 14 "Reservation abonne banni" "KO" "client.ClientReservation" @("3", "L002")

# T11 Emprunt direct sans réservation
Test-Client 15 "Emprunt direct sans reservation" "OK" "client.ClientEmprunt" @("4", "D005")


Write-Host ""
Write-Host "============================================================" -ForegroundColor Cyan
Write-Host "   RESULTATS : $OK OK  |  $KO ECHEC" -ForegroundColor Cyan
Write-Host "============================================================" -ForegroundColor Cyan
Write-Host ""
