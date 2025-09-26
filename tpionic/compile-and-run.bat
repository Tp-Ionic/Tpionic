@echo off
echo === Compilation et Demarrage de Tpionic ===
echo.

echo 1. Nettoyage du projet...
call mvn clean

echo.
echo 2. Compilation du projet...
call mvn compile

if %ERRORLEVEL% EQU 0 (
    echo.
    echo 3. Compilation reussie! Demarrage de l'application...
    echo L'application sera accessible sur: http://localhost:8080
    echo Appuyez sur Ctrl+C pour arreter l'application
    echo.
    
    call mvn spring-boot:run
) else (
    echo.
    echo ERREUR lors de la compilation!
    echo Verifiez les erreurs ci-dessus.
    pause
)

echo.
echo Script termine
pause
