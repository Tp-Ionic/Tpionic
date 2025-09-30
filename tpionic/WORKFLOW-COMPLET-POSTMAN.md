# 🎯 WORKFLOW COMPLET TPIONIC - GUIDE POSTMAN

## 📋 **VUE D'ENSEMBLE DU SCÉNARIO**

Ce guide présente un workflow complet pour tester le système TPIONIC avec :
- **2 Associations** (Bamako et Ségou)
- **2 Parents** (Moussa Traoré et Aminata Cissé)
- **4 Sponsors** (Pierre Martin, Marie Dubois, John Johnson, Anna Mueller)
- **3 Enfants** (Fatou avec 2 sponsors, Ibrahim et Aicha)
- **4 Paiements** et confirmations
- **3 Dépenses** pour le matériel scolaire

---

## 🚀 **ÉTAPE 1 : DÉMARRAGE DE L'APPLICATION**

### Prérequis
```bash
# Compilation et démarrage
cd tpionic
mvn clean package -DskipTests
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

**URL Base :** `http://localhost:8081/api`

---

## 🏢 **ÉTAPE 2 : CRÉATION DES ASSOCIATIONS**

### 2.1 Association Solidarité Bamako
```http
POST http://localhost:8081/api/associations
Content-Type: application/json

{
  "nom": "Association Solidarité Bamako",
  "email": "contact@solidarite-bamako.org",
  "telephone": "+223 20 22 33 44",
  "adresse": "Avenue de l'Indépendance, Bamako",
  "ville": "Bamako",
  "pays": "Mali",
  "description": "Association pour l'éducation des enfants démunis à Bamako",
  "motDePasse": "password123"
}
```

### 2.2 Fondation Education Ségou
```http
POST http://localhost:8081/api/associations
Content-Type: application/json

{
  "nom": "Fondation Education Ségou",
  "email": "info@education-segou.ml",
  "telephone": "+223 21 44 55 66",
  "adresse": "Quartier Hamdallaye, Ségou",
  "ville": "Ségou",
  "pays": "Mali",
  "description": "Fondation pour l'éducation et le développement à Ségou",
  "motDePasse": "password456"
}
```

**Vérification :**
```http
GET http://localhost:8081/api/associations
```

---

## 👨‍👩‍👧‍👦 **ÉTAPE 3 : CRÉATION DES PARENTS**

### 3.1 Moussa Traoré (Bamako)
```http
POST http://localhost:8081/api/parents
Content-Type: application/json

{
  "nom": "Traoré",
  "prenom": "Moussa",
  "email": "moussa.traore@email.com",
  "telephone": "+223 70 12 34 56",
  "adresse": "Quartier Hippodrome, Bamako",
  "ville": "Bamako",
  "pays": "Mali",
  "motDePasse": "parent123",
  "nombreEnfants": 2,
  "profession": "Commerçant",
  "revenuMensuel": 150000
}
```

### 3.2 Aminata Cissé (Ségou)
```http
POST http://localhost:8081/api/parents
Content-Type: application/json

{
  "nom": "Cissé",
  "prenom": "Aminata",
  "email": "aminata.cisse@email.com",
  "telephone": "+223 76 98 76 54",
  "adresse": "Quartier Niaréla, Ségou",
  "ville": "Ségou",
  "pays": "Mali",
  "motDePasse": "parent456",
  "nombreEnfants": 1,
  "profession": "Enseignante",
  "revenuMensuel": 120000
}
```

**Vérification :**
```http
GET http://localhost:8081/api/parents
```

---

## 👨‍💼 **ÉTAPE 4 : CRÉATION DES SPONSORS**

### 4.1 Pierre Martin (France)
```http
POST http://localhost:8081/api/parrains
Content-Type: application/json

{
  "nom": "Martin",
  "prenom": "Pierre",
  "email": "pierre.martin@email.com",
  "telephone": "+33 6 12 34 56 78",
  "adresse": "123 Rue de la Paix, Paris",
  "ville": "Paris",
  "pays": "France",
  "motDePasse": "sponsor123",
  "profession": "Ingénieur",
  "revenuAnnuel": 65000,
  "motivation": "Soutenir l'éducation des enfants au Mali"
}
```

### 4.2 Marie Dubois (France)
```http
POST http://localhost:8081/api/parrains
Content-Type: application/json

{
  "nom": "Dubois",
  "prenom": "Marie",
  "email": "marie.dubois@email.com",
  "telephone": "+33 6 98 76 54 32",
  "adresse": "456 Avenue des Champs, Lyon",
  "ville": "Lyon",
  "pays": "France",
  "motDePasse": "sponsor456",
  "profession": "Médecin",
  "revenuAnnuel": 75000,
  "motivation": "Contribuer à l'éducation des enfants défavorisés"
}
```

### 4.3 John Johnson (USA)
```http
POST http://localhost:8081/api/parrains
Content-Type: application/json

{
  "nom": "Johnson",
  "prenom": "John",
  "email": "john.johnson@email.com",
  "telephone": "+1 555 123 4567",
  "adresse": "789 Main Street, New York",
  "ville": "New York",
  "pays": "USA",
  "motDePasse": "sponsor789",
  "profession": "Avocat",
  "revenuAnnuel": 85000,
  "motivation": "Aider les enfants à accéder à l'éducation"
}
```

### 4.4 Anna Mueller (Allemagne)
```http
POST http://localhost:8081/api/parrains
Content-Type: application/json

{
  "nom": "Mueller",
  "prenom": "Anna",
  "email": "anna.mueller@email.com",
  "telephone": "+49 30 123 456 78",
  "adresse": "321 Berliner Straße, Berlin",
  "ville": "Berlin",
  "pays": "Allemagne",
  "motDePasse": "sponsor321",
  "profession": "Architecte",
  "revenuAnnuel": 70000,
  "motivation": "Participer au développement éducatif en Afrique"
}
```

**Vérification :**
```http
GET http://localhost:8081/api/parrains
```

---

## 👶 **ÉTAPE 5 : CRÉATION DES ENFANTS**

### 5.1 Fatou Traoré (avec parent existant)
```http
POST http://localhost:8081/api/enfants
Content-Type: application/json

{
  "nom": "Traoré",
  "prenom": "Fatou",
  "dateNaissance": "2015-03-15",
  "adresse": "Quartier Hippodrome, Bamako",
  "age": 9,
  "apropos_de_enfant": "Enfant très motivée pour ses études",
  "associationId": 1,
  "parentId": 1
}
```

### 5.2 Ibrahim Traoré (création parent + enfant)
```http
POST http://localhost:8081/api/enfants
Content-Type: application/json

{
  "nom": "Traoré",
  "prenom": "Ibrahim",
  "dateNaissance": "2013-07-22",
  "adresse": "Quartier Hippodrome, Bamako",
  "age": 11,
  "apropos_de_enfant": "Excellent élève en mathématiques",
  "associationId": 1,
  "parentNom": "Traoré",
  "parentPrenom": "Moussa",
  "parentEmail": "moussa.traore@email.com",
  "parentMotDePasse": "parent123",
  "parentTelephone": "+223 70 12 34 56",
  "parentAdresse": "Quartier Hippodrome, Bamako",
  "parentPays": "Mali",
  "parentVille": "Bamako",
  "parentProfession": "Commerçant",
  "parentRelationAvecEnfant": "Père"
}
```

### 5.3 Aicha Cissé (création parent + enfant)
```http
POST http://localhost:8081/api/enfants
Content-Type: application/json

{
  "nom": "Cissé",
  "prenom": "Aicha",
  "dateNaissance": "2014-11-08",
  "adresse": "Quartier Niaréla, Ségou",
  "age": 10,
  "apropos_de_enfant": "Passionnée par la lecture",
  "associationId": 2,
  "parentNom": "Cissé",
  "parentPrenom": "Aminata",
  "parentEmail": "aminata.cisse@email.com",
  "parentMotDePasse": "parent456",
  "parentTelephone": "+223 76 98 76 54",
  "parentAdresse": "Quartier Niaréla, Ségou",
  "parentPays": "Mali",
  "parentVille": "Ségou",
  "parentProfession": "Enseignante",
  "parentRelationAvecEnfant": "Mère"
}
```

**Vérification :**
```http
GET http://localhost:8081/api/enfants
GET http://localhost:8081/api/associations/1/enfants
GET http://localhost:8081/api/associations/2/enfants
```

---

## 🤝 **ÉTAPE 6 : CRÉATION DES PARRAINAGES**

### 6.1 Pierre Martin → Fatou Traoré
```http
POST http://localhost:8081/api/parrainer-enfant
Content-Type: application/json

{
  "parrainId": 1,
  "enfantId": 1
}
```

### 6.2 Marie Dubois → Fatou Traoré (2ème parrain)
```http
POST http://localhost:8081/api/parrainer-enfant
Content-Type: application/json

{
  "parrainId": 2,
  "enfantId": 1
}
```

### 6.3 John Johnson → Ibrahim Traoré
```http
POST http://localhost:8081/api/parrainer-enfant
Content-Type: application/json

{
  "parrainId": 3,
  "enfantId": 2
}
```

### 6.4 Anna Mueller → Aicha Cissé
```http
POST http://localhost:8081/api/parrainer-enfant
Content-Type: application/json

{
  "parrainId": 4,
  "enfantId": 3
}
```

**Vérification :**
```http
GET http://localhost:8081/api/parrainages
GET http://localhost:8081/api/parrainages/en-attente-paiement
```

---

## 💰 **ÉTAPE 7 : CRÉATION DES PAIEMENTS**

### 7.1 Pierre Martin pour Fatou (50€)
```http
POST http://localhost:8081/api/paiements
Content-Type: application/json

{
  "montant": 50.0,
  "typePaiement": "VIREMENT_BANCAIRE",
  "parrainId": 1,
  "enfantId": 1,
  "datePaiement": "2024-10-01",
  "notes": "Premier paiement mensuel pour le parrainage de Fatou"
}
```

### 7.2 Marie Dubois pour Fatou (30€)
```http
POST http://localhost:8081/api/paiements
Content-Type: application/json

{
  "montant": 30.0,
  "typePaiement": "PAYPAL",
  "parrainId": 2,
  "enfantId": 1,
  "datePaiement": "2024-10-01",
  "notes": "Contribution complémentaire pour Fatou"
}
```

### 7.3 John Johnson pour Ibrahim (60€)
```http
POST http://localhost:8081/api/paiements
Content-Type: application/json

{
  "montant": 60.0,
  "typePaiement": "CARTE_BANCAIRE",
  "parrainId": 3,
  "enfantId": 2,
  "datePaiement": "2024-10-01",
  "notes": "Paiement mensuel pour Ibrahim"
}
```

### 7.4 Anna Mueller pour Aicha (40€)
```http
POST http://localhost:8081/api/paiements
Content-Type: application/json

{
  "montant": 40.0,
  "typePaiement": "VIREMENT_BANCAIRE",
  "parrainId": 4,
  "enfantId": 3,
  "datePaiement": "2024-10-01",
  "notes": "Premier paiement pour Aicha"
}
```

**Vérification :**
```http
GET http://localhost:8081/api/paiements
GET http://localhost:8081/api/paiements/parrain/1
GET http://localhost:8081/api/paiements/enfant/1
```

---

## ✅ **ÉTAPE 8 : CONFIRMATION DES PAIEMENTS**

### 8.1 Confirmation Paiement 1
```http
POST http://localhost:8081/api/confirmations-paiement
Content-Type: application/json

{
  "paiementId": 1,
  "statut": "CONFIRME",
  "notes": "Paiement confirmé par la banque"
}
```

### 8.2 Confirmation Paiement 2
```http
POST http://localhost:8081/api/confirmations-paiement
Content-Type: application/json

{
  "paiementId": 2,
  "statut": "CONFIRME",
  "notes": "Paiement PayPal validé"
}
```

### 8.3 Confirmation Paiement 3
```http
POST http://localhost:8081/api/confirmations-paiement
Content-Type: application/json

{
  "paiementId": 3,
  "statut": "CONFIRME",
  "notes": "Carte bancaire acceptée"
}
```

### 8.4 Confirmation Paiement 4
```http
POST http://localhost:8081/api/confirmations-paiement
Content-Type: application/json

{
  "paiementId": 4,
  "statut": "CONFIRME",
  "notes": "Virement bancaire reçu"
}
```

**Vérification :**
```http
GET http://localhost:8081/api/confirmations-paiement
GET http://localhost:8081/api/confirmations-paiement/paiement/1
```

---

## 💸 **ÉTAPE 9 : CRÉATION DES DÉPENSES**

### 9.1 Matériel scolaire pour Fatou
```http
POST http://localhost:8081/api/depenses
Content-Type: application/json

{
  "montant": 25.0,
  "typeDepense": "MATERIEL_SCOLAIRE",
  "description": "Livres, cahiers et stylos pour Fatou",
  "associationId": 1,
  "dateDepense": "2024-10-02"
}
```

### 9.2 Uniforme pour Ibrahim
```http
POST http://localhost:8081/api/depenses
Content-Type: application/json

{
  "montant": 20.0,
  "typeDepense": "UNIFORME",
  "description": "Uniforme scolaire pour Ibrahim",
  "associationId": 1,
  "dateDepense": "2024-10-02"
}
```

### 9.3 Fournitures pour Aicha
```http
POST http://localhost:8081/api/depenses
Content-Type: application/json

{
  "montant": 18.0,
  "typeDepense": "MATERIEL_SCOLAIRE",
  "description": "Cahiers et crayons pour Aicha",
  "associationId": 2,
  "dateDepense": "2024-10-02"
}
```

**Vérification :**
```http
GET http://localhost:8081/api/depenses
GET http://localhost:8081/api/depenses/association/1
GET http://localhost:8081/api/depenses/association/2
```

---

## 🔄 **ÉTAPE 10 : GESTION DES TRANSFERTS (OPTIONNEL)**

### 10.1 Demande de transfert (déménagement)
```http
POST http://localhost:8081/api/demandes-transfert
Content-Type: application/json

{
  "parrainId": 1,
  "enfantSourceId": 1,
  "enfantDestinationId": 2,
  "montantTransfert": 30.0,
  "motif": "Déménagement de la famille",
  "associationId": 1
}
```

### 10.2 Réponse du parrain
```http
PUT http://localhost:8081/api/demandes-transfert/1/reponse
Content-Type: application/json

{
  "statut": "ACCEPTE",
  "messageParrain": "J'accepte de transférer le parrainage vers Ibrahim"
}
```

---

## 📊 **VÉRIFICATIONS FINALES**

### Résumé des données créées
```http
# Associations
GET http://localhost:8081/api/associations

# Parents
GET http://localhost:8081/api/parents

# Sponsors
GET http://localhost:8081/api/parrains

# Enfants
GET http://localhost:8081/api/enfants

# Parrainages actifs
GET http://localhost:8081/api/parrainages

# Paiements confirmés
GET http://localhost:8081/api/paiements

# Dépenses
GET http://localhost:8081/api/depenses

# Statistiques par association
GET http://localhost:8081/api/associations/1/enfants
GET http://localhost:8081/api/associations/2/enfants

# Parrainages d'un enfant (Fatou avec 2 sponsors)
GET http://localhost:8081/api/parrainages/enfant/1
```

---

## 🎯 **RÉSULTATS ATTENDUS**

### Données créées :
- ✅ **2 Associations** (ID: 1, 2)
- ✅ **2 Parents** (ID: 1, 2)
- ✅ **4 Sponsors** (ID: 1, 2, 3, 4)
- ✅ **3 Enfants** (ID: 1, 2, 3)
- ✅ **4 Parrainages** (dont 2 pour Fatou)
- ✅ **4 Paiements** confirmés
- ✅ **3 Dépenses** pour matériel scolaire

### Cas d'usage testés :
- ✅ **Multi-parrainage** : Fatou parrainée par 2 sponsors
- ✅ **Workflow complet** : Parrainage → Paiement → Confirmation → Dépense
- ✅ **Gestion des associations** : Enfants répartis sur 2 associations
- ✅ **Types de paiement** : Virement, PayPal, Carte bancaire
- ✅ **Types de dépenses** : Matériel scolaire, Uniformes

---

## 🚨 **GESTION D'ERREURS**

### Erreurs communes et solutions :

1. **"Association non trouvée"**
   - Vérifier que l'association existe avec `GET /api/associations`
   - Utiliser le bon `associationId`

2. **"Parent non trouvé"**
   - Vérifier que le parent existe avec `GET /api/parents`
   - Utiliser le bon `parentId`

3. **"Parrain non trouvé"**
   - Vérifier que le sponsor existe avec `GET /api/parrains`
   - Utiliser le bon `parrainId`

4. **"Enfant non trouvé"**
   - Vérifier que l'enfant existe avec `GET /api/enfants`
   - Utiliser le bon `enfantId`

5. **"Paiement non trouvé"**
   - Vérifier que le paiement existe avec `GET /api/paiements`
   - Utiliser le bon `paiementId`

---

## 📝 **NOTES IMPORTANTES**

- **Ordre d'exécution** : Respecter l'ordre des étapes (Associations → Parents → Sponsors → Enfants → Parrainages → Paiements)
- **IDs** : Les IDs sont générés automatiquement, s'adapter selon les réponses
- **Encodage** : Éviter les caractères spéciaux dans les JSON (é → e, è → e, etc.)
- **Transactions** : Chaque paiement valide automatiquement le parrainage associé
- **Multi-parrainage** : Un enfant peut avoir plusieurs sponsors actifs

---

## 🎉 **SUCCÈS !**

Si toutes les étapes sont exécutées correctement, vous devriez avoir un système TPIONIC fonctionnel avec :
- Un workflow complet de parrainage
- Un enfant parrainé par 2 sponsors
- Des paiements et dépenses tracés
- Une gestion multi-associations

Le système est maintenant prêt pour la production ! 🚀
