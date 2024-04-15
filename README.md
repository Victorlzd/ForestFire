# ForestFire

## Description
ForestFire est simulateur de Feu de Forêt qui suit le modèle suivant :
La forêt est représentée par une grille de dimension h x l.

La dimension temporelle est discrétisée. Le déroulement de la simulation se fait donc étape par étape.

Dans l’état initial, une ou plusieurs cases sont en feu.

Si une case est en feu à l’étape t, alors à l’étape t+1 :

Le feu s'éteint dans cette case (la case est remplie de cendre et ne peut ensuite plus brûler)

et il y a une probabilité p que le feu se propage à chacune des 4 cases adjacentes

La simulation s’arrête lorsqu’il n’y a plus aucune case en feu

Les dimensions de la grille, la position des cases initialement en feu, ainsi que la probabilité de propagation, sont des paramètres du programme stockés dans un fichier de configuration (format libre).

## How to use
### Back
La partie Back prend en entrée un fichier Json contenant les dimensions de la grille ainsi que la probabilité de propagation et les cases contenant du feu au début de la simulation.
Le fichier pris en entrée doit être placé dans le répertoire d'exécution et nommé input.json

Le programme produit un fichier de sortie output.json qui contient les dimensions de la grille, le nombre d'étapes de la simulation ainsi que l'état de la grille à chaque étape

### Front
La partie Front est une page html/css/javascript. Après l'avoir ouverte dans votre navigateur utiliser le bouton choisissez un fichier pour sélectionner le fichier de résultat de simulation (output.json produit par le back) que vous voulez visualiser. Les boutons suivant et précédent permettent de parcourir les différentes étapes de la simulation.