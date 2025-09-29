# Guide de Test API Tpionic

## 🚀 Démarrage de l'application

1. **Ouvrir un terminal dans le dossier tpionic**
2. **Exécuter la commande :**
   ```
   mvn spring-boot:run
   ```
3. **Attendre que l'application démarre** (vous verrez "Started DemoApplication")

## 📋 Tests avec Postman

### 1. Créer une Association
- **URL :** `POST http://localhost:8080/api/associations`
- **Headers :** `Content-Type: application/json`
- **Body :**
```json
{
    "nom": "Association Solidarité Enfants",
    "email": "contact@solidarite-enfants.org",
    "motDePasse": "motdepasse123",
    "telephone": "+221 33 123 45 67",
    "adresse": "123 Avenue de la Liberté",
    "pays": "Sénégal",
    "ville": "Dakar",
    "description": "Association dédiée à l'éducation des enfants défavorisés"
}
```

### 2. Lister les Associations
- **URL :** `GET http://localhost:8080/api/associations`

### 3. Ajouter un Enfant
- **URL :** `POST http://localhost:8080/api/associations/1/enfants`
- **Headers :** `Content-Type: application/json`
- **Body :**
```json
{
    "nom": "Diop",
    "prenom": "Fatou",
    "dateNaissance": "2010-05-15",
    "adresse": "Quartier Parcelles Assainies",
    "email": "fatou.diop@email.com",
    "password": "enfant123",
    "age": 13,
    "apropos_de_enfant": "Fatou est une élève brillante qui aime les mathématiques"
}
```

### 4. Upload de Fichiers
- **Bulletin PDF :** `POST http://localhost:8080/api/associations/1/enfants/1/upload-bulletin`
- **Photo Activité :** `POST http://localhost:8080/api/associations/1/enfants/1/upload-photo`
- **Liste Présence :** `POST http://localhost:8080/api/associations/1/enfants/1/upload-presence`

**Body :** `form-data` avec `file` = [votre fichier]

## 🔧 Résolution des Problèmes

### Erreur de démarrage
Si l'application ne démarre pas :
1. Vérifiez que MySQL est démarré
2. Vérifiez la configuration dans `application.properties`
3. Exécutez `mvn clean compile` puis `mvn spring-boot:run`

### Erreur de base de données
Assurez-vous que :
- MySQL est installé et démarré
- La base de données `gest_parrain` existe
- Les identifiants dans `application.properties` sont corrects

## 📁 Structure des Fichiers Uploadés
Les fichiers sont stockés dans : `uploads/enfants/{enfantId}/{type}/`

## ✅ Tests de Validation
1. Créer une association ✅
2. Ajouter un enfant ✅
3. Uploader un bulletin PDF ✅
4. Uploader une photo ✅
5. Uploader une liste de présence ✅
6. Vérifier que les fichiers sont bien associés ✅
