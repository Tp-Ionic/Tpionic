#!/bin/bash
echo "Test de l'application Spring Boot"
echo "Compilation..."
mvn clean compile
echo "Démarrage de l'application..."
mvn spring-boot:run
