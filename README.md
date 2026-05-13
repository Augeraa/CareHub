# CareHub - Gestionnaire Hopital

## Groupe
- Prenom1 Nom1, Prenom2 Nom2, Prenom3 Nom3

## Domaine metier
Application de gestion hospitaliere permettant de gerer les patients, le personnel medical
(medecins et infirmiers), les soins, les salles et la file d'attente aux urgences.
Les donnees sont persistees en CSV et rechargees automatiquement au demarrage.

## Interface choisie
Option A - Java Swing. Choix justifie par la simplicite de deploiement sans serveur,
et la richesse des composants natifs Java (JTable, JDialog, tri integre).

## Compilation et execution
    javac -d out HopitalController.java CapaciteDepasseeException.java PatientDejaAdmisException.java ActeChirurgical.java Consultation.java Facturable.java Infirmier.java Medecin.java Patient.java PatientUrgence.java Personne.java Personnel.java Planifiable.java Salle.java Soignable.java Soin.java Urgence.java Gestionnaire.java PersistanceCSV.java AjoutPersonneDialog.java HopitalTableModel.java MainFrame.java ModifierPersonneDialog.java SallesPanel.java StatistiquesPanel.java UrgencesPanel.java
    java -cp out view.MainFrame

## Fonctionnalites implementees
- CRUD complet : Ajouter, Modifier, Supprimer toute personne
- Filtrage 3 criteres : Role + Nom + Date admission
- Tri dynamique : par Nom et par Role
- Statistiques : 8 indicateurs calcules dynamiquement
- Persistance CSV : sauvegarde/chargement automatique
- Exceptions metier : PatientDejaAdmisException, CapaciteDepasseeException
- File d'attente urgences avec PriorityQueue (priorite medicale)
- Gestion des salles et lits
- Dossier medical par patient (soins, facture)
- 4 vues distinctes : Liste principale, Statistiques, Urgences, Salles

## Architecture
- model/     : Personne, Personnel (abstraites), Soin (abstraite), + sous-classes + 4 interfaces
- util/      : Gestionnaire<T extends Personne> avec List, Set, TreeMap, PriorityQueue
- controller/: HopitalController
- view/      : MainFrame, HopitalTableModel, AjoutPersonneDialog, ModifierPersonneDialog,
               StatistiquesPanel, UrgencesPanel, SallesPanel
- exception/ : PatientDejaAdmisException, CapaciteDepasseeException

## Repartition des taches
| Membre   | Responsabilite                              |
|----------|---------------------------------------------|
| Membre 1 | model/ : classes abstraites + interfaces    |
| Membre 2 | util/ + controller/ : generics + streams    |
| Membre 3 | view/ : Swing + toutes les vues             |