# Script PowerShell pour démarrer l'application Spring Boot
Write-Host "Démarrage de l'application Spring Boot..." -ForegroundColor Green

# Aller dans le répertoire tpionic
Set-Location "C:\Users\DELL Latitude\Desktop\Tpionic\tpionic"

# Vérifier que nous sommes dans le bon répertoire
Write-Host "Répertoire actuel: $(Get-Location)" -ForegroundColor Yellow

# Compiler l'application
Write-Host "Compilation de l'application..." -ForegroundColor Blue
mvn clean compile

# Démarrer l'application
Write-Host "Démarrage de l'application..." -ForegroundColor Blue
mvn spring-boot:run

Write-Host "Application démarrée sur http://localhost:8081" -ForegroundColor Green