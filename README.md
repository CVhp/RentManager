# RentManager

Créé le 03.04.2020 
-------------------------------------------------- ProjetManager --------------------------------------------------
Site Web permetant la gestion des clients, véhicules et réservations. 

____ Getting Started _______________________________________________________________________________________________

Télécharger Eclipse du site suivant : https://www.eclipse.org/downloads/
Dans "eclipseinstaller", choisir la version "Eclipse IDE for Entreprise Java Developers".
Ce projet utilise Tomcat v9.0 (télécharger la version "Tomcat 9.0.33 Released" : http://tomcat.apache.org/). 


____ Deployment ____________________________________________________________________________________________________

Dans les properties du dossier de projet > Project Facets s'assurer d'avoir :
	Dynamic Web Module 4.0
	Java 1.8
	JavaScript 1.0

Créer un serveur Tomcat v9.0 en localhost. 
Le dossier WebContent sera utilisé quand le serveur sera lancé avec Eclipse.

____ Technical Choices _____________________________________________________________________________________________
-------- Ajout d'un package validator contenant les méthodes de validation des inputs

-------- Ajout de fonctions get() dans Client(), Vehicle(), Reservation()
-------------- Client()
getListVehicle(Client client) : retourne la liste de véhicules associés aux réservations du client en question
-------------- Vehicle()
getListClient(Vehicle vehicle) : retourne la liste de clients associés aux réservations du véhicule en question
-------------- Reservation()
getVehicle() : retourne l'objet Vehicle associé à la reservation en question
getClient() : retourne l'objet Client associé à la reservation en question 

-------- Ajout de fichiers .jsp et Sevlets afin de permettre à l'utilisateur de modifier et d'afficher les détails
-------------- Modifier

1) Une requête SQL a été créée dans les différentes classes Dao afin de permettre de faire un UPDATE de la table. 
L'utilisateur a la possibilité de changer tous les paramètres SAUF l'ID de l'objet en question. Cela permet de 
modifier uniquement les informations voulues tout en gardant le même ID et donc de ne pas perdre les 
jonctions avec les autres objets.
Exemple : si l'utilisateur du site décide de modifier l'adresse email d'un client, une fois la modification effectuée, 
ID de ce client restera inchangée et le client sera donc toujours lié à ses réservations. 

2) Pour les modifications d'un client et d'un véhicule, les informations avant modification seront affichées. 
Il est ainsi plus simple pour l'utilisateur de modifier seulement l'information voulue. Il n'a pas à renseigner 
une nouvelle fois tous les champs.

-------------- Details

____ Fonctionalities _____________________________________________________________________________________________


____ Author ________________________________________________________________________________________________________
Cécile VANHELLEPUTTE - EPF - Majeure MIN - P2021
