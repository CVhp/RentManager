package com.epf.RentManager.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;

public class Client {
    private long id;
    private String nom;
    private String prenom;
    private String email;
    private Date naissance;

    public Client() {
    }

    public Client(long id, String nom, String prenom, String email, Date naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public Date getNaissance() {
        return naissance;
    }

    public void setId(long id) { //à supprimer ? ----------------------------------------------------------------------------------------------------
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNaissance(Date naissance) {
        this.naissance = naissance;
    }
    
    public List<Vehicle> getListVehicle(Client client){
    	List<Vehicle> listVehicle = new ArrayList<>();
    	VehicleService vehicleService = VehicleService.getInstance(false);
    	ReservationService reservationService = ReservationService.getInstance(false);
    	Boolean unique = true;
    	try {
			List<Reservation> listResa = reservationService.findByIdClient(client.getId());
			
			for(Reservation resa : listResa) {
				long id_vehicle = resa.getVehicle_id();
				for (Vehicle vehicle : listVehicle) {
					if (vehicle.getId_Vehicle() == id_vehicle) {
						unique = false;
					}					
				}
				if (unique) {
					listVehicle.add(vehicleService.findById(id_vehicle));
				}				
			}
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listVehicle;   	
    }

    @Override
    public String toString() {
    	return "Client [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", naissance="
				+ naissance + "]";
    }
}
