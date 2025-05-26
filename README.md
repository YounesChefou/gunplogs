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
