# Projet Greman

![Badge de version](https://img.shields.io/badge/version-1.0.0-blue)

## Description
Application bureautique qui permet de générer et de visualiser d’un côté 
le schéma d’un circuit électrique construit sous une forme plus simple à 
observer avec les valeurs de chaque composant, et de l’autre la mesure d’impédance 
fournie (module et phase) par l’utilisateur ainsi que celle résultant du circuit électrique construit. 


## Prérequis
Avant d'installer ce projet, assurez-vous d'avoir :

- [Java (OpenJDK 22.0.1 utilisé)](https://www.oracle.com/fr/java/technologies/downloads/)
- [Maven](https://maven.apache.org/)
- [Octave (9.4.0 utilisé)](https://octave.org/download)

## Installation

1. Clonez le dépôt :
   ```sh
   git clone https://github.com/reephoxe/GREMAN.git
   cd GREMAN
   ```

2. Compilez et installez les dépendances :
   ```sh
   mvn clean install
   ```

## Utilisation
Pour l'utiliser le .jar vérifier que le fichier soit au même niveau que le dossier Algorithmes (contenant les algorithmes Ocave) pour le bon fonctionnement ainsi que le dossier src pour les images du circuit.
Pour exécuter l'application (depuis une console):
```sh
java -jar GREMAN-1.0-shaded.jar
```

## Auteurs

Équipe PolyCircuit de Polytech Tours :

- VIOLET Martin
- SCHMID Floran
- GHESQUIERE Lilian
- MENNERUN Noé
- LAFOSSE Romain
- MARECESCHE Maxime
