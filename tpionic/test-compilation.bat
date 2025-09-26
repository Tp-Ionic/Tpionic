@echo off
echo === Test de Compilation Tpionic ===
echo.

echo 1. Nettoyage du projet...
call mvn clean -q

echo 2. Compilation du projet...
call mvn compile -q

if %ERRORLEVEL% EQU 0 (
    echo ✓ Compilation reussie!
    echo.
    echo 3. Demarrage de l'application...
    echo L'application sera accessible sur: http://localhost:8080
    echo Appuyez sur Ctrl+C pour arreter l'application
    echo.
    
    call mvn spring-boot:run
) else (
    echo ✗ Erreur lors de la compilation!
    echo.
    echo Verifiez les erreurs ci-dessus.
    echo.
    echo Solutions possibles:
    echo - Verifiez que tous les imports sont corrects
    echo - Verifiez que tous les services ont les methodes necessaires
    echo - Verifiez la configuration de la base de données
    pause
)

echo.
echo Script termine
pause
