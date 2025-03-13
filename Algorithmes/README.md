# PRI
PRI Modélisation automatique d'une impédance repository

## Le nouvel algorithme
Le nouvel algorithme est implémenté par la fonction Impedance_Estimation_Basic3. Elle prend en paramètres d'entrée les impédances empiriques, les fréquences, le nombre de condensateurs, le nombre de bobines, le coefficient de pondération pour les capacités et le coefficient de pondération pour les inductances. Un coefficient de 1 permet de ne pas avoir de pondération. Les nombres de bobines et de condensateurs doivent etre des entiers.
La fonction fournit en sortie les vecteurs de résistances, inductances et capacités estimés.

## Guide d'utilisation

###  Chargement de paquet d'optimisation sous Octave
Dans la fenetre de commande, exécutez la commande suivante : "pkg load optim"

###  Exécution du programme
Seule la fonction faisant le parcours était réimplémentée. Elle peut etre appelée de la meme manière que Impedance_Estimation_Basic2. Deux fichiers d'exemples "Example1_Drill.m" et "Example3_Lamp.m" peuvent etre directement exécutés.

