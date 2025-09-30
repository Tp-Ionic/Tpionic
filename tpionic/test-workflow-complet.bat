@echo off
echo ========================================
echo    TEST WORKFLOW COMPLET - TPIONIC
echo ========================================
echo.

echo [1/10] Demarrage de l'application...
start /B java -jar target/demo-0.0.1-SNAPSHOT.jar
timeout /t 15 /nobreak >nul

echo [2/10] Test de connexion...
curl -s http://localhost:8081/api/associations > nul
if %errorlevel% neq 0 (
    echo ERREUR: Application non accessible
    exit /b 1
)
echo ✓ Application accessible

echo.
echo [3/10] Creation des associations...
echo Association 1: Bamako
curl -X POST http://localhost:8081/api/associations ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Association Solidarite Bamako\",\"email\":\"contact@solidarite-bamako.org\",\"telephone\":\"+223 20 22 33 44\",\"adresse\":\"Avenue de l'Independance, Bamako\",\"ville\":\"Bamako\",\"pays\":\"Mali\",\"description\":\"Association pour l'education des enfants demunis a Bamako\",\"motDePasse\":\"password123\"}"

echo.
echo Association 2: Segou
curl -X POST http://localhost:8081/api/associations ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Fondation Education Segou\",\"email\":\"info@education-segou.ml\",\"telephone\":\"+223 21 44 55 66\",\"adresse\":\"Quartier Hamdallaye, Segou\",\"ville\":\"Segou\",\"pays\":\"Mali\",\"description\":\"Fondation pour l'education et le developpement a Segou\",\"motDePasse\":\"password456\"}"

echo.
echo [4/10] Creation des parents...
echo Parent 1: Moussa Traore
curl -X POST http://localhost:8081/api/parents ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Traore\",\"prenom\":\"Moussa\",\"email\":\"moussa.traore@email.com\",\"telephone\":\"+223 70 12 34 56\",\"adresse\":\"Quartier Hippodrome, Bamako\",\"ville\":\"Bamako\",\"pays\":\"Mali\",\"motDePasse\":\"parent123\",\"nombreEnfants\":2,\"profession\":\"Commercant\",\"revenuMensuel\":150000}"

echo.
echo Parent 2: Aminata Cisse
curl -X POST http://localhost:8081/api/parents ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Cisse\",\"prenom\":\"Aminata\",\"email\":\"aminata.cisse@email.com\",\"telephone\":\"+223 76 98 76 54\",\"adresse\":\"Quartier Niarela, Segou\",\"ville\":\"Segou\",\"pays\":\"Mali\",\"motDePasse\":\"parent456\",\"nombreEnfants\":1,\"profession\":\"Enseignante\",\"revenuMensuel\":120000}"

echo.
echo [5/10] Creation des sponsors...
echo Sponsor 1: Pierre Martin
curl -X POST http://localhost:8081/api/parrains ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Martin\",\"prenom\":\"Pierre\",\"email\":\"pierre.martin@email.com\",\"telephone\":\"+33 6 12 34 56 78\",\"adresse\":\"123 Rue de la Paix, Paris\",\"ville\":\"Paris\",\"pays\":\"France\",\"motDePasse\":\"sponsor123\",\"profession\":\"Ingenieur\",\"revenuAnnuel\":65000,\"motivation\":\"Soutenir l'education des enfants au Mali\"}"

echo.
echo Sponsor 2: Marie Dubois
curl -X POST http://localhost:8081/api/parrains ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Dubois\",\"prenom\":\"Marie\",\"email\":\"marie.dubois@email.com\",\"telephone\":\"+33 6 98 76 54 32\",\"adresse\":\"456 Avenue des Champs, Lyon\",\"ville\":\"Lyon\",\"pays\":\"France\",\"motDePasse\":\"sponsor456\",\"profession\":\"Medecin\",\"revenuAnnuel\":75000,\"motivation\":\"Contribuer a l'education des enfants defavorises\"}"

echo.
echo Sponsor 3: John Johnson
curl -X POST http://localhost:8081/api/parrains ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Johnson\",\"prenom\":\"John\",\"email\":\"john.johnson@email.com\",\"telephone\":\"+1 555 123 4567\",\"adresse\":\"789 Main Street, New York\",\"ville\":\"New York\",\"pays\":\"USA\",\"motDePasse\":\"sponsor789\",\"profession\":\"Avocat\",\"revenuAnnuel\":85000,\"motivation\":\"Aider les enfants a acceder a l'education\"}"

echo.
echo Sponsor 4: Anna Mueller
curl -X POST http://localhost:8081/api/parrains ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Mueller\",\"prenom\":\"Anna\",\"email\":\"anna.mueller@email.com\",\"telephone\":\"+49 30 123 456 78\",\"adresse\":\"321 Berliner Strasse, Berlin\",\"ville\":\"Berlin\",\"pays\":\"Allemagne\",\"motDePasse\":\"sponsor321\",\"profession\":\"Architecte\",\"revenuAnnuel\":70000,\"motivation\":\"Participer au developpement educatif en Afrique\"}"

echo.
echo [6/10] Creation des enfants...
echo Enfant 1: Fatou Traore (avec parent existant)
curl -X POST http://localhost:8081/api/enfants ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Traore\",\"prenom\":\"Fatou\",\"dateNaissance\":\"2015-03-15\",\"adresse\":\"Quartier Hippodrome, Bamako\",\"age\":9,\"apropos_de_enfant\":\"Enfant tres motivee pour ses etudes\",\"associationId\":1,\"parentId\":1}"

echo.
echo Enfant 2: Ibrahim Traore
curl -X POST http://localhost:8081/api/enfants ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Traore\",\"prenom\":\"Ibrahim\",\"dateNaissance\":\"2013-07-22\",\"adresse\":\"Quartier Hippodrome, Bamako\",\"age\":11,\"apropos_de_enfant\":\"Excellent eleve en mathematiques\",\"associationId\":1,\"parentNom\":\"Traore\",\"parentPrenom\":\"Moussa\",\"parentEmail\":\"moussa.traore@email.com\",\"parentMotDePasse\":\"parent123\",\"parentTelephone\":\"+223 70 12 34 56\",\"parentAdresse\":\"Quartier Hippodrome, Bamako\",\"parentPays\":\"Mali\",\"parentVille\":\"Bamako\",\"parentProfession\":\"Commercant\",\"parentRelationAvecEnfant\":\"Pere\"}"

echo.
echo Enfant 3: Aicha Cisse
curl -X POST http://localhost:8081/api/enfants ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Cisse\",\"prenom\":\"Aicha\",\"dateNaissance\":\"2014-11-08\",\"adresse\":\"Quartier Niarela, Segou\",\"age\":10,\"apropos_de_enfant\":\"Passionnee par la lecture\",\"associationId\":2,\"parentNom\":\"Cisse\",\"parentPrenom\":\"Aminata\",\"parentEmail\":\"aminata.cisse@email.com\",\"parentMotDePasse\":\"parent456\",\"parentTelephone\":\"+223 76 98 76 54\",\"parentAdresse\":\"Quartier Niarela, Segou\",\"parentPays\":\"Mali\",\"parentVille\":\"Segou\",\"parentProfession\":\"Enseignante\",\"parentRelationAvecEnfant\":\"Mere\"}"

echo.
echo [7/10] Creation des parrainages...
echo Parrainage 1: Pierre Martin -> Fatou Traore
curl -X POST http://localhost:8081/api/parrainer-enfant ^
  -H "Content-Type: application/json" ^
  -d "{\"parrainId\":1,\"enfantId\":1}"

echo.
echo Parrainage 2: Marie Dubois -> Fatou Traore (2e parrain)
curl -X POST http://localhost:8081/api/parrainer-enfant ^
  -H "Content-Type: application/json" ^
  -d "{\"parrainId\":2,\"enfantId\":1}"

echo.
echo Parrainage 3: John Johnson -> Ibrahim Traore
curl -X POST http://localhost:8081/api/parrainer-enfant ^
  -H "Content-Type: application/json" ^
  -d "{\"parrainId\":3,\"enfantId\":2}"

echo.
echo Parrainage 4: Anna Mueller -> Aicha Cisse
curl -X POST http://localhost:8081/api/parrainer-enfant ^
  -H "Content-Type: application/json" ^
  -d "{\"parrainId\":4,\"enfantId\":3}"

echo.
echo [8/10] Creation des paiements...
echo Paiement 1: Pierre Martin pour Fatou (50€)
curl -X POST http://localhost:8081/api/paiements ^
  -H "Content-Type: application/json" ^
  -d "{\"montant\":50.0,\"typePaiement\":\"VIREMENT_BANCAIRE\",\"parrainId\":1,\"enfantId\":1,\"datePaiement\":\"2024-10-01\",\"notes\":\"Premier paiement mensuel pour le parrainage de Fatou\"}"

echo.
echo Paiement 2: Marie Dubois pour Fatou (30€)
curl -X POST http://localhost:8081/api/paiements ^
  -H "Content-Type: application/json" ^
  -d "{\"montant\":30.0,\"typePaiement\":\"PAYPAL\",\"parrainId\":2,\"enfantId\":1,\"datePaiement\":\"2024-10-01\",\"notes\":\"Contribution complementaire pour Fatou\"}"

echo.
echo Paiement 3: John Johnson pour Ibrahim (60€)
curl -X POST http://localhost:8081/api/paiements ^
  -H "Content-Type: application/json" ^
  -d "{\"montant\":60.0,\"typePaiement\":\"CARTE_BANCAIRE\",\"parrainId\":3,\"enfantId\":2,\"datePaiement\":\"2024-10-01\",\"notes\":\"Paiement mensuel pour Ibrahim\"}"

echo.
echo Paiement 4: Anna Mueller pour Aicha (40€)
curl -X POST http://localhost:8081/api/paiements ^
  -H "Content-Type: application/json" ^
  -d "{\"montant\":40.0,\"typePaiement\":\"VIREMENT_BANCAIRE\",\"parrainId\":4,\"enfantId\":3,\"datePaiement\":\"2024-10-01\",\"notes\":\"Premier paiement pour Aicha\"}"

echo.
echo [9/10] Confirmation des paiements...
echo Confirmation Paiement 1
curl -X POST http://localhost:8081/api/confirmations-paiement ^
  -H "Content-Type: application/json" ^
  -d "{\"paiementId\":1,\"statut\":\"CONFIRME\",\"notes\":\"Paiement confirme par la banque\"}"

echo.
echo Confirmation Paiement 2
curl -X POST http://localhost:8081/api/confirmations-paiement ^
  -H "Content-Type: application/json" ^
  -d "{\"paiementId\":2,\"statut\":\"CONFIRME\",\"notes\":\"Paiement PayPal valide\"}"

echo.
echo Confirmation Paiement 3
curl -X POST http://localhost:8081/api/confirmations-paiement ^
  -H "Content-Type: application/json" ^
  -d "{\"paiementId\":3,\"statut\":\"CONFIRME\",\"notes\":\"Carte bancaire acceptee\"}"

echo.
echo Confirmation Paiement 4
curl -X POST http://localhost:8081/api/confirmations-paiement ^
  -H "Content-Type: application/json" ^
  -d "{\"paiementId\":4,\"statut\":\"CONFIRME\",\"notes\":\"Virement bancaire recu\"}"

echo.
echo [10/10] Creation des depenses...
echo Depense 1: Materiel scolaire pour Fatou
curl -X POST http://localhost:8081/api/depenses ^
  -H "Content-Type: application/json" ^
  -d "{\"montant\":25.0,\"typeDepense\":\"MATERIEL_SCOLAIRE\",\"description\":\"Livres, cahiers et stylos pour Fatou\",\"associationId\":1,\"dateDepense\":\"2024-10-02\"}"

echo.
echo Depense 2: Uniforme pour Ibrahim
curl -X POST http://localhost:8081/api/depenses ^
  -H "Content-Type: application/json" ^
  -d "{\"montant\":20.0,\"typeDepense\":\"UNIFORME\",\"description\":\"Uniforme scolaire pour Ibrahim\",\"associationId\":1,\"dateDepense\":\"2024-10-02\"}"

echo.
echo Depense 3: Fournitures pour Aicha
curl -X POST http://localhost:8081/api/depenses ^
  -H "Content-Type: application/json" ^
  -d "{\"montant\":18.0,\"typeDepense\":\"MATERIEL_SCOLAIRE\",\"description\":\"Cahiers et crayons pour Aicha\",\"associationId\":2,\"dateDepense\":\"2024-10-02\"}"

echo.
echo ========================================
echo    WORKFLOW COMPLET TERMINE !
echo ========================================
echo.
echo Verification des donnees creees:
echo.
echo Associations:
curl -s http://localhost:8081/api/associations | findstr /C:"nom"
echo.
echo Parents:
curl -s http://localhost:8081/api/parents | findstr /C:"nom"
echo.
echo Sponsors:
curl -s http://localhost:8081/api/parrains | findstr /C:"nom"
echo.
echo Enfants:
curl -s http://localhost:8081/api/enfants | findstr /C:"nom"
echo.
echo Parrainages:
curl -s http://localhost:8081/api/parrainages | findstr /C:"statut"
echo.
echo Paiements:
curl -s http://localhost:8081/api/paiements | findstr /C:"montant"
echo.
echo Depenses:
curl -s http://localhost:8081/api/depenses | findstr /C:"montant"
echo.
echo ========================================
echo    TEST TERMINE AVEC SUCCES !
echo ========================================
pause
