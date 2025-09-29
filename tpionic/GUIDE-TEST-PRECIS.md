# 🧪 Guide de Test API - Exemples Précis

## ⚠️ Erreur 400 (Bad Request) - Solutions

### **Cause commune :** Champs obligatoires manquants ou mal formatés

## 🏢 Test Association - Exemple Complet

### **1. Créer une Association (POST)**
**URL :** `POST http://localhost:8080/api/associations`

**Headers :**
```
Content-Type: application/json
```

**Body JSON (copier-coller exact) :**
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

**✅ Réponse attendue (201 Created) :**
```json
{
    "id": 1,
    "nom": "Association Solidarité Enfants",
    "email": "contact@solidarite-enfants.org",
    "telephone": "+221 33 123 45 67",
    "adresse": "123 Avenue de la Liberté",
    "pays": "Sénégal",
    "ville": "Dakar",
    "description": "Association dédiée à l'éducation des enfants défavorisés",
    "logoUrl": null,
    "actif": true,
    "nombreEnfants": 0
}
```

**❌ Erreurs possibles :**
- `"Le nom de l'association est obligatoire"` → Ajoutez le champ `nom`
- `"L'email de l'association est obligatoire"` → Ajoutez le champ `email`
- `"Le mot de passe est obligatoire"` → Ajoutez le champ `motDePasse`
- `"Le téléphone est obligatoire"` → Ajoutez le champ `telephone`
- `"L'adresse est obligatoire"` → Ajoutez le champ `adresse`
- `"Le pays est obligatoire"` → Ajoutez le champ `pays`
- `"La ville est obligatoire"` → Ajoutez le champ `ville`

---

## 👨‍👩‍👧‍👦 Test Parrain - Exemple Complet

### **1. Créer un Parrain (POST)**
**URL :** `POST http://localhost:8080/api/parrains`

**Headers :**
```
Content-Type: application/json
```

**Body JSON (copier-coller exact) :**
```json
{
    "nom": "Dupont",
    "prenom": "Jean",
    "email": "jean.dupont@email.com",
    "motDePasse": "parrain123",
    "telephone": "+33 6 12 34 56 78",
    "pays": "France",
    "ville": "Paris",
    "avatarUrl": "https://example.com/avatar.jpg"
}
```

**✅ Réponse attendue (201 Created) :**
```json
{
    "id": 1,
    "nom": "Dupont",
    "prenom": "Jean",
    "email": "jean.dupont@email.com",
    "telephone": "+33 6 12 34 56 78",
    "pays": "France",
    "ville": "Paris",
    "avatarUrl": "https://example.com/avatar.jpg",
    "actif": true
}
```

### **2. Connexion Parrain (POST)**
**URL :** `POST http://localhost:8080/api/parrains/login`

**Body (form-data) :**
```
email: jean.dupont@email.com
motDePasse: parrain123
```

---

## 👶 Test Enfant - Exemple Complet

### **1. Ajouter un Enfant (POST)**
**URL :** `POST http://localhost:8080/api/associations/1/enfants`

**Headers :**
```
Content-Type: application/json
```

**Body JSON (copier-coller exact) :**
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

---

## 💰 Test Paiement - Exemple Complet

### **1. Créer un Paiement (POST)**
**URL :** `POST http://localhost:8080/api/Paiements`

**Headers :**
```
Content-Type: application/json
```

**Body JSON (copier-coller exact) :**
```json
{
    "montant": 50000.0,
    "typePaiement": "VIREMENT_BANCAIRE",
    "datePaiement": "2024-01-15",
    "reference": "VIR001234567",
    "notes": "Paiement mensuel pour Fatou Diop",
    "parrainId": 1,
    "enfantId": 1
}
```

---

## 🔍 Vérifications Importantes

### **1. Ordre de Test Obligatoire :**
1. ✅ **Créer Association** (ID = 1)
2. ✅ **Créer Parrain** (ID = 1)
3. ✅ **Ajouter Enfant** (ID = 1)
4. ✅ **Créer Paiement** (utilise les IDs précédents)

### **2. Champs Obligatoires :**

**Association :**
- `nom` (string)
- `email` (string)
- `motDePasse` (string)
- `telephone` (string)
- `adresse` (string)
- `pays` (string)
- `ville` (string)

**Parrain :**
- `nom` (string)
- `prenom` (string)
- `email` (string)
- `motDePasse` (string)

**Enfant :**
- `nom` (string)
- `prenom` (string)
- `dateNaissance` (string)
- `adresse` (string)
- `age` (number)

**Paiement :**
- `montant` (number)
- `typePaiement` (enum)
- `datePaiement` (string)
- `parrainId` (number)
- `enfantId` (number)

### **3. Types de Paiement Valides :**
- `"VIREMENT_BANCAIRE"`
- `"CHEQUE"`
- `"ESPECES"`
- `"CARTE_BANCAIRE"`
- `"MOBILE_MONEY"`

---

## 🚀 Test Rapide avec Postman

### **Collection Postman :**
1. Importez le fichier `Tpionic-API-Collection.postman_collection.json`
2. Suivez l'ordre des requêtes
3. Vérifiez les réponses

### **Test avec curl :**
```bash
# Créer Association
curl -X POST http://localhost:8080/api/associations \
  -H "Content-Type: application/json" \
  -d '{"nom":"Test Association","email":"test@test.com","motDePasse":"test123","telephone":"123456789","adresse":"Test Address","pays":"Senegal","ville":"Dakar"}'
```

---

## ⚠️ Erreurs Courantes et Solutions

### **Erreur 400 :**
- Vérifiez que tous les champs obligatoires sont présents
- Vérifiez le format JSON
- Vérifiez les types de données

### **Erreur 500 :**
- Vérifiez que l'application est démarrée
- Vérifiez la connexion à la base de données
- Vérifiez les logs de l'application

### **Erreur de connexion :**
- Vérifiez que l'application écoute sur le port 8080
- Vérifiez l'URL (http://localhost:8080)
