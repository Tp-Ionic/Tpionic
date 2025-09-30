# Guide API Complet - Système de Parrainage

## 🚀 Démarrage de l'application

```bash
# Démarrer l'application
cd tpionic
mvn spring-boot:run

# L'application sera disponible sur : http://localhost:8081
```

---

## 📋 Table des matières

1. [Associations](#associations)
2. [Enfants](#enfants)
3. [Parents](#parents)
4. [Parrains](#parrains)
5. [Parrainages](#parrainages)
6. [Paiements](#paiements)
7. [Dépenses](#dépenses)
8. [Frais Scolaires](#frais-scolaires)
9. [Confirmations de Paiement](#confirmations-de-paiement)
10. [Reçus de Dépense](#reçus-de-dépense)
11. [Parrainage-Dépense](#parrainage-dépense)
12. [Demandes de Transfert](#demandes-de-transfert)

---

## 🏢 Associations

### Créer une association (statut EN_ATTENTE par défaut)
```http
POST /api/associations
Content-Type: application/json

{
  "nom": "Association Solidarité Mali",
  "email": "contact@solidarite-mali.org",
  "motDePasse": "motdepasse123",
  "telephone": "+223123456789",
  "adresse": "123 Rue de la Paix",
  "pays": "Mali",
  "ville": "Bamako",
  "description": "Association pour l'éducation des enfants défavorisés",
  "logoUrl": "https://example.com/logo.png"
}
```

### Récupérer toutes les associations
```http
GET /api/associations
```

### Récupérer une association par ID
```http
GET /api/associations/1
```

### Mettre à jour une association
```http
PUT /api/associations/1
Content-Type: application/json

{
  "nom": "Association Solidarité Mali - Mise à jour",
  "telephone": "+223123456790",
  "description": "Description mise à jour"
}
```

### Supprimer une association
```http
DELETE /api/associations/1
```

### Récupérer les associations en attente (pour l'admin)
```http
GET /api/associations/en-attente
```

### Valider une association (pour l'admin)
```http
PUT /api/associations/1/valider?statut=ACCEPTE
```

```http
PUT /api/associations/1/valider?statut=REFUSE
```

### Ajouter un enfant à une association
```http
POST /api/associations/1/enfants
Content-Type: application/json

{
  "nom": "Traoré",
  "prenom": "Fatoumata",
  "dateNaissance": "2012-03-15",
  "adresse": "456 Rue de l'Espoir",
  "age": 11,
  "apropos_de_enfant": "Enfant brillante et motivée",
  "parentNom": "Traoré",
  "parentPrenom": "Moussa",
  "parentEmail": "moussa.traore@email.com",
  "parentMotDePasse": "motdepasse123",
  "parentTelephone": "+223123456791",
  "parentAdresse": "456 Rue de l'Espoir",
  "parentPays": "Mali",
  "parentVille": "Bamako",
  "parentProfession": "Commerçant",
  "parentRelationAvecEnfant": "Père"
}
```

### Récupérer les enfants d'une association
```http
GET /api/associations/1/enfants
```

### Upload de fichiers pour un enfant
```http
POST /api/associations/1/enfants/1/upload-bulletin
Content-Type: multipart/form-data

file: [fichier PDF du bulletin]
```

```http
POST /api/associations/1/enfants/1/upload-photo
Content-Type: multipart/form-data

file: [fichier image de l'activité]
```

```http
POST /api/associations/1/enfants/1/upload-presence
Content-Type: multipart/form-data

file: [fichier PDF de la liste de présence]
```

---

## 👶 Enfants

### Récupérer tous les enfants
```http
GET /api/enfants
```

### Récupérer un enfant par ID
```http
GET /api/enfants/1
```

### Mettre à jour un enfant
```http
PUT /api/enfants/1
Content-Type: application/json

{
  "nom": "Traoré",
  "prenom": "Fatoumata",
  "age": 12,
  "apropos_de_enfant": "Enfant brillante et motivée - mise à jour"
}
```

### Supprimer un enfant
```http
DELETE /api/enfants/1
```

### Récupérer les enfants d'une association
```http
GET /api/enfants/association/1
```

### Récupérer les enfants non parrainés d'une association
```http
GET /api/enfants/association/1/non-parraines
```

### Rechercher des enfants par nom
```http
GET /api/enfants/search/nom/Traoré
```

### Rechercher des enfants par prénom
```http
GET /api/enfants/search/prenom/Fatoumata
```

---

## 👨‍👩‍👧‍👦 Parents

### Créer un parent
```http
POST /api/parents
Content-Type: application/json

{
  "nom": "Traoré",
  "prenom": "Moussa",
  "email": "moussa.traore@email.com",
  "motDePasse": "motdepasse123",
  "telephone": "+223123456791",
  "adresse": "456 Rue de l'Espoir",
  "pays": "Mali",
  "ville": "Bamako",
  "profession": "Commerçant",
  "relationAvecEnfant": "Père"
}
```

### Récupérer tous les parents
```http
GET /api/parents
```

### Récupérer un parent par ID
```http
GET /api/parents/1
```

### Récupérer un parent par email
```http
GET /api/parents/email/moussa.traore@email.com
```

### Mettre à jour un parent
```http
PUT /api/parents/1
Content-Type: application/json

{
  "telephone": "+223123456792",
  "profession": "Commerçant - Mise à jour"
}
```

### Supprimer un parent
```http
DELETE /api/parents/1
```

### Rechercher des parents
```http
GET /api/parents/search/nom/Traoré
GET /api/parents/search/prenom/Moussa
GET /api/parents/search/pays/Mali
GET /api/parents/search/ville/Bamako
GET /api/parents/search/profession/Commerçant
GET /api/parents/search/relation/Père
```

---

## 👨‍💼 Parrains

### Créer un parrain
```http
POST /api/parrains
Content-Type: application/json

{
  "nom": "Diallo",
  "prenom": "Bakary",
  "email": "Boobadiallo@email.com",
  "motDePasse": "motdepasse123",
  "telephone": "+22374309564",
  "pays": "France",
  "ville": "Paris",w
  "avatarUrl": "https://example.com/avatar.jpg"
}
```

### Récupérer tous les parrains
```http
GET /api/parrains
```

### Récupérer un parrain par ID
```http
GET /api/parrains/1
```

### Mettre à jour un parrain
```http
PUT /api/parrains/1
Content-Type: application/json

{
  "telephone": "+33123456790",
  "ville": "Lyon"
}
```

### Supprimer un parrain
```http
DELETE /api/parrains/1
```

### Récupérer les enfants parrainés par un parrain
```http
GET /api/parrains/1/enfants-parraines
```

### Récupérer les rapports scolaires d'un enfant parrainé
```http
GET /api/parrains/1/enfants/1/rapports-scolaires
```

### Récupérer les bulletins d'un enfant parrainé
```http
GET /api/parrains/1/enfants/1/bulletins
```

### Récupérer les photos d'activités d'un enfant parrainé
```http
GET /api/parrains/1/enfants/1/photos-activites
```

### Récupérer les listes de présence d'un enfant parrainé
```http
GET /api/parrains/1/enfants/1/listes-presence
```

### Récupérer les confirmations de paiement d'un parrain
```http
GET /api/parrains/1/confirmations-paiement
```

### Récupérer les reçus de dépense d'un parrain
```http
GET /api/parrains/1/recus-depense
```

### Récupérer le résumé complet d'un enfant parrainé
```http
GET /api/parrains/1/enfants/1/resume-complet
```

---

## 🤝 Parrainages

### Créer une demande de parrainage (par l'association)
```http
POST /api/parrainages
Content-Type: application/json

{
  "parrainId": 1,
  "enfantId": 1,
  "associationId": 1,
  "montantMensuel": 50.0,
  "messageDemande": "Demande de parrainage pour l'année scolaire 2024",
  "notes": "Parrainage pour l'année scolaire 2024"
}
```

### Parrainer un enfant (Nouveau - Interface Simplifiée)
```http
POST /api/parrainages/parrainer-enfant
Content-Type: application/json

{
  "parrainId": 1,
  "enfantId": 5
}
```

**Réponse :**
```json
{
  "id": 1,
  "parrainId": 1,
  "parrainNom": "Martin",
  "parrainPrenom": "Pierre",
  "enfantId": 5,
  "enfantNom": "Traoré",
  "enfantPrenom": "Fatoumata",
  "associationId": 2,
  "associationNom": "École Primaire de Paris",
  "statut": "EN_ATTENTE_PAIEMENT",
  "dateDemande": "2024-02-15",
  "montantMensuel": 50.0,
  "messageDemande": "Demande de parrainage créée via l'interface",
  "notes": "Parrainage créé par confirmation du modal"
}
```

### Obtenir les informations de confirmation pour le modal
```http
GET /api/parrainages/confirmation/1/5
```

**Réponse pour le modal :**
```json
{
  "parrainNom": "Martin Pierre",
  "enfantNom": "Traoré Fatoumata",
  "enfantAge": 11,
  "associationNom": "École Primaire de Paris",
  "montantMensuel": 50.0,
  "message": "Êtes-vous sûr de vouloir parrainer Traoré Fatoumata ?"
}
```

### Effectuer le paiement pour valider le parrainage
```http
POST /api/paiements
Content-Type: application/json

{
  "montant": 50.0,
  "typePaiement": "VIREMENT_BANCAIRE",
  "datePaiement": "2024-02-15",
  "reference": "PAY-2024-003",
  "notes": "Paiement pour valider le parrainage",
  "parrainId": 1,
  "enfantId": 5
}
```

**Résultat :** Le parrainage passe automatiquement au statut "ACTIF" après le paiement !

### Créer un parrainage direct (par le parrain - Ancien système)
```http
POST /api/parrainages/direct
Content-Type: application/json

{
  "parrainId": 1,
  "enfantId": 1,
  "montantMensuel": 50.0,
  "messageDemande": "Je souhaite parrainer cet enfant",
  "notes": "Parrainage direct pour l'année scolaire 2024"
}
```

### Récupérer tous les parrainages
```http
GET /api/parrainages
```

---

## 🔄 Workflow Complet : Parrainage Simplifié avec Modal de Confirmation

### Étape 1 : Le parrain voit la liste des enfants d'une association
```http
GET /api/enfants/association/1/non-parraines
```

### Étape 2 : Le parrain clique sur "Parrainer cet enfant" et obtient les informations pour le modal
```http
GET /api/parrainages/confirmation/1/5
```

**Réponse pour le modal :**
```json
{
  "parrainNom": "Martin Pierre",
  "enfantNom": "Traoré Fatoumata",
  "enfantAge": 11,
  "associationNom": "École Primaire de Paris",
  "montantMensuel": 50.0,
  "message": "Êtes-vous sûr de vouloir parrainer Traoré Fatoumata ?"
}
```

### Étape 3 : Le parrain clique sur "Confirmer" dans le modal
```http
POST /api/parrainages/parrainer-enfant
Content-Type: application/json

{
  "parrainId": 1,
  "enfantId": 5
}
```

**Réponse :**
```json
{
  "id": 1,
  "parrainId": 1,
  "parrainNom": "Martin",
  "parrainPrenom": "Pierre",
  "enfantId": 5,
  "enfantNom": "Traoré",
  "enfantPrenom": "Fatoumata",
  "associationId": 2,
  "associationNom": "École Primaire de Paris",
  "statut": "EN_ATTENTE_PAIEMENT",
  "dateDemande": "2024-02-15",
  "montantMensuel": 50.0,
  "messageDemande": "Demande de parrainage créée via l'interface",
  "notes": "Parrainage créé par confirmation du modal"
}
```

### Étape 4 : Le parrain effectue immédiatement le paiement
```http
POST /api/paiements
Content-Type: application/json

{
  "montant": 50.0,
  "typePaiement": "VIREMENT_BANCAIRE",
  "datePaiement": "2024-02-15",
  "reference": "PAY-2024-003",
  "notes": "Paiement pour valider le parrainage",
  "parrainId": 1,
  "enfantId": 5
}
```

### Étape 5 : Le parrainage est automatiquement validé !
- ✅ Le parrainage passe au statut "ACTIF"
- ✅ L'enfant devient le filleul du parrain
- ✅ Le parrain peut maintenant suivre les rapports scolaires, bulletins, etc.

### Étape 6 : Le parrain peut voir ses filleuls actifs
```http
GET /api/parrains/1/enfants-parraines
```

### Interface Utilisateur Simplifiée

1. **Liste des enfants** → Clic sur un enfant → **Informations détaillées**
2. **Bouton "Parrainer cet enfant"** → **Modal de confirmation**
3. **Modal avec message prédéfini** → Boutons "Annuler" / "Confirmer"
4. **Clic sur "Confirmer"** → **Redirection vers paiement**
5. **Paiement effectué** → **Parrainage validé automatiquement**

---

## 🚫 Absences

### Créer une déclaration d'absence temporaire (par le parent)
```http
POST /api/absences
Content-Type: application/json

{
  "parentId": 1,
  "enfantId": 1,
  "dateDebut": "2024-12-01",
  "dateFin": "2024-12-15",
  "raison": "Voyage familial",
  "details": "L'enfant doit voyager avec sa famille pour des raisons personnelles",
  "typeAbsence": "TEMPORAIRE"
}
```

### Créer une déclaration de déménagement définitif (par le parent)
```http
POST /api/absences
Content-Type: application/json

{
  "parentId": 1,
  "enfantId": 1,
  "dateDebut": "2024-02-01",
  "dateFin": "2024-02-01",
  "raison": "Déménagement définitif",
  "details": "La famille déménage dans une autre ville et quitte l'association",
  "typeAbsence": "DEMENAGEMENT_DEFINITIF"
}
```

### Récupérer toutes les absences
```http
GET /api/absences
```

### Récupérer une absence par ID
```http
GET /api/absences/1
```

### Récupérer les absences d'un parent
```http
GET /api/absences/parent/1
```

### Récupérer les absences d'un enfant
```http
GET /api/absences/enfant/1
```

### Récupérer les absences d'une association
```http
GET /api/absences/association/1
```

### Récupérer les absences en attente d'une association
```http
GET /api/absences/association/1/en-attente
```

### Récupérer les absences actives
```http
GET /api/absences/actives
```

### Récupérer les absences actives d'un enfant
```http
GET /api/absences/enfant/1/actives
```

### Mettre à jour une absence
```http
PUT /api/absences/1
Content-Type: application/json

{
  "dateDebut": "2024-12-02",
  "dateFin": "2024-12-16",
  "raison": "Voyage familial - dates modifiées",
  "details": "Dates de voyage modifiées"
}
```

### Valider une absence (pour l'association)
```http
PUT /api/absences/1/valider
Content-Type: application/json

{
  "statut": "ACCEPTE",
  "reponseAssociation": "Absence acceptée. Bon voyage !"
}
```

```http
PUT /api/absences/1/valider
Content-Type: application/json

{
  "statut": "REFUSE",
  "reponseAssociation": "Absence refusée. Raison : période d'examens"
}
```

### Supprimer une absence
```http
DELETE /api/absences/1
```

### Récupérer les absences de déménagement par association
```http
GET /api/absences/association/1/demenagement
```

### Récupérer les absences de déménagement en attente par association
```http
GET /api/absences/association/1/demenagement/en-attente
```

### Récupérer les absences de déménagement acceptées par association
```http
GET /api/absences/association/1/demenagement/acceptees
```

### Récupérer un parrainage par ID
```http
GET /api/parrainages/1
```

### Récupérer les parrainages d'un parrain
```http
GET /api/parrainages/parrain/1
```

### Récupérer les parrainages d'un enfant
```http
GET /api/parrainages/enfant/1
```

### Récupérer les parrainages d'une association
```http
GET /api/parrainages/association/1
```

### Récupérer les parrainages en attente d'un parrain
```http
GET /api/parrainages/parrain/1/en-attente
```

### Récupérer les demandes de parrainage en attente de paiement d'un parrain
```http
GET /api/parrainages/parrain/1/en-attente-paiement
```

**Réponse :**
```json
[
  {
    "id": 1,
    "parrainId": 1,
    "parrainNom": "Martin",
    "parrainPrenom": "Pierre",
    "enfantId": 1,
    "enfantNom": "Traoré",
    "enfantPrenom": "Fatoumata",
    "associationId": 2,
    "associationNom": "École Primaire de Paris",
    "statut": "EN_ATTENTE_PAIEMENT",
    "dateDemande": "2024-02-10",
    "montantMensuel": 50.0,
    "messageDemande": "Je souhaite parrainer cet enfant",
    "notes": "En attente du paiement pour valider le parrainage"
  }
]
```

### Accepter un parrainage
```http
POST /api/parrainages/1/accepter
Content-Type: application/json

{
  "notes": "J'accepte de parrainer cet enfant"
}
```

### Refuser un parrainage
```http
POST /api/parrainages/1/refuser
Content-Type: application/json

{
  "notes": "Je ne peux pas parrainer pour le moment"
}
```

### Mettre à jour un parrainage
```http
PUT /api/parrainages/1
Content-Type: application/json

{
  "montantMensuel": 60.0,
  "notes": "Augmentation du montant mensuel"
}
```

### Supprimer un parrainage
```http
DELETE /api/parrainages/1
```

---

## 💰 Paiements

### Créer un paiement
```http
POST /api/paiements
Content-Type: application/json

{
  "montant": 200.0,
  "typePaiement": "VIREMENT_BANCAIRE",
  "datePaiement": "2024-01-15",
  "reference": "PAY-2024-001",
  "notes": "Paiement pour le parrainage de Fatoumata",
  "parrainId": 1,
  "enfantId": 1
}
```

### Récupérer tous les paiements
```http
GET /api/paiements
```

### Récupérer un paiement par ID
```http
GET /api/paiements/1
```

### Mettre à jour un paiement
```http
PUT /api/paiements/1
Content-Type: application/json

{
  "montant": 250.0,
  "notes": "Paiement mis à jour"
}
```

### Supprimer un paiement
```http
DELETE /api/paiements/1
```

---

## 💸 Dépenses

### Créer une dépense
```http
POST /api/depenses
Content-Type: application/json

{
  "description": "Achat de fournitures scolaires",
  "montant": 75.0,
  "typeDepense": "FOURNITURES_SCOLAIRES",
  "dateDepense": "2024-01-20",
  "justificatifUrl": "https://example.com/justificatif.pdf",
  "notes": "Fournitures pour le trimestre 1",
  "paiementId": 1,
  "enfantId": 1,
  "associationId": 1
}
```

### Récupérer toutes les dépenses
```http
GET /api/depenses
```

### Récupérer une dépense par ID
```http
GET /api/depenses/1
```

### Mettre à jour une dépense
```http
PUT /api/depenses/1
Content-Type: application/json

{
  "montant": 80.0,
  "notes": "Dépense mise à jour"
}
```

### Supprimer une dépense
```http
DELETE /api/depenses/1
```

### Récupérer les dépenses d'un paiement
```http
GET /api/depenses/paiement/1
```

### Récupérer les dépenses d'un enfant
```http
GET /api/depenses/enfant/1
```

### Récupérer les dépenses d'une association
```http
GET /api/depenses/association/1
```

---

## 📚 Frais Scolaires

### Créer des frais scolaires
```http
POST /api/frais-scolaires
Content-Type: application/json

{
  "montant": 100.0,
  "typeFrais": "FRAIS_INSCRIPTION",
  "dateEcheance": "2024-02-01",
  "statut": "EN_ATTENTE",
  "notes": "Frais d'inscription pour l'année 2024",
  "enfantId": 1
}
```

### Récupérer tous les frais scolaires
```http
GET /api/frais-scolaires
```

### Récupérer des frais scolaires par ID
```http
GET /api/frais-scolaires/1
```

### Récupérer les frais scolaires d'un enfant
```http
GET /api/frais-scolaires/enfant/1
```

### Mettre à jour des frais scolaires
```http
PUT /api/frais-scolaires/1
Content-Type: application/json

{
  "statut": "PAYE",
  "notes": "Frais payés"
}
```

### Supprimer des frais scolaires
```http
DELETE /api/frais-scolaires/1
```

---

## ✅ Confirmations de Paiement

### Créer une confirmation de paiement
```http
POST /api/confirmations-paiement
Content-Type: application/json

{
  "paiementId": 1,
  "statut": "CONFIRME",
  "dateConfirmation": "2024-01-16",
  "notes": "Paiement confirmé par la banque",
  "associationId": 1
}
```

### Récupérer toutes les confirmations
```http
GET /api/confirmations-paiement
```

### Récupérer une confirmation par ID
```http
GET /api/confirmations-paiement/1
```

### Récupérer les confirmations d'un paiement
```http
GET /api/confirmations-paiement/paiement/1
```

### Récupérer les confirmations d'une association
```http
GET /api/confirmations-paiement/association/1
```

### Récupérer les confirmations par statut
```http
GET /api/confirmations-paiement/statut/CONFIRME
```

### Mettre à jour une confirmation
```http
PUT /api/confirmations-paiement/1
Content-Type: application/json

{
  "statut": "REJETE",
  "notes": "Paiement rejeté par la banque"
}
```

### Supprimer une confirmation
```http
DELETE /api/confirmations-paiement/1
```

---

## 🧾 Reçus de Dépense

### Créer un reçu de dépense
```http
POST /api/recus-depense
Content-Type: application/json

{
  "depenseId": 1,
  "statut": "EN_ATTENTE",
  "dateReception": "2024-01-21",
  "notes": "Reçu en attente de validation",
  "associationId": 1
}
```

### Récupérer tous les reçus
```http
GET /api/recus-depense
```

### Récupérer un reçu par ID
```http
GET /api/recus-depense/1
```

### Récupérer les reçus d'un parrain
```http
GET /api/recus-depense/parrain/1
```

### Récupérer les reçus d'une association
```http
GET /api/recus-depense/association/1
```

### Récupérer les reçus d'une dépense
```http
GET /api/recus-depense/depense/1
```

### Récupérer les reçus par statut
```http
GET /api/recus-depense/statut/EN_ATTENTE
```

### Mettre à jour un reçu
```http
PUT /api/recus-depense/1
Content-Type: application/json

{
  "statut": "VALIDE",
  "notes": "Reçu validé"
}
```

### Supprimer un reçu
```http
DELETE /api/recus-depense/1
```

---

## 🔗 Parrainage-Dépense

### Créer une relation parrainage-dépense
```http
POST /api/parrainage-depense
Content-Type: application/json

{
  "parrainageId": 1,
  "depenseId": 1,
  "montant": 50.0,
  "statut": "EN_ATTENTE",
  "notes": "Dépense liée au parrainage"
}
```

### Récupérer les relations d'un parrainage
```http
GET /api/parrainage-depense/parrainage/1
```

### Récupérer les relations d'un parrain
```http
GET /api/parrainage-depense/parrain/1
```

### Récupérer les relations d'un enfant
```http
GET /api/parrainage-depense/enfant/1
```

### Récupérer une relation par ID de dépense
```http
GET /api/parrainage-depense/depense/1
```

### Récupérer le résumé d'une dépense
```http
GET /api/parrainage-depense/depense/1/resume
```

### Mettre à jour une relation
```http
PUT /api/parrainage-depense/1
Content-Type: application/json

{
  "montant": 60.0,
  "notes": "Montant mis à jour"
}
```

### Confirmer une relation
```http
PUT /api/parrainage-depense/1/confirmer
Content-Type: application/json

{
  "notes": "Relation confirmée"
}
```

### Refuser une relation
```http
PUT /api/parrainage-depense/1/refuser
Content-Type: application/json

{
  "notes": "Relation refusée"
}
```

### Supprimer une relation
```http
DELETE /api/parrainage-depense/1
```

---

## 🔄 Demandes de Transfert

### Créer une demande de transfert (par l'association)
```http
POST /api/demandes-transfert
Content-Type: application/json

{
  "associationId": 2,
  "enfantSourceId": 5,
  "enfantDestinationId": 8,
  "parrainId": 3,
  "parrainageSourceId": 12,
  "montantTransfert": 500.00,
  "raisonDemande": "Transfert suite au déménagement définitif",
  "detailsDemande": "L'enfant source a déménagé, transfert vers un nouvel enfant",
  "notes": "Transfert automatique suite au déménagement"
}
```

### Récupérer toutes les demandes de transfert
```http
GET /api/demandes-transfert
```

### Récupérer une demande de transfert par ID
```http
GET /api/demandes-transfert/1
```

### Récupérer les demandes de transfert par association
```http
GET /api/demandes-transfert/association/2
```

### Récupérer les demandes de transfert par parrain
```http
GET /api/demandes-transfert/parrain/3
```

### Récupérer les demandes de transfert en attente
```http
GET /api/demandes-transfert/en-attente
```

### Récupérer les statistiques de transfert par association
```http
GET /api/demandes-transfert/statistiques/association/2
```

**Réponse :**
```json
{
  "totalDemandes": 15,
  "demandesEnAttente": 3,
  "demandesAcceptees": 10,
  "demandesRefusees": 2,
  "montantTotalTransfere": 5000.00,
  "montantEnAttente": 750.00
}
```

### Réponse du parrain à la demande
```http
PUT /api/demandes-transfert/1/reponse-parrain
Content-Type: application/json

{
  "reponseParrain": "ACCEPTE",
  "commentaireParrain": "J'accepte de parrainer le nouvel enfant"
}
```

```http
PUT /api/demandes-transfert/1/reponse-parrain
Content-Type: application/json

{
  "reponseParrain": "REFUSE",
  "commentaireParrain": "Je ne peux pas parrainer pour le moment"
}
```

### Validation finale par l'association
```http
PUT /api/demandes-transfert/1/valider
Content-Type: application/json

{
  "reponseAssociation": "VALIDE",
  "commentaireAssociation": "Transfert validé et effectué",
  "dateTransfertEffectif": "2024-02-15"
}
```

```http
PUT /api/demandes-transfert/1/valider
Content-Type: application/json

{
  "reponseAssociation": "REFUSE",
  "commentaireAssociation": "Transfert refusé pour des raisons administratives"
}
```

### Supprimer une demande de transfert
```http
DELETE /api/demandes-transfert/1
```

### Exemple de réponse complète d'une demande de transfert
```json
{
  "id": 1,
  "associationId": 2,
  "associationNom": "École Primaire de Paris",
  "enfantSourceId": 5,
  "enfantSourceNom": "Traoré",
  "enfantSourcePrenom": "Fatoumata",
  "enfantDestinationId": 8,
  "enfantDestinationNom": "Diallo",
  "enfantDestinationPrenom": "Aminata",
  "parrainId": 3,
  "parrainNom": "Martin",
  "parrainPrenom": "Pierre",
  "parrainageSourceId": 12,
  "montantTransfert": 500.00,
  "statut": "ACCEPTE",
  "dateDemande": "2024-02-10T10:30:00",
  "raisonDemande": "Transfert suite au déménagement définitif",
  "detailsDemande": "L'enfant source a déménagé, transfert vers un nouvel enfant",
  "reponseParrain": "ACCEPTE",
  "reponseAssociation": "VALIDE",
  "dateReponseParrain": "2024-02-12T14:20:00",
  "dateReponseAssociation": "2024-02-14T09:15:00",
  "notes": "Transfert automatique suite au déménagement",
  "dateTransfertEffectif": "2024-02-15",
  "nouveauParrainageId": 25,
  "nouveauParrainageStatut": "ACTIF"
}
```

---

## 🔄 Workflow Complet : Déménagement et Transfert de Fonds

### Étape 1 : Déclaration de déménagement par le parent
```http
POST /api/absences
Content-Type: application/json

{
  "parentId": 1,
  "enfantId": 5,
  "dateDebut": "2024-02-01",
  "dateFin": "2024-02-01",
  "raison": "Déménagement définitif",
  "details": "La famille déménage dans une autre ville et quitte l'association",
  "typeAbsence": "DEMENAGEMENT_DEFINITIF"
}
```

### Étape 2 : Validation par l'association
```http
PUT /api/absences/1/valider
Content-Type: application/json

{
  "statut": "ACCEPTE",
  "reponseAssociation": "Déménagement accepté. Demande de transfert de fonds initiée."
}
```

### Étape 3 : Création de la demande de transfert par l'association
```http
POST /api/demandes-transfert
Content-Type: application/json

{
  "associationId": 2,
  "enfantSourceId": 5,
  "enfantDestinationId": 8,
  "parrainId": 3,
  "parrainageSourceId": 12,
  "montantTransfert": 500.00,
  "raisonDemande": "Transfert suite au déménagement définitif",
  "detailsDemande": "L'enfant source a déménagé, transfert vers un nouvel enfant",
  "notes": "Transfert automatique suite au déménagement"
}
```

### Étape 4 : Réponse du parrain
```http
PUT /api/demandes-transfert/1/reponse-parrain
Content-Type: application/json

{
  "reponseParrain": "ACCEPTE",
  "commentaireParrain": "J'accepte de parrainer le nouvel enfant"
}
```

### Étape 5 : Finalisation par l'association
```http
PUT /api/demandes-transfert/1/valider
Content-Type: application/json

{
  "reponseAssociation": "VALIDE",
  "commentaireAssociation": "Transfert validé et effectué",
  "dateTransfertEffectif": "2024-02-15"
}
```

### Résultat : Transfert automatique effectué
- ✅ Parrainage source terminé (statut: TERMINE)
- ✅ Nouveau parrainage créé pour l'enfant destination (statut: ACTIF)
- ✅ Montant mensuel conservé
- ✅ Historique complet tracé

---

## 📁 Accès aux fichiers

### Télécharger un fichier
```http
GET /api/associations/files/{nomDuFichier}
```

---

## 🧪 Script de test

Utilisez le fichier `test-api.bat` pour tester automatiquement les endpoints :

```batch
@echo off
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
```

---

## 📝 Notes importantes

1. **Authentification** : L'authentification a été supprimée et sera gérée par Spring Security
2. **Enfants** : Les enfants n'ont plus d'email/password car ils n'accèdent pas directement au système
3. **Parents** : Créés automatiquement lors de l'ajout d'un enfant
4. **Port** : L'application tourne sur le port 8081
5. **Base de données** : MySQL avec schéma recréé à chaque démarrage (`create-drop`)
6. **Parrainage direct** : Un parrain peut maintenant parrainer directement un enfant sans demande préalable
7. **Enfants non parrainés** : Endpoint pour lister les enfants disponibles au parrainage
8. **Statut des associations** : Les associations sont créées avec le statut "EN_ATTENTE" et doivent être validées par l'admin
9. **Déclaration d'absence** : Les parents peuvent déclarer des absences temporaires ou définitives (déménagement) pour leurs enfants
10. **Transfert de fonds** : Système de transfert de parrainage lors des déménagements d'enfants
11. **Types d'absence** : TEMPORAIRE (voyage, maladie) et DEMENAGEMENT_DEFINITIF (quitte l'association)
12. **Workflow de transfert** : Validation en deux étapes (parrain + association) pour les transferts
13. **Parrainage avec paiement obligatoire** : Nouveau système où le paiement valide automatiquement le parrainage
14. **Statuts de parrainage** : EN_ATTENTE_PAIEMENT (nouveau), EN_ATTENTE, ACCEPTE, REFUSE, ACTIF, TERMINE
15. **Validation automatique** : Le parrainage passe à ACTIF dès qu'un paiement est effectué

---

## 🔧 Dépannage

Si l'application ne démarre pas :

1. Vérifiez que MySQL est démarré
2. Vérifiez que le port 8081 est libre
3. Utilisez les scripts `start-app.bat` ou `start-app.ps1`
4. Consultez les logs pour plus d'informations

---

*Guide mis à jour le 26/09/2024 - Version avec système de transfert de fonds pour déménagements*
