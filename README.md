# RentManager

Créé le 03.04.2020 
-------------------------------------------------- ProjetManager --------------------------------------------------
Site Web permetant la gestion des clients, véhicules et réservations. 

____ Getting Started _______________________________________________________________________________________________

+ Télécharger Eclipse du site suivant : https://www.eclipse.org/downloads/
+ Dans "eclipseinstaller", choisir la version "Eclipse IDE for Entreprise Java Developers".
+ Ce projet utilise Tomcat v9.0 (télécharger la version "Tomcat 9.0.33 Released" : http://tomcat.apache.org/). 


____ Deployment ____________________________________________________________________________________________________

- Dans les properties du dossier de projet > Project Facets s'assurer d'avoir :
	+ Dynamic Web Module 4.0
	+ Java 1.8
	+ JavaScript 1.0

- Créer un serveur Tomcat v9.0 en localhost et l'associer au projet. 
- Lancer le serveur avec Eclipse en faisant clic droit sur le "HomeServlet" puis choisir : Run as > Run on Server.

____ Technical Choices _____________________________________________________________________________________________
- Ajout d'un package validator contenant les méthodes de validation des inputs

- Ajout de fonctions get() dans Client(), Vehicle(), Reservation()
	* Client()
getListVehicle(Client client) : retourne la liste de véhicules associés aux réservations du client en question
	* Vehicle()
getListClient(Vehicle vehicle) : retourne la liste de clients associés aux réservations du véhicule en question
	* Reservation()
getVehicle() : retourne l'objet Vehicle associé à la reservation en question
getClient() : retourne l'objet Client associé à la reservation en question 

- Ajout de fichiers .jsp et Sevlets afin de permettre à l'utilisateur de modifier et d'afficher les détails
	* Modifier

		1) Une requête SQL a été créée dans les différentes classes Dao afin de permettre de faire un UPDATE de la table. 
L'utilisateur a la possibilité de changer tous les paramètres SAUF l'ID de l'objet en question. Cela permet de 
modifier uniquement les informations voulues tout en gardant le même ID et donc de ne pas perdre les 
jonctions avec les autres objets.
	Exemple : si l'utilisateur du site décide de modifier l'adresse email d'un client, une fois la modification effectuée, 
ID de ce client restera inchangée et le client sera donc toujours lié à ses réservations. 

		2) Pour les modifications d'un client et d'un véhicule, les informations avant modification seront affichées. 
Il est ainsi plus simple pour l'utilisateur de modifier seulement l'information voulue. Il n'a pas à renseigner 
une nouvelle fois tous les champs.

	* Details
	
		1) L'utilisateur peut avoir accès aux détails d'un client : afficher le nombre total de réservations et de véhicules liés au client choisi. Ainsi qu'au détails de ces réservations et aux détails de ces véhicules dans deux onglets séparés. 
		2) L'utilisateur peut avoir accès aux détails d'un véhicule : afficher le nombre total de réservations et de clients liés au véhicule choisi. Ainsi qu'au détails de ces réservations et aux détails de ces clients dans deux onglets séparés.
		3) L'utilisateur peut avoir accès aux détails d'une réservation : afficher les détails du véhicule et du client associé.

____ Fonctionalities _____________________________________________________________________________________________

- Basique : Ajouter, lister, modifier, afficher les détails et supprimer un client, un véhicule ou une réservation.
- Contraintes métiers :
	* un client n'ayant pas 18ans ne peut pas être créé
	* un client ayant une adresse mail déjà prise ne peut pas être créé
	* une voiture ne peux pas être réservé 2 fois le même jour
	* une voiture ne peux pas être réservé plus de 7 jours de suite par le même utilisateur
	* une voiture ne peux pas être réservé 30 jours de suite sans pause 
	* si un client ou un véhicule est supprimé, alors il faut supprimer les réservations associées
	* une voiture doit avoir un modèle et un constructeur, son nombre de place doit être compris entre 2 et 9
        
- Validation des entrées :
	* les entrées ne peuvent pas être vides
	* le nom et le prénom d'un client doivent faire au moins 3 caractères
	* la date de fin de réservation doit être supérieure à la date de début 
	* un email doit contenir le sigle @ 
        

____ Author ______________________________________________________________________________________________________
Cécile VANHELLEPUTTE - EPF - Majeure MIN - P2021
