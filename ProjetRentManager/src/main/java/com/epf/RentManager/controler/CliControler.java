package com.epf.RentManager.controler;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Client;
import com.epf.RentManager.model.Reservation;
import com.epf.RentManager.model.Vehicle;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class CliControler {
    private ClientService clientService = ClientService.getInstance(false);
    private VehicleService vehicleService = VehicleService.getInstance(false);
    private ReservationService reservationService = ReservationService.getInstance(false);

    public static void main(String[] args){
    	CliControler cli = new CliControler();

        Scanner sc = new Scanner (System.in);
        boolean done = false;
        boolean doneFin = false;

        while (doneFin != true) {
        	System.out.println("Que voulez-vous faire?");
			System.out.println("1- Affiche la liste des opérations clients");
			System.out.println("2- Affiche la liste des opérations véhicules");
			System.out.println("3- Affiche la liste des opérations réservations");
			System.out.println("0- Quitter");

            int choixMode = sc.nextInt();
            sc.nextLine();

            switch (choixMode){
                case 0:
                    doneFin = true;
                    break;
                case 1:
                	done = false;
    				while (!done) {
    					System.out.println("Liste des opérations - Client");
    					System.out.println("1- Affiche la liste des clients");
    					System.out.println("2- Ajoute un client");
    					System.out.println("3- Supprime un client");
    					System.out.println("4- Recherche un client");
    					System.out.println("0- Retour");

	                    int choixClient = sc.nextInt();
	                    sc.nextLine();
	                    
	                    switch (choixClient) {
	                        case 0:
	                            done = true;
	                            break;
	                        case 1:
	                            printAllClients(cli);
	                            break;
	                        case 2:
	                            createClient(cli, sc);
	                            break;
	                        case 3:
	                        	deleteClient(cli, sc);
	                            break;
	                        case 4:
	                        	findClientByID(cli, sc);
	                            break;
	                        default:
	                            System.out.println("Pas le bon choix");
	                    }
                    }
                    break;
                case 2 :
                	done = false;
    				while (!done) {
    					System.out.println("Liste des opérations - Véhicule");
    					System.out.println("1- Affiche la liste des véhicules");
    					System.out.println("2- Ajoute un véhicule");
    					System.out.println("3- Supprime un véhicule");
    					System.out.println("4- Recherche un véhicule");
    					System.out.println("0- Retour");
    					
	                    int choixVehicle = sc.nextInt();
	                    sc.nextLine();
	                    
	                    switch (choixVehicle) {
	                        case 0:
	                            done = true;
	                            break;
	                        case 1:
	                            printAllVehicles(cli);
	                            break;
	                        case 2:
	                            createVehicle(cli, sc);
	                            break;
	                        case 3:
		                         deleteVehicle(cli, sc);
	                            break;
	                        case 4:
	                        	findVehcileByID(cli, sc);	                 
	                            break;
	                        default:
	                            System.out.println("Pas le bon choix");
	                    }
                    }
                    break;
                case 3 :
                	done = false;
    				while (!done) {
    					System.out.println("Liste des opérations - Réservation");
    					System.out.println("1- Affiche la liste des réservations");
    					System.out.println("2- Ajoute une réservation");
    					System.out.println("3- Supprime une réservation");
    					System.out.println("4- Recherche une réservation par ID client");
    					System.out.println("5- Recherche une réservation par ID véhicule");
    					System.out.println("0- Retour");
    					
	                    int choixReservation = sc.nextInt();
	                    sc.nextLine();
	
	                    switch (choixReservation) {
	                        case 0:
	                            done = true;
	                            break;
	                        case 1:
	                            printAllReservations(cli);
	                            break;
	                        case 2:
	                            createReservation(cli, sc);
	                            break;
	                        case 3:
	                            deleteReservation(cli, sc);
	                            break;
	                        case 4:
	                        	findReservationByIDClient(cli, sc);	                            
	                            break;
	                        case 5:
	                        	findReservationByIDVehicle(cli, sc);	                            
	                            break;
	                        default:
	                            System.out.println("Pas le bon choix");
	                    }
                    }

                    break;
                default:
                    System.out.println("Pas le bon choix de mode");
            }
        }
        sc.close();
    }

    
    
    
    
    private static void printAllClients(CliControler cli) {
        try{
            List<Client> list = cli.clientService.findAll();            
            for (Client client : list){
                System.out.println(client);
            }
        } catch (ServiceException e){
            System.out.println("Une erreur est survenue : "+ e.getMessage());
        }
    }
    
    private static void createClient(CliControler cli, Scanner sc) {
        Client client = new Client();

        System.out.println("Entrez le nom :");
        client.setNom(sc.nextLine());
        System.out.println("Entrez le prénom :");
        client.setPrenom(sc.nextLine());
        System.out.println("Entrez l'email :");
        client.setEmail(sc.nextLine());
        System.out.println("Entrez la date de naissance au format yyyy-[m]m-[d]d");
        client.setNaissance(Date.valueOf(sc.nextLine()));

        try {
            cli.clientService.create(client);
        } catch (ServiceException e) {
            e.printStackTrace(); //--------------------- System.out.println("Une erreur est survenue: "+ e.getMessage());
        }
    }
    
    
    
    private static void deleteClient(CliControler cli, Scanner sc) {
    	Client client = new Client();
		System.out.println("Entrer l'ID du client à supprimer : ");
		client.setId(sc.nextInt());
		sc.nextLine();
    	
        try {          
            cli.clientService.delete(client);
        } catch (ServiceException e){
            e.printStackTrace(); //--------------------- System.out.println("Une erreur est survenue: "+ e.getMessage());
        }
    }

    private static void findClientByID(CliControler cli, Scanner sc) {
    	System.out.println("Entrez l'id du client à rechercher : ");
    	try{            
            Client client = cli.clientService.findById((long)sc.nextInt());
            System.out.println(client);
        } catch (ServiceException e){
            e.printStackTrace();
        }
    }

    


    private static void printAllVehicles(CliControler cli) {
        try{
            List<Vehicle> list = cli.vehicleService.findAll();
            for (Vehicle vehicle : list){
                System.out.println(vehicle.toString());
            }
        } catch (ServiceException e){
            System.out.println("Une erreur est survenue : "+ e.getMessage());
        }
    }

    private static void createVehicle(CliControler cli, Scanner sc) {
        Vehicle vehicle = new Vehicle();
        
        System.out.println("Entrez le constructeur : ");
        vehicle.setConstructeur(sc.nextLine());
        System.out.println("Entrez le modele : ");
        vehicle.setModele(sc.nextLine());
        System.out.println("Entrez le nombre de places : ");
        vehicle.setNb_places(sc.nextInt());

        try {
            cli.vehicleService.create(vehicle);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private static void deleteVehicle(CliControler cli, Scanner sc) {
    	Vehicle vehicle = new Vehicle();
    	System.out.println("Entrez l'ID du véhicule à supprimer : ");
        vehicle.setId_Vehicle(sc.nextInt());
        
        try {            
            cli.vehicleService.delete(vehicle);
        }catch (ServiceException e){
            e.printStackTrace();
        }
    }
    
    private static void findVehcileByID(CliControler cli, Scanner sc) {
    	System.out.println("Entrez l'ID du véhicule à rechercher : ");
    	try{            
            Vehicle vehicle = cli.vehicleService.findById((long)sc.nextInt());
            System.out.println(vehicle.toString());
        } catch (ServiceException e){
            e.printStackTrace();
        }
    }
    
    
    
    
    private static void printAllReservations(CliControler cli) {
        try{
            List<Reservation> list = cli.reservationService.findAll();
            for (Reservation reservation : list){
                System.out.println(reservation.toString());
            }
        } catch (ServiceException e){
            System.out.println("Une erreur est survenue : "+ e.getMessage());
        }
    }
    
    private static void createReservation(CliControler cli, Scanner sc) {
        Reservation reservation = new Reservation();
        
        System.out.println("Entrez l'ID du client : ");
        reservation.setClient_id(sc.nextLong());
        sc.nextLine();
        System.out.println("Entrez l'ID du véhicule : ");
        reservation.setVehicle_id(sc.nextLong());
        sc.nextLine();
        System.out.println("Entrez la date de début au format yyyy-[m]m-[d]d : ");
        reservation.setDebut(Date.valueOf(sc.nextLine()));
        System.out.println("Entrez la date de fin au format yyyy-[m]m-[d]d : ");
        reservation.setFin(Date.valueOf(sc.nextLine()));
        try {
            cli.reservationService.create(reservation);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    private static void deleteReservation(CliControler cli, Scanner sc) {
    	Reservation reservation = new Reservation();
    	System.out.println("Entrez l'id de la réservation à supprimer : ");
        reservation.setReservation_id(sc.nextInt());
        sc.nextLine();
        
        try {            
            cli.reservationService.delete(reservation);
        }catch (ServiceException e){
            e.printStackTrace();
        }
    }
  
    private static void findReservationByIDClient(CliControler cli, Scanner sc) {
    	System.out.println("Entrez l'ID du client pour voir ses réservations : ");
    	try{            
            List<Reservation> list = cli.reservationService.findByIdClient((long)sc.nextInt());
            sc.nextLine();
            for (Reservation reservation : list){
                System.out.println(reservation.toString());
            }
        } catch (ServiceException e){
            e.printStackTrace();
        }
    }

    private static void findReservationByIDVehicle(CliControler cli, Scanner sc) {
    	System.out.println("Entrez l'ID du véhicule pour voir ses réservations : ");
    	try{            
            List<Reservation> list = cli.reservationService.findByIdVehicle((long)sc.nextInt());
            sc.nextLine();
            for (Reservation reservation : list){
                System.out.println(reservation.toString());
            }
        } catch (ServiceException e){
            e.printStackTrace();
        }
    }
  

}
