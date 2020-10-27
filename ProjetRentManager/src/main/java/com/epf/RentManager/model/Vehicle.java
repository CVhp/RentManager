package com.epf.RentManager.model;

import java.util.ArrayList;
import java.util.List;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;

public class Vehicle {
    private long id_vehicle;
    private String constructeur;
    private String modele;
    private int nb_places;

    public Vehicle(long id, String constructeur, String modele, int nb_places) {
        this.id_vehicle = id;
        this.constructeur = constructeur;
        this.modele = modele;
        this.nb_places = nb_places;
    }

    public Vehicle() {
    }

    public long getId_Vehicle() {
        return id_vehicle;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public String getModele() {
        return modele;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setId_Vehicle(long id_vehicle) {
        this.id_vehicle = id_vehicle;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }
    
    public List<Client> getListClient(Vehicle vehicle){
    	List<Client> listClient = new ArrayList<>();
    	ClientService clientService = ClientService.getInstance(false);
    	ReservationService reservationService = ReservationService.getInstance(false);
    	Boolean unique = true;
    	try {
			List<Reservation> listResa = reservationService.findByIdVehicle(vehicle.getId_Vehicle());
			
			for(Reservation resa : listResa) {
				long id_client = resa.getClient_id();
				for (Client client : listClient) {
					if (client.getId() == id_client) {
						unique = false;
					}					
				}
				if (unique) {
					listClient.add(clientService.findById(id_client));
				}				
			}
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listClient;   	
    }

    @Override
    public String toString() {
    	return "Vehicle [id=" + id_vehicle + ", constructeur=" + constructeur + ", modele=" + modele + ", nb_places="
				+ nb_places + "]";
    }
}
