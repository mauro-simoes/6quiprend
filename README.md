# 6quiprend

6-qui-prend ! est un jeu de cartes dont une partie peut opposer de 2 à 10 joueurs. Les 104 cartes portent chacune deux valeurs : une valeur numérique (comprise entre 1 et 104) qui permet de classer les cartes entre elles et un nombre de ”têtes de bœufs” (compris entre 1 et 7) qui représente la pénalité associée à la carte.

Le paquet de cartes est initialement mélangé et 10 cartes sont distribuées à chaque joueur. Les quatre premières cartes du paquet restant sont disposées sur la table faces visibles sous la forme d’une colonne. Chacune de ces cartes forme le début d’une série qui sera complétée au fur et à mesure du déroulement du jeu et qui ne pourra jamais comporter plus de 5 cartes en tout. Les cartes restantes ne seront pas employées et peuvent être mises de côté. Au début de chaque tour de jeu, chaque joueur choisit une de ses cartes et la pose face cachée devant lui. Une fois que chaque joueur a sélectionné une de ses cartes, celles-ci sont retournées de manière à être connues de tous.
Les joueurs placent une à une la carte qu’ils ont sélectionnée dans une des 4 séries qui s’y trouvent. Le joueur ayant choisi la carte de plus faible valeur commence, puis c’est au tour de celui qui a choisi la seconde carte la plus faible
et ainsi de suite. Une fois que tous les joueurs ont placé leur carte, un nouveau tour commence et cela jusqu'à ce que toutes les cartes distribuées aient été placées sur la table.

La série dans laquelle le joueur doit placer sa carte est déterminée quasi-automatiquement par un ensemble de 4 règles. Seule la dernière règle implique une décision complémentaire du joueur. 

1. Les cartes d’une même série sont toujours de valeurs croissantes. En conséquence, une nouvelle carte ne peut être posée à droite d’une série que si sa valeur est supérieure à la dernière carte de la série.
2. Une carte doit toujours être déposée dans la série où la différence entre sa valeur et celle de la dernière carte de la série est la plus faible.
3. Lorsqu’une sixième carte doit être déposée dans une série (en comportant donc déjà 5), le joueur ramasse les 5 cartes de la série (c’est sa pénalité) et la sixième forme alors le début d’une nouvelle série.
4. Si la carte devant être déposée par un joueur a une valeur si faible qu’elle ne peut aller dans aucune série, le joueur ramasse toutes les cartes d’une série de son choix et la carte du joueur forme alors le début d'une nouvelle série.
Les cartes ramassées par les joueurs ne vont pas dans leur main mais sont mises de côté. A la fin de la partie, le score de chaque joueur correspond à la somme des “têtes de bœuf" qu’il aura ramassées durant le jeu. Le joueur en ayant le moins est le vainqueur.

Les cartes dont la valeur se termine par un 5 valent 2 ”têtes de bœufs” ; celles dont la valeur se termine par un 0 en valent 3 ; celles dont la valeur est formée par deux chiffres égaux en valent 5 et les autres cartes valent une ”tête de bœufs”. Toutefois, la carte 55 vaut 7 ”têtes de bœuf” car sa valeur se termine par 5 (2 têtes) et est composée de deux chiffres égaux (5 têtes).

Pour jouer à une partie de 6 qui prend, il suffit d'écrire le nom des joueurs dans un fichier texte nommé “config.txt” situé dans le même répertoire que la classe Application et de lancer la partie. Il est préférable de lancer le programe dans un terminal.


