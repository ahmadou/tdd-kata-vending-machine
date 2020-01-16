# vending-machine

Le but de cet exercice est de modéliser un distributeur de boissons.
La machine prend en entrée de l'argent et donne en échange une bouteille.
Elle n'accepte en entrée que des pieces de 5 centimes, 10 centimes, 50 centimes 1 et deux euros.

Elle vend trois boissons :

Budweiser : 0,65 €

Leffe : 1,10 €

Paix Dieu : 1,75 €

Il est possible de se faire restituer son crédit d'argent tout moment en appuyant sur un bouton.


Cette approche de solution utilise un modele à base d'evenements.
La machine est constituée de plusieurs modules qui communique en envoyant un evenement à chaque fois qu'une condition est remplie.


