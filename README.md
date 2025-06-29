# Devlog pour Gunplogs

Préface : Premier projet sur AndroidStudio, le but est de faire une application similaire à Discogs mais pour les
model kits Gunplas => avoir un système permettant d'ajouter des kits à sa collection, à sa wishlist et de pouvoir
chercher tous les kits existants.

## v0.1 :
 * Premiers pas sur AndroidStudio, tout est mis dans la MainActivity
 * Les données sont affichées mais non exploitables

## v0.2 : 
 * L'architecture des composants UI a été retravaillé avec 1 composant par fichier
 * Ajout d'un ViewModel pour gérer les interactions entre état et UI
 * Trois listes différentes : Tous les kits, Collection, Wishlist
 * Il est possible d'ajouter les kits de la première liste dans Collection et Wishlist
 * TODO: Optimiser les imports
 * TODO: changer la manière dont est gérée les différentes catégories, utiliser un Id au lieu de
 mettre des conditions sur des strings écrites en dur,
 * TODO: integrer une vraie database au lieu d'une List
 
## v0.2.1 :
 * Ajout d'une database crée à partir du fichier base_gunplog.json
 * TODO: Retablir les fonctions permettant de changer de catégorie => le faire à partir d'une requête dans le DAO
 * TODO: Retablir les fonctions permettant l'ajout des kits à la Collection ou Wishlist => FAIT, il faut le tester
 * TODO: Ajouter les routines permettant de changer les icones quand on appuie dessus 
 * TODO: changer la manière dont est gérée les différentes catégories, utiliser un Id au lieu de
 mettre des conditions sur des strings écrites en dur,
 * TODO: Ajout des tests unitaires sur la database
 
## v0.2.2 :
 * Changement de catégorie à nouveau possible
 * Ajout des kits dans la Collection et Wishlist
 * TODO: Ajouter les routines permettant de changer les icones quand on appuie dessus => ne fonctionne pas, continuer à chercher
 * TODO: quand on clique sur une carte, afficher une page complète avec le kit
 * TODO: Ajout des tests unitaires sur la database
 
## v0.2.2.1
 * Cliquer sur une carte affiche une page complète avec le kit avec bouton pour revenir à l'écran principal
 * TODO: Ajouter les routines permettant de changer les icones quand on appuie dessus => ne fonctionne toujours pas, même sur les pages individuelles
 * TODO: Ajout des tests unitaires sur la database
 * TODO: Refaire le script JSON avec la base de données car de nombreuses données sont fausses
