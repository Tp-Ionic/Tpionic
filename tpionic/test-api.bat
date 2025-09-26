@echo off
echo Test de l'application Spring Boot
echo.

echo Test 1: Vérifier si l'application répond...
curl -X GET http://localhost:8081/actuator/health
echo.
echo.

echo Test 2: Créer une association...
curl -X POST http://localhost:8081/api/associations ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Test Association\",\"email\":\"test@test.com\",\"motDePasse\":\"test123\",\"telephone\":\"+223123456789\",\"adresse\":\"Test Address\",\"pays\":\"Mali\",\"ville\":\"Bamako\",\"description\":\"Test Description\"}"
echo.
echo.

echo Test 3: Créer un parrain...
curl -X POST http://localhost:8081/api/parrains ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Diallo\",\"prenom\":\"Bakary\",\"email\":\"Boobadiallo@email.com\",\"motDePasse\":\"motdepasse123\",\"telephone\":\"+22374309564\",\"pays\":\"France\",\"ville\":\"Paris\"}"
echo.
echo.

echo Test 4: Créer un enfant (sans email/password)...
curl -X POST http://localhost:8081/api/associations/1/enfants ^
  -H "Content-Type: application/json" ^
  -d "{\"nom\":\"Traoré\",\"prenom\":\"Fatoumata\",\"dateNaissance\":\"2012-03-15\",\"adresse\":\"456 Rue de l'Espoir\",\"age\":11,\"apropos_de_enfant\":\"Enfant brillante\",\"parentNom\":\"Traoré\",\"parentPrenom\":\"Moussa\",\"parentEmail\":\"moussa.traore@email.com\",\"parentMotDePasse\":\"motdepasse123\",\"parentTelephone\":\"+223123456791\",\"parentAdresse\":\"456 Rue de l'Espoir\",\"parentPays\":\"Mali\",\"parentVille\":\"Bamako\",\"parentProfession\":\"Commerçant\",\"parentRelationAvecEnfant\":\"Père\"}"
echo.
echo.

echo Test 5: Voir les enfants non parrainés de l'association...
curl -X GET http://localhost:8081/api/enfants/association/1/non-parraines
echo.
echo.

echo Test 6: Créer un parrainage direct...
curl -X POST http://localhost:8081/api/parrainages/direct ^
  -H "Content-Type: application/json" ^
  -d "{\"parrainId\":1,\"enfantId\":1,\"montantMensuel\":50.0,\"messageDemande\":\"Je souhaite parrainer cet enfant\",\"notes\":\"Parrainage direct\"}"
echo.
echo.

echo Test 7: Vérifier les parrainages créés...
curl -X GET http://localhost:8081/api/parrainages
echo.
echo.

pause