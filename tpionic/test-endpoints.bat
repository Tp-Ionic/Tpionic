@echo off
echo 🚀 Test des Endpoints API - Tpionic
echo =====================================

echo.
echo 📋 Test 1: Créer une Association
curl -X POST http://localhost:8080/api/associations ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Association Test\",\"email\":\"test@test.com\",\"motDePasse\":\"123456\",\"telephone\":\"+221 77 123 45 67\",\"adresse\":\"123 Test\",\"pays\":\"Sénégal\",\"ville\":\"Dakar\"}"

echo.
echo 📋 Test 2: Créer un Parrain
curl -X POST http://localhost:8080/api/parrains ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Dupont\",\"prenom\":\"Jean\",\"email\":\"jean@test.com\",\"motDePasse\":\"123456\",\"telephone\":\"+221 77 987 65 43\",\"pays\":\"France\",\"ville\":\"Paris\"}"

echo.
echo 📋 Test 3: Ajouter un Enfant
curl -X POST http://localhost:8080/api/associations/1/enfants ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Diop\",\"prenom\":\"Fatou\",\"dateNaissance\":\"2010-05-15\",\"adresse\":\"456 Rue Test\",\"age\":13,\"apropos_de_enfant\":\"Enfant test\"}"

echo.
echo 📋 Test 4: Créer des Frais Scolaires
curl -X POST http://localhost:8080/api/frais-scolaires ^
  -H "Content-Type: application/json" ^
  -d "{\"classe\":\"CM2\",\"annee_scolaire\":\"2023-2024\",\"montant\":50000.0,\"description\":\"Frais test\",\"statut\":\"EN_ATTENTE\",\"dateEcheance\":\"2024-02-15\",\"enfantId\":1}"

echo.
echo 📋 Test 5: Créer une Demande de Parrainage
curl -X POST http://localhost:8080/api/parrainages ^
  -H "Content-Type: application/json" ^
  -d "{\"parrainId\":1,\"enfantId\":1,\"associationId\":1,\"montantMensuel\":25000.0,\"messageDemande\":\"Test parrainage\",\"notes\":\"Test\"}"

echo.
echo 📋 Test 6: Créer un Paiement
curl -X POST http://localhost:8080/api/paiements ^
  -H "Content-Type: application/json" ^
  -d "{\"montant\":100000.0,\"typePaiement\":\"VIREMENT_BANCAIRE\",\"datePaiement\":\"2024-01-15\",\"reference\":\"VIR001\",\"notes\":\"Test paiement\",\"parrainId\":1,\"enfantId\":1}"

echo.
echo 📋 Test 7: Créer une Dépense
curl -X POST http://localhost:8080/api/depenses ^
  -H "Content-Type: application/json" ^
  -d "{\"description\":\"Test dépense\",\"montant\":25000.0,\"typeDepense\":\"FOURNITURES_SCOLAIRES\",\"dateDepense\":\"2024-01-16\",\"notes\":\"Test\",\"paiementId\":1,\"enfantId\":1,\"associationId\":1}"

echo.
echo ✅ Tests terminés !
echo.
echo 📖 Consultez le GUIDE-ENDPOINTS-COMPLET.md pour tous les détails
pause
