package com.epf.RentManager.service;

import com.epf.RentManager.dao.VehicleDao;
import com.epf.RentManager.exception.DaoException;
import com.epf.RentManager.exception.ServiceException;
import com.epf.RentManager.model.Reservation;
import com.epf.RentManager.model.Vehicle;
import com.epf.RentManager.validator.VehicleValidator;

import java.util.List;
import java.util.Optional;

public class VehicleService {

	private VehicleDao vehicleDao;
	private boolean test;
	
	public static VehicleService instance;
	private static VehicleService instanceTest = null;

	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance(false);
	}
	
	private VehicleService(boolean test) {
		this.vehicleDao = VehicleDao.getInstance(test);
		this.test = test;
	}
	
	public static VehicleService getInstance(boolean test) {
		if(test){
			if(instanceTest == null) {
				instanceTest = new VehicleService(true);
			}
			return instanceTest;
		}else {
			if (instance == null) {
				instance = new VehicleService(false);
			}
			return instance;
		}
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		try{
			VehicleValidator.checkConstructeur(vehicle);
			VehicleValidator.checkModele(vehicle);
			checkNbPlaces(vehicle);
			return vehicleDao.create(vehicle);
		} catch(DaoException e){
			throw new ServiceException("Problème pour la création du véhicule : " + e.getMessage());
		}
	}
	
	public long delete(Vehicle vehicle) throws ServiceException{
		try{
			ReservationService reservationService = ReservationService.getInstance(false);
			try {
				List<Reservation> listReservation = reservationService.findByIdVehicle(vehicle.getId_Vehicle());
				for (Reservation reservationAssociee : listReservation) {
					reservationService.delete(reservationAssociee);
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return vehicleDao.delete(vehicle);
		}catch (DaoException e){
			throw new ServiceException("Problème pour la suppression du véhicule : " + e.getMessage());
		}
	}
	
	public long update(Vehicle vehicle) throws ServiceException {
		try{
			VehicleValidator.checkConstructeur(vehicle);
			VehicleValidator.checkModele(vehicle);
			checkNbPlaces(vehicle);
			return vehicleDao.update(vehicle);
		} catch(DaoException e){
			throw new ServiceException("Problème pour l'update du véhicule : " + e.getMessage());
		}
	}
	
	
	public Vehicle findById(long id) throws ServiceException {
		try{
			Optional<Vehicle> optVehicle = vehicleDao.findById(id);
			Vehicle v = new Vehicle();
			if(optVehicle.isPresent()){
				v = optVehicle.get();
				return v;
			} else {
				throw new ServiceException("ID n'existe pas");
			}			
		} catch (DaoException e){
			throw new ServiceException("Problème pour trouver le véhicule par ID :" + e.getMessage());
		}
	}
	
	public List<Vehicle> findAll() throws ServiceException {
		try{
			return vehicleDao.findAll();
		} catch (DaoException e){
			throw new ServiceException("Problème pour récupérer la liste des véhicules : " + e.getMessage());
		}
	}
	
	
	private void checkNbPlaces(Vehicle vehicle) throws ServiceException {
		if(vehicle.getNb_places() < 2 || vehicle.getNb_places()>9){
			throw new ServiceException("Le nombre de place du véhicule doit etre compris entre 2 et 9 inclus.");
		}
	}



	
}
