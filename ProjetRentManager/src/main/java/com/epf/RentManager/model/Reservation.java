package com.epf.RentManager.model;

import java.sql.Date;

import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.VehicleService;

public class Reservation implements Comparable<Reservation>{
    private long reservation_id;
    private long client_id;
    private long vehicle_id;
    private Date debut;
    private Date fin;

    public Reservation(long reservation_id, long client_id, long vehicle_id, Date debut, Date fin) {
        this.reservation_id = reservation_id;
        this.client_id = client_id;
        this.vehicle_id = vehicle_id;
        this.debut = debut;
        this.fin = fin;
    }

    public Reservation() {
    }

    
    //---------------------------------------------------------------------------------------------------------------------------------
    public long getReservation_id() {
        return reservation_id;
    }

    public long getClient_id() {
        return client_id;
    }
    
    public long getVehicle_id() {
        return vehicle_id;
    }
    
    public Date getDebut() {
        return debut;
    }

    public Date getFin() {
        return fin;
    }
    //---------------------------------------------------------------------------------------------------------------------------------
    
    public void setReservation_id(long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }

    public void setVehicle_id(long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    
    
    
    
    public String getClient_name() {
    	Client client = new Client();
    	ClientService clientService = ClientService.getInstance(false);
    	
		try {
			client = clientService.findById(this.client_id);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client.getNom();
    }
    
    public Vehicle getVehicle() {
    	Vehicle vehicule = new Vehicle();
    	VehicleService vehicleService = VehicleService.getInstance(false);
    	
		try {
			vehicule = vehicleService.findById(this.vehicle_id);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vehicule;
    }
    
    public Client getClient() {
    	Client client = new Client();
    	ClientService clientService = ClientService.getInstance(false);
    	
		try {
			client = clientService.findById(this.client_id);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
    }
    
    
    public String getVehicule_constructeur() {
    	Vehicle vehicule = new Vehicle();
    	VehicleService vehicleService = VehicleService.getInstance(false);
    	
		try {
			vehicule = vehicleService.findById(this.vehicle_id);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vehicule.getConstructeur();
    }
    
    @Override
    public String toString() {
    	return "Reservation [reservation_id=" + reservation_id + ", client_id=" + client_id + ", vehicle_id=" + vehicle_id + ", debut="
				+ debut + ", fin=" + fin + "]";
    }

	@Override
	public int compareTo(Reservation o) {
		return getDebut().compareTo(o.getFin());
	}
}
